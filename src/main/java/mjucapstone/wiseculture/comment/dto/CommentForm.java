package mjucapstone.wiseculture.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class CommentForm {

	@NotEmpty
	private String writerNickname;

	@NotEmpty
	private String content;

}
