package register.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import register.demo.common.annotation.login.Login;
import register.demo.login.LoginForm;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@Login LoginForm loginForm ){
        log.info("loginForm : {}", loginForm);
        if (loginForm == null) {
            return "home";
        }

        return "redirect:/main";
    }
}
