package co.yzf
/**
 * 项目订单表
 */
class Project {
    //客户关联用户，创建订单后创建
    User user
    //客户名
    String customer
    //合同号
    String contractNo
    //工程地址
    String address
    //计划开始日期
    Date planStartDate
    Date planEndDate
    //实际开始结束日期
    Date realStartDate
    Date realEndDate
    //工程监理
    User responser
    //工程质检
    User qc
    //工程当前施工状态：1：未展开 2：施工中 3:完工
    int status = 1
    //总款
    int totalCost = 0
    //预付款
    int imprest = 0
    //已经支付费用
    int payment = 0
    boolean isDeleted =false
    Date dateCreated
    static constraints = {
        user nullable:true
        customer nullable:false,maxSize: 63
        contractNo nullable:true,maxSize: 32
        address nullable:true,maxSize: 127
        planStartDate nullable:true
        planEndDate nullable:true
        realStartDate nullable:true
        realEndDate nullable:true
        responser nullable:true
        qc nullable:true
        isDeleted default:false
    }
    static mapping = {
        version(false)
    }

    @Override
    String toString(){
        return contractNo;
    }
}
