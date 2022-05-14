package mjucapstone.wiseculture.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BoardListResponse {

    private List<BoardResponse> boards;
}
