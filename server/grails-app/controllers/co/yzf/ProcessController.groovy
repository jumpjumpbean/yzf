package co.yzf

import grails.converters.JSON
import grails.transaction.Transactional
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile

@Transactional(readOnly = true)
class ProcessController {
    def springSecurityService
    def dataSource
    def index(Integer max) {
        render "index"
    }
    //修改计划日期
    @Transactional
    def addPlanDate() {
        if (params.processId == null) {
            render "error";
            return
        }

        Process.executeUpdate("update Process set planStartDate=?,planEndDate=?,status=2 where id=?",[CommonUtil.parseDate(params.startDate),CommonUtil.parseDate(params.endDate),params.long("processId")])
        render "ok";
    }

    def schedule(){
        if (params.id == null) {
            render "error";
            return
        }
        render( view: "/project/schedule",model: [projectId:params.id,processList:Process.findAllByProject(Project.get(params.id))])
    }

    //申请验收
    @Transactional
    def applyExamine(){
        if (params.procstepid == null) {
            render "error";
            return
        }
        def psId = params.long("procstepid")
        ProcessStep procstep = ProcessStep.get(psId)
        if(procstep != null){
            ProcessStep.executeUpdate("update ProcessStep set status=3 where id=?",[psId])
            def nextStep = ProcessStep.executeQuery("from ProcessStep p where p.process=? and p.step.sortNo>?",[procstep.process,procstep.step.sortNo],[max:1])
            if(nextStep != null && nextStep.size() > 0){
                def ns = nextStep.get(0)
                if(ns.status == 1){
                    println(ns)
                    ProcessStep.executeUpdate("update ProcessStep set status=2 where id=?",[ns.id])
                }
            }
            if(procstep.step.sgroup == 0){
                def plog = new Plog([process:procstep.process,ps:procstep,status:3,operator:springSecurityService.currentUser,memo:"申请材料进场审核"])
                plog.save(flush:true)
            }else{
                def plog = new Plog([process:procstep.process,ps:procstep,status:3,operator:springSecurityService.currentUser,memo:"申请施工审核"])
                plog.save(flush:true)
            }
        }
        render "ok";
    }
    //验收
    def examing(){
        if(params.id == null){
            redirect controller: "project", action: "index", method: "GET"
        }else{
            def procstep = ProcessStep.get(params.id)
            if(procstep != null){
                def process = procstep.process
                def picList = null
                if(procstep.step.sgroup == 0) {
                    picList = Pic.executeQuery("from Pic p where p.processId=? and isDeleted = false and isMaterial=true",[String.valueOf(process.id)])
                }else{
                    picList = Pic.executeQuery("from Pic p where p.processId=? and isDeleted = false and isMaterial=false",[String.valueOf(process.id)])
                }
                render(view: "/project/examing", model: [procstepid: params.id, process: process, material: ProjectMaterial.findAllByProcessAndIsDeleted(process, false), picList: picList])
            }else{
                flash.message="没有相关的审核阶段"
                render (view: "/project/examing")
            }
        }
    }

    //验收
    def picedit(){
        if(params.id == null){
            redirect controller: "project", action: "index", method: "GET"
        }else{
            def procstep = ProcessStep.get(params.id)
            def picList = null
            if(procstep != null){
                def process=procstep.process
                if(procstep.step.sgroup == 0) {
                    picList = Pic.executeQuery("from Pic p where p.processId=? and isDeleted = false and isMaterial=true",[process.id.toString()])
                }else{
                    picList = Pic.executeQuery("from Pic p where p.processId=? and isDeleted = false and isMaterial=false",[process.id.toString()])
                }
            }
            render (view: "/project/picEdit",model: [procstepid:params.id,picList:picList])
        }
    }

