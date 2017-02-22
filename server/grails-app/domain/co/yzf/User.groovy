package co.yzf

class User {
    transient springSecurityService
    transient passwordEncoder
    String id
    String uname
    String account
    String password
    String address
    String telephone
    String description
    boolean enabled = true
    boolean isActive = true//todo:changed it to false when deploy;
    boolean accountLocked = false
    boolean accountExpired = false
    boolean passwordExpired = false
    Date dateCreated

    static constraints = {
        uname(nullable: true,size:0..32)
        account(blank:false,nullable: false,size:1..100)
        password(blank:false,nullable: false,size:1..64)
        address (nullable: true,size:0..64)
        telephone (nullable: true,size:0..16)
        description (nullable: true,size:0..128)
        dateCreated nullable: true
    }
    static mapping = {
        id generator:'uuid.hex'
        version(false)
    }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this).collect { it.role } as Set
    }

    Role getCurAuth(){
        println(this.id)
        def authList = Role.executeQuery("select ur.role from UserRole ur where ur.user=:u and ur.role.isShow=true",[u:this])
        println(authList)
        if(authList != null && authList.size() > 0){
            return authList.get(0)
        }
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }
    protected void encodePassword() {
        //userPassword = springSecurityService.encodePassword(userPassword)
        password = passwordEncoder.encodePassword(password, account)
    }
    String toString(){
        return this.uname;
    }
}
