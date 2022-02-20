package register.demo.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import register.demo.student.domain.Student;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

}
