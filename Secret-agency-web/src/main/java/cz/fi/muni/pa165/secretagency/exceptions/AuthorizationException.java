package cz.fi.muni.pa165.secretagency.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception which should be thrown when user is not authorized to perform certain operation.
 *
 * @author Jan Pavlu
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "You are not authorized to perform this action.")
public class AuthorizationException extends RuntimeException {
    public AuthorizationException() {};
}
