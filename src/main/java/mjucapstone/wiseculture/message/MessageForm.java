package mjucapstone.wiseculture.message;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MessageForm {
	
	@NotNull
	private Long sender;
	
	@NotNull
	private Long receiver;
	
	@NotEmpty
	private String content;

}
