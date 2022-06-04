package mjucapstone.wiseculture.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@Builder
public class MessageForm {
	
	@NotEmpty
	private String receiverUserId;

	@NotEmpty
	private String title;

	@NotEmpty
	private String content;

}
