package mjucapstone.wiseculture.board;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Getter;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private Location location;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
}
