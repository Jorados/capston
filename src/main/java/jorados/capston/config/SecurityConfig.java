package jorados.capston.config;

import jorados.capston.config.jwt.JwtAuthenticationFilter;
import jorados.capston.config.jwt.JwtAuthorizationFilter;
import jorados.capston.config.oauth.PrincipalOauth2UserService;
import jorados.capston.domain.type.UserEnum;
import jorados.capston.repository.UserRepository;
import jorados.capston.util.CustomResponseUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/*
 * 시큐리티 컨피그 설정 공식 문서
 * https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    // 모든 필터 등록은 여기서!! (AuthenticationManager 순환 의존 문제로 내부 클래스로 만들어진 듯, 추측임)
    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            // log.debug("디버그 : SecurityConfig의 configure");
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http.addFilter(new JwtAuthenticationFilter(authenticationManager));
            http.addFilter(new JwtAuthorizationFilter(authenticationManager,userRepository));
        }
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // log.debug("디버그 : SecurityConfig의 filterChain");
        http.headers().frameOptions().disable(); // iframe 허용안함
        http.csrf().disable(); // csrf 허용안함
        http.cors().configurationSource(configurationSource()); // cors 재정의

        // ExcpetionTranslationFilter (인증 확인 필터) , authorization에서 authException (인증안됨) 터지는 예외처리
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            CustomResponseUtil.fail(response, "로그인 오류", HttpStatus.UNAUTHORIZED);
        });

        // 권한 실패 , authorization에서 허가 못받으면 터지는 예외처리
        http.exceptionHandling().accessDeniedHandler((request, response, e) -> {
            CustomResponseUtil.fail(response, "권한이 없습니다", HttpStatus.FORBIDDEN);
        });

        /*
         * SessionCreationPolicy.STATELESS
         * 클라이언트가 로그인 request
         * 서버는 User 세션 저장
         * 서버가 response
         * 세션값 사라짐. (즉 유지하지 않음)
         */
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.formLogin().disable();
        // httpBasic()은 브라우저가 팝업창을 이용해서 사용자 인증을 진행한다.
        http.httpBasic().disable();

        // 필터 적용
        http.apply(new CustomSecurityFilterManager());

        http.authorizeHttpRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/api/user/**").authenticated() //여긴 그냥 접속 되야함. //권한 테스트
                .antMatchers("/api/admin/**").hasRole("" + UserEnum.ADMIN) // ROLE_ 안붙여도 됨 //권한 테스트
                .antMatchers("/centerReservation/**").authenticated() // 예약 관련된 거는 전부 일반유저면 허용
                .antMatchers("/center/**").authenticated()
                .antMatchers("/post/**").authenticated()
                .antMatchers("/comment/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .oauth2Login()
                .loginPage("/loginForm")
                .userInfoEndpoint()
                .userService(principalOauth2UserService);

        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        // log.debug("디버그 : SecurityConfig의 configurationSource");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        configuration.addAllowedOriginPattern("*"); // 프론트 서버의 주소 등록
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
