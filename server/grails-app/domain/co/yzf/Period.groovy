package co.yzf

/**
 * 阶段表
 */
class Period {
    int orderNo = 0
    String name
    String memo
    int hasMaterial = 0
    static constraints = {
        name nullable:false,maxSize: 32
        memo nullable:true,maxSize: 127
    }
    static mapping = {
        version(false)
    }

    String toString(){
        return this.name;
    }
}
