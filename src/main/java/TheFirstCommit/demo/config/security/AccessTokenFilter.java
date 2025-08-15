package TheFirstCommit.demo.config.security;

import TheFirstCommit.demo.config.security.util.ExpiredException;
import TheFirstCommit.demo.config.security.util.JWTUtil;
import TheFirstCommit.demo.exception.ErrorCode;
import TheFirstCommit.demo.user.entity.UserEntity;
import TheFirstCommit.demo.user.service.UserValidateService;
import TheFirstCommit.demo.common.FailResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class AccessTokenFilter extends OncePerRequestFilter {

    private final UserValidateService userValidateService;

    public AccessTokenFilter(UserValidateService userValidateService) {
        this.userValidateService = userValidateService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);

        Long userId;
        try {
            userId = JWTUtil.decodeToken(token);
        }catch (ExpiredException e){
            FailResponseUtil.writeResponse(response, ErrorCode.EXPIRED_TOKEN);
            return;
        }

        Optional<UserEntity> user = userValidateService.findById(userId);
        if(user.isEmpty()){
            FailResponseUtil.writeResponse(response, ErrorCode.INVALID_TOKEN);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.get(), null, user.get().getRole().getAuthorities()));

        StringBuilder str = new StringBuilder();
        str.append("access token success " + userId +", " + user.get().getName());
        user.get().getRole().getAuthorities().forEach(authority -> str.append(", " + authority.getAuthority()));
        log.info(str.toString());

        filterChain.doFilter(request, response);
    }
}
