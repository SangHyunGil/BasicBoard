package register.demo.login;

import register.demo.student.domain.Student;

public interface LoginService {
    Student login(LoginForm loginForm);
}
