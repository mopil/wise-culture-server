package mjucapstone.wiseculture.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.member.MemberRepository;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.member.exception.MemberNotFoundException;
import mjucapstone.wiseculture.member.exception.SignUpException;

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
    
    
    
    // 회원 목록(직접 테스트시 확인 용)
    public List<Member> getAllMember() {
    	return memberRepository.findAll();
    }
    
    // 회원 조회
    public Member findMember(String userID) throws MemberNotFoundException {
    	Member member = memberRepository.findByUserId(userID);
    	if(member == null) throw new MemberNotFoundException(userID);
    	return member;
    }
    
    


}
