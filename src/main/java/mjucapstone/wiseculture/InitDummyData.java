package mjucapstone.wiseculture;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.member.MemberRepository;
import mjucapstone.wiseculture.member.domain.Member;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitDummyData {

    private final MemberRepository memberRepository;
    
    // 스프링이 띄워질때 자동으로 해당 메서드 실행
    @PostConstruct
    public void userDummyData() {
        Member member = Member.builder()
                .email("mopil1102@naver.com")
                .name("테스트")
                .nickname("토토로")
                .userId("mopil1102")
                .password("123456")
                .phoneNumber("010-1234-1234")
                .build();
        memberRepository.save(member);
        
    }
}
