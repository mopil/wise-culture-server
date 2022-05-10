package mjucapstone.wiseculture.board.controller;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.board.dto.BoardForm;
import mjucapstone.wiseculture.board.service.BoardService;
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
public class BoardController {
	
	private final BoardService boardService;
	
	// 게시글 목록 조회
	@GetMapping("")
	public ResponseEntity<?> getBoardList() {
		return success(boardService.getTitleList());
	}
	
	// 게시글 내용 조회
	@GetMapping("/{id}")
	public ResponseEntity<?> getBoard(@PathVariable Long id) {
		return success(boardService.getPost(id));
	}
	
	// 게시글 작성
	@PostMapping("")
	public ResponseEntity<?> writeBoard(@Valid @RequestBody BoardForm boardForm, BindingResult bindingResult, @Login Member loginMember) {
		if(bindingResult.hasErrors()) return badRequest(convertJson(bindingResult.getFieldErrors()));
		return success(boardService.writePost(boardForm.getTitle(), boardForm.getContent(), boardForm.getLocation(), loginMember));
	}
	
	// 게시글 수정
	@PutMapping("/{id}")
	public ResponseEntity<?> editBoard(@PathVariable Long id, @Valid @RequestBody BoardForm boardForm, BindingResult bindingResult, @Login Member loginMember) {
		if(bindingResult.hasErrors()) return badRequest(convertJson(bindingResult.getFieldErrors()));
		return success(boardService.editPost(id, boardForm.getTitle(), boardForm.getContent(), boardForm.getLocation(), loginMember));
	}
	
	// 게시글 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBoard(@PathVariable Long id, @Login Member loginMember) {
		boardService.deletePost(id, loginMember);
		return success(new BoolResponse(true));
	}
	
}
