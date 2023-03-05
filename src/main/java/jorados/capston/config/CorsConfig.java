//package jorados.capston.config;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//@Configuration
//public class CorsConfig {
//    @Bean
//    public org.springframework.web.filter.CorsFilter corsFilter(){
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true); //내 서버가 응답을 할 때 json을 자바스크립트에서 처리할 수 있게 할지를 설정하는 것
//        config.addAllowedOrigin("*"); //모든 ip응답 허용
//        config.addAllowedHeader("*"); //모든 header에 응답을 허용
//        config.addAllowedMethod("*"); //모든 get,post,delete,put 요청을 허용
//        config.addAllowedOriginPattern("*"); // 프론트 서버의 주소 등록
//        config.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
//        config.addExposedHeader("Authorization");
//        source.registerCorsConfiguration("/**",config);
//        return new CorsFilter(source);
//    }
//}
