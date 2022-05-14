package mjucapstone.wiseculture;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.board.Board;
import mjucapstone.wiseculture.board.BoardRepository;
import mjucapstone.wiseculture.comment.CommentService;
import mjucapstone.wiseculture.comment.dto.CommentForm;
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
    private final CommentService commentService;

    @PostConstruct
    public void userDummyData() {
        // 사용자 세팅
        Member member = Member.builder()
                .email("mopil1102@naver.com")
                .name("테스트")
                .nickname("토토로")
                .userId("test1234")
                .password(EncryptManager.hash("123456"))
                .phoneNumber("010-1234-1234")
                .build();
        memberService.signUp(member);

        Member tester = memberService.findByNickName("토토로");

        // 게시물 세팅
        List<Board> temp = new ArrayList<>();
        for (int i = 0 ; i<10 ; i++) {
            Board board = Board.builder()
                    .title("테스트 게시물" + i)
                    .content("테스팅" + i * 10)
                    .writer(tester)
                    .build();
            temp.add(board);
        }
        boardRepository.saveAll(temp);

        Board board = boardRepository.findByTitle("테스트 게시물2").get();

        // 댓글 세팅
        String commentContent = "야 니팀 쩔더라 ㅋ";
        CommentForm commentForm = new CommentForm(tester.getNickname(), commentContent);
        commentService.createComment(board.getId(), commentForm, tester);

    }
}
