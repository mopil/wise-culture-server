package mjucapstone.wiseculture.comment;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.board.BoardRepository;
import mjucapstone.wiseculture.member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
	
	private final CommentRepository commentRepository;
	private final BoardRepository boardRepository;
	
	// 게시글 번호로 덧글 찾기
	public List<Comment> getComments(Long boardId) {
		return commentRepository.findAllByBoard(boardRepository.findById(boardId).orElseThrow(() -> new CommentException("존재하지 않는 게시글")));
	}
	
	// 댓글 작성
	@Transactional
	public Comment writeComment(Long boardId, String content, Member writer) {
		Comment comment = Comment.builder()
				.board(boardRepository.findById(boardId).orElseThrow(() -> new CommentException("존재하지 않는 게시글")))
				.content(content)
				.writer(writer)
				.build();
		
		// 댓글 저장
		commentRepository.save(comment);
		
		return comment;
	}
	
	// 댓글 수정
	@Transactional
	public Comment editComment(Long id, String content, Member writer) {
		if(!commentRepository.existsById(id)) throw new CommentException("존재하지 않는 댓글");
		if(!Objects.equals(commentRepository.getById(id).getMember().getId(), writer.getId())) throw new CommentException("다른 회원의 댓글");
		
		// 게시글 업데이트 
		commentRepository.updateContent(id, content);
		
		return commentRepository.findById(id).get(); // 위에서 null인 경우 걸러짐
	}
	
	// 댓글 삭제
	@Transactional
	public void deleteComment(Long id, Member writer) {
		if(!commentRepository.existsById(id)) throw new CommentException("존재하지 않는 댓글");
		if(!Objects.equals(commentRepository.getById(id).getMember().getId(), writer.getId())) throw new CommentException("다른 회원의 댓글");
		
		commentRepository.deleteById(id);
	}

}
