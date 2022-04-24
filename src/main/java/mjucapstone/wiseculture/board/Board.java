package mjucapstone.wiseculture.board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import mjucapstone.wiseculture.comment.Comment;
import org.springframework.data.annotation.CreatedDate;

import mjucapstone.wiseculture.location.Location;
import mjucapstone.wiseculture.member.domain.Member;

@Entity @Getter
public class Board {
	
	@Id @GeneratedValue
	@Column(name = "board_id")
	private Long id;
	
	private String title;
	
	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdDate;
	
	private String content;
	private int viewCount;

	@OneToMany(mappedBy = "board")
	private List<Comment> comments = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private Location location;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
}
