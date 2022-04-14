package mjucapstone.wiseculture.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mjucapstone.wiseculture.member.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository <Member, Long>{

    boolean existsByName(String name);
    boolean existsByNickname(String nickname);
    Member findByUserId(String userId);
    List<Member> findByEmailAndName(String email, String name);
    List<String> findUserIdByEmailAndName(String email, String name);
}
