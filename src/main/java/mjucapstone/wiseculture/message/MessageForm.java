package mjucapstone.wiseculture.message;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class MessageForm {
	
	@NotEmpty
	private Long sender;
	
	@NotEmpty
	private Long receiver;
	
	@NotEmpty
	private String content;

}
