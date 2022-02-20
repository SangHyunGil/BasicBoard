package register.demo.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class LoginForm {
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    public LoginForm() {
    }
}
