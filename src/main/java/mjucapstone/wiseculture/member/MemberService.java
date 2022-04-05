package mjucapstone.wiseculture.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Transactional
    public Long signUp(Member member) throws Exception {
        if (memberRepository.existsByName(member.getName())) {
            throw new Exception("이미 존재하는 회원입니다.");
        }
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 닉네임 중복 체크
     */
    public boolean nicknameCheck(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }



}
