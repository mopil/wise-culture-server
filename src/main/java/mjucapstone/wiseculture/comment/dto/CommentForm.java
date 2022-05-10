package mjucapstone.wiseculture.comment.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CommentForm {

	@NotEmpty
	private String content;

}
