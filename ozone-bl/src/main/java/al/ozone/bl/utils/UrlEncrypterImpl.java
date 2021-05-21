package al.ozone.bl.utils;

import java.io.UnsupportedEncodingException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class UrlEncrypterImpl implements UrlEncrypter{
	
	private Cipher ecipher;
    private Cipher dcipher;
    private byte[] salt =  {
            (byte)0xA9, (byte)0x9B, (byte)0xC2, (byte)0x32,
            (byte)0x55, (byte)0x35, (byte)0xE3, (byte)0x23
        };
    int iterationCount = 3;
    
    public UrlEncrypterImpl(String passPhrase) {

        try{
            KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

            ecipher = Cipher.getInstance(key.getAlgorithm());
            dcipher = Cipher.getInstance(key.getAlgorithm());

            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

        } catch (java.security.InvalidAlgorithmParameterException e){
        } catch (java.security.spec.InvalidKeySpecException e){
        } catch (javax.crypto.NoSuchPaddingException e){
        } catch (java.security.NoSuchAlgorithmException e){
        } catch (java.security.InvalidKeyException e){
        }
    }

    public String encrypt(String str){
        try{
            byte[] utf8 = str.getBytes("UTF8");
            byte[] enc  = ecipher.doFinal(utf8);
            String rv = Base64.encodeBase64URLSafeString(enc);
            return rv;
        } catch (javax.crypto.BadPaddingException e){
        } catch (IllegalBlockSizeException e){
        } catch (UnsupportedEncodingException e){
        }
        return null;
    }

    public String decrypt(String str){
        try{
            byte[] dec = Base64.decodeBase64(str.getBytes("UTF8"));
            byte[] utf8 = dcipher.doFinal(dec);
            return new String(utf8,"UTF8");
        } catch (javax.crypto.BadPaddingException e){
        } catch (IllegalBlockSizeException e){
        } catch (UnsupportedEncodingException e){
        } catch (java.io.IOException e){
        }
        return null;
    }
    
    public static void main(String[] args) {
    	UrlEncrypterImpl s = new UrlEncrypterImpl("statusi1");
    	String e = s.encrypt("ermal");
    	System.out.println(e);
    	String d = s.decrypt(e);
    	System.out.println(d);
	}
}
