package mjucapstone.wiseculture.member.signup;

import lombok.Data;
import mjucapstone.wiseculture.member.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class SignUpDto {

    @NotEmpty(message = "아이디를 입력해 주세요")
    @Size(min = 4, max = 10, message = "아이디는 4~10글자 사이로 입력해주세요")
    private String id;

    @NotEmpty(message = "비밀번호를 입력해 주세요")
    @Size(min = 6, max = 10, message = "비밀번호는 6~10글자 사이로 입력해주세요")
    private String password;

    @NotEmpty(message = "이름을 입력해 주세요")
    private String name;

    @NotEmpty(message = "이메일을 입력해 주세요")
    @Email(message = "이메일 양식을 지켜주세요")
    private String email;

    @NotEmpty(message = "전화번호를 입력해 주세요")
    private String phoneNumber;

    @Size(min = 2, max = 10, message = "닉네임은 2~10글자 사이로 입력해주세요")
    private String nickname;

    public Member buildMember(String encryptedPassword) {
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
