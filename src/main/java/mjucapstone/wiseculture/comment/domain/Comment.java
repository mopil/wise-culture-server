package mjucapstone.wiseculture.comment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mjucapstone.wiseculture.board.domain.Board;
import mjucapstone.wiseculture.member.domain.Member;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity @Getter
public class Comment {
	
	@Id @GeneratedValue
	@Column(name = "comment_id")
	private Long id;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;
	
	private String content;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@Builder
	public Comment(Board board, String content, Member writer) {
		this.board		= board;
		this.content	= content;
		this.member		= writer;
	}
	
}