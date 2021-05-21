package al.ozone.admin.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.faces.validator.FacesValidator;

@FacesValidator("cityNameValidator")
public class CityNameValidator implements Validator{
	 
	private static final String STRING_PATTERN = "[A-Z][a-z]+";
 
	private Pattern pattern;
	private Matcher matcher;
 
	public CityNameValidator(){
		  pattern = Pattern.compile(STRING_PATTERN);
	}
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		matcher = pattern.matcher(value.toString());
		if(!matcher.matches()){
			FacesMessage msg = new FacesMessage("City name string validation failed.", 
												"Invalid city name string.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}
}