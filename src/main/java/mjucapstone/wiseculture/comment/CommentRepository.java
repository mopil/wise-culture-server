package mjucapstone.wiseculture.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mjucapstone.wiseculture.board.Board;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	List<Comment> findAllByBoard(Board board);
	void deleteAllByBoard(Board board);
	
	@Modifying(clearAutomatically = true)
    @Query("UPDATE Comment c SET c.content = :content WHERE c.id = :id")
    void updateContent(@Param("id") Long id, @Param("content") String content);

}
