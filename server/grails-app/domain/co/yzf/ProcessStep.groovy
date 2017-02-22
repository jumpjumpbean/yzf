package co.yzf

/**
 * 流程步骤表
 */
class ProcessStep {
    //工程进度ID
    Process process
    Step step
    //工程当前施工状态：1：未开始 2：施工中 3：施工完毕
    int status = 1
    //sgroup:主要为了把有材料的阶段步骤划分为2个阶段
    int sgroup = 1
    //备注
    String memo
    //操作者
    User operator
    //验收态度：1：拒绝通过 2：一般通过 3：满意 4：非常满意
    int attitude = 0
    Date dateUpdated
    static constraints = {
        memo nullable:true,maxSize: 127
        operator nullable:true
    }
    static mapping = {
        version(false)
    }

}
