package jorados.capston.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jorados.capston.config.auth.PrincipalDetails;
import jorados.capston.domain.User;
import jorados.capston.util.CustomResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;



//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음.
//login 요청해서 username,password 전송하면 (post)
//UsernamePasswordAuthenticationFilter 동작을 함.
//-> 근데 이게 .formLogin().disable() 때문에 작동을 안함
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {  //인증필터

    private final AuthenticationManager authenticationManager;

    //login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("JwtAuthenticationFilter: 로그인 시도중");

        //1.username,password 를 받아서
        try {
            //request 받은 거 String -> 오브젝트로 파싱
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);

            //username과 password를 담은 토큰을 만들어서 날림 -> JWT 토큰이아니고 인증을 위한 인증토큰(그냥 username,password를 담고있는 정보)
            //authenticationManager.authenticate를 호출 -> PrincipalService의 loadUserByUsername() 함수가 실행됨 -> DB 확인
            //DB에 있는 username과 password가 일치한다.
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);  //인증토큰으로 강제로 시큐리티세션 authentication에 그 loginuser를 등록시켜버림.

            //로그인이 되었다는 뜻.
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            log.info("로그인 완료됨 = {}", principalDetails.getUser().getUsername());

            //authentication객체가 session영역에 저장을해야하고 그 방법이 return 해주면됨.
            //리턴의 이유는 권한관리를 security가 대신 해주기 떄문에 편리하려고 하는거임.
            //굳이 JWT토큰을 사용하면서 세션을 만들 이유가 없음. 근데 단지 권한 처리리 떄문에 session에 넣어 준다.

            return authentication;  //--> successfulAuthentication(); 호출 -> 토큰 만드는 곳
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행된다.
    //여기서 JWT 토큰을 만들어서 request요청한 사용자에게  / JWT토큰을 응답해주면 된다.


    //위에서 만든 토큰 암호화 하는 부분.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        log.info("successfulAuthentication 실행됨 : 인증이 완료되었다는 뜻임.");
        PrincipalDetails principalDetailis = (PrincipalDetails) authResult.getPrincipal();

        //hash 암호 방식
        String jwtToken = JWT.create()
                .withSubject(principalDetailis.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
                .withClaim("id", principalDetailis.getUser().getId())
                .withClaim("username", principalDetailis.getUser().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);
        CustomResponseUtil.success(response, principalDetailis.getUser());
    }

    //unSuccessfulAuthentication 이라는 것도 이씀.
}
