package mjucapstone.wiseculture.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mjucapstone.wiseculture.member.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository <Member, Long>{

    boolean existsByName(String name);
    boolean existsByNickname(String nickname);
    Member findByUserId(String userId);

    // 비밀번호 변경
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.password = :newEncryptedPassword WHERE m.id = :id")
    void updatePassword(Long id, String newEncryptedPassword);
}
