package TheFirstCommit.demo.user.entity;

import java.util.Arrays;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {
    SOCIAL("ROLE_SOCIAL"), // 로그인만 하고 가족이 없는 상태
    USER("ROLE_SOCIAL","ROLE_USER") ,
    ADMIN("ROLE_SOCIAL","ROLE_ADMIN", "ROLE_USER")
    ;
    String[] roles;
    UserRole(String ...roles) {
        this.roles = roles;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(this.roles)
            .map(SimpleGrantedAuthority::new)
            .toList();
    }
}
