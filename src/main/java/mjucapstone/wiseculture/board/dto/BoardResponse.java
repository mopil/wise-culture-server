package mjucapstone.wiseculture.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import mjucapstone.wiseculture.comment.dto.CommentResponse;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class BoardResponse {
    private Long boardId;
    private String writerNickName;
    private String title;
    private String content;
    private int viewCount;
    private String locationTitle;
    private LocalDateTime createdTime;

    private List<CommentResponse> comments;
}
