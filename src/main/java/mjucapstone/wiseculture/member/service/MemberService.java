package mjucapstone.wiseculture.member.service;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.member.MemberRepository;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.member.exception.SignUpException;
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
    public Member signUp(Member member) {
        if (memberRepository.existsByName(member.getName())) {
            throw new SignUpException("이미 존재하는 회원입니다.");
        }
        memberRepository.save(member);
        return member;
    }

    /**
     * 닉네임 중복 체크
     */
    public boolean nicknameCheck(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }



}
