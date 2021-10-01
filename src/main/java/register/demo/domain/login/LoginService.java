package register.demo.domain.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import register.demo.web.login.LoginForm;
import register.demo.domain.student.Student;
import register.demo.domain.student.StudentRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final StudentRepository studentRepository;

    public Student login(LoginForm loginForm) {
        return studentRepository.findByEmail(loginForm.getEmail())
                    .filter(student -> student.getPassword().equals(loginForm.getPassword()))
                    .orElse(null);
    }
}
