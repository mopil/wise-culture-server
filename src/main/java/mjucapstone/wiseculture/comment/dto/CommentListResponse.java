package mjucapstone.wiseculture.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CommentListResponse {
    private List<CommentResponse> comments;
}
