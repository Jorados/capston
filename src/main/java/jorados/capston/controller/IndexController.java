package jorados.capston.controller;


import jorados.capston.config.auth.PrincipalDetails;
import jorados.capston.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    @GetMapping("/loginForm")
    public String loginForm(@ModelAttribute User user, HttpSession session){
        if (session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            return "redirect:/";
        }
        return "loginForm";
    }
}
