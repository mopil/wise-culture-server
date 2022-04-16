package mjucapstone.wiseculture.member.service;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.common.EncryptManager;
import mjucapstone.wiseculture.member.MemberRepository;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.member.exception.MemberException;
import mjucapstone.wiseculture.member.exception.SignUpException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final EmailService emailService;

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

    /**
     * 비밀번호 재설정
     */
    @Transactional
    public Member passwordReset(Long memberId) {
        // 이메일 주소 조회를 위한 디비에서 회원 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException("디비 회원 조회 실패."));

        // 넘어온 PK에 해당되는 멤버의 이메일 주소로 임시 비밀번호 메일 전송
        String tempPassword = emailService.sendPasswordResetMail(member.getEmail());

        // 해당 임시 비밀번호로 해당 멤버의 비밀번호를 암호화해서 디비에 업데이트
        memberRepository.updatePassword(memberId, EncryptManager.hash(tempPassword));

        // 비밀번호 재설정된 멤버 객체 리턴
        return member;
    }



}
