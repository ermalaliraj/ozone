package al.ozone.bl.exception;

/**

 * @author Ermal Aliraj
 */
public class EasyPayException extends RuntimeException{

	private static final long serialVersionUID = -8040432028609055188L;

	public EasyPayException(String message) {
        super(message);
    }
}
