package mjucapstone.wiseculture.util.exception;

import lombok.extern.slf4j.Slf4j;
import mjucapstone.wiseculture.util.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.LoginException;

import static mjucapstone.wiseculture.util.dto.RestResponse.badRequest;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> otherExHandle(Exception e) {
        log.error("예외처리 하지 않은 예외 발생 : 공통 핸들러 작동");
        return badRequest(new ErrorResponse(ErrorCode.COMMON_ERROR, e.getMessage()));
    }

    // 로그인 예외 처리 (인가 되지 않은 사용자의 접근)
    @ExceptionHandler(LoginException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> loginExHandle(LoginException e) {
        log.error("로그인 예외 발생 : 핸들러 작동");
        return badRequest(new ErrorResponse(ErrorCode.LOGIN_ERROR, e.getMessage()));
    }

}