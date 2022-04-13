package mjucapstone.wiseculture.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjucapstone.wiseculture.common.dto.ApiResponse;
import mjucapstone.wiseculture.common.EncryptManager;
import mjucapstone.wiseculture.common.dto.BoolResponse;
import mjucapstone.wiseculture.common.dto.ErrorDto;
import mjucapstone.wiseculture.common.error.ErrorCode;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.member.exception.SignUpException;
import mjucapstone.wiseculture.member.service.MemberService;
import mjucapstone.wiseculture.member.dto.SignUpForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/new")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpForm form, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("Errors = {}", bindingResult.getFieldErrors());
            return ApiResponse.badRequest(ErrorDto.convertJson(bindingResult.getFieldErrors()));
        }
        Member member = form.toMember(EncryptManager.hash(form.getPassword()));
        memberService.signUp(member);
        return ApiResponse.success(member);
    }

    @GetMapping("/nickname-check/{nickname}")
    public ResponseEntity<?> nicknameCheck(@PathVariable String nickname) {
        return ApiResponse.success(new BoolResponse(memberService.nicknameCheck(nickname)));
    }

    /**
     *  예외 처리
     */
    // 중복 회원
    @ExceptionHandler(SignUpException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> signUpExHandle(SignUpException e) {
        log.error("[exceptionHandle] ex", e);
        return ApiResponse.badRequest(new ErrorDto(ErrorCode.SIGN_UP_ERROR, e.getMessage()));
    }


}
