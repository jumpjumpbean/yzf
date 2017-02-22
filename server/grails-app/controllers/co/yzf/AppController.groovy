package co.yzf
import grails.converters.JSON
import grails.transaction.Transactional
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile

class AppController {
    def passwordEncoder
    def beforeInterceptor = [action:this.&check, except:[
            'index',
            'check',
            'login',
            'logout'
    ]]
    def index(){
        render "app index"
    }
    def check() {
        if(params.token != null && session[params.token] != null){
            return true
        }
        render([result:0,msg: "无权访问"] as JSON)
        return false
    }

    def login(){
        if(params.account == null || params.password == null){
            render([result:0,token:null,msg: "用户名或密码为空"] as JSON)
            return
        }
        def user= User.findByAccount(params.account)
        if(user == null){
            render([result:0,token:null,msg: "用户不存在"] as JSON)
            return
        }
        if(user.password == passwordEncoder.encodePassword(params.password,params.account)){
            def token = session.id
            session[token] = user
            render([result:1,token: token,userId:user.id,msg: "登陆成功",auth:user.getAuthorities().collect{it.authority} - "ROLE_USER"] as JSON)
        }
    }

    def logout(){
        if(params.token != null){
            session[params.token] = null
        }
        render([result:1,msg: "成功退出"] as JSON)
    }

    def project(){
        def curUser = session[params.token]
        if(curUser != null){
            def project
            def auths = curUser.authorities?.toString()
            if(auths == null || "[]".equals(auths)){
                render([result:0,msg: "不存在工程"] as JSON)
                return;
            }else {
                if (auths.indexOf("ROLE_ADMIN") >= 0 || auths.indexOf("ROLE_SUPER,") >= 0 || auths.indexOf("ROLE_SUPER]") >= 0) {
                    project = Project.findAllByIsDeleted(false)
                } else if (auths.indexOf("ROLE_SUPERVISOR") >= 0 || auths.indexOf("ROLE_QC") >= 0) {
                    project = Process.executeQuery("select distinct p.project from Process p where (p.charger=? or p.project.qc=?) and p.project.isDeleted=false", [curUser, curUser])
                } else if (auths.indexOf("ROLE_CUSTOMER") > 0) {
                    project = Project.findAllByUserAndIsDeleted(curUser,false)
                }
                if (project != null && project.size() > 0) {
                    render(contentType: "text/json") {
                        [
                            result : 1,
                            project: project.collect() {
                                [
                                    id           : it.id,
                                    customer     : it.customer,
                                    qc           : it.qc?.uname,
                                    supervisor   : it.responser?.uname,
                                    contractNo   : it.contractNo,
                                    address      : it.address,
                                    planStartDate: it.planStartDate,
                                    planEndDate  : it.planEndDate,
                                    realStartDate: it.realStartDate,
                                    realEndDate  : it.realEndDate,
                                    status:it.status,
                                    curProcess   : it.status != 2 ? [] : Process.findByProjectAndStatus(it,2).collect(){[
                                        id:it.id,
                                        name:it.period.name,
                                        planStartDate:it.planStartDate,
                                        planEndDate:it.planEndDate,
                                        realStartDate: it.realStartDate
                                        ]
                                    }
                                ]
                            }]
                    };
                    return
                }
            }
            render([result:0,msg: "不存在工程"] as JSON)
            return
        }else{
            render([result:0,msg: "不存在工程"] as JSON)
            return
        }
    }

    def process(){
        if(params.projectId == null){
            render([result:0,msg: "请提供工程Id"] as JSON)
        }
        def curUser = session[params.token]
        if(curUser != null){
            def project = Project.get(params.long("projectId"))
            if(project != null){
                render(contentType:"text/json"){[
                    result:1,
                    process:Process.findAllByProject(project).collect(){[
                        id:it.id,
                        period:it.period?.name,
                        status:it.status,
                        supervisorId:it.charger.id,
                        supervisor:it.charger.uname,
                        qcId:project.qc.id,
                        qcName:project.qc.uname,
                        planStartDate:it.planStartDate,
                        planEndDate:it.planEndDate,
                        realStartDate:it.realStartDate,
                        realEndDate:it.realEndDate,
                        attitude:it.attitude,
                        hasMaterial:it.period?.hasMaterial,
                        memo:it.memo
                    ]}]
                };
                return
            }else{
                render([result:0,msg: "提供的工程Id不正确"] as JSON)
            }
        }
    }

