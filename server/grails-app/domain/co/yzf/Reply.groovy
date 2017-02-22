package co.yzf

/**
 * 问题反馈表应答表
 */
class Reply {
    User user
    Feedback feedback
    String content
    boolean isDeleted =false
    Date dateCreated
    static constraints = {
        feedback nullable:false
        content nullable:false,maxSize: 1023
    }
    static mapping = {
        version(false)
    }
}
