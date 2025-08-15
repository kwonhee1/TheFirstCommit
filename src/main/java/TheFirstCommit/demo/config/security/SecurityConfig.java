package TheFirstCommit.demo.config.security;

import TheFirstCommit.demo.user.entity.UserRole;
import TheFirstCommit.demo.user.service.UserValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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
                .requestMatchers("/admin/**").hasRole(UserRole.ADMIN.name())
                    .requestMatchers(
                            "/swagger-ui.html",
                            "/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/api-docs/**"
                    ).permitAll()
                .anyRequest().denyAll()
            )

            .addFilterBefore(new AccessTokenFilter(userValidateService), LogoutFilter.class) // 3 번째

            ;

            return http.build();
    }
}
