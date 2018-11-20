package cz.fi.muni.pa165.secretagency.service.exceptions;

/**
 * Author: Adam Kral <433328>
 * Date: 11/19/18
 * Time: 3:49 PM
 */
public class AgentServiceException extends RuntimeException {
    public AgentServiceException() { super(); }

    public AgentServiceException(String message, Throwable cause,
                                 boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AgentServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AgentServiceException(String message) {
        super(message);
    }

    public AgentServiceException(Throwable cause) {
        super(cause);
    }
}
