package mjucapstone.wiseculture;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.board.Board;
import mjucapstone.wiseculture.board.BoardRepository;
import mjucapstone.wiseculture.comment.CommentService;
import mjucapstone.wiseculture.comment.dto.CommentForm;
import mjucapstone.wiseculture.member.config.EncryptManager;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.member.service.MemberService;
import mjucapstone.wiseculture.message.MessageService;
import mjucapstone.wiseculture.message.dto.MessageForm;
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
    private final MessageService messageService;

    @PostConstruct
    public void userDummyData() {
        // 사용자 세팅
        Member member = Member.builder()
                .email("mopil1102@naver.com")
                .name("황동호")
                .nickname("토토로")
                .userId("test1234")
                .password(EncryptManager.hash("123456"))
                .phoneNumber("010-1234-1234")
                .build();
        Member member1 = Member.builder()
                .email("mopil1102@naver.com")
                .name("배성흥")
                .nickname("밤톨이")
                .userId("mopil1102")
                .password(EncryptManager.hash("123456"))
                .phoneNumber("010-1234-1234")
                .build();
        memberService.signUp(member);
        memberService.signUp(member1);

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

        // 메시지 세팅
        MessageForm messageForm = MessageForm.builder()
                .title("테스트 쪽지")
                .content("테스트용 쪽지 내용입니다")
                .receiverNickname(member1.getNickname())
                .build();
        MessageForm messageForm1 = MessageForm.builder()
                .title("테스트 쪽지2")
                .content("테스트용 쪽지 내용입니다2")
                .receiverNickname(member1.getNickname())
                .build();
        MessageForm messageForm3 = MessageForm.builder()
                .title("견적요청입니다")
                .content("은 훼이크 입니다")
                .receiverNickname(tester.getNickname())
                .build();
        MessageForm messageForm4 = MessageForm.builder()
                .title("고객센터입니다")
                .content("also fake yeah")
                .receiverNickname(tester.getNickname())
                .build();
        messageService.createMessage(tester, messageForm);
        messageService.createMessage(tester, messageForm1);
        messageService.createMessage(member1, messageForm3);
        messageService.createMessage(member1, messageForm4);

    }
}
