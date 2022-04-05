package mjucapstone.wiseculture.comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Comment {
	
	@Id @GeneratedValue
	@Column(name = "comment_id")
	private Long id;
	
	@Column(name = "board_id")
	private Long boardId;
	
	private String content;
	
	@Column(name = "created_at")
	private String createdAt;
	
}
