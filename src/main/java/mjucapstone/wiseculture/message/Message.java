package mjucapstone.wiseculture.message;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import mjucapstone.wiseculture.member.Member;

@Entity
public class Message {
	
	@Id @GeneratedValue
	@Column(name = "message_id")
	private Long id;
	
	@ManyToOne
	private Member sender;
	
	@ManyToOne
	private Member receiver;
	
	private String content;
	private LocalDateTime createdDate;
	
}
