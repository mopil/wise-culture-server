package mjucapstone.wiseculture.util.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mjucapstone.wiseculture.util.exception.ErrorCode;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter @AllArgsConstructor
public class ErrorResponse {

    private String code;
    private String message;

    public static ErrorResponse convertJson(FieldError error) {
        return new ErrorResponse(ErrorCode.VALIDATION_ERROR, error.getDefaultMessage());
    }

    public static ArrayList<ErrorResponse> convertJson(List<FieldError> bindingResults) {
        ArrayList<ErrorResponse> result = new ArrayList<>();
        for (FieldError e : bindingResults) {
            result.add(ErrorResponse.convertJson(e));
        }
        return result;
    }
}
