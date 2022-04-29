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

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mjucapstone.wiseculture.comment.Comment;
import mjucapstone.wiseculture.location.Location;
import mjucapstone.wiseculture.member.domain.Member;

@NoArgsConstructor
@Entity @Getter
public class Board {
	
	@Id @GeneratedValue
	@Column(name = "board_id")
	private Long id;
	
	private String title;
	
	//@CreatedDate
	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdDate;
	
	private String content;
	private int viewCount;

	//@OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
	@OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Comment> comments = new ArrayList<>();
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private Location location;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	@Builder
	public Board(String title, String content, Location location, Member writer) {
		this.title		= title;
		this.content	= content;
		this.location	= location;
		this.member		= writer;
		
		this.viewCount	= 0;
	}
	
}
