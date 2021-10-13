package register.demo.domain.student;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

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
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        Long tempId = 2L;
        ReflectionTestUtils.setField(student, "id", tempId);

        //mocking
        given(studentRepository.save(student)).willReturn(tempId);
        given(studentRepository.findById(tempId)).willReturn(student);

        //when
        Long registerId = studentService.join(student);
        Student findStudent = studentService.findStudent(registerId);

        //then
        assertEquals(student.getId(), findStudent.getId());
        assertEquals(student.getPassword(), findStudent.getPassword());
        assertEquals(student.getNickname(), findStudent.getNickname());
    }

    @Test
    public void ID_회원조회() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        Long tempId = 2L;
        ReflectionTestUtils.setField(student, "id", tempId);

        //mocking
        given(studentRepository.save(student)).willReturn(tempId);
        given(studentRepository.findById(tempId)).willReturn(student);

        //when
        Long registerId = studentService.join(student);
        Student findStudent = studentService.findStudent(registerId);

        //then
        assertEquals(student, findStudent);
    }

    @Test
    public void EMAIL_회원조회() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        Long tempId = 2L;
        ReflectionTestUtils.setField(student, "id", tempId);

        //mocking
        given(studentRepository.save(student)).willReturn(tempId);
        given(studentRepository.findByEmail(student.getEmail())).willReturn(Optional.ofNullable(student));

        //when
        studentService.join(student);
        Student findStudent = studentService.findStudent("testID@gmail.com").get();

        //then
        assertEquals(student, findStudent);
    }

}
