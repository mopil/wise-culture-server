package mjucapstone.wiseculture.message.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class MessageForm {
	
	@NotNull
	private Long sender;
	
	@NotNull
	private Long receiver;
	
	@NotEmpty
	private String content;

}
