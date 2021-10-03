package register.demo.domain.student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    Student save(Student student);

    Student findById(Long id);

    List<Student> findAll();

    Optional<Student> findByEmail(String email);

}
