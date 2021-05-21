package al.ozone.bl.exception;

/**
 * Exception for creating a new file, when file with such name already exist
 * Only RuntimeExceptions are considered from AOP for rollback in case of errors.
 * 
 * @author Ermal Aliraj
 */
public class FileAlreadyExistException extends RuntimeException{

	private static final long serialVersionUID = 6976043594129542897L;

	public FileAlreadyExistException(String message) {
        super(message);
    }
}
