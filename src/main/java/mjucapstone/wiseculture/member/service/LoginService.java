package mjucapstone.wiseculture.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.common.EncryptManager;
import mjucapstone.wiseculture.member.MemberRepository;
import mjucapstone.wiseculture.member.domain.Member;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {
	
	private final MemberRepository memberRepository;
	
	public Member login(String userId, String password) {
		
		Member member = memberRepository.findByUserId(userId);
		
		if(member != null && member.getPassword().equals(EncryptManager.hash(password)))
			return member;
		
		return null;
	}
	

}
