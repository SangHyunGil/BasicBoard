package register.demo.domain.student;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class StudentH2Repository implements StudentRepository{

    private final EntityManager em;

    public Long save(Student student) {
        log.info("saveStudent");
        em.persist(student);
        return student.getId();
    }

    public Student findById(Long id) {
        log.info("StudentFindById");
        return em.find(Student.class, id);
    }

    public List<Student> findAll() {
        log.info("StudentFindAll");
        return em.createQuery("select s from Student s", Student.class)
                .getResultList();
    }

    public Optional<Student> findByEmail(String email) {
        log.info("StudentFindByEmail");
        return Optional.ofNullable(em.createQuery("select s from Student s where s.email =: email", Student.class)
                .setParameter("email", email)
                .getSingleResult());
    }
}
