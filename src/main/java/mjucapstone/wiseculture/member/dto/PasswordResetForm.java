package mjucapstone.wiseculture.member.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class PasswordResetForm {

    @NotEmpty
    private String userId;

    @NotEmpty
    @Email
    private String email;
}
