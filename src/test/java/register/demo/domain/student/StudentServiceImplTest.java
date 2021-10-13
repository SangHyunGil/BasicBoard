package register.demo.domain.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StudentServiceImplTest {

    @Autowired
    StudentService studentService;
    @Autowired
    StudentRepository studentRepository;

    @Test
    public void 회원가입() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");

        //when
        Long id = studentService.join(student);

        //then
        assertEquals(student, studentRepository.findById(id));
    }
    @Test
    public void EMAIL_회원조회() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");

        //when
        studentService.join(student);

        //then
        assertEquals(student, studentRepository.findByEmail("testID@gmail.com").get());
    }
    @Test
    public void ID_회원조회() throws Exception {
        //given

        //when
        String nickname = studentRepository.findById(1L).getNickname();

        //then
        assertEquals("qwe", nickname);
    }

    @Test
    public void 전체회원조회() throws Exception {
        //given
        Student student1 = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        Student student2 = new Student("testID2@gmail.com", "testPW", "테스터2", "테스터2", "컴공", "백엔드");

        //when
        studentService.join(student1);
        studentService.join(student2);

        List<Student> allStudent = studentService.findAllStudent();

        //then
        assertEquals(3, allStudent.size());

    }

}