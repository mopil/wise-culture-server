package mjucapstone.wiseculture.comment.controller;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.comment.dto.CommentForm;
import mjucapstone.wiseculture.comment.service.CommentService;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.member.service.LoginService;
import mjucapstone.wiseculture.util.dto.BoolResponse;
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
@RequestMapping("/board")
@RequiredArgsConstructor
public class CommentController {
	
	private final CommentService commentService;
	private final LoginService loginService;
	
	// 댓글 조회
	@GetMapping("/{boardId}/comment")
	public ResponseEntity<?> getComment(@PathVariable Long boardId) {
		return success(commentService.getComments(boardId));
	}
	
	// 댓글 작성
	@PostMapping("/{boardId}/comment")
	public ResponseEntity<?> writeComment(HttpServletRequest request,
										  @PathVariable Long boardId,
										  @Valid @RequestBody CommentForm commentForm,
										  BindingResult bindingResult) throws LoginException {
		if(bindingResult.hasErrors()) return badRequest(convertJson(bindingResult.getFieldErrors()));
		Member loginMember = loginService.getLoginMember(request);
		return success(commentService.writeComment(boardId, commentForm.getContent(), loginMember));
	}
	
	// 댓글 수정
	@PutMapping("/{boardId}/comment/{commentId}")
	public ResponseEntity<?> editComment(HttpServletRequest request,
										 @PathVariable Long boardId,
										 @PathVariable Long commentId,
										 @Valid @RequestBody CommentForm commentForm,
										 BindingResult bindingResult) throws LoginException {
		
		if(bindingResult.hasErrors()) return badRequest(convertJson(bindingResult.getFieldErrors()));
		Member loginMember = loginService.getLoginMember(request);
		return success(commentService.editComment(commentId, commentForm.getContent(), loginMember));
	}
	
	// 댓글 삭제
	@DeleteMapping("/{boardId}/comment/{commentId}")
	public ResponseEntity<?> deleteComment(HttpServletRequest request,
										   @PathVariable Long boardId,
										   @PathVariable Long commentId) throws LoginException {
		Member loginMember = loginService.getLoginMember(request);
		commentService.deleteComment(commentId, loginMember);
		return success(new BoolResponse(true));
	}

}
