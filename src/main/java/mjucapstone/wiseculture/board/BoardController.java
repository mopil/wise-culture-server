package mjucapstone.wiseculture.board;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	
	// 게시글 목록 조회
	@GetMapping("")
	public ResponseEntity<?> getList() {
		return null;
	}
	
	// 게시글 내용 조회
	@GetMapping("/{id}")
	public ResponseEntity<?> get() {
		return null;
	}
	
	// 게시글 작성
	@PostMapping("")
	public ResponseEntity<?> write() {
		return null;
	}
	
	// 게시글 수정
	@PutMapping("/{id}")
	public ResponseEntity<?> edit() {
		return null;
	}
	
	// 게시글 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete() {
		return null;
	}
	
}
