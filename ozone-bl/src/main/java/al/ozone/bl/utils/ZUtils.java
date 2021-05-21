package al.ozone.bl.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import al.ozone.bl.exception.FileAlreadyExistException;
import al.ozone.bl.model.Category;
import al.ozone.bl.model.City;
import al.ozone.bl.model.Credit;
import al.ozone.bl.model.Customer;
import al.ozone.bl.model.Deal;
import al.ozone.bl.model.MenuItem;
import al.ozone.bl.model.Partner;
import al.ozone.bl.model.Role;
import al.ozone.bl.model.User;

/**
 * Util class
 * 
 * @author Ermal Aliraj
 */
public class ZUtils {
	
	protected static final transient Log logger = LogFactory.getLog(ZUtils.class);
	
	public static boolean isEmptyString(String str){
		return (str==null || str.equals("") || str.equalsIgnoreCase("null"));
	}
	
	public static double roundToDecimals(double d, int c) {
		int temp=(int)((d*Math.pow(10,c)));
		return (((double)temp)/Math.pow(10,c));
	}

	public static Partner getNewPartner() {
		Partner p = new Partner();
		p.setCategory(new Category());
		p.setCity(new City());
		return p;
	}

	public static boolean isEmptyCollection(Collection<Role> rolesList) {
		return (rolesList==null || rolesList.size()==0) ? true : false;
	}
	
	public static boolean isEmptyMap(Map<String, String> map) {
		return (map==null || map.size()==0) ? true : false;
	}
	
	public static <T> boolean isEmptyList(List<T> list) {
		return (list==null || list.size()==0);
	}
	
	public static <T> boolean isEmptyArray(T[] arr) {
		return (arr == null || arr.length==0);
	}

	public static <T> void printListOnLogger(List<T> list, Log logger, String level) {
		if(level.equalsIgnoreCase("warn")){
			for (T element : list) {
				logger.warn(element);
			}
		}else if(level.equalsIgnoreCase("info")){
			for (T element : list) {
				logger.info(element);
			}
		}else if(level.equalsIgnoreCase("debug")){
			for (T element : list) {
				logger.debug(element);
			}
		}else if(level.equalsIgnoreCase("warn")){
			for (T element : list) {
				logger.debug(element);
			}
		}
	}
	
	public static Date addDaysToDate(Date date, int dayDelta) {
		if(date==null) return new Date();
    	
		Calendar cal = new GregorianCalendar();
    	cal.setTime(date);
    	cal.add(Calendar.DATE, dayDelta);
    	return cal.getTime();
	}

	public static Date addMonthsToDate(Date date, int monthDelta) {
		if(date==null) return new Date();
    	
		Calendar cal = new GregorianCalendar();
    	cal.setTime(date);
    	cal.add(Calendar.MONTH, monthDelta);
    	return cal.getTime();
	}

	/**
	 * return true if the credit has not been used AND has not expired
	 * @param c credit to check
	 * @return true, if the credit can be counted as valid 
	 */
	public static boolean isValidCredit(Credit c) {	
		Date today = getDateAsDDMMYYYY(new Date());
		Date creditDate = getDateAsDDMMYYYY(c.getValidDate());
		return c.getUsedDate()==null && today.compareTo(creditDate) <= 0;
	}
	
	/**
	 * Returns true if the date passed as parameter is greater or equals to today.
	 * @param d date to check if is valid
	 * @return true, if the date is today date or a future date.
	 */
	public static boolean isFutureOrTodayDate(Date d) {	
		Date today = getDateAsDDMMYYYY(new Date());
		Date compareDate = getDateAsDDMMYYYY(d);
		return today.compareTo(compareDate) <= 0;
	}
	
	public static boolean isTodayDate(Date d) {	
		Date today = getDateAsDDMMYYYY(new Date());
		Date compareDate = getDateAsDDMMYYYY(d);
		return today.compareTo(compareDate) == 0;
	}
	
	/**
	 * Returns true id the date passed as parameter is greater or equals to today.
	 * @param d date to check if is valid
	 * @return true, if the date is today date or a future date.
	 */
	public static boolean isFutureDate(Date d) {	
		Date today = getDateAsDDMMYYYY(new Date());
		Date compareDate = getDateAsDDMMYYYY(d);
		return today.compareTo(compareDate) < 0;
	}
	
