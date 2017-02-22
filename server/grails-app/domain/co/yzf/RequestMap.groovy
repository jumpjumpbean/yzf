package co.yzf

class RequestMap {
    String url
    String configAttribute
    static constraints = {
        url blank: false, unique: true
        configAttribute blank: false
    }
    static mapping = {
        version(false)
    }

}