    //删除图片
    @Transactional
    def deletePic(){
        def bb = params.get("picids[]")?.toString()
       if(bb == null){
           render "error";
           return
       }else{
           if(bb.indexOf(",") > 0){
               bb = bb.substring(1,bb.length()-1)
           }
           bb = bb.toLowerCase()
           bb.replaceAll("'","");
           bb.replaceAll(" or ","");
           def db = new groovy.sql.Sql (dataSource)
           db.execute("update pic set is_deleted = true where id in("+bb+")");
           render "ok";
       }
    }
    //状态更新 审核失败、通过
    @Transactional
    def updateStatus(){
        if (params.procstepid == null) {
            render "error";
            return
        }
        def status = params.int("status")
        if(status != 4 && status != 5 && status != 6){
            render "error";
            return
        }
        def procstepid = params.long("procstepid")
        def procstep = ProcessStep.get(procstepid)
        if(procstep != null){
            if(status == 4){
                ProcessStep.executeUpdate("update ProcessStep set status=1 where process=? and sgroup=?",[procstep.process,procstep.sgroup])
                def nextStep = ProcessStep.executeQuery("from ProcessStep p where p.process=? and p.step.sortNo<? order by p.step.sortNo desc",[procstep.process,procstep.step.sortNo],[max:1])
                if(nextStep != null && nextStep.size() > 0){
                    def ns = nextStep.get(0)
                    if(ns.status == 1){
                        ProcessStep.executeUpdate("update ProcessStep set status=2 where id=?",[ns.id])
                    }
                }
                if(procstep.sgroup == 0){
                    def plog = new Plog([process:procstep.process,ps:procstep,status:status,operator:springSecurityService.currentUser,memo:"材料质检审核:驳回"])
                    plog.save(flush:true)
                }else{
                    def plog = new Plog([process:procstep.process,ps:procstep,status:status,operator:springSecurityService.currentUser,memo:"施工质检审核:驳回"])
                    plog.save(flush:true)
                }
            }else{
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
                        def plog = new Plog([process:procstep.process,ps:procstep,status:status,operator:springSecurityService.currentUser,memo:"材料质检审核:通过"])
                        plog.save(flush:true)
                    }else{
                        def plog = new Plog([process:procstep.process,ps:procstep,status:status,operator:springSecurityService.currentUser,memo:"施工质检审核:通过"])
                        plog.save(flush:true)
                    }
                }else{
                    if(procstep.sgroup == 0){
                        def plog = new Plog([process:procstep.process,ps:procstep,status:status,operator:springSecurityService.currentUser,memo:"材料照片整理"])
                        plog.save(flush:true)
                    }else{
                        def plog = new Plog([process:procstep.process,ps:procstep,status:status,operator:springSecurityService.currentUser,memo:"施工照片整理"])
                        plog.save(flush:true)
                    }
                }
            }
        }
        render procstep.process.project.id;
    }
    /**
     * 客户验收
     * @return
     */
    def verify(){
        if(params.id == null){
            redirect controller: "project", action: "index", method: "GET"
        }else{
            def procstepid = params.long("id")
            def procstep = ProcessStep.get(procstepid)
            if(procstep != null){
                def process = procstep.process
                def picList = null
                def material = null
                if(procstep.step.sgroup == 0) {
                    picList = Pic.executeQuery("from Pic p where p.processId=? and isDeleted = false and isMaterial=true",[process.id.toString()])
                    material = ProjectMaterial.findAllByProcessAndIsDeleted(process,false)
                }else{
                    picList = Pic.executeQuery("from Pic p where p.processId=? and isDeleted = false and isMaterial=false",[process.id.toString()])
                }
                render (view: "/project/verify",model: [procstepid:params.id,process:procstep?.process,material:material,picList:picList])
            }else{
                flash.message="你没有权限执行验收"
                render (view: "/project/verify")
            }
        }
    }
    /**
     * 验收结果
     */
    @Transactional
    def doverify(){
        if (params.procstepid == null) {
            render "error";
            return
        }
        def procstepid = params.long("procstepid")
        def procstep = ProcessStep.get(procstepid)
        if(procstep != null) {
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

            def plog = new Plog([process: process,ps:procstep, status: status, operator: springSecurityService.currentUser, attitude: attitude, memo: "客户验收 " + memo])
            plog.save(flush: true)
            render process.project.id;
            return
        }
        return
    }

    @Transactional
    def upload(){
        if (!request instanceof MultipartHttpServletRequest) {
            println "No multipart"
            render "系统故障，没有启动上传模式"
            return
        }
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request
        CommonsMultipartFile orginalFile = (CommonsMultipartFile) multiRequest.getFile("Filedata")
        // 判断是否上传文件
        if (orginalFile == null || orginalFile.isEmpty()) {
            render "没有选择上传文件或者上传文件内容为空，请检查"
            return
        }else{
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
                    pic.uploader = springSecurityService.currentUser
                    if(procstep.step.sgroup == 0){
                        pic.isMaterial = true
                    }
                    if(!pic.save(flush:true)){
                        println(pic.errors)
                    }
                    if(Plog.list([sort: "id",order: "desc",max: 1])?.get(0).status != 2){
                        if(procstep.step.sgroup == 0) {
                            def plog = new Plog([process: progress,ps:procstep, status: 2, operator: springSecurityService.currentUser, memo: "上传材料照片"])
                            plog.save(flush:true)
                        }else{
                            def plog = new Plog([process: progress,ps:procstep, status: 2, operator: springSecurityService.currentUser, memo: "上传施工照片"])
                            plog.save(flush:true)
                        }
                    }
                }
            }
            render "ok"
            return
        }
    }

    /**
     * 查看照片
     * @return
     */
    def pics(){
        if(params.id == null){
            redirect controller: "project", action: "index", method: "GET"
            return;
        }else{
            def process = Process.get(params.id)
            if(process != null){
                def mPicList = null
                def pPicList = null
                def mAtti = null
                def pAtti = process.memo
                if(process.period.hasMaterial == 1){
                    mPicList = Pic.findAllByProcessIdAndIsDeletedAndIsMaterial(params.id,false,true)
                    def mStep = ProcessStep.findByProcessAndStep(process,Step.findBySortNo(3))
                    if(mStep != null){
                        if(mStep.memo != null){
                            mAtti = mStep.memo
                        }
                    }
                    pPicList = Pic.findAllByProcessIdAndIsDeletedAndIsMaterial(params.id,false,false)
                }else{
                    pPicList = Pic.findAllByProcessIdAndIsDeletedAndIsMaterial(params.id,false,false)
                }
                render (view: "/project/pics",model: [processId:params.id,process:process,material:ProjectMaterial.findAllByProcessAndIsDeleted(process,false),mPicList:mPicList,pPicList:pPicList,mAtti:mAtti,pAtti:pAtti])
                return
            }else{
                redirect controller: "project", action: "index", method: "GET"
                return
            }
        }
    }

    def uploadpic(){
        if (params.id == null) {
            flash.message = "请指定步骤ID"
            return
        }

        def procstepid = params.long("id")
        def procstep = ProcessStep.get(procstepid)
        if(procstep != null){
            def picList
            def procesId = procstep.process.id.toString()
            if(procstep.sgroup == 0){
                picList = Pic.findAllByProcessIdAndIsDeletedAndIsMaterial(procesId,false,true)
            }else{
                picList = Pic.findAllByProcessIdAndIsDeletedAndIsMaterial(procesId,false,false)
            }
            render(view: "/project/uploadpic",model: [procstepid:params.id,picList:picList,procstep:procstep,project:procstep.process.project ])
        }else{
            flash.message = "没有找到该阶段的图片"
            return
        }
    }

    def drawtl(){
        if (params.projectId == null) {
            render "error";
            return
        }
        def processList = Process.findAllByProject(Project.get(params.long("projectId")),[sort: "period.orderNo"])
        def plan = []
        def event = []
        if(processList != null && processList.size() > 0){
            int len = processList.size();
            def process;
            java.text.SimpleDateFormat df=new java.text.SimpleDateFormat("yyyy-MM-dd");
            for(int i = 0; i < len; ++i){
                process = processList.get(i)
//                if(i == 0){
//                    if(process.planStartDate != null){
//                        def pend = df.format(process.planEndDate)
//                        plan.add(["k":pend,"v":1,"t":process.period.name+"计划结束"])
//                        event.add(pend)
//                    }
//                    if(process.realEndDate != null){
//                        plan.add(["k":df.format(process.realEndDate),"v":1,"t":process.period.name+"实际结束"])
//                    }
//                }else{
                    if(process.planEndDate != null){
                        def pend = df.format(process.planEndDate)
                        plan.add(["k":pend,"v":1,"v2":null,"f":0,"t":process.period.name+"计划结束"])
                        event.add(pend)
                    }
                    if(process.realEndDate != null){
                        plan.add(["k":df.format(process.realEndDate),"v":1,"v2":2,"f":1,"t":process.period.name+"实际结束"])
                    }
//                }
            }
        }
        render(["plan":plan,"evt":event] as JSON)
    }
    //更换监理
    def changeQc(){
        if (params.processId == null) {
            render "error";
            return
        }
        def curQc
        if (params.curqc != null) {
            curQc = User.get(params.curqc)
        }
        render(template: "/templates/changeqc",model: [curQc:curQc,processId:params.processId])
    }
    @Transactional
    def updateQc(){
        if (params.processId == null || params.newqc == null) {
            render "error";
            return
        }
        Process.executeUpdate("update Process set charger=? where id=?",[User.get(params.newqc),params.long("processId")])
        render "ok"
    }
}
