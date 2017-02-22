package co.yzf

/**
 * 工程图片表
 */
class Plog {
    //工程进度
    Process process
    ProcessStep ps
    String memo
    int status = 1
    int attitude = 0
    //上传者信息
    User operator
    Date dateCreated
    static constraints = {
        memo nullable:true,maxSize: 255
        ps nullable:true
    }
    static mapping = {
        version(false)
    }
}