    def pics(){
        if(params.procstepid == null){
            render([result: 0,msg: "请提供过程步骤Id"] as JSON)
            return
        }
        def curUser = session[params.token]
        if(curUser != null){
            def psId = params.long("procstepid")
            ProcessStep procstep = ProcessStep.get(psId)
            if(procstep != null) {
                def process = procstep.process
                println process
                def sgroup = procstep.step.sgroup
                def pics
                if(sgroup == 0){
                    pics = Pic.findAllByProcessIdAndIsDeletedAndIsMaterial(process.id.toString(), false,true)
                }else{
                    pics = Pic.findAllByProcessIdAndIsDeletedAndIsMaterial(process.id.toString(), false,false)
                }
                def picRoot = request.getContextPath()
                if (pics != null && pics.size() > 0) {
                    render(contentType: "text/json") {
                        [
                            result: 1,
                            oplog : pics.collect() {
                                [
                                    id : it.id,
                                    pic: picRoot + "/upld/" + it.picPath + "/" + it.picName
                                ]
                        }]
                    };
                    return
                } else {
                    render([result: 0, msg: "此阶段没有图片"] as JSON)
                    return
                }
            }else{
                render([result: 0,msg: "过程步骤Id不正确"] as JSON)
                return
            }
        }
    }

    def material(){
        if(params.processId == null){
            render([msg: "请提供过程Id"] as JSON)
        }
        def curUser = session[params.token]
        if(curUser != null){
            def process = Process.get(params.long("processId"))
            if(process != null) {
                def mtrl = ProjectMaterial.findAllByProcessAndIsDeleted(process, false)
               if (mtrl != null && mtrl.size() > 0) {
                    render(contentType: "text/json") {[
                        result: 1,
                        oplog : mtrl.collect() {
                            [
                                id : it.id,
                                period: it.process.period.name,
                                name:it.material.name,
                                type:it.material.type,
                                cnt:it.cnt,
                                memo:it.memo,
                                unit:it.material.unit,
                                brand:it.material.brand,
                                model:it.material.modelno
                            ]
                    }]};
                    return
                } else {
                    render([result: 0, msg: "此阶段没有材料"] as JSON)
                    return
                }
            }else{
                render([result: 0, msg: "请指定processId"] as JSON)
                return
            }
        }
    }

    def allmaterial(){
        if(params.projectId == null){
            render([msg: "请提供工程Id"] as JSON)
        }
        def curUser = session[params.token]
        if(curUser != null){
            def project = Project.get(params.long("projectId"))
            if(project != null) {
                def mtrl = ProjectMaterial.findAllByProjectAndIsDeleted(project, false)
                if (mtrl != null && mtrl.size() > 0) {
                    render(contentType: "text/json") {[
                            result: 1,
                            oplog : mtrl.collect() {
                                [
                                        id : it.id,
                                        period: it.process.period.name,
                                        name:it.material.name,
                                        type:it.material.type,
                                        cnt:it.cnt,
                                        memo:it.memo,
                                        unit:it.material.unit,
                                        brand:it.material.brand,
                                        model:it.material.modelno
                                ]
                            }]};
                    return
                } else {
                    render([result: 0, msg: "此工程还没添加任何材料"] as JSON)
                    return
                }
            }else{
                render([result: 0, msg: "请指定工程Id"] as JSON)
                return
            }
        }
    }

    def procstep(){
        if(params.processId == null){
            render([msg: "请提供过程Id"] as JSON)
        }
        def curUser = session[params.token]
        if(curUser != null){
            def process = Process.get(params.long("processId"))
            if(process != null){
                render(contentType:"text/json"){[
                        result:1,
                        procstep:ProcessStep.findAllByProcess(process).collect(){[
                                id:it.id,
                                stepId:it.step?.id,
                                stepName:it.step.name,
                                status:it.status,
                                sgroup:it.sgroup,
                                memo:it.memo,
                                attitude:it.attitude,
                                dateUpdated:it.dateUpdated
                        ]}]
                };
                return
            }else{
                render([result:0,msg: "提供的过程Id不正确"] as JSON)
            }
        }
    }

