package fr.fms.exception;

/**
 * Crée une exception avec un message explicatif.
 *
 * @param message message décrivant l'erreur
 */
public class InsufficientBalanceException extends Exception {

	private static final long serialVersionUID = 1L;

	public InsufficientBalanceException (String message) {
		super(message);
	}
}
