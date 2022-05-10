package mjucapstone.wiseculture;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.board.domain.Board;
import mjucapstone.wiseculture.board.repository.BoardRepository;
import mjucapstone.wiseculture.member.config.EncryptManager;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.member.service.MemberService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class InitDummyData {
    private final MemberService memberService;
    private final BoardRepository boardRepository;

    @PostConstruct
    public void userDummyData() {
        Member member = Member.builder()
                .email("mopil1102@naver.com")
                .name("테스트")
                .nickname("토토로")
                .userId("test1234")
                .password(EncryptManager.hash("123456"))
                .phoneNumber("010-1234-1234")
                .build();
        memberService.signUp(member);
    }

    @PostConstruct
    public void boardDummyData() {
        List<Board> temp = new ArrayList<>();
        for (int i = 0 ; i<10 ; i++) {
            Board board = Board.builder()
                    .title("테스트 게시물" + i)
                    .content("테스팅")
                    .build();
            temp.add(board);
        }
        boardRepository.saveAll(temp);

    }
}
