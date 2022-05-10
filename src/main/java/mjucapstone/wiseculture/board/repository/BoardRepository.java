package mjucapstone.wiseculture.board.repository;

import mjucapstone.wiseculture.board.domain.Board;
import mjucapstone.wiseculture.board.dto.BoardSummary;
import mjucapstone.wiseculture.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
	
	List<BoardSummary> findAllBy();
	
	@Query("SELECT b.member FROM Board b where b.id = :id")
	Optional<Member> findWriterById(@Param("id") Long id);
	
	@Modifying(clearAutomatically = true)
    @Query("UPDATE Board b SET b.title = :title, b.content = :content WHERE b.id = :id")
    void updateBoard(@Param("id") Long id, @Param("title") String title, @Param("content") String content);
	
	@Modifying(clearAutomatically = true)
    @Query("UPDATE Board b SET b.viewCount = :viewCount WHERE b.id = :id")
    void updateViewCount(@Param("id") Long id, @Param("viewCount") int viewCount);

}