    /**
     * 取得过程的操作详细记录
     * @return
     */
    def oplogs(){
        if(params.processId == null){
            render([msg: "请提供过程Id"] as JSON)
        }
        def curUser = session[params.token]
        if(curUser != null){
            def process = Process.get(params.long("processId"))
            if(process != null){
                render(contentType:"text/json"){[
                    result:1,
                    oplog:Plog.findAllByProcess(process).collect(){[
                            id:it.id,
                            operater:it.operator?.uname,
                            status:it.status,
                            memo:it.memo,
                            attitude:it.attitude,
                            dateCreated:it.dateCreated
                    ]}]
                };
                return
            }else{
                render([result:0,msg: "提供的过程Id不正确"] as JSON)
            }
        }
    }

    @Transactional
    def upload(){
        if (!request instanceof MultipartHttpServletRequest) {
            render([result:0,msg: "系统故障，没有启动上传模式"] as JSON)
            return
        }
        def curUser = session[params.token]
        if(curUser != null) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request
            CommonsMultipartFile orginalFile = (CommonsMultipartFile) multiRequest.getFile("Filedata")
            // 判断是否上传文件
            if (orginalFile == null || orginalFile.isEmpty()) {
                render([result: 0, msg: "没有选择上传文件或者上传文件内容为空，请检查"] as JSON)
                return
            } else {
                if(params.procstepid != null){
                    ProcessStep procstep = ProcessStep.get(params.long("procstepid"))
                    if(procstep != null){
                        def progress = procstep.process
                        def imgRootPath = grailsApplication.config.grails.filePath.imagePath
                        def relativePath = progress.project.id + "/"+progress.period.id
                        def fullPath = imgRootPath + relativePath
                        def f = new File(imgRootPath + relativePath)
                        if(!f.exists()) {
                            f.mkdirs()
                        }
                        def originame = orginalFile.fileItem.name
                        def fileNameWithoutPath = System.currentTimeMillis() + CommonUtil.randomNum(10000) + originame.substring(originame.lastIndexOf("."))
                        orginalFile.transferTo(new File(fullPath+"/"+fileNameWithoutPath))
                        def pic = new Pic()
                        pic.processId = progress.id.toString()
                        pic.picName = fileNameWithoutPath
                        pic.picPath = relativePath
                        pic.uploader = curUser
                        if(procstep.step.sgroup == 0){
                            pic.isMaterial = true
                        }
                        if(!pic.save(flush:true)){
                            println(pic.errors)
                        }
                        if(Plog.list([sort: "id",order: "desc",max: 1])?.get(0).status != 2){
                            if(procstep.step.sgroup == 0) {
                                def plog = new Plog([process: progress,ps:procstep, status: 2, operator: curUser, memo: "上传材料照片"])
                                plog.save(flush:true)
                            }else{
                                def plog = new Plog([process: progress,ps:procstep, status: 2, operator: curUser, memo: "上传施工照片"])
                                plog.save(flush:true)
                            }
                        }
                    }
                }
                render([result: 1, msg: "上传成功"] as JSON)
                return
            }
        }
    }


    //申请验收
    @Transactional
    def applyExamine(){
        if (params.procstepid == null) {
            render([result:0,msg: "请提供过程步骤Id:procstepid"] as JSON)
            return
        }
        def curUser = session[params.token]
        def psId = params.long("procstepid")
        ProcessStep procstep = ProcessStep.get(psId)
        if(procstep != null){
            ProcessStep.executeUpdate("update ProcessStep set status=3 where id=?",[psId])
            def nextStep = ProcessStep.executeQuery("from ProcessStep p where p.process=? and p.step.sortNo>?",[procstep.process,procstep.step.sortNo],[max:1])
            if(nextStep != null && nextStep.size() > 0){
                def ns = nextStep.get(0)
                if(ns.status == 1){
                    ProcessStep.executeUpdate("update ProcessStep set status=2 where id=?",[ns.id])
                }
            }
            if(procstep.step.sgroup == 0){
                def plog = new Plog([process:procstep.process,ps:procstep,status:3,operator:curUser,memo:"申请材料进场审核"])
                plog.save(flush:true)
            }else{
                def plog = new Plog([process:procstep.process,ps:procstep,status:3,operator:curUser,memo:"申请施工审核"])
                plog.save(flush:true)
            }
        }
        render([result:1,msg: "申请成功"] as JSON)
        return
    }

    //删除图片
    @Transactional
    def deletePic(){
        def bb = params.get("picids[]")?.toString()
        if(bb == null || "[]".equals(bb)){
            render([result:0,msg: "请提供要删除的图片Id"] as JSON)
            return
        }else{
            if(!(auths.indexOf("ROLE_ADMIN") >= 0 || auths.indexOf("ROLE_SUPER,") >= 0 || auths.indexOf("ROLE_SUPER]") >= 0)){
                render([result:0,msg: "你无权进行此操作"] as JSON)
                return
            }
            if(bb.indexOf(",") > 0){
                bb = bb.substring(1,bb.length()-1)
            }
            bb = bb.toLowerCase()
            bb.replaceAll("'","");
            bb.replaceAll(" or ","");
            def db = new groovy.sql.Sql (dataSource)
            db.execute("update pic set is_deleted = true where id in("+bb+")");
            render([result:1,msg: "删除成功"] as JSON)
            return
        }
    }

    //状态更新 审核失败、通过
    @Transactional
    def updateStatus(){
        if (params.procstepid == null) {
            render([result:0,msg: "请提供过程步骤Id:procstepid"] as JSON)
            return
        }
        def status = params.int("status")
        def curUser = session[params.token]
        if(status != 4 && status != 5 && status != 6){
            render([result:0,msg: "参数不正确"] as JSON)
            return
        }
        def procstepid = params.long("procstepid")
        def procstep = ProcessStep.get(procstepid)
        if(procstep != null){
            if(status == 4){//拒绝通过
                ProcessStep.executeUpdate("update ProcessStep set status=1 where process=? and sgroup=?",[procstep.process,procstep.sgroup])
                def nextStep = ProcessStep.executeQuery("from ProcessStep p where p.process=? and p.step.sortNo<? order by p.step.sortNo desc",[procstep.process,procstep.step.sortNo],[max:1])
                if(nextStep != null && nextStep.size() > 0){
                    def ns = nextStep.get(0)
                    if(ns.status == 1){
                        ProcessStep.executeUpdate("update ProcessStep set status=2 where id=?",[ns.id])
                    }
                }
                if(procstep.sgroup == 0){
                    def plog = new Plog([process:procstep.process,ps:procstep,status:status,operator:curUser,memo:"材料质检审核:驳回"])
                    plog.save(flush:true)
                }else{
                    def plog = new Plog([process:procstep.process,ps:procstep,status:status,operator:curUser,memo:"施工质检审核:驳回"])
                    plog.save(flush:true)
                }
            }else{//通过
                ProcessStep.executeUpdate("update ProcessStep set status=3 where id=?",[procstepid])
                def nextStep = ProcessStep.executeQuery("from ProcessStep p where p.process=? and p.step.sortNo>?",[procstep.process,procstep.step.sortNo],[max:1])
                if(nextStep != null && nextStep.size() > 0){
                    def ns = nextStep.get(0)
                    if(ns.status == 1){
                        ProcessStep.executeUpdate("update ProcessStep set status=2 where id=?",[ns.id])
                    }
                }
                if(status == 5){
                    if(procstep.sgroup == 0){
                        def plog = new Plog([process:procstep.process,ps:procstep,status:status,operator:curUser,memo:"材料质检审核:通过"])
                        plog.save(flush:true)
                    }else{
                        def plog = new Plog([process:procstep.process,ps:procstep,status:status,operator:curUser,memo:"施工质检审核:通过"])
                        plog.save(flush:true)
                    }
                }else{//图片整理
                    if(procstep.sgroup == 0){
                        def plog = new Plog([process:procstep.process,ps:procstep,status:status,operator:curUser,memo:"材料照片整理"])
                        plog.save(flush:true)
                    }else{
                        def plog = new Plog([process:procstep.process,ps:procstep,status:status,operator:curUser,memo:"施工照片整理"])
                        plog.save(flush:true)
                    }
                }
            }
        }
        render([result:1,msg: "更新成功"] as JSON)
        return
    }

    @Transactional
    def doverify(){
        if (params.procstepid == null) {
            render([result:0,msg: "请提供过程步骤Id:procstepid"] as JSON)
            return
        }
        def procstepid = params.long("procstepid")
        def procstep = ProcessStep.get(procstepid)
        def curUser = session[params.token]
        if(procstep != null && curUser != null) {
            if(procstep.status != 2){
                render([result:0,msg: "状态不正确，还不能执行此操作"] as JSON)
                return
            }
            def attitude = params.int("attitude")
            def memo = CommonUtil.trimStr(params.memo)
            if(memo != null){
                memo = CommonUtil.getAttitude(attitude) + ",留言："+memo
            }else{
                memo = CommonUtil.getAttitude(attitude)
            }
            def status = 8
            if (attitude == 1) {
                status = 7
            }
            def process = procstep.process
            def auths = curUser?.authorities?.toString()
            if(!(curUser.id != process.project?.user?.id) &&
                    !(auths.indexOf("ROLE_ADMIN") >= 0 || auths.indexOf("ROLE_SUPER,") >= 0 || auths.indexOf("ROLE_SUPER]") >= 0)){
                render([result:0,msg: "你无权进行此操作"] as JSON)
                return
            }
            def processId = process.id

            if (status == 8) {
                ProcessStep.executeUpdate("update ProcessStep set attitude=?,memo=?,status=3 where id=?",[attitude,memo,procstepid])
                //更新下一个阶段的实际开始时间
                int sortNo = procstep.step.sortNo
                if(sortNo == 3){
                    Process.executeUpdate("update Process set attitude=?,memo=?,realEndDate=? where id=?", [attitude, memo, new Date(), processId])
                    def nextStep = ProcessStep.executeQuery("from ProcessStep p where p.process=? and p.step.sortNo>?",[procstep.process,procstep.step.sortNo],[max:1])
                    if(nextStep != null && nextStep.size() > 0){
                        def ns = nextStep.get(0)
                        if(ns.status == 1){
                            ProcessStep.executeUpdate("update ProcessStep set status=2 where id=?",[ns.id])
                        }
                    }
                }else{
                    Process.executeUpdate("update Process set attitude=?,memo=?,status=3,realEndDate=? where id=?", [attitude, memo, new Date(), processId])
                    def nextProc = Process.executeQuery("from Process where project=? and id > ?",[process.project,processId],[max: 1])
                    if(nextProc != null && nextProc.size() > 0){
                        def nextP = nextProc.get(0)
                        nextP.status = 2
                        nextP.realStartDate = new Date()
                        nextP.save(flush:true)
                        def nextStep = ProcessStep.executeQuery("from ProcessStep p where p.process.id=?",[nextP.id],[sort:"p.step.sortNo",max:1])
                        if(nextStep != null && nextStep.size() > 0){
                            def ns = nextStep.get(0)
                            if(ns.status == 1){
                                ProcessStep.executeUpdate("update ProcessStep set status=2 where id=?",[ns.id])
                            }
                        }
                    }else{
                        Project.executeUpdate("update Project set status=3 where id=?",[process.project.id])
                    }
                }
            } else {
                ProcessStep.executeUpdate("update ProcessStep set status=1 where process=? and sgroup=?",[process,procstep.sgroup])
                Process.executeUpdate("update Process set attitude=?,memo=? where id=?", [attitude, memo, processId])
                def nextStep = ProcessStep.executeQuery("from ProcessStep p where p.process=? and p.step.sortNo=?",[procstep.process,procstep.step.sortNo-3],[max:1])
                if(nextStep != null && nextStep.size() > 0){
                    def ns = nextStep.get(0)
                    if(ns.status == 1){
                        ProcessStep.executeUpdate("update ProcessStep set status=2 where id=?",[ns.id])
                    }
                }
            }
            def plog = new Plog([process: process,ps:procstep, status: status, operator: curUser, attitude: attitude, memo: "客户验收 " + memo])
            plog.save(flush: true)
            render([result:1,msg: "更新成功"] as JSON)
            return
        }else{
            render([result:0,msg: "提供的参数不正确"] as JSON)
        }
        return
    }

    def todos(){
        def curUser = session[params.token]
        if(curUser != null){
            def auths = curUser.authorities?.toString()
            if(auths == null || "[]".equals(auths)){
                render([result:0,msg: "你无权查看任何消息"] as JSON)
                return;
            }else {
                def todos
//                def c = ProcessStep.createCriteria()
                if (auths.indexOf("ROLE_SUPER,") >= 0||auths.indexOf("ROLE_SUPER]") >= 0) {
                    todos = []
                }else if (auths.indexOf("ROLE_ADMIN") >= 0) {
                    todos = ProcessStep.executeQuery("from ProcessStep ps where ps.step.sortNo in(2,6,10) and ps.status=2",[],[sort:"dateUpdated",order:'desc',max:20])
//                    c.list (max: 20) {
//                        and {
//                            'in'("step.sortNo", [2,6,10])
//                            eq("status", "2")
//                        }
//                        order("dateUpdated", "desc")
//                    }
                } else if (auths.indexOf("ROLE_SUPERVISOR") >= 0) {
                    todos = ProcessStep.executeQuery("from ProcessStep ps where ps.step.sortNo in(0,4,8) and ps.status=2 and ps.process.status=2 and ps.process.charger=?",[curUser],[sort:"dateUpdated",order:'desc',max:20])
//                    todos = c.list (max: 20) {
//                        and {
//                            'in'("step.sortNo", [0,4,8])
//                            eq("status", "2")
//                            eq('process.status',2)
//                            eq('process.charger',curUser)
//                        }
//                        order("dateUpdated", "desc")
//                    }
                }else if (auths.indexOf("ROLE_QC") >= 0) {
//                    def project = Project.findAllByQcAndIsDeleted(curUser,false)
                    todos = ProcessStep.executeQuery("from ProcessStep ps where ps.step.sortNo in(1,5,9) and ps.status=2 and ps.process.status=2 and ps.process.project.qc=?",[curUser],[sort:"dateUpdated",order:'desc',max:20])
//                    todos = c.list (max: 20) {
//                        and {
//                            'in'("step.sortNo", [1,5,9])
//                            'in'("process.project", project)
//                            eq("status", "2")
//                            eq('process.status',2)
//                        }
//                        order("dateUpdated", "desc")
//                    }
                } else if (auths.indexOf("ROLE_CUSTOMER") > 0) {
                    todos = ProcessStep.executeQuery("from ProcessStep ps where ps.step.sortNo in(3,7,11) and ps.status=2 and ps.process.status=2 and ps.process.project.user=?",[curUser],[sort:"dateUpdated",order:'desc',max:20])
//                    def project = Project.findAllByUserAndIsDeleted(curUser,false)
//                    if(project != null && project.size() > 0){
//                        todos = c.list (max: 20) {
//                            and {
//                                'in'("step.sortNo", [3,7,11])
//                                'in'("process.project", project)
//                                eq("status", "2")
//                                eq('process.status',2)
//                            }
//                            order("dateUpdated", "desc")
//                        }
//                    }
                }
                if (todos != null && todos.size() > 0) {
                    render(contentType: "text/json") {[
                        result : 1,
                        todos: todos.collect() {
                            [
                                stepId       : it.id,
                                stepName     : it.step.name,
                                processId    : it.process.id,
                                processName  : it.process.period.name,
                                projectId    : it.process.project?.id,
                                customer     : it.process.project.customer,
                                address      : it.process.project.address,
                                contractNo   : it.process.project.contractNo
                            ]
                        }]
                    };
                    return
                }
            }
            render([result:0,todos:[],msg: "没有待办事项"] as JSON)
            return
        }else{
            render([result:0,todos:[],msg: "没有待办事项"] as JSON)
            return
        }
    }
}
