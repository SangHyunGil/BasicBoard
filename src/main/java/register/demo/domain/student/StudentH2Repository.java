package register.demo.domain.student;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import register.demo.domain.student.Student;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class StudentH2Repository implements StudentRepository{

    private final EntityManager em;

    public Long save(Student student) {
        em.persist(student);
        return student.getId();
    }

    public Student findById(Long id) {
        return em.find(Student.class, id);
    }

    public List<Student> findAll() {
        return em.createQuery("select s from Student s", Student.class)
                .getResultList();
    }

    public Optional<Student> findByEmail(String email) {
        return Optional.ofNullable(em.createQuery("select s from Student s where s.email =: email", Student.class)
                .setParameter("email", email)
                .getSingleResult());
    }
}
