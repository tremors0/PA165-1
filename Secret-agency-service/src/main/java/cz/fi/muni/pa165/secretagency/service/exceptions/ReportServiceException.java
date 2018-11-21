package cz.fi.muni.pa165.secretagency.service.exceptions;

/**
 * Class for exceptions thrown by Report Service.
 *
 * @author Jan Pavlu (487548)
 */
public class ReportServiceException extends RuntimeException {

    public ReportServiceException(String message) {
        super(message);
    }
}
