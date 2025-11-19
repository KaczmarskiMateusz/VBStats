package pl.vbstats.user.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.vbstats.user.exceptions.UserNotAuthorizedException;

import java.util.List;

@ControllerAdvice
public class GlobalHandlerExceptions {

    @ExceptionHandler(UserNotAuthorizedException.class)
    public ResponseEntity<?> handleUserNotAuthorized(UserNotAuthorizedException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiErrorResponse(false, List.of(ex.getMessage())));
    }

}
