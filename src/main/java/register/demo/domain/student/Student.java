package register.demo.domain.student;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
public class Student {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String department;
    private String major;

    public Student() {
    }

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
