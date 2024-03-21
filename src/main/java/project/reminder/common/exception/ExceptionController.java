package project.reminder.common.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.reminder.common.util.TransUtil;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {

    private final TransUtil transUtil;

    @ExceptionHandler(HttpCallException.class)
    public ResponseEntity<String> handleHttpCallException(HttpCallException e) {
        HttpCallExceptionMessage message = new HttpCallExceptionMessage(e.getStatus(), e.getMessage());
        String json = transUtil.valueAsJson(message);
        return new ResponseEntity<>(json, e.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>("Internal Server Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
