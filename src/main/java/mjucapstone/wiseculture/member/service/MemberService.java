package mjucapstone.wiseculture.member.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.common.EncryptManager;
import mjucapstone.wiseculture.common.dto.ApiResponse;
import mjucapstone.wiseculture.common.dto.ErrorDto;
import mjucapstone.wiseculture.common.error.ErrorCode;
import mjucapstone.wiseculture.member.MemberRepository;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.member.dto.ModifyMemberForm;
import mjucapstone.wiseculture.member.exception.MemberNotFoundException;
import mjucapstone.wiseculture.member.exception.ModifyDeniedException;
import mjucapstone.wiseculture.member.exception.SignUpException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final LoginService loginService;

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
    
    
    
    // 전체 회원 목록
    public List<Member> getAllMember() {
    	return memberRepository.findAll();
    }
    
    // 회원 조회
    public Member findMember(String userID) throws MemberNotFoundException {
    	Member member = memberRepository.findByUserId(userID);
    	if(member == null) throw new MemberNotFoundException(userID);
    	return member;
    }
    
    // 현재 회원 정보 조회
    public Member findMember(HttpServletRequest request) {
    	
    	// 현재 로그인된 사용자 확인
    	Member member = loginService.checkLogin(request);
    	
    	// 현재 로그인된 사용자 정보 가져오기
    	member = this.findMember(member.getUserId());
    	if(member == null) throw new MemberNotFoundException("사용자가 존재하지 않음");
    	
    	return member;
    	
    }
    
    // 회원 수정
    @Transactional
    public Member modifyMember(Member member) {
    	memberRepository.save(member);
    	return member;
    }
    
    // 아이디 찾기
    public List<String> findUserId(String email, String name) {
    	
    	// 사용자 찾기
    	List<Member> memberList = memberRepository.findByEmailAndName(email, name);
    	
    	// 아이디만 가져오기
    	List<String> userIdList = new ArrayList<>();
    	memberList.forEach(m -> userIdList.add(m.getUserId()));
    	
    	return userIdList;
    	
    }
    
    // 닉네임 변경
    @Transactional
    public void changeNickname(ModifyMemberForm form, HttpServletRequest request) {
    	
    	if(form.getNickname() == null)
    		throw new ModifyDeniedException("닉네임이 입력되지 않음");
    	
    	// 중복 체크
    	if(this.nicknameCheck(form.getNickname()) == true)
    		throw new ModifyDeniedException("이미 사용중인 닉네임");
    	
    	// 로그인 확인
    	if(loginService.checkLogin(request, form.getId()) == false)
    		throw new ModifyDeniedException("다른 사용자의 정보를 변경할 수 없음");
    	
    	// 멤버 찾기
    	Member member = this.findMember(form.getId());
    	if(member == null) throw new MemberNotFoundException("사용자가 존재하지 않음");
    	
    	// 닉네임 변경
    	//member.setNickname(form.getNickname());
    	//memberRepository.save(member);
    	memberRepository.updateNickname(member.getId(), form.getNickname());
    	
    }
    
    // 비밀번호 변경
    @Transactional
    public void changePassword(ModifyMemberForm form, HttpServletRequest request) {
    	
    	if(form.getNewPassword() == null) throw new ModifyDeniedException("새 비밀번호가 입력되지 않음");
    	if(form.getCurPassword() == null) throw new ModifyDeniedException("현재 비밀번호가 입력되지 않음");
    	
    	// 로그인 확인
    	if(loginService.checkLogin(request, form.getId()) == false)
    		throw new ModifyDeniedException("다른 사용자의 정보를 변경할 수 없음");
    	if(loginService.checkPassword(form.getId(), form.getCurPassword()) == false)
    		throw new ModifyDeniedException("잘못된 비밀번호");
    	
    	// 멤버 찾기
    	Member member = this.findMember(form.getId());
    	if(member == null) throw new MemberNotFoundException("사용자가 존재하지 않음");
    	
    	// 비밀번호 변경
    	//member.setPassword(EncryptManager.hash(form.getNewPassword()));
    	//memberRepository.save(member);
    	memberRepository.updatePassword(member.getId(), EncryptManager.hash(form.getNewPassword()));
    	
    }
    
    // 회원 삭제
    @Transactional
    public void delete(ModifyMemberForm form, HttpServletRequest request) {
    	
    	if(form.getCurPassword() == null) throw new ModifyDeniedException("현재 비밀번호가 입력되지 않음");
    	
    	// 로그인 확인
    	if(loginService.checkLogin(request, form.getId()) == false)
    		throw new ModifyDeniedException("다른 사용자의 정보를 변경할 수 없음");
    	if(loginService.checkPassword(form.getId(), form.getCurPassword()) == false)
    		throw new ModifyDeniedException("잘못된 비밀번호");
    	
    	// 멤버 찾기
    	Member member = this.findMember(form.getId());
    	if(member == null) throw new MemberNotFoundException("사용자가 존재하지 않음");
    	
    	// 회원 삭제
    	memberRepository.delete(member);
    	
    }


}
