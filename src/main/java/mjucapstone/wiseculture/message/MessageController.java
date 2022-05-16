package mjucapstone.wiseculture.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.member.service.LoginService;
import mjucapstone.wiseculture.message.dto.MessageForm;
import mjucapstone.wiseculture.message.dto.MessageListResponse;
import mjucapstone.wiseculture.util.dto.BoolResponse;
import mjucapstone.wiseculture.util.dto.ErrorResponse;
import mjucapstone.wiseculture.util.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static mjucapstone.wiseculture.util.dto.ErrorResponse.convertJson;
import static mjucapstone.wiseculture.util.dto.RestResponse.badRequest;
import static mjucapstone.wiseculture.util.dto.RestResponse.success;

@RestController
@Slf4j
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
	
	private final MessageService messageService;
	private final LoginService loginService;

	/**
	 * 메시지 조회
	 */
	// 메시지 id로 하나 조회
	@GetMapping("/{messageId}")
	public ResponseEntity<?> getMessage(@PathVariable Long messageId) {
		Message message = messageService.findById(messageId);
		return success(message.toResponse());
	}

	// (로그인 유저 기준) 받은 메시지 전체 조회
	@GetMapping("/received")
	public ResponseEntity<?> getAllReceived(HttpServletRequest request) throws LoginException {
		Member loginMember = loginService.getLoginMember(request);
		MessageListResponse allReceived = messageService.getAllReceived(loginMember);
		return success(allReceived);
	}

	// (로그인 유저 기준) 보낸 메시지 전체 조회
	@GetMapping("/sent")
	public ResponseEntity<?> getAllSent(HttpServletRequest request) throws LoginException {
		Member loginMember = loginService.getLoginMember(request);
		MessageListResponse allSent = messageService.getAllSent(loginMember);
		return success(allSent);
	}

	/**
	 * 메시지 생성
	 */
	@PostMapping("")
	public ResponseEntity<?> createMessage(HttpServletRequest request,
										   @Valid @RequestBody MessageForm messageForm,
										   BindingResult bindingResult) throws LoginException {
		if(bindingResult.hasErrors()) return badRequest(convertJson(bindingResult.getFieldErrors()));
		Member loginMember = loginService.getLoginMember(request);
		Message message = messageService.createMessage(loginMember, messageForm);
		return success(message.toResponse());
	}

	/**
	 * 메시지 삭제
	 */
	// 메시지 하나 삭제
	@DeleteMapping("/{messageId}")
	public ResponseEntity<?> deleteMessage(HttpServletRequest request,
										   @PathVariable Long messageId) throws LoginException {
		Member loginMember = loginService.getLoginMember(request);
		messageService.delete(messageId, loginMember);
		return success(new BoolResponse(true));
	}

	// (로그인 기준) 받은 메시지 전체 삭제
	@DeleteMapping("/received")
	public ResponseEntity<?> deleteAllReceived(HttpServletRequest request) throws LoginException {
		Member loginMember = loginService.getLoginMember(request);
		messageService.deleteAllReceived(loginMember);
		return success(new BoolResponse(true));
	}

	// (로그인 기준) 보낸 메시지 전체 삭제
	@DeleteMapping("/sent")
	public ResponseEntity<?> deleteAllSent(HttpServletRequest request) throws LoginException {
		Member loginMember = loginService.getLoginMember(request);
		messageService.deleteAllSent(loginMember);
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
