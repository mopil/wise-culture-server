package mjucapstone.wiseculture.member.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ModifyMemberForm {
	
	@NotEmpty
	@Size(min = 4, max = 10, message = "아이디를 입력해주세요")
	private String id;
	
	@Size(min = 6, max = 10, message = "비밀번호는 6~10글자 사이로 입력해주세요")
	private String password;
	
	@Size(min = 2, max = 10, message = "닉네임은 2~10글자 사이로 입력해주세요")
	private String nickname;
	
}
