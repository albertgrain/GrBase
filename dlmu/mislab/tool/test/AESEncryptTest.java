package dlmu.mislab.tool.test;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

import dlmu.mislab.tool.AESEncrypt;

public class AESEncryptTest {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		try{
			Key k = AESEncrypt.generateKey();
		    SecureRandom random = new SecureRandom();
		    IvParameterSpec iv = new IvParameterSpec(random.generateSeed(16));
		
		    String plainText = "hello world中国人";
		    System.out.println("Clear Text:" + plainText);
		    String encryptedString = AESEncrypt.encrypt(plainText,k,iv);
		    System.out.println("Encrypted String:" + encryptedString);
		    System.out.println("Decrypted String:"+AESEncrypt.decrypt(encryptedString,k,iv));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

	}

}
