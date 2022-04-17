package mjucapstone.wiseculture.member.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mjucapstone.wiseculture.member.MemberRepository;
import mjucapstone.wiseculture.member.dto.ModifyMemberForm;
import mjucapstone.wiseculture.member.dto.PasswordResetForm;
import mjucapstone.wiseculture.member.exception.MemberException;
import mjucapstone.wiseculture.member.exception.ModifyDeniedException;
import mjucapstone.wiseculture.member.exception.SignUpException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.common.EncryptManager;
import mjucapstone.wiseculture.member.domain.Member;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final EmailService emailService;
    private final LoginService loginService;

    /**
     * 회원 가입
     */
    @Transactional
    public Member signUp(Member member) {
        if (memberRepository.existsByName(member.getName())) {
            throw new SignUpException("이미 존재하는 회원입니다");
        }
        memberRepository.save(member);
        return member;
    }

    // 회원가입 : 닉네임 중복 체크
    public boolean nicknameCheck(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    // 회원가입 : 유저 아이디 중복 체크
    public boolean userIdCheck(String userId) {
        return memberRepository.existsByUserId(userId);
    }

    /**
     * 회원 조회
     */
    // 전체 회원 목록
    public List<Member> getAllMember() {
    	return memberRepository.findAll();
    }

    // 유저 아이디로 회원 하나 조회
    public Member findMember(String userID) throws MemberException {
    	return memberRepository.findByUserId(userID)
                .orElseThrow(() -> new MemberException("해당 회원을 찾을 수 없음"));
    }

    // PK로 회원 하나 조회
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberException("해당 회원을 찾을 수 없음"));
    }

    // 현재 회원 정보 조회
    public Member findMember(HttpServletRequest request) {

        // 현재 로그인된 사용자 정보 가져오기
    	Member loginMember = loginService.checkLogin(request);
    	return findMember(loginMember.getUserId());

    }


    /**
     * 회원 수정
     */
    // 닉네임 변경
    @Transactional
    public Member changeNickname(Long memberId, ModifyMemberForm form, HttpServletRequest request) {
        
    	// 중복 체크
    	if(nicknameCheck(form.getNickname()))
    		throw new ModifyDeniedException("이미 사용중인 닉네임");

    	// 로그인 확인
    	if(!loginService.checkLogin(request, form.getId()))
    		throw new ModifyDeniedException("다른 사용자의 정보를 변경할 수 없음");

    	memberRepository.updateNickname(memberId, form.getNickname());
        return findById(memberId);

    }

    // 비밀번호 변경
    @Transactional
    public Member changePassword(Long memberId, ModifyMemberForm form, HttpServletRequest request) {
    	
        // 로그인 확인
    	if(!loginService.checkLogin(request, form.getId()))
    		throw new ModifyDeniedException("다른 사용자의 정보를 변경할 수 없음");

        // 현재 폼에 입력된 비밀번호 확인
    	if(!loginService.checkPassword(form.getId(), form.getCurPassword()))
    		throw new ModifyDeniedException("잘못된 비밀번호");

    	// 비밀번호 변경
    	memberRepository.updatePassword(memberId, EncryptManager.hash(form.getNewPassword()));
        return findById(memberId);

    }

    /**
     * 회원 삭제
     */
    @Transactional
    public void delete(Long memberId, ModifyMemberForm form, HttpServletRequest request) {

    	if(form.getCurPassword() == null) throw new ModifyDeniedException("현재 비밀번호가 입력되지 않음");

    	// 로그인 확인
    	if(!loginService.checkLogin(request, form.getId()))
    		throw new ModifyDeniedException("다른 사용자의 정보를 변경할 수 없음");

        // 현재 폼에 입력된 비밀번호 확인
    	if(!loginService.checkPassword(form.getId(), form.getCurPassword()))
    		throw new ModifyDeniedException("잘못된 비밀번호");

    	// 회원 삭제
    	memberRepository.deleteById(memberId);

    }

    /**
     * 회원 아이디 찾기
     */
    public Member findUserId(String email, String name) {
        return memberRepository.findByEmailAndName(email, name)
                .orElseThrow(() -> new MemberException("해당 회원을 찾을 수 없음"));
    }

    /**
     * 비밀번호 찾기 (재설정)
     */
    @Transactional
    public Member passwordReset(PasswordResetForm form) {
        
        // 폼에 적힌 이메일로 디비에서 멤버 조회
        Member member = memberRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new MemberException("해당 회원을 찾을 수 없음"));

        // 넘어온 PK에 해당되는 멤버의 이메일 주소로 임시 비밀번호 메일 전송
        String tempPassword = emailService.sendPasswordResetMail(member.getEmail());

        // 해당 임시 비밀번호로 해당 멤버의 비밀번호를 암호화해서 디비에 업데이트
        memberRepository.updatePassword(member.getId(), EncryptManager.hash(tempPassword));

        // 비밀번호 재설정된 멤버 객체 리턴
        return member;
    }


}
