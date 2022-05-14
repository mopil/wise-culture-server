package mjucapstone.wiseculture.board;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.board.dto.BoardForm;
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
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	private final LoginService loginService;
	
	// 게시글 목록 조회
	@GetMapping("")
	public ResponseEntity<?> getBoardList() {
		return success(boardService.findAll());
	}
	
	// 게시글 하나 조회
	@GetMapping("/{boardId}")
	public ResponseEntity<?> getBoard(@PathVariable Long boardId) {
		return success(boardService.findById(boardId));
	}
	
	// 게시글 작성
	@PostMapping("")
	public ResponseEntity<?> createBoard(HttpServletRequest request,
										 @Valid @RequestBody BoardForm boardForm,
										 BindingResult bindingResult) throws LoginException {
		if(bindingResult.hasErrors()) return badRequest(convertJson(bindingResult.getFieldErrors()));
		Member loginMember = loginService.getLoginMember(request);
		Board board = boardService.createBoard(boardForm, loginMember);
		return success(board.toResponse());
	}
	
	// 게시글 수정
	@PutMapping("/{boardId}")
	public ResponseEntity<?> updateBoard(HttpServletRequest request,
										 @PathVariable Long boardId,
										 @Valid @RequestBody BoardForm boardForm, BindingResult bindingResult) throws LoginException {
		if(bindingResult.hasErrors()) return badRequest(convertJson(bindingResult.getFieldErrors()));
		Member loginMember = loginService.getLoginMember(request);
		Board board = boardService.updateBoard(boardId, boardForm, loginMember);
		return success(board.toResponse());
	}
	
	// 게시글 삭제
	@DeleteMapping("/{boardId}")
	public ResponseEntity<?> deleteBoard(HttpServletRequest request,
										 @PathVariable Long boardId) throws LoginException {
		Member loginMember = loginService.getLoginMember(request);
		boardService.deleteBoard(boardId, loginMember);
		return success(new BoolResponse(true));
	}
	
}
