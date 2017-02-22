package co.yzf

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityUtils
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile

class UserController {
    def userService
    def springSecurityService
    def passwordEncoder
    def index() {
    }

    def addUser={
        //springSecurityService.reauthenticate userInstance.username
        if(request.post){
            params.account = CommonUtil.trimStr(params.account)
            params.password = CommonUtil.trimStr(params.password)
            if(params.account == null || params.password == null){
                flash.message="请提供账号和密码"
            }else if(User.findByAccount(params.account) != null){
                flash.message="提供的账号名称已经被注册，请换一个"
            }else{
                User user = userService.doRegister(params,request)
                redirect action: 'listAll'
            }
        }
        render(view:"addUser")
    }

    def edit(){
        if(params.id == null){
            render view:"edit",model: [edtuser:springSecurityService.currentUser,roleList:Role.list()]
        }else{
            if(SpringSecurityUtils.ifAnyGranted("ROLE_ADMIN,ROLE_SUPER")){
                render view:"edit",model: [edtuser:User.get(params.id),roleList:Role.list()]
            }else{
                flash.message="你没有权限编辑别人的账号"
                redirect(action:"listAll")
            }
        }
    }

    def save(){
        def user
        if(SpringSecurityUtils.ifAnyGranted("ROLE_ADMIN,ROLE_SUPER")){
            def userId = CommonUtil.trimStr(params.uid)
            if(userId != null){
                user = User.get(params.uid)
            }
        }else{
            user=springSecurityService.currentUser
        }
        if(user != null){
            user.uname = params.uname
            user.address = params.address
            user.telephone = params.telephone
            user.description = params.description
            if(!user.save(flush:true)){
                println(user.errors)
                flash.message = "更新失败，请联系管理员"
                redirect(action: "edit",id:user.id)
                return
            }else{
                UserRole.removeShow(user,true)
                if(params.uauths != null){
                    UserRole.create(user,Role.get(params.uauths),true)
                }
//                for(def rl:params.uauths){
//                    UserRole.create(user,Role.get(rl),true)
//                }
                redirect(action: "listAll")
                return
            }
        }
        flash.message = "更新失败，请联系管理员"
        redirect(action: "listAll")
    }

    def resetPswd(){
        def acount = CommonUtil.trimStr(params.account)
        def pswd = CommonUtil.trimStr(params.passwd)
        if(acount == null || pswd == null){
            render([msg: "请提供用户名和密码"] as JSON)
            return
        }else{
            def user
            if(SpringSecurityUtils.ifAnyGranted("ROLE_ADMIN,ROLE_SUPER")){
                def userId = CommonUtil.trimStr(params.userId)
                if(userId != null){
                    user = User.get(params.userId)
                }
            }else{
                user=springSecurityService.currentUser
            }
            if(user != null){
                def userList = User.findAllByAccount(acount);
                if(userList != null){
                    def cntOfUser = userList.size()
                    if(cntOfUser == 0 || (cntOfUser == 1 && userList.get(0).id == user.id)){
                        user.account = acount
                        user.password = pswd
                        //User.executeUpdate("update User set account=?,password=? where id=?",[acount,passwordEncoder.encodePassword(acount,pswd),user.id])
                        if(!user.save(flush:true)){
                            println(user.errors)
                            render([msg: "密码更新失败，请联系管理员"] as JSON)
                            return
                        }else{
                            render([] as JSON)
                            return
                        }
                    }else{
                        render([msg: "你提供的登陆账号已经存在，请换一个"] as JSON)
                        return
                    }
                }else{
                    user.account = acount
                    user.password = pswd
                    if(!user.save(flush:true)){
                        println(user.errors)
                        render([msg: "密码更新失败，请联系管理员"] as JSON)
                        return
                    }else{
                        render([] as JSON)
                        return
                    }
                }
            }else{
                render([msg: "你现在无权更新此账号，请联系管理员"] as JSON)
                return
            }
        }
        render([msg: "未知错误导致更新失败"] as JSON)
        return
    }

    def listAll={
        render(view: "/user/listAll",model: [allUser:User.list([sort: "dateCreated",order: "desc"]),curUser:springSecurityService.currentUser])
    }

    def feedback={
        render(view: "feedback")
    }

//    def doEmailresetpassword = {
//        def user = User.findByUserEmail(params.email)
//        if (!user){
//            render(view: '/user/error',model: [message: "用户不存在"])
//            return
//        }
//        if (params.new_userPassword == "" || params.again_userPassword == ""){
//            flash.message = "密码不能为空"
//            redirect(uri: "/user/emailresetpassword?email=${params.email}&c=${params.c}")
//            return
//        }
//        if(params.new_userPassword != params.again_userPassword){
//            flash.message = "密码不匹配！"
//            redirect(uri: "/user/emailresetpassword?email=${params.email}&c=${params.c}")
//            return
//        }
//        user.userPassword = springSecuritd(params.new_userPassword)
//        if (!user.save(flush: true)){
//            println user.errors
//            flash.message = "保存失败！"
//            redirect(uri: "/user/emailresetpassword?email=${params.email}&c=${params.c}")
//            return
//        }
//        def uc = UserCode.findByUserAndCodeAndTypeAndIsUsed(user,params.c,2,false)
//        uc.isUsed = true
//        uc.save(flush: true)
//        springSecurityService.clearCachedRequestmaps()
//        springSecurityService.reauthenticate(user?.userEmail)
//        render(view: '/user/success',model: [title: '重置密码成功',message:'重置密码成功'])
//        return
//
//    }

    def upload(){
        if (!request instanceof MultipartHttpServletRequest) {
            println "No multipart"
            render ([msg:"系统故障，没有启动上传模式"] as JSON)
            return
        }
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request
        CommonsMultipartFile orginalFile = (CommonsMultipartFile) multiRequest.getFile("upfile")
        // 判断是否上传文件
        if (orginalFile == null || orginalFile.isEmpty()) {
            render ([msg:"没有选择上传文件或者上传文件内容为空，请检查"] as JSON)
            return
        }else{
            def curUser = springSecurityService.currentUser
            if(curUser != null && SpringSecurityUtils.ifAnyGranted("ROLE_CUSTOMER,ROLE_ADMIN")) {
                def imgRootPath = grailsApplication.config.grails.filePath.imagePath
                def relativePath = "user/" + CommonUtil.getCurMonth() + "/" + curUser.id
                def fullPath = imgRootPath + relativePath
                def f = new File(imgRootPath + relativePath)
                if (!f.exists()) {
                    f.mkdirs()
                }
                def originame = orginalFile.fileItem.name
                def fileNameWithoutPath = System.currentTimeMillis() + CommonUtil.randomNum(10000) + originame.substring(originame.lastIndexOf("."))
                orginalFile.transferTo(new File(fullPath + "/" + fileNameWithoutPath))
                render ([url:relativePath + "/" + fileNameWithoutPath] as JSON)
                return
            }
            render ([msg:"用户权限受限"] as JSON)
            return
        }
    }
}
