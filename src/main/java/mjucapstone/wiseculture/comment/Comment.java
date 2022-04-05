package mjucapstone.wiseculture.comment;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Getter;
import mjucapstone.wiseculture.board.Board;

@Entity @Getter
public class Comment {
	
	@Id @GeneratedValue
	@Column(name = "comment_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;
	
	private String content;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
}
