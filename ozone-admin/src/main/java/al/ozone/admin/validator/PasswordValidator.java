package al.ozone.admin.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.faces.validator.FacesValidator;

@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator{
	
	//Password of 8 to 16 characters and contain three of the following 4 items: upper case letter, lower case letter, a symbol, a number-->
    //(?=^[^\s]{8,16}$)((?=.*?\d)(?=.*?[A-Z])(?=.*?[a-z])|(?=.*?\d)(?=.*?[^\w\d\s])(?=.*?[a-z])|(?=.*?[^\w\d\s])(?=.*?[A-Z])(?=.*?[a-z])|(?=.*?\d)(?=.*?[A-Z])(?=.*?[^\w\d\s]))^.*
	private static final String PWD_PATTERN = "(?=^[^\\s]{8,16}$)((?=.*?\\d)(?=.*?[A-Z])(?=.*?[a-z])|(?=.*?\\d)(?=.*?[^\\w\\d\\s])(?=.*?[a-z])|(?=.*?[^\\w\\d\\s])(?=.*?[A-Z])(?=.*?[a-z])|(?=.*?\\d)(?=.*?[A-Z])(?=.*?[^\\w\\d\\s]))^.*";
 
	private Pattern pattern;
	private Matcher matcher;
 
	public PasswordValidator(){
		  pattern = Pattern.compile(PWD_PATTERN);
	}
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		matcher = pattern.matcher(value.toString());
		if(!matcher.matches()){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid E-mail format.");
			throw new ValidatorException(msg);
		}
	}
}