package mjucapstone.wiseculture.member.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class LoginForm {
	
	@NotEmpty
	private String userId;
	
	@NotEmpty
	private String password;

}
