package mjucapstone.wiseculture.member.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class FindIdForm {
	
	@NotEmpty
	@Email(message = "이메일 양식을 지켜주세요")
	private String email;
	
}
