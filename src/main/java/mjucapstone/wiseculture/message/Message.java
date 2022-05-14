package mjucapstone.wiseculture.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mjucapstone.wiseculture.member.domain.Member;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

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
