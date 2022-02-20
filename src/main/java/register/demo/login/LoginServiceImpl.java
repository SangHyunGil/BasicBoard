package register.demo.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import register.demo.student.repository.StudentRepository;
import register.demo.student.domain.Student;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginServiceImpl implements LoginService{

    private final StudentRepository studentRepository;

    @Transactional(readOnly = true)
    public Student login(LoginForm loginForm) {
        return studentRepository.findByEmail(loginForm.getEmail())
                    .filter(student -> student.getPassword().equals(loginForm.getPassword()))
                    .orElse(null);
    }
}
