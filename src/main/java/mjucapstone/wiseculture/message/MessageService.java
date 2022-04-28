package mjucapstone.wiseculture.message;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.member.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageService {
	
	private final MessageRepository messageRepository;
	private final MemberRepository memberRepository;
	
	// 대화 상대 조회
	public Set<Member> getContact(Member member) {
		Set<Member> result = messageRepository.findReceiverBySender(member);
		result.addAll(messageRepository.findSenderByReceiver(member));
		return result;
	}
	
	// 메시지 조회
	public List<Message> getMessages(Member member, Long id) {
		if(memberRepository.existsById(id) == false) throw new MessageException("대화 상대를 찾을 수 없음");
		
		return messageRepository.findMessage(member, memberRepository.getById(id));
	}
	
	// 메시지 쓰기
	@Transactional
	public Message send(Long sender, Long reciever, String content, Member member) {
		if(member.getId() != sender) throw new MessageException("현재 회원과 보내는 회원이 다름");
		if(memberRepository.existsById(sender) == false) throw new MessageException("보내는 회원을 찾을 수 없음");
		if(memberRepository.existsById(reciever) == false) throw new MessageException("받는 회원을 찾을 수 없음");
		
		Message message = Message.builder()
				.sender(memberRepository.getById(sender))
				.receiver(memberRepository.getById(reciever))
				.content(content)
				.build();
		
		// 메시지 보내기
		messageRepository.save(message);
		
		return message;
	}
	
	// 메시지 삭제
	@Transactional
	public void delete(Long id, Member member) {
		Message message = messageRepository.findById(id).orElseThrow(() -> new MessageException("메시지를 찾을 수 없음"));
		if(message.getSender() != member && message.getReceiver() != member) throw new MessageException("현재 회원이 보내거나 받은 메시지가 아님");
		
		// 메시지 삭제
		messageRepository.deleteById(id);
	}

}
