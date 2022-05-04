package mjucapstone.wiseculture.board;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mjucapstone.wiseculture.location.domain.Location;
import mjucapstone.wiseculture.member.domain.Member;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
	
	List<BoardSummary> findAllBy();
	
	@Query("SELECT b.member FROM Board b where b.id = :id")
	Optional<Member> findWriterById(@Param("id") Long id);
	
	@Modifying(clearAutomatically = true)
    @Query("UPDATE Board b SET b.title = :title, b.content = :content, b.location = :location WHERE b.id = :id")
    void updateBoard(@Param("id") Long id, @Param("title") String title, @Param("content") String content, @Param("location") Location location);
	
	@Modifying(clearAutomatically = true)
    @Query("UPDATE Board b SET b.viewCount = :viewCount WHERE b.id = :id")
    void updateViewCount(@Param("id") Long id, @Param("viewCount") int viewCount);

}
