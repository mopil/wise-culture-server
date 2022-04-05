package mjucapstone.wiseculture.board;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;

@Entity
public class Board {
	
	@Id @GeneratedValue
	@Column(name = "board_id")
	private Long id;
	
	private String title;
	
	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	private String content;
	private Integer viewCount;
	
}
