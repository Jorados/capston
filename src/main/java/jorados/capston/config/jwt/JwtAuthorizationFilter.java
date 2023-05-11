package jorados.capston.config.jwt;



import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jorados.capston.config.auth.PrincipalDetails;
import jorados.capston.domain.User;
import jorados.capston.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//시큐리티가 filter를 가지고 있는데 그 필터중에 BasicAuthenticationFilter라는 것이 있다.
//권한이나 인증이 필요한 특정 주소를 요청을 했을 때 위 필터를 무조건 타게 돼있다.
//만약에 권한이나 인증이 필요한 주소가 아니라면 이 필터를 안탄다.
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {  //권한 허가 필터

    //모든 주소에서 동작함
    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    //인증 권한이 필요한 주소요청이 있을 때 해당 필터를 타게 됨.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JwtProperties.HEADER_STRING);
        log.info("인증이나 권한이  필요한 주소 요청이 됨.");
        log.info("jwtHeader = {}",header);

        //header가 있는지 확인
        if(header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        //JWT토큰을 검증을 해서 정상적인 사용자인지 확인해야한다.

        //토큰파싱 (Bearer 없애기)
        String token = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

        //토큰검증
        String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
                .getClaim("username").asString();

        //서명이 정상적으로 됨
        if (username != null) {
            User userEntity = userRepository.findByUsername(username).orElseThrow(()->new InternalAuthenticationServiceException("인증 실패"));

            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);

            //정상 로그인을 통해서 만든 authentication객체가 아닌
            //jwt토큰 서명을 통해서 서명이 정상이면 authentication객체를 만들어 준다.
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            //강제로 시큐리티 세션에 접근하여 authentication객체를 저장.
            //그러면 로그인이 된다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request,response);
    }
}
