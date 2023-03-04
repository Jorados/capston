package jorados.capston.config;

import jorados.capston.config.jwt.JwtAuthenticationFilter;
import jorados.capston.config.jwt.JwtAuthorizationFilter;
import jorados.capston.domain.UserEnum;
import jorados.capston.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final UserRepository userRepository;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션 사용x , 세션관리 안함
        .and()
        .addFilter(corsFilter)
        .formLogin().disable()
        .httpBasic().disable() //기본인증방식 -> 안쓸거임 //토큰방식으로 암호화해서쓸거임

        .addFilter(new JwtAuthenticationFilter(authenticationManager())) //AuthenticationManager
        .addFilter(new JwtAuthorizationFilter(authenticationManager(),userRepository))
        .authorizeRequests()
        .antMatchers("/api/user/**").authenticated()
        .antMatchers("/api/admin/**").hasRole("" + UserEnum.ADMIN) // ROLE_ 안붙여도 됨
        .anyRequest().permitAll();
    }
}
