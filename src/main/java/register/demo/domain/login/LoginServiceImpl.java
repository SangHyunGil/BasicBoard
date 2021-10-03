package register.demo.domain.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import register.demo.domain.student.StudentRepository;
import register.demo.web.login.LoginForm;
import register.demo.domain.student.Student;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final StudentRepository studentRepository;

    public Student login(LoginForm loginForm) {
        return studentRepository.findByEmail(loginForm.getEmail())
                    .filter(student -> student.getPassword().equals(loginForm.getPassword()))
                    .orElse(null);
    }
}
