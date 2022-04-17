package mjucapstone.wiseculture.member.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class ChangePasswordForm {

    @NotEmpty
    private String currentPassword;

    @NotEmpty
    @Size(min = 6, max = 10, message = "비밀번호는 6~10글자 사이로 입력해주세요")
    private String newPassword;

    @NotEmpty
    private String newPasswordCheck;
}
