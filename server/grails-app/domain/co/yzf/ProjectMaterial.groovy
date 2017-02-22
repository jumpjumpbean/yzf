package co.yzf

/**
 * 工程材料表
 */
class ProjectMaterial {
    //工程进度ID
    Project project
    Process process
    Material material
    int cnt
    boolean isDeleted =false
    //工程当前施工状态：1：未发放 2：发放完毕
    int status = 1
    //备注
    String memo
    //材料领取人信息
    User applyer
    Date applyDate
    Date dateCreated
    static constraints = {
        memo nullable:true,maxSize: 127
        applyer nullable:true
        applyDate nullable:true
        process nullable:true
    }
    static mapping = {
        version(false)
    }

}
