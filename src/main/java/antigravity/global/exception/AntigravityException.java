package antigravity.global.exception;

import antigravity.global.exception.code.ErrorCode;
import lombok.Getter;

import java.util.Map;

@Getter
public class AntigravityException extends RuntimeException{
    private final ErrorCode errorCode;
    private Map<String, String> additionalParams;

    public AntigravityException(ErrorCode code) {
        super(code.getMessage());
        this.errorCode = code;
    }

    public AntigravityException(ErrorCode code, Map<String, String> additionalParams) {
        super(code.addParamsToMessage(additionalParams));
        this.errorCode = code;
        this.additionalParams = additionalParams;
    }
}
