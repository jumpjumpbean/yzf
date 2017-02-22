package co.yzf

class BaseController {
    def springSecurityService
    def auth() {
        if(!springSecurityService.isLoggedIn()) {
            session['referer'] = '/'+ controllerName +'/' + actionName + (params?.id?'/'+params?.id:'')
            redirect(uri:"/login")
            return false
        }
        return true
    }

    def index(){
        println "here"
        render(view:"/index")
    }
}
