package mjucapstone.wiseculture.board;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.board.dto.BoardForm;
import mjucapstone.wiseculture.board.dto.BoardListResponse;
import mjucapstone.wiseculture.board.dto.BoardResponse;
import mjucapstone.wiseculture.member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	
	// 디비에서 가져온 게시판 엔티티를 응답 객체 리스트로 변환하는 메소드
	private BoardListResponse convertResponse(List<Board> boards) {
		List<BoardResponse> result = new ArrayList<>();
		boards.forEach(board -> result.add(board.toResponse()));
		return new BoardListResponse(result);
	}
	
	// 게시글 목록 조회
	@Transactional(readOnly = true)
	public BoardListResponse findAll() {
		return convertResponse(boardRepository.findAll());
	}
	
	// 게시글 내용 조회
	public Board findById(Long id) {
		Board board = boardRepository.findById(id).orElseThrow(() -> new BoardException("존재하지 않는 게시글"));
		// 조회수 1 증가
		board.addViewCount();
		return board;
	}
	
	// 게시글 작성
	public Board createBoard(BoardForm form, Member loginMember) {
		Board board = Board.builder()
				.title(form.getTitle())
				.content(form.getContent())
				.writer(loginMember)
				.build();
		boardRepository.save(board);
		return board;
	}
	
	// 게시글 내용 수정
	public Board updateBoard(Long boardId, BoardForm form, Member writer) {
		Board board = findById(boardId);
		writerCheck(board, writer);
		board.update(form);
		return board;
	}

	// 게시글 삭제
	public void deleteBoard(Long boardId, Member writer) {
		Board board = findById(boardId);
		writerCheck(board, writer);
		// 게시글 삭제
		boardRepository.deleteById(boardId);
	}
	
	// 게시글 수정, 삭제일때 작성자가 맞는지 확인
	private void writerCheck(Board board, Member writer) {
		if(!Objects.equals(board.getMember().getId(), writer.getId())) throw new BoardException("다른 회원의 게시글");
	}
	
}
