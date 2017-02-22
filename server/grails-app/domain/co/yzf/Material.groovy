package co.yzf

class Material {
    //材料名
    String name
    //品牌
    String brand
    String modelno
    //类型
    String type
    String unit
    //供应商
    String supplier
    static constraints = {
        name nullable:true,maxSize: 64
        brand nullable:true,maxSize: 64
        type nullable:true,maxSize: 32
        unit nullable:true,maxSize: 16
        supplier nullable:true,maxSize: 64
        modelno  nullable:true,maxSize: 63
    }
    static mapping = {
        version(false)
    }
    String toString(){
        return this.type + "/" + this.brand + "/" + this.modelno;
    }
}
