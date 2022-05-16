package mjucapstone.wiseculture.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.message.dto.MessageResponse;
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

	private String title;
	private String content;

	@CreationTimestamp
	private LocalDateTime createdTime;

	@Builder
	public Message(Member sender, Member receiver, String title, String content) {
		this.sender = sender;
		this.receiver = receiver;
		this.title = title;
		this.content = content;
	}

	public MessageResponse toResponse() {
		return MessageResponse.builder()
				.messageId(id)
				.sender(sender.toResponse())
				.receiver(receiver.toResponse())
				.title(title)
				.content(content)
				.createdTime(createdTime)
				.build();
	}
	
}
