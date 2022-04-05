package mjucapstone.wiseculture.message;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import mjucapstone.wiseculture.member.Member;

@Entity
public class Message {
	
	@Id @GeneratedValue
	@Column(name = "message_id")
	private Long id;
	
	@Column(name = "member_id")
	private Member member;
	
	private String sender;
	private String receiver;
	private String content;
	private LocalDateTime createdDate;
	
}
