package mjucapstone.wiseculture.message;

import mjucapstone.wiseculture.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	
	@Query("SELECT m.sender FROM Message m where m.receiver = :receiver")
	Set<Member> findSenderByReceiver(@Param("receiver") Member receiver);
	
	@Query("SELECT m.receiver FROM Message m where m.sender = :sender")
	Set<Member> findReceiverBySender(@Param("sender") Member sender);
	
	@Query("SELECT m FROM Message m where (m.sender = :member1 AND m.receiver = :member2) OR (m.sender = :member2 AND m.receiver = :member1)")
	List<Message> findMessage(@Param("member1") Member member1, @Param("member2") Member member2);

}
