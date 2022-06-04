package mjucapstone.wiseculture.message;

import mjucapstone.wiseculture.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	
	@Query("SELECT m FROM Message m where (m.sender = :member1 AND m.receiver = :member2) OR (m.sender = :member2 AND m.receiver = :member1)")
	List<Message> findMessage(@Param("member1") Member member1, @Param("member2") Member member2);

	@Query("SELECT m FROM Message m WHERE m.receiver.id = :loginMemberId")
	List<Message> findAllReceived(@Param("loginMemberId") Long loginMemberId);

	@Query("SELECT m FROM Message m WHERE m.sender.id = :loginMemberId")
	List<Message> findAllSent(@Param("loginMemberId") Long loginMemberId);

}
