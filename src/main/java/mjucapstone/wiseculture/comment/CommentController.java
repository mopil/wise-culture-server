package mjucapstone.wiseculture.comment;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.comment.dto.CommentForm;
import mjucapstone.wiseculture.comment.dto.CommentListResponse;
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
@RequiredArgsConstructor
public class CommentController {
	
	private final CommentService commentService;
	private final LoginService loginService;
	
	// 특정 게시글에 있는 댓글 전체 조회
	@GetMapping("/boards/{boardId}/comments")
	public ResponseEntity<?> getComment(@PathVariable Long boardId) {
		CommentListResponse comments = commentService.findAllByBoardId(boardId);
		return success(comments);
	}
	
	// 댓글 작성
	@PostMapping("/boards/{boardId}/comments")
	public ResponseEntity<?> createComment(HttpServletRequest request,
										   @PathVariable Long boardId,
										   @Valid @RequestBody CommentForm commentForm,
										   BindingResult bindingResult) throws LoginException {
		if(bindingResult.hasErrors()) return badRequest(convertJson(bindingResult.getFieldErrors()));
		Member loginMember = loginService.getLoginMember(request);
		Comment comment = commentService.createComment(boardId, commentForm, loginMember);
		return success(comment.toResponse());
	}
	
	// 댓글 수정
	@PutMapping("/boards/comments/{commentId}")
	public ResponseEntity<?> updateComment(HttpServletRequest request,
										   @PathVariable Long commentId,
										   @Valid @RequestBody CommentForm commentForm,
										   BindingResult bindingResult) throws LoginException {
		
		if(bindingResult.hasErrors()) return badRequest(convertJson(bindingResult.getFieldErrors()));
		Member loginMember = loginService.getLoginMember(request);
		Comment comment = commentService.updateComment(commentId, commentForm, loginMember);
		return success(comment.toResponse());
	}
	
	// 댓글 삭제
	@DeleteMapping("/boards/comments/{commentId}")
	public ResponseEntity<?> deleteComment(HttpServletRequest request,
										   @PathVariable Long commentId) throws LoginException {
		Member loginMember = loginService.getLoginMember(request);
		commentService.deleteComment(commentId, loginMember);
		return success(new BoolResponse(true));
	}

}
