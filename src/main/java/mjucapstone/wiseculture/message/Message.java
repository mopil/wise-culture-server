package mjucapstone.wiseculture.message;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mjucapstone.wiseculture.member.domain.Member;

@NoArgsConstructor
@Entity @Getter
public class Message {
	
	@Id @GeneratedValue
	@Column(name = "message_id")
	private Long id;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Member sender;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Member receiver;
	
	private String content;

	@CreationTimestamp
	private LocalDateTime createdDate;

	@Builder
	public Message(Member sender, Member receiver, String content) {
		this.sender = sender;
		this.receiver = receiver;
		this.content = content;
	}
	
}
