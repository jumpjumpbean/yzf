package co.yzf

/**
 * 流程步骤表
 */
class Step {
    //流程进度
    String name
    //type:(0:没有材料，1：有材料的）
    int type = 1
    int sortNo = 0
    //sgroup:主要为了把有材料的阶段步骤划分为2个阶段
    int sgroup = 1
    static constraints = {
        name nullable:true,maxSize: 31
    }
    static mapping = {
        version(false)
    }

    @Override
    String toString(){
        return name;
    }
}
