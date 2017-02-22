package co.yzf

/**
 * 工程图片表
 */
class Pic {
    //工程进度ID
    String processId
    String picName
    String picPath
    boolean isDeleted =false
    boolean isMaterial =false
    String description
    int width = 0
    int height = 0

    //上传者信息
    User uploader
    Date dateCreated
    static constraints = {
        processId maxSize: 32
        picName nullable:true,maxSize: 32
        picPath nullable:true,maxSize: 64
        description nullable:true,maxSize: 127
    }
    static mapping = {
        version(false)
    }
}
