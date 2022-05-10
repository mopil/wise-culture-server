package mjucapstone.wiseculture.member.service;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.member.config.EncryptManager;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.member.dto.ChangePasswordForm;
import mjucapstone.wiseculture.member.dto.DeleteMemberForm;
import mjucapstone.wiseculture.member.dto.PasswordResetForm;
import mjucapstone.wiseculture.member.exception.MemberException;
import mjucapstone.wiseculture.member.exception.ModifyDeniedException;
import mjucapstone.wiseculture.member.exception.SignUpException;
import mjucapstone.wiseculture.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 회원 수정
     */
    // 닉네임 변경
    @Transactional
    public Member changeNickname(Member loginMember, String newNickname) {
    	memberRepository.updateNickname(loginMember.getId(), newNickname);
        return findById(loginMember.getId());

    }

    // 비밀번호 변경
    @Transactional
    public Member changePassword(Member loginMember, ChangePasswordForm form) {

        // 현재 비밀번호를 제대로 입력했는지 확인
        boolean currentPasswordCheck = EncryptManager.check(form.getCurrentPassword(), loginMember.getPassword());
        if (!currentPasswordCheck) throw new ModifyDeniedException("현재 비밀번호가 틀립니다");

    	// 비밀번호 변경
    	memberRepository.updatePassword(loginMember.getId(), EncryptManager.hash(form.getNewPassword()));
        return findById(loginMember.getId());

    }

    // 닉네임 변경 : 로그인 없이
    @Transactional
    public Member changeNickname(Long memberId, String newNickname) {
        memberRepository.updateNickname(memberId, newNickname);
        return findById(memberId);

    }

    // 비밀번호 변경 : 로그인 없이
    @Transactional
    public Member changePassword(Long memberId, ChangePasswordForm form) {

        Member member = findById(memberId);

        // 현재 비밀번호를 제대로 입력했는지 확인
        boolean currentPasswordCheck = EncryptManager.check(form.getCurrentPassword(), member.getPassword());
        if (!currentPasswordCheck) throw new ModifyDeniedException("현재 비밀번호가 틀립니다");

        // 비밀번호 변경
        memberRepository.updatePassword(memberId, EncryptManager.hash(form.getNewPassword()));
        return member;

    }

    /**
     * 회원 삭제
     */
    @Transactional
    public void delete(Member loginMember, DeleteMemberForm form, HttpServletRequest request) {
        
        // 폼의 비밀번호와 비밀번호 확인이 같은지 체크
        if (!form.getPassword().equals(form.getPasswordCheck())) {
            throw new ModifyDeniedException("비밀번호와 비밀번호 확인이 서로 다름");
        }
        
        // 로그인된 사용자 비밀번호와 현재 폼에 입력된 비밀번호가 같은지 체크
        if (!EncryptManager.check(form.getPassword(), loginMember.getPassword())) {
            throw new ModifyDeniedException("잘못된 비밀번호");
        }
        
        // 해당 사용자 로그아웃
        loginService.logout(request);

    	// 회원 삭제
    	memberRepository.deleteById(loginMember.getId());

    }

    // 회원 삭제 : 로그인 없이
    @Transactional
    public void delete(Long memberId, DeleteMemberForm form, HttpServletRequest request) {
        Member member = findById(memberId);

        // 폼의 비밀번호와 비밀번호 확인이 같은지 체크
        if (!form.getPassword().equals(form.getPasswordCheck())) {
            throw new ModifyDeniedException("비밀번호와 비밀번호 확인이 서로 다름");
        }

        // 로그인된 사용자 비밀번호와 현재 폼에 입력된 비밀번호가 같은지 체크
        if (!EncryptManager.check(form.getPassword(), member.getPassword())) {
            throw new ModifyDeniedException("잘못된 비밀번호");
        }

        // 해당 사용자 로그아웃
        loginService.logout(request);

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
