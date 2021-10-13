package register.demo.domain.student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Long join(Student student);

    Student findStudent(Long id);

    Optional<Student> findStudent(String email);

    List<Student> findAllStudent();
}
