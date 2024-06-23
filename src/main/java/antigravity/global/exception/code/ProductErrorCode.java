package antigravity.global.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductErrorCode implements ErrorCode{
    NOT_EXIST_PRODUCT_ID(HttpStatus.NOT_FOUND, "The product identified by '{id}' is not present.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
