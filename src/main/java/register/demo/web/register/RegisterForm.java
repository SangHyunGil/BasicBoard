package register.demo.web.register;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class RegisterForm {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String passwordConfirm;
    @NotBlank
    private String name;
    private String nickname;
    private String department;
    private String major;

    public RegisterForm() {
    }

    public RegisterForm(String email, String password, String passwordConfirm, String name, String nickname, String department, String major) {
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.name = name;
        this.nickname = nickname;
        this.department = department;
        this.major = major;
    }
}
