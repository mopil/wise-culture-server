package mjucapstone.wiseculture.common.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mjucapstone.wiseculture.common.error.ErrorCode;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter @AllArgsConstructor
public class ErrorDto {

    private String code;
    private String message;

    public static ErrorDto convertJson(FieldError error) {
        return new ErrorDto(ErrorCode.VALIDATION_ERROR, error.getDefaultMessage());
    }

    public static ArrayList<ErrorDto> convertJson(List<FieldError> bindingResults) {
        ArrayList<ErrorDto> result = new ArrayList<>();
        for (FieldError e : bindingResults) {
            result.add(ErrorDto.convertJson(e));
        }
        return result;
    }
}
