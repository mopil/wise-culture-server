package mjucapstone.wiseculture.comment;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.board.Board;
import mjucapstone.wiseculture.board.BoardRepository;
import mjucapstone.wiseculture.comment.dto.CommentForm;
import mjucapstone.wiseculture.comment.dto.CommentListResponse;
import mjucapstone.wiseculture.comment.dto.CommentResponse;
import mjucapstone.wiseculture.member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
	
	private final CommentRepository commentRepository;
	private final BoardRepository boardRepository;

	private Board findBoard(Long boardId) {
		return boardRepository.findById(boardId).orElseThrow(() -> new CommentException("존재하지 않는 게시글"));
	}
	
	// 게시글 번호로 덧글 찾기
	@Transactional(readOnly = true)
	public CommentListResponse findAllByBoardId(Long boardId) {
		Board findBoard = findBoard(boardId);
		List<Comment> findComments = findBoard.getComments();
		List<CommentResponse> result = new ArrayList<>();
		findComments.forEach(comment -> result.add(comment.toResponse()));
		return new CommentListResponse(result);
	}
	
	// 댓글 작성
	public Comment createComment(Long boardId, CommentForm form, Member writer) {
		Board findBoard = findBoard(boardId);
		Comment comment = Comment.builder()
				.board(findBoard)
				.content(form.getContent())
				.writer(writer)
				.build();
		commentRepository.save(comment);
		findBoard.addComment(comment);
		return comment;
	}
	
	// 댓글 수정
	public Comment updateComment(Long commentId, CommentForm form, Member writer) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentException("댓글 조회 오류"));
		commentLoginCheck(writer, comment);
		comment.update(form.getContent());
		return comment;
	}
	
	// 댓글 삭제
	public void deleteComment(Long commentId, Member writer) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentException("댓글 조회 오류"));
		commentLoginCheck(writer, comment);
		commentRepository.deleteById(commentId);
	}

	private void commentLoginCheck(Member writer, Comment comment) {
		if (!Objects.equals(comment.getBoard().getMember().getId(), writer.getId())) throw new CommentException("다른 회원의 댓글");
	}

}
