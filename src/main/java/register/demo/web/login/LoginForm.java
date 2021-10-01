package register.demo.web.login;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class LoginForm {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