	public static boolean isEarlierDate(Date d) {
		Date today = getDateAsDDMMYYYY(new Date());
		Date compareDate = getDateAsDDMMYYYY(d);
		return today.compareTo(compareDate) > 0;
	}
	
	/**
	 * Returns true if the date passed as parameter is greater or equals to today.
	 * @param d date to check if is valid
	 * @return true, if the date is today date or a future date.
	 */
	public static boolean isEarlierOrTodayDate(Date d) {	
		Date today = getDateAsDDMMYYYY(new Date());
		Date compareDate = getDateAsDDMMYYYY(d);
		return today.compareTo(compareDate) >= 0;
	}
	
	public static boolean isTodayWithinRange(Date startDate, Date endDate) {
		return ( isEarlierOrTodayDate(startDate) &&  isFutureOrTodayDate(endDate));
	}


	/**
	 * TODO getDateAsDDMMYYYY seems to return the new formated date, but do not do that!
	 * see getDateAsStringAsDDMMYYYY()
	 * 
	 * Format the given date as dd/MM/yyyy
	 * @param d date to format
	 * @return new formated date
	 */
	public static Date getDateAsDDMMYYYY(Date d) {
	    Date retDate = null;
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		    String str = df.format(d);
			retDate = df.parse(str);
		} catch (ParseException e) {
			logger.error("Can not truncate date +"+d+" in format dd/MM/yyyy");
		} catch (Exception e2) {
			logger.error("General error converting date +"+d+" in format dd/MM/yyyy");
		}
	    return retDate;
	}
	
	/**
	 * Format the given date as dd/MM/yyyy
	 * @param d date to format
	 * @return new formated date as string
	 */
	public static String getDateAsStringAsDDMMYYYY(Date d) {
		String rv = "";
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			rv = df.format(d);
		} catch (Exception e) {
			logger.info("Error converting Date as String:", e);
		}
	    return rv;
	}
	
	/**
	 * Format the given date as dd/MM/yyyy HH:mm:ss
	 * @param d date to format
	 * @return new formated date as string
	 */
	public static String getDateAsString(Date d) {
		String rv = "";
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			rv = df.format(d);
		} catch (Exception e) {
			logger.info("Error converting Date as String:", e);
		}
	    return rv;
	}
	
	public static String getDealIdsFromList(List<Deal> list) {
        StringBuffer msg = new StringBuffer();

        for (Deal publication : list) {	
            msg.append(publication.getId()).append(", ");
        }
        if (msg.length() > 0) {
        	msg.deleteCharAt(msg.length() - 1);
            msg.deleteCharAt(msg.length() - 1);
        }
        return msg.toString();
	}

	public static int calculateRemainingCredits(List<Credit> credits) {
		int tot = 0;
		for (Credit c : credits) {
			if(isValidCredit(c)){
				tot = tot + c.getValue();
			}
		}
		return tot;		
	}
	
	public static List<Credit> getValidCreditsForCustomer(Customer customer) {
		List<Credit> allCredits = customer.getCredits();
		List<Credit> validCredits = new ArrayList<Credit>();
		for (Credit c : allCredits) {
			if(isValidCredit(c)){
				validCredits.add(c);
			}
		}
		return validCredits;		
	}

	/**
	 * Return Boolean.TRUE if val is "true", Boolean.FALSE if val is "false", null otherwise
	 * @param val
	 * @return
	 */
	public static Boolean getBooleanFromString(String val) {
		if(val==null){
			return null;
		}
		
		if(val.equals("true")){
			return Boolean.TRUE;
		}else
		if(val.equals("false")){
			return Boolean.FALSE;
		}else{
			return null;
		}
	}
	
	public static boolean getPrimitiveBooleanFromString(String val) {
		if(val == null){
			return false;
		}
		
		if(val.equals("true")){
			return true;
		}else{
			return false;
		}
	}
	
	public static List<String> getEmailsFromUsers(List<User> users) {
		List<String> emails = new ArrayList<String>();
		String email; 
		for (User user : users) {
			email = user.getEmail();
			if(!ZUtils.isEmptyString(email)){
				emails.add(email);
			}
		}
		return emails;
	}
	
    public static String printMenuChilds(MenuItem menu) {
    	StringBuffer sb = new StringBuffer();
    	sb.append(menu.getId()).append("\n");
    	List<MenuItem> childsList = menu.getChilds();
    	if(childsList == null) return "";

    	for (MenuItem menuItem : childsList) {
			sb.append("\t");
			sb.append(printMenuChilds(menuItem));
		}
		return sb.toString();
	}
    
	public static String getShortString(String string, int length) {
		String retVal = "";
		try {
			retVal = string.substring(0, length);
		} catch (StringIndexOutOfBoundsException e) {
			retVal = string;
		}
		return retVal;
	}
	
	/**
	 * Return the string with length at least 'length' characters.
	 * The string will be cut in the next ' ' (empty) character.
	 * @param string string to be cut
	 * @param length length to start considering cuting the string
	 * @return cuted string
	 */
	public static String getShortStringApprox(String string, int length) {
		String rv = "";	
		try {
	    	while(string.charAt(length+1) != ' '){
	    		length++;
	    	}
	    	rv = string.substring(0, length+1);		
		} catch (StringIndexOutOfBoundsException e) {
			rv = string;
		}
		return rv;
	}
	
    public static String getLoggedUsername() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User user = (User) authentication.getPrincipal();
	    String username = user.getUsername();
	    return username;
	}

	public static Collection<Role> getLoggedUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getRoles();
    }

	/**
	 * From the roles passed as parameter returns an array with only ID's or Descriptions
	 * depending on the toGet parameter
	 * @param roles Role's collection
	 * @param toGet ID in case we need the IDs, DESC in case we need the role descriptions
	 * @return string array
	 */
	public static String[] getUserRolesAsArray(Collection<Role> roles, String toGet) {
		String[] rolesName = new String[roles.size()];
		int i = 0;
		for (Role role : roles) {
			if(toGet.equals("ID")){
				rolesName[i] = role.getId();
			}else if(toGet.equals("DESC")){
				rolesName[i] = role.getDescription();
			}
			
			i++;
		}
		return rolesName;
	}

	public static String getUserRolesAsString(Collection<Role> roles) {
		String allRoles = "";
		for (Role role : roles) {
			allRoles += role.getDescription() + ", ";
		}
		allRoles = allRoles.substring(0, allRoles.length()-2);
		return allRoles;
	}

	public static String getUserRolesAsString() {
		Collection<Role> roles = ZUtils.getLoggedUserRoles();
		return getUserRolesAsString(roles);
	}
    
    public static String getLoggedUserFullname() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String userFullName = user.getName() + " " + user.getSurname();
        return userFullName;
    }

    /**
     * Get the cause of the Exception in case is not null, or the Message otherwise.
     * The message returned will be long length characters.
     * @param e Exception to get the message
     * @param length length of the returned string
     * @return Error message or Cause
     */
	public static String getMessageFromException(Exception e, int length) {
		String str = "";
		if(e.getCause()!=null){
			str = getShortString(e.getCause().toString(), length);
		}else if(e.getMessage()!=null){
			str = getShortString(e.getMessage(), length);
		}else{
			str = e.getClass().toString();
		}
		
		return str;
	}
	
	public static Integer getIntegerIfNotNull(String str) {
		int rv = 0;
		try {
			if (str != null && !str.equals("")) {
				rv = Integer.valueOf(str);
			} else {
				rv = 0;
			}
		} catch (NumberFormatException e) {
			rv = 0;			
		}	
		
		return rv;
	}
	
	/**
	 * Creates a file in the server from the input stream passed as parameter.
	 * 
	 * @param in InputStream of the file to save on the server
	 * @param fileName name of file to save on the server
	 * @param path path where to same the file
	 * @return full path of the uploaded file
	 * @throws IOException this exception will not be handled by AOP to do 
	 * 			any rollback in case of errors. See the caller to understand how
	 * 			transactions are managed.
	 * @throws FileAlreadyExistException runtime exception if the name exists on the server
	 */
	public static String uploadOnServer(InputStream in, String fileName, String path) throws IOException, FileAlreadyExistException {
		String fullPath = path + fileName;
        File file = new File(fullPath);

        try {
        	if(!file.exists()){
	        	FileOutputStream fileOutputStream = new FileOutputStream(file);
	
	            byte[] buffer = new byte[6124];
	            int bulk;
	            while (true) {
	            	bulk = in.read(buffer);
	            	if (bulk < 0) {
	            		break;
				    }
	            	fileOutputStream.write(buffer, 0, bulk);
	            	fileOutputStream.flush();
	            }
	
	            fileOutputStream.close();
	            in.close();
	
	            logger.info("The file has been uploaded in path:"+fullPath);
				//JSFUtils.addInfoMessage(event.getFile().getFileName()+ " is uploaded.");
        	}else{
        		throw new FileAlreadyExistException("File "+fileName+" already exist");
        	}
        } catch (IOException e) {
        	logger.error("IOException uploading file:"+fullPath, e);
        	throw e;
            //JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
        }
        return fullPath;
	}

	/**
	 * Remove physically the file with given filename from the file system server.
	 * @param fileName file name to delete
	 * @return true if any file is deleted, false otherwise
	 */
	public static boolean removeFromServer(String path, String fileName) {
		String fullPath = path + fileName;
		logger.debug("Deleting from server file "+fullPath);
        File file = new File(fullPath);
		return file.delete();
	}

	public static String getFileExtension(String fileName) {
		String[] splits = fileName.split("\\.");
		//last split will be the extensin. Example .jpg
		String ext = "." + splits[splits.length-1];
		return ext;
	} 
	
	public static Date getMidnightForDate(Date date){
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
    /**
     * Parses all command line switches and return an Hashtable
     * @param args
     * @return
     */
    public static Map<String, String> parseArgs(String[] args) {
        Hashtable<String, String> argsAsHash = new Hashtable<String, String>();
        String opt,val;
        for (int x=0; x<args.length; x++)
        {
           if (args[x].startsWith("-")) {
              int eq = args[x].indexOf("=");
              opt = args[x].substring(1,(eq!=-1 ? eq : args[x].length()));              
              if (eq!=-1){
            	  val = args[x].substring(eq+1);
              }
              else{
            	  val = opt;
              }
              argsAsHash.put(opt, val);
           }
        }
        return argsAsHash;
    }

	public static <T> List<T> cloneList(List<T> list) {
		List<T> cloneList = new ArrayList<T>();
		
		for (T e : list) {
			cloneList.add(e);
		}
		return cloneList;
	}

	public static boolean isStringNumber(String s) {
		boolean isNum = false;
			
		try {
			Integer.parseInt(s);
			isNum = true;
		} catch (NumberFormatException e) {
			isNum = false;
		}
		return isNum;
	}

	public static Date getDateFromString(String dateAsString) {
		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			if(dateAsString!=null){
				date = dateFormat.parse(dateAsString);
			}
		} catch (ParseException e) {
			logger.error("Can not convert "+dateAsString+" to Date. Error:"+ e.getMessage());
		}
		return date;
	}

	public static Deal createEmptyDeal() {
		Deal d = new Deal();
		d.setConditions("No Active publication!!!! Fake Conditions");
		d.setSynthesis("Fake Synthesis");
		d.setDescription("Fake Description");
		return d;
	}

	public static boolean isNumber(String str) {
		boolean rv = false;
		
		if(str==null){
			rv = false;
		}else{
			rv = true;
		}
		
		try {
			Integer.valueOf(str);
			rv = true;
		} catch (NumberFormatException e) {
			rv = false;
		}
		
		return rv;
	}
	
	public static boolean isDoubleZero(Double value) {
		if( value == null || value==0.0){
			return true;
		} else {
			return false;
		}		
	}
	
	public static Integer[] getDealChoiceIds(String dealChangeId) {
		Integer[] rv = new Integer[2];
		try {
			String[] ids = dealChangeId.split("-");
			rv[0] = Integer.parseInt(ids[0]);
			rv[1] = Integer.parseInt(ids[1]);
			return rv;
		} catch (Exception e) {
			logger.error("Error spliting Id +"+dealChangeId+" of DealChoice from drop-down.");
			return null;
		}
	}
	
	public static String html2text(String html) {
	    return Jsoup.parse(html).text();
	}
	
    public static String getCityId(String location) {
    	String rv = "";
    	    	
    	if( location.toUpperCase().contains(City.TIRANE.toUpperCase()) ){
    		rv = "TR";
    	} else
		if( location.toUpperCase().contains(City.VLORE.toUpperCase()) ){
			rv = "VL";
		} else
		if( location.toUpperCase().contains(City.DURRES.toUpperCase()) ){
			rv = "DR";
		} else
		if( location.toUpperCase().contains(City.SHKODER.toUpperCase()) ){
			rv = "SH";
		} else
		if( location.toUpperCase().contains(City.FIER.toUpperCase()) ){
			rv = "FR";
		} else
		if( location.toUpperCase().contains(City.LUSHNJE.toUpperCase()) ){
			rv = "LU";
		} else
		if( location.toUpperCase().contains(City.ELBASAN.toUpperCase()) ){
			rv = "EL";
		} else
    	if( location.toUpperCase().contains(City.BERAT.toUpperCase()) ){
    		rv = "BR";
    	}else
		if( location.toUpperCase().contains(City.KORCE.toUpperCase()) ){
			rv = "KO";
		}  else 
    	if( location.toUpperCase().contains(City.BULQIZE.toUpperCase()) ){
    		rv = "BZ";
    	} else 
    	if( location.toUpperCase().contains(City.DELVINE.toUpperCase()) ){
    		rv = "DL";
		} else
		if( location.toUpperCase().contains(City.DEVOLL.toUpperCase()) ){
			rv = "DV";
		} else
		if( location.toUpperCase().contains(City.DIBER.toUpperCase()) ){
			rv = "DI";
		} else
		if( location.toUpperCase().contains(City.GJIROKASTER.toUpperCase()) ){
			rv = "GJ";
		} else
		if( location.toUpperCase().contains(City.GRAMSH.toUpperCase()) ){
			rv = "GR";
		} else
		if( location.toUpperCase().contains(City.HAS.toUpperCase()) ){
			rv = "HS";
		} else
		if( location.toUpperCase().contains(City.KAVAJE.toUpperCase()) ){
			rv = "KJ";
		} else
		if( location.toUpperCase().contains(City.KOLONJE.toUpperCase()) ){
			rv = "ER";
		} else
		if( location.toUpperCase().contains(City.KRUJE.toUpperCase()) ){
			rv = "KR";
		} else
		if( location.toUpperCase().contains(City.KUCOVE.toUpperCase()) ){
			rv = "KV";
		} else
		if( location.toUpperCase().contains(City.KUKES.toUpperCase()) ){
			rv = "KU";
		} else
		if( location.toUpperCase().contains(City.LAC.toUpperCase()) ){
			rv = "LA";
		} else
		if( location.toUpperCase().contains(City.LEZHE.toUpperCase()) ){
			rv = "LE";
		} else
		if( location.toUpperCase().contains(City.LIBRAZHD.toUpperCase()) ){
			rv = "LB";
		} else
//		if( location.toUpperCase().contains(City.MALESI_E_MADHE.toUpperCase()) ){
//			rv = "TR";
//		} else
//		if( location.toUpperCase().contains(City.MALLAKASTER.toUpperCase()) ){
//			rv = "TR";
//		} else
//		if( location.toUpperCase().contains(City.MAT.toUpperCase()) ){
//			rv = "MT";
//		} else
//		if( location.toUpperCase().contains(City.MIRDITE.toUpperCase()) ){
//			rv = "";
//		} else
//		if( location.toUpperCase().contains(City.PEQIN.toUpperCase()) ){
//			rv = "TR";
//		} else 
		if( location.toUpperCase().contains(City.PERMET.toUpperCase()) ){
			rv = "PR";
		} else
		if( location.toUpperCase().contains(City.POGRADEC.toUpperCase()) ){
			rv = "PG";
		} else
		if( location.toUpperCase().contains(City.PUK.toUpperCase()) ){
			rv = "PU";
		} else
		if( location.toUpperCase().contains(City.SARANDE.toUpperCase()) ){
			rv = "SR";
		} else
		if( location.toUpperCase().contains(City.SKRAPAR.toUpperCase()) ){
			rv = "SK";
		} else
		if( location.toUpperCase().contains(City.TEPELENE.toUpperCase()) ){
			rv = "TP";
		} else	
		if( location.toUpperCase().contains(City.BARJAM_CURRI.toUpperCase()) ){
			rv = "BC";
		} else if( location.toUpperCase().contains("Albania".toUpperCase()) ){
			rv = "NA";
		}else{
			rv = "";
		}

		return rv;	
	}

	public static String getPathAfterContext(String url) {
		String rv = "";
		String[] arr = url.split("ozone.al/");
		String[] arrTest = url.split("OZoneFE/");
		
		//if arr[1]!= null, means there is a path behind the context
		if(arr.length == 2){			
			rv = "/" + arr[1];
		} else if (arrTest.length == 2){
			rv = "/" + arrTest[1];
		}
		return rv;
	}

}
