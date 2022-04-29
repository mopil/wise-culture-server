package mjucapstone.wiseculture.board;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.comment.CommentRepository;
import mjucapstone.wiseculture.location.Location;
import mjucapstone.wiseculture.member.domain.Member;

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
	public Board writePost(String title, String content, Location location, Member writer) {
		Board board = Board.builder()
				.title(title)
				.content(content)
				.location(location)
				.writer(writer)
				.build();
		
		// 게시글 저장
		boardRepository.save(board);
		
		return board;
	}
	
	// 게시글 수정
	@Transactional
	public Board editPost(Long id, String title, String content, Location location, Member writer) {
		if(boardRepository.existsById(id) == false) throw new BoardException("존재하지 않는 게시글");
		if(boardRepository.findWriterById(id)
				.orElseThrow(() -> new BoardException("작성자가 존재하지 않음"))
				.getId() != writer.getId()) 
			throw new BoardException("다른 회원의 게시글");
			
		// 게시글 업데이트
		boardRepository.updateBoard(id, title, content, location);
			
		return boardRepository.findById(id).get(); // 위에서 이미 존재하지 않는 경우 체크 됨
	}
	
	// 게시글 삭제
	@Transactional
	public void deletePost(Long id, Member writer) {
		/*
		if(boardRepository.existsById(id) == false) throw new BoardException("존재하지 않는 게시글");
		if(boardRepository.findWriterById(id)
				.orElseThrow(() -> new BoardException("작성자가 존재하지 않음"))
				.getId() != writer.getId()) 
			throw new BoardException("다른 회원의 게시글");
		
		// 댓글 삭제
		commentRepository.deleteAllByBoard(boardRepository.findById(id).orElseThrow(() -> new BoardException("존재하지 않는 게시글")));
		*/
		
		Board board = boardRepository.findById(id).orElseThrow(() -> new BoardException("존재하지 않는 게시글"));
		if(board.getMember().getId() != writer.getId()) throw new BoardException("다른 회원의 게시글");
		
		// 댓글 삭제
		commentRepository.deleteAllByBoard(board);
		
		// 게시글 삭제
		boardRepository.deleteById(id);
	}
	
}
