package antigravity.global.exception.dto;

import antigravity.global.exception.code.ErrorCode;
import antigravity.global.exception.code.ValidationErrorCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
public class ErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime occurredTime = LocalDateTime.now();
    private final String name;
    private final String message;
    private String parameter;

    public static ResponseEntity<ErrorResponse> errorToResponseEntity(ErrorCode errorCode, Map<String, String> replaceParam) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .name(errorCode.name())
                        .message((replaceParam != null && !replaceParam.isEmpty())
                                ? errorCode.addParamsToMessage(replaceParam)
                                : errorCode.getMessage())
                        .build());
    }

    public static ResponseEntity<ErrorResponse> validErrorToResponseEntity(ValidationErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .name(errorCode.name())
                        .parameter(errorCode.getParameter())
                        .message(errorCode.getMessage())
                        .build());
    }
}