package TheFirstCommit.demo.config.security;

import TheFirstCommit.demo.config.security.util.ExpiredException;
import TheFirstCommit.demo.config.security.util.JWTUtil;
import TheFirstCommit.demo.exception.ErrorCode;
import TheFirstCommit.demo.user.entity.UserEntity;
import TheFirstCommit.demo.user.service.UserValidateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

        // get access from header berea token
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // "Bearer " 접두사를 제거하고 실제 토큰 값 추출
        String token = authorizationHeader.substring(7);

        Long userId;
        try {
            userId = JWTUtil.decodeToken(token);
        }catch (ExpiredException e){
            writeResponse(response, ErrorCode.EXPIRED_TOKEN);
            return;
        }

        Optional<UserEntity> user = userValidateService.findById(userId);
        if(user.isEmpty()){
            writeResponse(response, ErrorCode.INVALID_TOKEN);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.get(), null, user.get().getRole().getAuthorities()));

        filterChain.doFilter(request, response);
    }

    private void writeResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), errorCode.toResponseBody());
    }
}
