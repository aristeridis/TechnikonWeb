package gr.codehub.ed.technikonweb.exceptions;

/**
 *
 * @author alexandrosaristeridis
 */
public class CustomException extends RuntimeException {

	/**
	 *
	 * @param message
	 */
	public CustomException(String message) {
        super(message);
    }
}
