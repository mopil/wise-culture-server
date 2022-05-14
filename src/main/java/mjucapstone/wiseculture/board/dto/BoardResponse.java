package mjucapstone.wiseculture.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

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
    private LocalDateTime createdDate;
}
