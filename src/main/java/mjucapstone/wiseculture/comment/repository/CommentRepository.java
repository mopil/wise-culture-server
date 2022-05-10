package mjucapstone.wiseculture.comment.repository;

import mjucapstone.wiseculture.board.domain.Board;
import mjucapstone.wiseculture.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	List<Comment> findAllByBoard(Board board);
	void deleteAllByBoard(Board board);
	
	@Modifying(clearAutomatically = true)
    @Query("UPDATE Comment c SET c.content = :content WHERE c.id = :id")
    void updateContent(@Param("id") Long id, @Param("content") String content);

}
