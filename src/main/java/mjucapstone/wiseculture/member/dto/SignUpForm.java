package mjucapstone.wiseculture.member.dto;

import lombok.Data;
import mjucapstone.wiseculture.member.domain.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class SignUpForm {

    @NotEmpty
    @Size(min = 4, max = 10, message = "아이디는 4~10글자 사이로 입력해주세요")
    private String id;

    @NotEmpty
    @Size(min = 6, max = 10, message = "비밀번호는 6~10글자 사이로 입력해주세요")
    private String password;

    @NotEmpty
    private String name;

    @NotEmpty
    @Email(message = "이메일 양식을 지켜주세요")
    private String email;

    @NotEmpty
    private String phoneNumber;

    @Size(min = 2, max = 10, message = "닉네임은 2~10글자 사이로 입력해주세요")
    private String nickname;

    public Member toMember(String encryptedPassword) {
        return Member.builder()
                .userId(this.id)
                .name(this.name)
                .password(encryptedPassword)
                .nickname(this.nickname)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .build();
    }
}
