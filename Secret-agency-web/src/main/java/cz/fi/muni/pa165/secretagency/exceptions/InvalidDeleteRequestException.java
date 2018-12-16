package cz.fi.muni.pa165.secretagency.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception which should be thrown when user cannot delete entity because another entities have to be deleted first
 *
 * @author Milos Silhar (433614)
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Cannot delete entity, there are still some dependencies")
public class InvalidDeleteRequestException extends RuntimeException {
}
