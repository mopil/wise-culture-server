package mjucapstone.wiseculture;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.board.Board;
import mjucapstone.wiseculture.board.BoardRepository;
import mjucapstone.wiseculture.comment.CommentService;
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
                .point(100)
                .build();
        Member member1 = Member.builder()
                .email("mopil1102@gmail.com")
                .name("배성흥")
                .nickname("밤톨이")
                .userId("mopil1102")
                .password(EncryptManager.hash("123456"))
                .phoneNumber("010-1234-1234")
                .point(100)
                .build();
        memberService.signUp(member);
        memberService.signUp(member1);

        Member tester = memberService.findByNickName("토토로");

        // 게시물 세팅
        List<Board> temp = new ArrayList<>();
        temp.add(Board.builder()
                .title("제부도 놀러갈 분!!")
                .content("이번주 주말에 제부도 놀러가요~")
                .writer(tester)
                .build());
        temp.add(Board.builder()
                .title("심심해요")
                .content("무료하다")
                .writer(tester)
                .build());
        temp.add(Board.builder()
                .title("즐거운 꽃구경~")
                .content("날씨도 좋아요~")
                .writer(tester)
                .build());
        temp.add(Board.builder()
                .title("충청도 친구")
                .content("산책갈 친구 구합니다")
                .writer(tester)
                .build());
        temp.add(Board.builder()
                .title("서울 나들이")
                .content("여행도하고 맛집도 가고!!")
                .writer(tester)
                .build());
        boardRepository.saveAll(temp);

//        Board board = boardRepository.findByTitle("테스트 게시물2").get();
//
//        // 댓글 세팅
//        String commentContent = "야 니팀 쩔더라 ㅋ";
//        CommentForm commentForm = new CommentForm(tester.getNickname(), commentContent);
//        commentService.createComment(board.getId(), commentForm, tester);

        // 메시지 세팅
        MessageForm messageForm = MessageForm.builder()
                .title("경원대")
                .content("저랑 경원대 축제 같이가요!")
                .receiverUserId(member1.getUserId())
                .build();
        MessageForm messageForm1 = MessageForm.builder()
                .title("여행코스")
                .content("전주쪽 여행코스 아는곳 있으신가요?")
                .receiverUserId(member1.getUserId())
                .build();
        MessageForm messageForm3 = MessageForm.builder()
                .title("참여요청입니다")
                .content("식도락 여행 같이가요")
                .receiverUserId(tester.getUserId())
                .build();
        MessageForm messageForm4 = MessageForm.builder()
                .title("고인돌")
                .content("어떤가요?")
                .receiverUserId(tester.getUserId())
                .build();
        messageService.createMessage(tester, messageForm);
        messageService.createMessage(tester, messageForm1);
        messageService.createMessage(member1, messageForm3);
        messageService.createMessage(member1, messageForm4);

    }
}
