package mjucapstone.wiseculture.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import mjucapstone.wiseculture.member.dto.MemberResponse;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class MessageResponse {
    private Long messageId;
    private MemberResponse sender;
    private MemberResponse receiver;
    private String title;
    private String content;
    private LocalDateTime createdTime;
}
