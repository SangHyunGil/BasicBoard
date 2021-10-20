package register.demo.domain.student;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@Getter
@SequenceGenerator(
        name = "STUDENT_SEQ_GENERATOR",
        sequenceName = "STUDENT_SEQ"
)
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STUDENT_SEQ_GENERATOR")
    @Column(name = "student_id")
    private Long id;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String department;
    private String major;

    protected Student() {
    }

    @Builder
    public Student(String email, String password, String name, String nickname, String department, String major) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.department = department;
        this.major = major;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
