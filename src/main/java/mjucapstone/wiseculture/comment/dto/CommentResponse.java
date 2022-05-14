package mjucapstone.wiseculture.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class CommentResponse {
    private Long commentId;
    private String writerNickname;
    private String content;
    private LocalDateTime createdTime;
}
