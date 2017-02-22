package co.yzf

import grails.plugin.springsecurity.SpringSecurityUtils

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ProjectController {
    def dataSource
    def springSecurityService
    def index() {
        params.max = 20
        params.sort="dateCreated"
        params.order="desc"
        def projectList = null
        def cnt = 0
        def curUser = springSecurityService.currentUser
        def sql = ""
        def status = CommonUtil.trimStr(params.status)
        if(status != null && !"0".equals(status)){
            sql = " and status="+status + " "
        }else{
            status = 0
        }
        def nextMonth = null
        def curMonth = CommonUtil.parseDate(params.dateCreated)
        if(curMonth != null ){
            nextMonth = CommonUtil.getNextMonth(curMonth);
            sql = sql + " and dateCreated > ? and dateCreated < ? "
        }
        def strsql = null;
        if(SpringSecurityUtils.ifAnyGranted("ROLE_CUSTOMER")){
            strsql = " from Project where user=? and isDeleted = false " + sql
            if(curMonth == null){
                projectList = Project.executeQuery(strsql,[curUser],params)
                cnt = Project.executeQuery(strsql,[curUser]).size()
            }else{
                projectList = Project.executeQuery(strsql,[curUser,curMonth,nextMonth],params)
                cnt = Project.executeQuery(strsql,[curUser,curMonth,nextMonth]).size()
            }
        }else if(SpringSecurityUtils.ifAnyGranted("ROLE_ADMIN,ROLE_SUPER")){
            strsql = " from Project where isDeleted = false " + sql
            if(curMonth == null){
                projectList = Project.executeQuery(strsql,[],params)
                cnt = Project.executeQuery(strsql).size()
            }else{
                projectList = Project.executeQuery(strsql,[curMonth,nextMonth],params)
                cnt = Project.executeQuery(strsql,[curMonth,nextMonth]).size()
            }
            def month = CommonUtil.getFilterDate()
            render (view:"/project/index_admin", model: [projectInstanceList:projectList,projectInstanceCount: cnt,months:month,status:status,dateCreated:params.dateCreated])
            return
        }else if(SpringSecurityUtils.ifAnyGranted("ROLE_SUPERVISOR,ROLE_QC")){
            sql = sql.replace("status","p.project.status")
            sql = sql.replace("dateCreated","p.project.dateCreated")
            strsql = " select distinct p.project from Process p where (p.charger=? or p.project.qc=?) and p.project.isDeleted=false " + sql
            if(curMonth == null) {
                projectList = Process.executeQuery(strsql, [curUser, curUser],params)
                cnt = Process.executeQuery(strsql, [curUser, curUser]).size()
            }else{
                projectList = Process.executeQuery(strsql, [curUser, curUser,curMonth,nextMonth],params)
                cnt = Process.executeQuery(strsql, [curUser, curUser,curMonth,nextMonth]).size()
            }
        }
        def month = CommonUtil.getFilterDate()
        render (view:"/project/index", model: [projectInstanceList:projectList,projectInstanceCount: cnt,months:month,status:status,dateCreated:params.dateCreated])
    }

    def show(Project projectInstance) {
        if(projectInstance==null){
            return
        }
        respond projectInstance, model:[curUser:springSecurityService.currentUser,processList:Process.findAllByProject(projectInstance,[sort: "period.orderNo"])]
    }

    def create() {
        respond new Project(params)
    }

    @Transactional
    def save(Project projectInstance) {
        if (projectInstance == null) {
            notFound()
            return
        }

        projectInstance.planStartDate = CommonUtil.parseDate(params.planStartDate)
        projectInstance.planEndDate = CommonUtil.parseDate(params.planEndDate)
        projectInstance.realStartDate = CommonUtil.parseDate(params.realStartDate)
        projectInstance.realEndDate = CommonUtil.parseDate(params.realEndDate)
        if(!projectInstance.save(flush: true,validate:false)){
            println projectInstance.errors
        }

        def db = new groovy.sql.Sql (dataSource)
        db.execute("insert into process(attitude,charger_id,date_created,period_id,project_id,status)select 0,?,now(),p.id,?,1 from period p order by p.order_no",[projectInstance.getResponser().getId(),projectInstance.getId()])
        def procList = Process.findAllByProject(projectInstance);
        procList.each {
            db.execute("insert into process_step(attitude,date_updated,process_id,status,step_id,sgroup)select 0,now(),?,1,s.id,sgroup from step s where s.type=? order by s.sort_no",[it.id,it.period.hasMaterial])
        }
        request.withFormat {
            form multipartForm {
                flash.message = "新订单创建成功"//message(code: 'default.created.message', args: [message(code: 'projectInstance.label', default: 'Project'), projectInstance.id])
                redirect projectInstance
            }
            '*' { respond projectInstance, [status: CREATED] }
        }
    }

    def edit(Project projectInstance) {
        respond projectInstance
    }

    @Transactional
    def update(Project projectInstance) {
        if (projectInstance == null) {
            notFound()
            return
        }

        //Project.executeUpdate("update Project set ")
        projectInstance.planStartDate = CommonUtil.parseDate(params.planStartDate)
        projectInstance.planEndDate = CommonUtil.parseDate(params.planEndDate)
//        if (projectInstance.hasErrors()) {
//            respond projectInstance.errors, view: 'edit'
//            return
//        }
        println projectInstance
        if(!projectInstance.save(flush: true,validate:false)){
            println projectInstance.errors
        }

        request.withFormat {
            form multipartForm {
                flash.message = "更新成功"//message(code: 'default.updated.message', args: [message(code: 'Project.label', default: 'Project'), projectInstance.id])
                redirect projectInstance
            }
            '*' { respond projectInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Project projectInstance) {

        if (projectInstance == null) {
            notFound()
            return
        }

        projectInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = "删除成功"//message(code: 'default.deleted.message', args: [message(code: 'Project.label', default: 'Project'), projectInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    @Transactional
    def start(){
        def processIds = params.get("processId")
        if(processIds != null && processIds.size() > 0){
            int len = processIds.size()
            def startD = params.pStartDate
            def endD = params.pEndDate
            for(int i = 0; i < len; ++i){
                if(i == 0){
                    def psId = Long.parseLong(processIds[i])
                    Project.executeUpdate("update Process set planStartDate=?,planEndDate=?,realStartDate=?,status=2 where id=?",[CommonUtil.parseDate(startD[i]),CommonUtil.parseDate(endD[i]),new Date(),psId])
                    def nextStep = ProcessStep.executeQuery("from ProcessStep p where p.process.id=?",[psId],[sort:"p.step.sortNo",max:1])
                    if(nextStep != null && nextStep.size() > 0){
                        def ns = nextStep.get(0)
                        if(ns.status == 1){
                            ProcessStep.executeUpdate("update ProcessStep set status=2 where id=?",[ns.id])
                        }
                    }
                }else{
                    Project.executeUpdate("update Process set planStartDate=?,planEndDate=? where id=?",[CommonUtil.parseDate(startD[i]),CommonUtil.parseDate(endD[i]),Long.parseLong(processIds[i])])
                }
            }
            Project.executeUpdate("update Project set realStartDate=?,status=? where id=?",[new Date(),2,params.long("projectId")])
        }
        redirect uri:"/project/show/"+params.projectId
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'projectInstance.label', default: 'Project'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    def addMaterial(){
        if(params.id == null){
            redirect(action:"index")
            return
        }
        def proj = Project.get(params.long("id"))
        if(proj == null){
            redirect(action:"index")
            return
        }
        def pmList = ProjectMaterial.findAllByProjectAndIsDeleted(proj,false)
        def allProcs = Process.executeQuery("from Process p where p.project=? and p.period.hasMaterial = 1",[proj])
        if(pmList.size() > 0){
            render(view:"addMaterial",model:[projectId:params.id,pmList:pmList,allMaterial:Material.list(),allProcs:allProcs])
        }else{
            render(view:"addMaterial",model: [projectId:params.id,allTypeList: Material.executeQuery("select distinct type from Material"),allMaterial:Material.list(),allProcs:allProcs])
        }
    }
    @Transactional
    def saveMaterial(){
        if(params.projectId == null || params.materialId == null || params.cnt == null){
            render "error"
            return;
        }
        try{
            ProjectMaterial pm = new ProjectMaterial(params)
            pm.material = Material.get(params.long("materialId"))
            pm.project = Project.get(params.long("projectId"))
            pm.process = Process.get(params.long("processId"))
            if(!pm.save(flush:true)){
                println pm.errors
                render "error"
                return;
            }
            render template: "savem",model: [pm:pm,indx:params.indx]
        }catch(Exception e){
            e.printStackTrace()
            render "error"
            return;
        }

    }

    @Transactional
    def deleteMaterial(){
        if(params.id == null){
            render "error"
            return;
        }
        def pm = ProjectMaterial.get(params.long('id'))
        if(pm != null){
            pm.delete()
        }
        render "ok"
    }

    def viewMaterial(){
        if(params.id == null){
            redirect(action:"index")
            return
        }
        def proj = Project.get(params.long("id"))
        if(proj == null){
            redirect(action:"index")
            return
        }
        def pmList = ProjectMaterial.findAllByProjectAndIsDeleted(proj,false)
        render(view:"viewMaterial",model:[projectId:params.id,pmList:pmList])
    }
}
