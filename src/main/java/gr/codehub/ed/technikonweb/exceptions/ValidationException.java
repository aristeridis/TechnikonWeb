package gr.codehub.ed.technikonweb.exceptions;

/**
 *
 * @author alexandrosaristeridis
 */
public class ValidationException extends RuntimeException {

	/**
	 *
	 * @param message
	 */
	public ValidationException(String message) {
        super(message);
    }
}
