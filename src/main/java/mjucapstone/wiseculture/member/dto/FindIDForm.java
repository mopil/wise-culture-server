package mjucapstone.wiseculture.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class FindIDForm {
	
	@NotEmpty
	@Email(message = "이메일 양식을 지켜주세요")
	private String email;
	
	@NotEmpty
	private String name;
	
}
