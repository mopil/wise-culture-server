package mjucapstone.wiseculture.comment.controller;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.comment.dto.CommentForm;
import mjucapstone.wiseculture.comment.service.CommentService;
import mjucapstone.wiseculture.member.config.Login;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.util.dto.BoolResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static mjucapstone.wiseculture.util.dto.ErrorResponse.convertJson;
import static mjucapstone.wiseculture.util.dto.RestResponse.badRequest;
import static mjucapstone.wiseculture.util.dto.RestResponse.success;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class CommentController {
	
	private final CommentService commentService;
	
	// 댓글 조회
	@GetMapping("/{boardId}/comment")
	public ResponseEntity<?> getComment(@PathVariable Long boardId) {
		return success(commentService.getComments(boardId));
	}
	
	// 댓글 작성
	@PostMapping("/{boardId}/comment")
	public ResponseEntity<?> writeComment(@PathVariable Long boardId, 
			@Valid @RequestBody CommentForm commentForm,
			BindingResult bindingResult, @Login Member loginMember) {
		
		if(bindingResult.hasErrors()) return badRequest(convertJson(bindingResult.getFieldErrors()));
		
		return success(commentService.writeComment(boardId, commentForm.getContent(), loginMember));
	}
	
	// 댓글 수정
	@PutMapping("/{boardId}/comment/{commentId}")
	public ResponseEntity<?> editComment(@PathVariable Long boardId, @PathVariable Long commentId, 
			@Valid @RequestBody CommentForm commentForm, 
			BindingResult bindingResult, @Login Member loginMember) {
		
		if(bindingResult.hasErrors()) return badRequest(convertJson(bindingResult.getFieldErrors()));
		
		return success(commentService.editComment(commentId, commentForm.getContent(), loginMember));
	}
	
	// 댓글 삭제
	@DeleteMapping("/{boardId}/comment/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable Long boardId, @PathVariable Long commentId, @Login Member loginMember) {
		commentService.deleteComment(commentId, loginMember);
		return success(new BoolResponse(true));
	}

}
