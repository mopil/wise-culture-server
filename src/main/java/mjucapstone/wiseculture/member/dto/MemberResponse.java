package mjucapstone.wiseculture.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MemberResponse {
    private Long memberId;
    private String userId;
    private String email;
    private String name;
    private String nickname;
    private String password;
    private int point;
    private String phoneNumber;
    private String sessionId;
}
