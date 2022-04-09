package mjucapstone.wiseculture.message;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Getter;
import mjucapstone.wiseculture.member.domain.Member;
import org.hibernate.annotations.CreationTimestamp;

@Entity @Getter
public class Message {
	
	@Id @GeneratedValue
	@Column(name = "message_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Member sender;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Member receiver;
	
	private String content;

	@CreationTimestamp
	private LocalDateTime createdDate;
	
}
