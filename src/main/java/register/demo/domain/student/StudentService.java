package register.demo.domain.student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Student join(Student student);

    Student findStudent(Long studentId);

    Optional<Student> findStudent(String email);

    List<Student> findAllStudent();
}
