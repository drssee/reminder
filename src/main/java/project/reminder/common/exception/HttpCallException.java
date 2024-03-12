package project.reminder.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpCallException extends RuntimeException {

    private final HttpStatus status;

    public HttpCallException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpCallException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }
}
