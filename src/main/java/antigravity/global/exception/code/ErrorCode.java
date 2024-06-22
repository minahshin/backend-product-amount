package antigravity.global.exception.code;

import org.springframework.http.HttpStatus;

import java.util.Map;

public interface ErrorCode {
    String name();
    HttpStatus getHttpStatus();
    String getMessage();

    default String addParamsToMessage(Map<String, String> additionalParams) {
        String convertedMessage = getMessage();
        for(String param : additionalParams.keySet()) {
            convertedMessage = convertedMessage.replaceAll("\\{" + param + "}", additionalParams.get(param));
        }
        return convertedMessage;
    }
}
