package co.yzf

class Role {
    String authority
    String comment
    boolean isShow = true

    //static embedded = ['createdBy']
    static mapping = {
        version false
        cache true
    }

    static constraints = {
        authority blank: false, unique: true,maxSize: 70
        comment nullable:true,maxSize: 100
    }

    String toString() {
        authority
    }

    static normalAdmin() {
        Role.findByAuthority('ROLE_NORMAL_ADMIN')
    }

    static normalUser() {
        Role.findByAuthority('ROLE_USER')
    }
}
