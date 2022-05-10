package mjucapstone.wiseculture.board.service;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.board.domain.Board;
import mjucapstone.wiseculture.board.dto.BoardSummary;
import mjucapstone.wiseculture.board.exception.BoardException;
import mjucapstone.wiseculture.board.repository.BoardRepository;
import mjucapstone.wiseculture.comment.repository.CommentRepository;
import mjucapstone.wiseculture.member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

	private final CommentRepository commentRepository;
	private final BoardRepository boardRepository;
	
	// 게시글 목록 조회
	public List<BoardSummary> getTitleList() {
		return boardRepository.findAllBy();
	}
	
	// 게시글 내용 조회
	@Transactional
	public Board getPost(Long id) {
		Board board = boardRepository.findById(id).orElseThrow(() -> new BoardException("존재하지 않는 게시글"));
		
		// 조회수 1 증가
		boardRepository.updateViewCount(id, board.getViewCount() + 1);
		
		return board;
	}
	
	// 게시글 작성
	@Transactional
	public Board writePost(String title, String content, Member writer) {
		Board board = Board.builder()
				.title(title)
				.content(content)
				.writer(writer)
				.build();
		
		// 게시글 저장
		boardRepository.save(board);
		
		return board;
	}
	
	// 게시글 수정
	@Transactional
	public Board editPost(Long id, String title, String content, Member writer) {
		if(!boardRepository.existsById(id)) throw new BoardException("존재하지 않는 게시글");
		if(!Objects.equals(boardRepository.findWriterById(id)
				.orElseThrow(() -> new BoardException("작성자가 존재하지 않음"))
				.getId(), writer.getId()))
			throw new BoardException("다른 회원의 게시글");
			
		// 게시글 업데이트
		boardRepository.updateBoard(id, title, content);
			
		return boardRepository.findById(id).get(); // 위에서 이미 존재하지 않는 경우 체크 됨
	}
	
	// 게시글 삭제
	@Transactional
	public void deletePost(Long id, Member writer) {
		
		Board board = boardRepository.findById(id).orElseThrow(() -> new BoardException("존재하지 않는 게시글"));
		if(!Objects.equals(board.getMember().getId(), writer.getId())) throw new BoardException("다른 회원의 게시글");
		
		// 댓글 삭제
		commentRepository.deleteAllByBoard(board);
		
		// 게시글 삭제
		boardRepository.deleteById(id);
	}
	
}
