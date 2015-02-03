package uk.ac.aber.dcs.cs221.group16.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
 

/**
 * @author http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
 * @author http://stackoverflow.com/questions/487906/java-phone-number-format-api
 * @author Steven(Sta17)
 * @since 2.1
 * @version 2.2
 * 
 * Name and Comment validator is by me, Steven(Sta17) rest is from links provided above
 *
 */
public class Validator {
 
	private Pattern pattern;
	private Matcher matcher;
 
	private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
	public Validator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}
 
	/**
	 * Validate hex with regular expression
	 * 
	 * @param hex
	 *            hex for validation
	 * @return true valid hex, false invalid hex
	 */
	public boolean validate(final String hex) {
 
		matcher = pattern.matcher(hex);
		return matcher.matches();
 
	}
	
	/**
	 * @param pPhoneNumber
	 * @return true if the phone number is correct
	 */
	public boolean isPhoneNumberCorrect(String pPhoneNumber) {

	    Pattern pattern = Pattern
	            .compile("((\\+[1-9]{3,4}|0[1-9]{4}|00[1-9]{3})\\-?)?\\d{8,20}");
	    Matcher matcher = pattern.matcher(pPhoneNumber);
	    if (matcher.matches()) {
	    	return true;
	    }
	    return false;
	}
	
	/**
	 * Does Validation for the basic for names.
	 * @param comment name to validate
	 * @return true if ok, false if bad format
	 */
	public boolean isNameCorrect(String name){
		Pattern pattern = Pattern
	            .compile("[a-zA-Z ]*");
	    Matcher matcher = pattern.matcher(name);
	    if (matcher.matches()) {
	    	return true;
	    }
	    return false;
	}
	
	/**
	 * Does Validation for the basic for comments.
	 * @param comment comment to validate
	 * @return true if ok, false if bad format
	 */
	public boolean isCommentCorrect(String comment){
		Pattern pattern = Pattern
	            .compile("[a-zA-Z 1-9!?/-_+()#&%=@*<>.,:$£€]*");
	    Matcher matcher = pattern.matcher(comment);
	    if (matcher.matches()) {
	    	return true;
	    }
	    return false;
	}
	
}