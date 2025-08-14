package TheFirstCommit.demo.user.entity;

public enum UserRole {
    SOCIAL("ROLE_SOCIAL"),
    USER("ROLE_SOCIAL","ROLE_USER") ,
    ADMIN("ROLE_SOCIAL","ROLE_ADMIN", "ROLE_USER")
    ;
    String[] roles;
    UserRole(String ...roles) {
        this.roles = roles;
    }
}
