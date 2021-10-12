package register.demo.domain.student;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;

    public void join(Student student) {
        studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        return studentRepository.findById(id);
    }

    public Optional<Student> findStudent(String email) {
        return studentRepository.findByEmail(email);
    }

    public List<Student> findAllStudent() {
        return studentRepository.findAll();
    }
}
