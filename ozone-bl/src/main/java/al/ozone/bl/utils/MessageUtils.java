package al.ozone.bl.utils;

import java.util.List;
import java.util.Locale;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;

public class MessageUtils {

    public static String buildMessageWithoutSpaces(String... txt) {
        StringBuffer msg = new StringBuffer();

        for (String msgPart : txt) {
            msg.append(msgPart);
        }
        return msg.toString();
    }

    public static String buildMessage(String... txt) {
        StringBuffer msg = new StringBuffer();

        for (String msgPart : txt) {
            msg.append(msgPart).append(" ");
        }
        if (msg.length() > 0) {
            msg.deleteCharAt(msg.length() - 1);
        }
        return msg.toString();
    }
    
    public static String buildMessageWithComma(String... txt) {
        StringBuffer msg = new StringBuffer();

        for (String msgPart : txt) {
            msg.append(msgPart).append(", ");
        }
        
        String rv = msg.toString();
        
        if (rv.length() > 0) {
        	rv = rv.substring(0, rv.length()-2);
        }
        return rv;
    }
    
    public static String buildMessageWithComma(List<String> txt) {
        StringBuffer msg = new StringBuffer();

        for (String msgPart : txt) {
            msg.append(msgPart).append(", ");
        }
        
        String rv = msg.toString();
        
        if (rv.length() > 0) {
        	rv = rv.substring(0, rv.length()-2);
        }
        return rv;
    }

    public static String getMessage(ResourceBundleMessageSource messageSource, String code) {
        return getMessage(messageSource, code, (Object[]) null);
    }

    public static String getMessage(ResourceBundleMessageSource messageSource, String code, Object... args) {
        String ret;

        try {
            ret = messageSource.getMessage(code, args, Locale.ENGLISH);
        } catch (NoSuchMessageException exc) {
            ret = "???" + code + "???";
        }
        return ret;
    }
}
