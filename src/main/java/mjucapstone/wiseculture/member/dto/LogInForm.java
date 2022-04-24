package mjucapstone.wiseculture.member.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class LogInForm {
	
	@NotEmpty
	private String userId;
	
	@NotEmpty
	private String password;

}
