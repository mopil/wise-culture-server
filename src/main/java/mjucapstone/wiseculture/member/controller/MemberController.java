package mjucapstone.wiseculture.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjucapstone.wiseculture.common.ApiResponse;
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
        // 유효성 검사에 실패한 요청, 오류들은 bindingResult 객체의 FieldError로 잡혀집니다. 그걸 JSON으로 변환해서 내려줍니다.
        if (bindingResult.hasErrors()) {
            log.info("Errors = {}", bindingResult.getFieldErrors());
            return ApiResponse.badRequest(ErrorDto.convertJson(bindingResult.getFieldErrors()));
        }
        Member member = form.toMember(EncryptManager.hash(form.getPassword()));
        memberService.signUp(member);
        return ApiResponse.success(member);
    }

    // 모든 HTTP 요청은 JSON형식으로 응답을 내려줘야해서 DTO를 만들었습니다. 그냥 bool을 리턴하면 JSON형태로 응답이 안 내려감
    @GetMapping("/nickname-check/{nickname}")
    public ResponseEntity<?> nicknameCheck(@PathVariable String nickname) {
        return ApiResponse.success(new BoolResponse(memberService.nicknameCheck(nickname)));
    }

    /**
     *  예외 처리
     *  스프링에서 예외를 처리하는 방법입니다. 예외가 던져지면 WAS까지 올라가는데 컨트롤러에서 잡아줘야 합니다.
     *  자세한건 스프링 REST API 예외처리 검색하면 나옵니다.
     */
    // 중복 회원
    @ExceptionHandler(SignUpException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> signUpExHandle(SignUpException e) {
        log.error("[exceptionHandle] ex", e);
        return ApiResponse.badRequest(new ErrorDto(ErrorCode.SIGN_UP_ERROR, e.getMessage()));
    }

    // 기타 예외 처리 : 서버에서 발생하는 다른 모든 예외는 얘가 잡아줍니다.
    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> otherExHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return ApiResponse.badRequest(new ErrorDto(ErrorCode.COMMON_ERROR, e.getMessage()));
    }


}
