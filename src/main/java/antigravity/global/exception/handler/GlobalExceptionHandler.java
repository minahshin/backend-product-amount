package antigravity.global.exception.handler;

import antigravity.global.exception.AntigravityException;
import antigravity.global.exception.code.ValidationErrorCode;
import antigravity.global.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AntigravityException.class)
    public ResponseEntity<ErrorResponse> handleAntigravityException(AntigravityException e){
        log.error("[Error] : {}", e.getMessage());
        return ErrorResponse.errorToResponseEntity(e.getErrorCode(), e.getAdditionalParams());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();

        int lastIndex = bindingResult.getAllErrors().size() - 1;
        FieldError fieldError = (FieldError) bindingResult.getAllErrors().get(lastIndex);
        ValidationErrorCode validationErrorCode = ValidationErrorCode.builder()
                .message(fieldError.getDefaultMessage())
                .parameter(fieldError.getField())
                .build();
        log.error("[Binding Error] : Field = {}, Message = {}", fieldError.getDefaultMessage(), fieldError.getField());

        return ErrorResponse.validErrorToResponseEntity(validationErrorCode);
    }
}
