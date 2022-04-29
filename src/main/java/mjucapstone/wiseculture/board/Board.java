package mjucapstone.wiseculture.board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;

import lombok.Builder;
import lombok.Getter;
import mjucapstone.wiseculture.comment.Comment;
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

	@OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private Location location;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	@Builder
	public Board(String title, String content, Location location, Member writer) {
		this.title 		= title;
		this.content 	= content;
		this.location 	= location;
		this.member 	= writer;
	}
	
}
