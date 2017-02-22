package co.yzf
/**
 * 工程进度表
 */
class Process {
    //工程
    Project project
    //阶段
    Period period
    //负责人
    User charger
    //计划开始日期
    Date planStartDate
    Date planEndDate
    //实际开始结束日期
    Date realStartDate
    Date realEndDate
    //状态：1：未展开 2：施工中 3：申请审核 4：审核失败 5：审核通过 6：照片整理 7：验收失败 8：验收通过
    int status = 1
    //验收态度：1：拒绝通过 2：一般通过 3：满意 4：非常满意
    int attitude = 0
    String memo
    User updater
    Date dateCreated
    static constraints = {
        charger nullable:true
        updater nullable:true
        planStartDate nullable:true
        planEndDate nullable:true
        realStartDate nullable:true
        realEndDate nullable:true
        memo nullable:true,maxSize: 255
    }
    static mapping = {
        version(false)
    }
}
