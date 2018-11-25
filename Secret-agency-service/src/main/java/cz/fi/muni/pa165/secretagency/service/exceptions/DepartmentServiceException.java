package cz.fi.muni.pa165.secretagency.service.exceptions;

/**
 * Class for exceptions thrown by Department Service.
 *
 * @author Milos Silhar (433614)
 */
public class DepartmentServiceException extends RuntimeException {

    public DepartmentServiceException() {
        super();
    }

    public DepartmentServiceException(String message) {
        super(message);
    }

    public DepartmentServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepartmentServiceException(Throwable cause) {
        super(cause);
    }
}
