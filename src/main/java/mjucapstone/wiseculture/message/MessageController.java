package mjucapstone.wiseculture.message;

import static mjucapstone.wiseculture.util.dto.ErrorResponse.convertJson;
import static mjucapstone.wiseculture.util.dto.RestResponse.badRequest;
import static mjucapstone.wiseculture.util.dto.RestResponse.success;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.member.config.Login;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.util.dto.BoolResponse;

@RestController
//@Slf4j
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
	
	private final MessageService messageService;
	
	// 대화 상대 조회
	@GetMapping("")
	public ResponseEntity<?> getUser(@Login Member loginMember) {
		return success(messageService.getContact(loginMember));
	}
	
	// user와 주고받은 메시지 조희
	@GetMapping("/{userId}")
	public ResponseEntity<?> getMessage(@PathVariable Long userId, @Login Member loginMember) {
		return success(messageService.getMessages(loginMember, userId));
	}
	
	// 메시지 쓰기
	@PostMapping("/new")
	public ResponseEntity<?> send(@Valid @RequestBody MessageForm messageForm, BindingResult bindingResult, @Login Member loginMember) {
		if(bindingResult.hasErrors()) return badRequest(convertJson(bindingResult.getFieldErrors()));
		return success(messageService.send(messageForm.getSender(), messageForm.getReceiver(), messageForm.getContent(), loginMember));
	}
	
	// 메시지 삭제
	@DeleteMapping("/{messageId}")
	public ResponseEntity<?> deleteMessage(@PathVariable Long messageId, @Login Member loginMember) {
		return success(new BoolResponse(true));
	}
	
}
