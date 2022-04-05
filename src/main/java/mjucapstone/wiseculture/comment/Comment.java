package mjucapstone.wiseculture.comment;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import mjucapstone.wiseculture.board.Board;

@Entity
public class Comment {
	
	@Id @GeneratedValue
	@Column(name = "comment_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "board_id")
	private Board board;
	
	private String content;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
}
