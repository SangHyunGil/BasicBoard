package register.demo.domain.login;

import register.demo.domain.student.Student;
import register.demo.web.login.LoginForm;

public interface LoginService {
    Student login(LoginForm loginForm);
}
