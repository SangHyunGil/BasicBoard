package register.demo.web.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
