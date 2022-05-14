package mjucapstone.wiseculture.board;

import mjucapstone.wiseculture.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

	@Query("SELECT b.member FROM Board b where b.id = :id")
	Optional<Member> findWriterById(@Param("id") Long id);

}
