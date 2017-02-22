package co.yzf

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class FeedbackController {
    def springSecurityService
    static allowedMethods = [save: "POST", update: "PUT", delete: "GET"]

    def index(Integer max) {
        if(SpringSecurityUtils.ifAnyGranted("ROLE_CUSTOMER")){
            params.max = Math.min(max ?: 20, 100)
            params.sort = "dateCreated"
            params.order="desc"
            respond Feedback.findAllByUserAndIsDeleted(springSecurityService.currentUser,false,params), model: [feedbackInstanceCount: Feedback.countByUserAndIsDeleted(springSecurityService.currentUser,false),curUser:springSecurityService.currentUser]
        }else{
            params.max = Math.min(max ?: 20, 100)
            params.sort = "dateCreated"
            params.order="desc"
            respond Feedback.findAllByIsDeleted(false,params), model: [feedbackInstanceCount: Feedback.count(),curUser:springSecurityService.currentUser]
        }
    }

    def show() {
        if(params.id == null){
            redirect(action:"index")
            return;
        }
        def fdb = Feedback.get(params.long("id"))
        if(fdb == null){
            redirect(action:"index")
            return;
        }else{
            render(view:"show",model:[feedbackInstance:fdb,curUser:springSecurityService.currentUser,replyList:Reply.findAllByFeedbackAndIsDeleted(fdb,false)])
        }
    }

    def create() {
        respond new Feedback(params)
    }

    @Transactional
    def save(Feedback feedbackInstance) {
        if(feedbackInstance == null || params.title == null || params.summary == null){
            flash.message="请填写标题和反馈内容"
            redirect(action:"create")
            return
        }

        feedbackInstance.user = springSecurityService.currentUser
        def project = Project.findByUser(springSecurityService.currentUser)
        if(project != null){
            feedbackInstance.project = project
        }
        if(!feedbackInstance.save(flush: true)){
            println feedbackInstance.errors
            respond feedbackInstance.errors, view: 'create'
            return
        }

        redirect(action:"index")
    }

    def edit(Feedback feedbackInstance) {
        respond feedbackInstance
    }

    @Transactional
    def update(Feedback feedbackInstance) {
        if (feedbackInstance == null) {
            notFound()
            return
        }

        if (feedbackInstance.hasErrors()) {
            respond feedbackInstance.errors, view: 'edit'
            return
        }

        if(!feedbackInstance.save(flush: true)){
            println feedbackInstance.errors
            respond feedbackInstance.errors, view: 'edit'
        }
        redirect action:"index"
    }

    @Transactional
    def delete(Feedback feedbackInstance) {
        if (feedbackInstance == null) {
            notFound()
            return
        }
        Feedback.executeUpdate("update Feedback set isDeleted=true where id=?",[feedbackInstance.id])
        Reply.executeUpdate("update Reply set isDeleted=true where feedback=?",[feedbackInstance])
        redirect action:"index"
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'feedbackInstance.label', default: 'Feedback'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
    @Transactional
    def reply(){
        def content = CommonUtil.trimStr(params.content)
        if(params.feedbackId == null || content == null){
            render "当前的造作不当，更新不成功"
            return
        }
        def fb = Feedback.get(params.long("feedbackId"))
        if (fb == null){
            render "评价的反馈不存在，可能已被删除，请刷新画面再操作"
            return
        }
        def reply = new Reply()
        reply.user = springSecurityService.currentUser
        reply.content = content;
        reply.feedback = fb
        if(!reply.save(flush:true)){
            println reply.errors
            render "数据不能更新到数据库，请核对"
            return
        }
        render "ok"
    }
}
