package mjucapstone.wiseculture.comment;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class CommentForm {
	
	//@NotNull
	//private Long board;
	
	@NotEmpty
	private String content;

}
