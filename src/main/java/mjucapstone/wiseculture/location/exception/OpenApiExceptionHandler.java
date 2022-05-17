package mjucapstone.wiseculture.location.exception;

import lombok.extern.slf4j.Slf4j;
import mjucapstone.wiseculture.util.dto.ErrorResponse;
import mjucapstone.wiseculture.util.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static mjucapstone.wiseculture.util.dto.RestResponse.badRequest;

@RestControllerAdvice
@Slf4j
public class OpenApiExceptionHandler {

    @ExceptionHandler(OpenApiException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> openApiExHandle(OpenApiException e) {
        log.error("오픈 API 예외 발생 : 핸들러 작동");
        return badRequest(new ErrorResponse(ErrorCode.OPEN_API_ERROR, e.getMessage()));
    }
}
