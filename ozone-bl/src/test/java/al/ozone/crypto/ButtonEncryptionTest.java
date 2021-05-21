//package al.ozone.crypto;
//
//import java.io.*;
//import java.security.Security;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.paypal.wpstoolkit.services.EWPServices;
//
//import al.ozone.bl.utils.EnvConfig;
//
///**
// * This class tests encryption of the button using certificates in the path defined in the
// * env.properties document. To test the correctness of the certificates, execute the class, run the
// * output html file, the class produces and check if the flow end up to paypal with same
// * parameters specified in this class.
// */
//
//public class ButtonEncryptionTest {
//
//	protected static final transient Log logger = LogFactory.getLog(ButtonEncryptionTest.class);
//
//	private static String output_file = "bin/paypal_enc_form.html";
//
//	protected static ClassPathXmlApplicationContext applicationContext;
//	static EnvConfig envConfig;
//
//	public static void main(String[] args) {
//		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//
//		applicationContext = new ClassPathXmlApplicationContext(new String[] { "classpath*:**//applicationContext-*.xml" });
//		envConfig = (EnvConfig) applicationContext.getBean("envConfig");
//
//		String invoice = "111";
//		String item_name = "Nje muaj palester 2000L ne vend te 4000L";
//		String amount = "17.00";
//		String discount_amount = "20.00";
//
//		File outputFile = new File( output_file );
//		if ( outputFile.exists() ){
//			outputFile.delete();
//		}
//
//		try {
//			String enc = encryptPayPalButton(invoice, item_name, amount, discount_amount);
//			logger.debug("Encrypted button: \n"+enc);
//
//			OutputStream fout= new FileOutputStream( output_file );
//	        OutputStream bout= new BufferedOutputStream(fout);
//			OutputStreamWriter out = new OutputStreamWriter(bout, "US-ASCII");
//
//	        out.write(enc);
//
//	        out.flush();  // Don't forget to flush!
//	        out.close();
//		}
//	    catch (UnsupportedEncodingException e) {
//	        logger.error("This VM does not support the ASCII character set.", e);
//	    }
//	    catch (IOException e) {
//	    	logger.error("IOException: ", e);
//		}
//		catch (Exception e) {
//			logger.error("General Exception: ", e);
//		}
//	}
//
//	private static String encryptPayPalButton(String invoice, String item_name, String amount, String discount_amount){
//		String button = null;
//		try {
//			EWPServices ewp = new EWPServices();
//			StringBuffer buffer = new StringBuffer("cmd=_xclick\n");
//			buffer.append("business=" + envConfig.getPpSellerEmail() + "\n");
//			buffer.append("currency_code=" + envConfig.getPpCurrencyCode() + "\n");
//			buffer.append("return=" + envConfig.getPpReturnUrl() + "\n");
//			buffer.append("cancel_return=" + envConfig.getPpCancelUrl() + "\n");
//			buffer.append("notify_url=" + envConfig.getPpNotifyUrl() + "\n");
//			buffer.append("invoice=" + invoice + "\n");
//			buffer.append("discount_amount=" + discount_amount + "\n");
//			buffer.append("item_name=" + item_name + "\n");
//			buffer.append("amount=" + amount + "\n");
//			buffer.append("cpp_cart_border_color=#3892BC\n");
//			buffer.append("cpp_logo_image=http://www.ozone.al/image/logo_paypal.png\n");
//
//			buffer.append("cert_id=" + envConfig.getPpCertId() + "\n");
//			buffer.append("charset=UTF-8\n");
//
//			String buttonStr = buffer.toString();
//			String ewpCertPath = envConfig.getPpEwpCertPath();
//			String privateKey = envConfig.getPpPrivateKey();
//			String ppCertPath = envConfig.getPpCertPath();
//			String envURL = envConfig.getPpEnv();
//			String btnImage = envConfig.getPpButtonImg();
//			logger.debug("Encrypting form: "+buttonStr);
//			button = ewp.encryptButton(buttonStr.getBytes("UTF-8"), ewpCertPath, privateKey, ppCertPath, envURL, btnImage);
//		} catch (Throwable e) {
//			logger.error("Error encrypting PayPal button:", e);
//		}
//
//		return button;
//	}
//}
