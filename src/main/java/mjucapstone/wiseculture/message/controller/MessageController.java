package mjucapstone.wiseculture.message.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjucapstone.wiseculture.member.config.Login;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.message.dto.MessageForm;
import mjucapstone.wiseculture.message.exception.MessageException;
import mjucapstone.wiseculture.message.service.MessageService;
import mjucapstone.wiseculture.util.dto.BoolResponse;
import mjucapstone.wiseculture.util.dto.ErrorResponse;
import mjucapstone.wiseculture.util.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static mjucapstone.wiseculture.util.dto.ErrorResponse.convertJson;
import static mjucapstone.wiseculture.util.dto.RestResponse.badRequest;
import static mjucapstone.wiseculture.util.dto.RestResponse.success;

@RestController
@Slf4j
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
	@GetMapping("/{memberId}")
	public ResponseEntity<?> getMessage(@PathVariable Long memberId, @Login Member loginMember) {
		return success(messageService.getMessages(loginMember, memberId));
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
		messageService.delete(messageId, loginMember);
		return success(new BoolResponse(true));
	}

	/**
	 * 예외 처리
	 */
	@ExceptionHandler(MessageException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<?> messageExHandle(MessageException e) {
		log.error("쪽지 예외 발생 : 핸들러 작동");
		return badRequest(new ErrorResponse(ErrorCode.MESSAGE_ERROR, e.getMessage()));
	}
	
}
