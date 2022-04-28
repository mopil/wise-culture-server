package mjucapstone.wiseculture.message;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
//@Slf4j
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
	
	// user의 메시지 목록 조회
	@GetMapping("/{userId}")
	public ResponseEntity<?> getMessageList(@PathVariable Long userId) {
		return null;
	}
	
	// user가 user2와 주고받은 메시지 조희
	@GetMapping("/{userId}/{userId2}")
	public ResponseEntity<?> getMessages(@PathVariable Long userId, @PathVariable Long userId2) {
		return null;
	}
	
	// 메시지 쓰기
	@PostMapping("/new")
	public ResponseEntity<?> send(@Valid @RequestBody MessageForm messageForm) {
		return null;
	}
	
	// 메시지 삭제
	@DeleteMapping("/{messageId}")
	public ResponseEntity<?> deleteMessage(@PathVariable Long messageId) {
		return null;
	}
	
}
