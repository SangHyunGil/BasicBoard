package register.demo.web.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import register.demo.domain.login.LoginService;
import register.demo.domain.student.Student;
import register.demo.web.session.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult,
                          @RequestParam(defaultValue = "/main") String redirectURL, HttpServletRequest request) {
        Student student = loginService.login(loginForm);
        if (student == null) {
            bindingResult.reject("inCorrectLogin", null);
        }

        if (bindingResult.hasErrors()) {
            return "login";
        }
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_STUDENT, loginForm);
        return "redirect:"+redirectURL;
    }

}
