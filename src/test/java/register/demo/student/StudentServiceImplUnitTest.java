package register.demo.student;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import register.demo.student.domain.Student;
import register.demo.student.repository.StudentRepository;
import register.demo.student.service.StudentServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplUnitTest {

    @InjectMocks
    StudentServiceImpl studentService;

    @Mock
    StudentRepository studentRepository;

    @Test
    public void 회원가입() throws Exception {
        //given
        Long studentId = 2L;
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        ReflectionTestUtils.setField(student, "id", studentId);

        //mocking
        given(studentRepository.save(student)).willReturn(student);

        //when
        Student joinStudent = studentService.join(student);

        //then
        assertEquals(student.getId(), joinStudent.getId());
        assertEquals(student.getPassword(), joinStudent.getPassword());
        assertEquals(student.getNickname(), joinStudent.getNickname());
    }

    @Test
    public void ID_회원조회() throws Exception {
        //given
        Long studentId = 2L;
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        ReflectionTestUtils.setField(student, "id", studentId);

        //mocking
        given(studentRepository.save(student)).willReturn(student);
        given(studentRepository.findById(studentId)).willReturn(Optional.ofNullable(student));

        //when
        Student joinStudent = studentService.join(student);
        Student findStudent = studentService.findStudent(joinStudent.getId());

        //then
        assertEquals(student, findStudent);
    }

    @Test
    public void EMAIL_회원조회() throws Exception {
        //given
        Long studentId = 2L;
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        ReflectionTestUtils.setField(student, "id", studentId);

        //mocking
        given(studentRepository.save(student)).willReturn(student);
        given(studentRepository.findByEmail(student.getEmail())).willReturn(Optional.ofNullable(student));

        //when
        studentService.join(student);
        Student findStudent = studentService.findStudent("testID@gmail.com").get();

        //then
        assertEquals(student, findStudent);
    }

}
