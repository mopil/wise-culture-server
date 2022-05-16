package mjucapstone.wiseculture.message;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.member.exception.MemberException;
import mjucapstone.wiseculture.member.repository.MemberRepository;
import mjucapstone.wiseculture.message.dto.MessageForm;
import mjucapstone.wiseculture.message.dto.MessageListResponse;
import mjucapstone.wiseculture.message.dto.MessageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;

    // 메시지 받는 상대방 가져오기
    public Member getReceiver(String receiverNickname) {
        return memberRepository.findByNickname(receiverNickname).orElseThrow(() -> new MemberException("해당 회원 조회 오류"));
    }

    // 메지시 id로 하나 조회 (상세 보기 용)
    public Message findById(Long messageId) {
        return messageRepository.findById(messageId).orElseThrow(() -> new MessageException("메시지 조회 오류"));
    }

    // (로그인 기준) 받은 메시지 전체 조회
    public MessageListResponse getAllReceived(Member loginMember) {
        List<Message> allReceived = messageRepository.findAllReceived(loginMember.getId());
        List<MessageResponse> result = new ArrayList<>();
        allReceived.forEach(message -> result.add(message.toResponse()));
        return new MessageListResponse(result);
    }

    // (로그인 기준) 보낸 베시지 전체 조회
    public MessageListResponse getAllSent(Member loginMember) {
        List<Message> allSent = messageRepository.findAllSent(loginMember.getId());
        List<MessageResponse> result = new ArrayList<>();
        allSent.forEach(message -> result.add(message.toResponse()));
        return new MessageListResponse(result);
    }

    // 메시지 쓰기
    public Message createMessage(Member sender, MessageForm messageForm) {
        Member receiver = getReceiver(messageForm.getReceiverNickname());

        Message message = Message.builder()
                .sender(sender)
                .receiver(receiver)
                .title(messageForm.getTitle())
                .content(messageForm.getContent())
                .build();
        messageRepository.save(message);
        return message;
    }

    // 메시지 삭제
    public void delete(Long messageId, Member member) {
        Message message = messageRepository.findById(messageId).orElseThrow(() -> new MessageException("메시지를 찾을 수 없음"));
        if (!Objects.equals(message.getSender().getId(), member.getId()) && !Objects.equals(message.getReceiver().getId(), member.getId()))
            throw new MessageException("현재 회원이 보내거나 받은 메시지가 아님");

        // 메시지 삭제
        messageRepository.deleteById(messageId);
    }

    // (로그인 기준) 받은 메시지 전체 삭제
    public void deleteAllReceived(Member loginMember) {
        List<Message> allReceived = messageRepository.findAllReceived(loginMember.getId());
        messageRepository.deleteAll(allReceived);
    }

    // (로그인 기준) 보낸 메시지 전체 삭제
    public void deleteAllSent(Member loginMember) {
        List<Message> allSent = messageRepository.findAllSent(loginMember.getId());
        messageRepository.deleteAll(allSent);
    }

}
