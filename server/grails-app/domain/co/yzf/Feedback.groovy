package co.yzf

/**
 * 问题反馈表
 */
class Feedback {
    User user
    Project project
    String title
    String summary
    String content
    boolean isDeleted =false
    Date dateCreated
    static constraints = {
        project nullable:true
        title nullable:false,maxSize: 127
        summary  nullable:false,maxSize: 127
        content nullable:false,maxSize: 1023
    }
    static mapping = {
        version(false)
    }
}
