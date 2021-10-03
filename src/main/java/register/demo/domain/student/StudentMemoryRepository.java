package register.demo.domain.student;

import org.springframework.stereotype.Repository;
import register.demo.domain.student.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class StudentMemoryRepository implements StudentRepository{
    private static Long ID = 0L;
    private static Map<Long, Student> studentMap = new ConcurrentHashMap<>();

    public Student save(Student student) {
        student.setId(++ID);
        studentMap.put(student.getId(), student);
        return student;
    }

    public Student findById(Long id) {
        return studentMap.get(id);
    }

    public List<Student> findAll() {
        return new ArrayList<>(studentMap.values());
    }

    public Optional<Student> findByEmail(String email) {
        return findAll().stream()
                    .filter(m -> m.getEmail().equals(email))
                    .findAny();
    }
}
