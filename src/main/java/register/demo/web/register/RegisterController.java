package register.demo.web.register;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import register.demo.domain.student.Student;
import register.demo.domain.student.StudentService;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final StudentService studentService;

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Validated @ModelAttribute RegisterForm registerForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {


        if(isExistStudent(registerForm).isPresent()) {
            bindingResult.reject("existStudent", null);
        }

        if(!registerForm.getPassword().equals(registerForm.getPasswordConfirm())) {
            bindingResult.reject("passwordFail", null);
        }
        log.info("bindingresult - " + bindingResult);
        if (bindingResult.hasErrors()) {
            return "register";
        }



        Student student = new Student(registerForm.getEmail(), registerForm.getPassword(), registerForm.getName(), registerForm.getNickname(),
                registerForm.getDepartment(), registerForm.getMajor());
        studentService.join(student);

        redirectAttributes.addAttribute("register", "true");

        return "redirect:/";
    }

    private Optional<Student> isExistStudent(RegisterForm registerForm) {
        return studentService.findAllStudent().stream()
                .filter(m -> m.getEmail().equals(registerForm.getEmail()))
                .findAny();
    }
}
