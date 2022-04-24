package mjucapstone.wiseculture.util.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
// JSON 형태로 내려줘야해서 만들었습니다  (그냥 boolean 타입으로 컨트롤러에서 리턴하면 값만 띠링 찍힘)
public class BoolResponse {
    boolean result;
}
