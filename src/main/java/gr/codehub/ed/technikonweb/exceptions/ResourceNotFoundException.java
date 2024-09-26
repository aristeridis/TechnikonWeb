package gr.codehub.ed.technikonweb.exceptions;

/**
 *
 * @author alexandrosaristeridis
 */
public class ResourceNotFoundException extends RuntimeException {

	/**
	 *
	 * @param message
	 */
	public ResourceNotFoundException(String message) {
        super(message);
    }
}
