package register.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import register.demo.common.annotation.login.Login;
import register.demo.student.domain.Student;
import register.demo.student.repository.StudentRepository;
import register.demo.student.service.StudentService;
import register.demo.login.LoginForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {

    private final StudentRepository studentRepository;
    private final StudentService studentService;

    @GetMapping
    public String main(@Login LoginForm loginForm, Model model) {
        log.info("login!");
        Optional<Student> loginStudent = studentService.findStudent(loginForm.getEmail());
        log.info("loginStudent : {}", loginStudent);
        model.addAttribute("loginStudent", loginStudent.get());
        return "main";
    }

    @GetMapping("/student")
    public String showStudent(Model model) {
        model.addAttribute("students", studentRepository.findAll());

        return "search";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        log.info("logout");
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }
}
