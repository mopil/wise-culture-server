package mjucapstone.wiseculture.member.exception;

import lombok.extern.slf4j.Slf4j;
import mjucapstone.wiseculture.util.dto.ErrorResponse;
import mjucapstone.wiseculture.util.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static mjucapstone.wiseculture.util.dto.RestResponse.badRequest;

@Slf4j
@RestControllerAdvice
public class MemberExceptionHandler {

    // 회원가입 : 중복 회원
    @ExceptionHandler(SignUpException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> signUpExHandle(SignUpException e) {
        log.error("회원가입 예외 발생 : 핸들러 작동");
        return badRequest(new ErrorResponse(ErrorCode.SIGN_UP_ERROR, e.getMessage()));
    }

    // 회원 CRUD 오류
    @ExceptionHandler(ModifyDeniedException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> MemberCrudExHandle(ModifyDeniedException e) {
        log.error("멤버 CRUD 예외 발생 : 핸들러 작동");
        return badRequest(new ErrorResponse(ErrorCode.MEMBER_CRUD_ERROR, e.getMessage()));
    }

    // 기타 회원 CRUD 오류
    @ExceptionHandler(MemberException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> otherMemberExHandle(MemberException e) {
        log.error("기타 멤버 CRUD 예외 발생 : 핸들러 작동");
        return badRequest(new ErrorResponse(ErrorCode.MEMBER_CRUD_ERROR, e.getMessage()));
    }



}
