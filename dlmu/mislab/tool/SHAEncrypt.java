package dlmu.mislab.tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Help to get and compare the password with SHA hashed string
 * 
 * @author Grain
 * 
 */
public class SHAEncrypt {
	private static final String ENCODING_ALOG = "SHA1";
	private static final String ENCODED_STRING_FOR_123 = "40bd001563085fc35165329ea1ff5c5ecbdbbeef";
	public static final String DEFAULT_ENCODED_PASSWORD = ENCODED_STRING_FOR_123;

	/**
	 * Judge whether the password is the same as encoded that stored in DB. The
	 * pwd string may be Chinese characters
	 * 
	 * @param pwd
	 *            Password in plain text, nullable
	 * @param shaStr
	 *            Encoded password, nullable
	 * @return true if password in plain text and encoded text is the same,
	 *         false when is not the same or pwd or shaStr is null.
	 */
	public static boolean isSame(String pwd, String shaStr) {
		if (pwd == null || shaStr == null) {
			return false;
		}
		if (shaStr.equals(getEncryptedString(pwd.trim()))) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param srcString
	 *            String to be encoded
	 * @return the encoded string (40 bytes)
	 */
	public static String getEncryptedString(String srcString) {
		if (srcString == null || srcString.isEmpty()) {
			return null;
		}
		try {
			MessageDigest sha = MessageDigest.getInstance(ENCODING_ALOG);
			byte[] srcBytes = srcString.getBytes();
			sha.update(srcBytes);
			return byteArray2Hex(sha.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String byteArray2Hex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String rtn=formatter.toString();
		formatter.close();
		return rtn;
	}

	public static void main(String[] args) {
		System.out.println(SHAEncrypt.getEncryptedString("123"));
		System.out.println(SHAEncrypt.getEncryptedString(""));
		System.out.println(SHAEncrypt.getEncryptedString(null));
		System.out.println(SHAEncrypt.getEncryptedString("����"));
		System.out.println(SHAEncrypt.isSame("����",
				"b9b0ec5e6872c2237692f22dd052409450a81b60"));
		System.out.println(SHAEncrypt.isSame("123", DEFAULT_ENCODED_PASSWORD));
		System.out.println(SHAEncrypt.isSame("123", ""));
		System.out.println(SHAEncrypt.isSame("123", null));
	}
}
