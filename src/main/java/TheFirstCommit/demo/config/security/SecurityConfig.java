package TheFirstCommit.demo.config.security;

import TheFirstCommit.demo.common.FailResponseUtil;
import TheFirstCommit.demo.exception.ErrorCode;
import TheFirstCommit.demo.user.entity.UserRole;
import TheFirstCommit.demo.user.service.UserValidateService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserValidateService userValidateService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf->csrf.disable())
            .cors(Customizer.withDefaults())
            .formLogin(formLogin->formLogin.disable())
            .httpBasic(httpBasic->httpBasic.disable())
            .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(authorizeRequests->authorizeRequests

                .requestMatchers("/test/**", "/public/**").permitAll()
                .requestMatchers("/api/**").hasRole(UserRole.USER.name())
                .requestMatchers("/social/**").hasRole(UserRole.SOCIAL.name()) // 가족 구성 이전의 회원 들 (다른 api 접근 불가능)
                .requestMatchers("/admin/**").hasRole(UserRole.ADMIN.name())
                    .requestMatchers(
                            "/swagger-ui.html",
                            "/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/api-docs/**"
                    ).permitAll()
                .anyRequest().denyAll()
            )
            .exceptionHandling(exception -> exception
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    var auth = SecurityContextHolder.getContext().getAuthentication();
                    if (auth != null && auth.getAuthorities().stream().anyMatch(
                        a -> a.getAuthority().equals("ROLE_SOCIAL"))
                    )
                        FailResponseUtil.writeResponse(response, ErrorCode.SOCIAL_AUTHORIZED);
                     else
                        FailResponseUtil.writeResponse(response, ErrorCode.UNAUTHORIZED);
                })
            )

            .addFilterBefore(new AccessTokenFilter(userValidateService), LogoutFilter.class) // 3 번째

            ;

            return http.build();
    }
}
