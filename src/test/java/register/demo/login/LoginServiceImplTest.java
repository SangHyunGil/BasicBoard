package register.demo.login;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import register.demo.student.domain.Student;
import register.demo.student.service.StudentService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LoginServiceImplTest {

    @Autowired
    StudentService studentService;
    @Autowired
    LoginService loginService;

    @Test
    public void 로그인테스트() throws Exception {
        //given
        Student student1 = new Student("testId@gmail.com", "testPw", "테스터", "테스터", "컴공", "백엔드");
        studentService.join(student1);
        Student studentA = studentService.findStudent("testId@gmail.com").get();

        Student student2 = new Student("testId2@gmail.com", "testPw", "테스터2", "테스터2", "컴공", "백엔드");
        studentService.join(student2);
        Student studentB = studentService.findStudent("testId2@gmail.com").get();

        //when
        LoginForm successLoginForm = new LoginForm("testId@gmail.com", "testPw");
        Student successLogin = loginService.login(successLoginForm);

        LoginForm failLoginForm = new LoginForm("testId2@gmail.com", "fail");
        Student failLogin = loginService.login(failLoginForm);

        //then
        assertEquals(studentA, successLogin);
        assertEquals(null, failLogin);
    }
}