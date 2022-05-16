package mjucapstone.wiseculture.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MessageListResponse {
    private List<MessageResponse> messages;
}
