package dlmu.mislab.tool;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import dlmu.mislab.common.Config;

//import org.apache.commons.codec.binary.Base64;
/**
 * 可靠AES对称加密方法
 * @author GuRui
 *
 */
public class AESEncrypt {
	public final static Key getKey(byte[] encodedKey){
		if(encodedKey==null){
			return null;
		}
		return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
	}
	
	public final static Key generateKey() throws NoSuchAlgorithmException{
	      KeyGenerator kg = KeyGenerator.getInstance("AES");
	      SecureRandom random = new SecureRandom();
	      kg.init(random);
	      return kg.generateKey();
	}
	
	public static final String encrypt(final String message, final Key key, final IvParameterSpec salt) throws IllegalBlockSizeException,
	BadPaddingException, NoSuchAlgorithmException,
	NoSuchPaddingException, InvalidKeyException,
	UnsupportedEncodingException, InvalidAlgorithmParameterException {
	      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	      cipher.init(Cipher.ENCRYPT_MODE,key,salt);
	      byte[] stringBytes = message.getBytes();
	      byte[] raw = cipher.doFinal(stringBytes);
	      //return new String(Base64.encodeBase64(raw));
	      return new String(Base64Coder.encode(raw));
	}
	
	public static final String decrypt(final String encrypted,final Key key, final IvParameterSpec salt) throws InvalidKeyException,
	NoSuchAlgorithmException, NoSuchPaddingException,
	IllegalBlockSizeException, BadPaddingException, IOException, InvalidAlgorithmParameterException {
	      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	      cipher.init(Cipher.DECRYPT_MODE, key,salt);
	      byte[] raw = Base64Coder.decode(bytesToChars(encrypted.getBytes(Config.CHARSET_ON_CLIENT)));
	      byte[] stringBytes = cipher.doFinal(raw);
	      String clearText = new String(stringBytes, Config.CHARSET_ON_CLIENT);
	      return clearText;
	}
	
	static byte[] charsToBytes (char[] chars) {
		   Charset cs = Charset.forName (Config.CHARSET_ON_CLIENT);
		   CharBuffer cb = CharBuffer.allocate (chars.length);
		   cb.put (chars);
		                 cb.flip ();
		   ByteBuffer bb = cs.encode (cb);
		  
		   return bb.array();

	}

	static char[] bytesToChars (byte[] bytes) {
	      Charset cs = Charset.forName (Config.CHARSET_ON_CLIENT);
	      ByteBuffer bb = ByteBuffer.allocate (bytes.length);
	      bb.put (bytes);
	                 bb.flip ();
	       CharBuffer cb = cs.decode (bb);
	  
	   return cb.array();
	}
	
	public static byte[] loadKey(File file){
		byte[] rtn=new byte[16];
		try{
			FileInputStream fi= new FileInputStream(file);
			BufferedInputStream bin= new BufferedInputStream(fi);
			bin.read(rtn);
			bin.close();
			return rtn;
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean saveKey(File file, byte[] key){
		try{
			FileOutputStream fs = new FileOutputStream(file);
	        BufferedOutputStream bos = new BufferedOutputStream(fs);
	        bos.write(key);
	        bos.close();
	        return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static IvParameterSpec getSalt(byte[] salt){
		return new IvParameterSpec(salt);
	}
	
	/***
	 * 解密字符串
	 * @param encoded 被加密字符串
	 * @param keyFile key文件
	 * @param salt	  16位
	 * @return null if any error occurs
	 */
	public static String decrypt(String encoded, File keyFile, byte[] salt){
		try {
			return AESEncrypt.decrypt(encoded, AESEncrypt.getKey(AESEncrypt.loadKey(keyFile)), AESEncrypt.getSalt(salt));
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | InvalidAlgorithmParameterException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args){
		Key key=null;
		byte[] DESIV = { 0x12, 0x33, 0x56, 0x78, 0x13, 0x34, 0x59, 0x78, 0x22, 0x47, 0x17, 0x78, 0x69, 0x34, 0x35, 0x72};// 16位缓冲区
		IvParameterSpec pm=new IvParameterSpec(DESIV);
		String encoded=null;
		try {
			key=AESEncrypt.generateKey();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File keyFile=new File("/temp/key.dat");
		AESEncrypt.saveKey(keyFile, key.getEncoded());
		key = AESEncrypt.getKey(AESEncrypt.loadKey(keyFile));
		
		
		try {
			encoded = AESEncrypt.encrypt("f388c51fe4be9ea13706737ef1527b09",key , pm);
		} catch (InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException | NoSuchAlgorithmException
				| NoSuchPaddingException | UnsupportedEncodingException
				| InvalidAlgorithmParameterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
				
		System.out.println(encoded);
		
		String decoded=null;
		try {
			decoded = AESEncrypt.decrypt(encoded, key, pm);
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | InvalidAlgorithmParameterException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(decoded);
	}

}
