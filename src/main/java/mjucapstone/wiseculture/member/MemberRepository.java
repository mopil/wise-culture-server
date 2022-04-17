package mjucapstone.wiseculture.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mjucapstone.wiseculture.member.domain.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository <Member, Long>{

    boolean existsByName(String name);
    boolean existsByNickname(String nickname);
    boolean existsByUserId(String userId);
    Optional<Member> findByUserId(String userId);
    Optional<Member> findByEmailAndName(String email, String name);
    Optional<Member> findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.nickname = :newNickname WHERE m.id = :id")
    void updateNickname(@Param("id") Long id, @Param("newNickname") String newNickname);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.password = :newEncryptedPassword WHERE m.id = :id")
    void updatePassword(@Param("id") Long id, @Param("newEncryptedPassword") String newEncryptedPassword);
}
