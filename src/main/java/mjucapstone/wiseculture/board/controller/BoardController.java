package mjucapstone.wiseculture.board.controller;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.board.dto.BoardForm;
import mjucapstone.wiseculture.board.service.BoardService;
import mjucapstone.wiseculture.member.config.Login;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.member.repository.MemberRepository;
import mjucapstone.wiseculture.util.dto.BoolResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Optional;

import static mjucapstone.wiseculture.util.dto.ErrorResponse.convertJson;
import static mjucapstone.wiseculture.util.dto.RestResponse.badRequest;
import static mjucapstone.wiseculture.util.dto.RestResponse.success;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	private final MemberRepository memberRepository;
	
	// 게시글 목록 조회
	@GetMapping("")
	public ResponseEntity<?> getBoardList() {
		return success(boardService.getTitleList());
	}
	
	// 게시글 내용 조회
	@GetMapping("/{boardId}")
	public ResponseEntity<?> getBoard(@PathVariable Long boardId) {
		return success(boardService.getPost(boardId));
	}
	
	// 게시글 작성
	@PostMapping("")
	public ResponseEntity<?> writeBoard(@Valid @RequestBody BoardForm boardForm, BindingResult bindingResult, @Login Member loginMember) {
		if(bindingResult.hasErrors()) return badRequest(convertJson(bindingResult.getFieldErrors()));
		return success(boardService.writePost(boardForm.getTitle(), boardForm.getContent(), loginMember));
	}
	
	// 게시글 수정
	@PutMapping("/{boardId}")
	public ResponseEntity<?> editBoard(@PathVariable Long boardId, @Valid @RequestBody BoardForm boardForm, BindingResult bindingResult, @Login Member loginMember) {
		if(bindingResult.hasErrors()) return badRequest(convertJson(bindingResult.getFieldErrors()));
		return success(boardService.editPost(boardId, boardForm.getTitle(), boardForm.getContent(), loginMember));
	}
	
	// 게시글 삭제
	@DeleteMapping("/{boardId}")
	public ResponseEntity<?> deleteBoard(@PathVariable Long boardId, @Login Member loginMember) {
		boardService.deletePost(boardId, loginMember);
		return success(new BoolResponse(true));
	}

	/**
	 * 로그인 없는 버전
	 */
	// 게시글 작성
	@PostMapping("/{memberId}")
	public ResponseEntity<?> writeBoardWithoutLogin(@PathVariable Long memberId, @Valid @RequestBody BoardForm boardForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) return badRequest(convertJson(bindingResult.getFieldErrors()));
		Optional<Member> member = memberRepository.findById(memberId);
		return success(boardService.writePost(boardForm.getTitle(), boardForm.getContent(), member.get()));
	}

	// 게시글 수정
	@PutMapping("/{memberId}/{boardId}")
	public ResponseEntity<?> editBoardWithoutLogin(@PathVariable Long memberId, @PathVariable Long boardId, @Valid @RequestBody BoardForm boardForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) return badRequest(convertJson(bindingResult.getFieldErrors()));
		Optional<Member> member = memberRepository.findById(memberId);
		return success(boardService.editPost(boardId, boardForm.getTitle(), boardForm.getContent(), member.get()));
	}

	// 게시글 삭제
	@DeleteMapping("/{memberId}/{boardId}")
	public ResponseEntity<?> deleteBoardWithoutLogin(@PathVariable Long memberId, @PathVariable Long boardId) {
		Optional<Member> member = memberRepository.findById(memberId);
		boardService.deletePost(boardId, member.get());
		return success(new BoolResponse(true));
	}
	
}
