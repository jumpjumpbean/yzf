package co.yzf

class UserService {

    def serviceMethod() {

    }

    def doRegister(def params,def request){
        try {
            def user = new User(params)
            if(!user.save(flush: true)){
                println user.errors
                return null
            }
            def userRole = UserRole.create(user,Role.normalUser(),true)
            return user
        }catch(Exception e) {
            e.printStackTrace()
            println e
            return null
        }
    }
}
