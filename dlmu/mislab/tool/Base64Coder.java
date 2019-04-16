package dlmu.mislab.tool;

import dlmu.mislab.common.Config;

public class Base64Coder {
	private static final String SIMPLE_KEY="=*$23.2_1Doe&m63923ks;dfke!Koe>lkejrw%kIPi3'23k@.c0,+af3mk#12~mfk3";
	
	private Base64Coder() {
	}

	// Mapping table from 6-bit nibbles to Base64 characters.
	private static char[] map1 = new char[64];
	static {
		int i = 0;
		for (char c = 'A'; c <= 'Z'; c++)
			map1[i++] = c;
		for (char c = 'a'; c <= 'z'; c++)
			map1[i++] = c;
		for (char c = '0'; c <= '9'; c++)
			map1[i++] = c;
		map1[i++] = '+';
		map1[i++] = '/';
	}

	// Mapping table from Base64 characters to 6-bit nibbles.
	private static byte[] map2 = new byte[128];
	static {
		for (int i = 0; i < map2.length; i++)
			map2[i] = -1;
		for (int i = 0; i < 64; i++)
			map2[map1[i]] = (byte) i;
	}

	protected static String encodeString(String s, String charsetName) {
		try {
			return new String(encode(s.getBytes(charsetName)));
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Encodes a string into Base64 format. No blanks or line breaks are
	 * inserted.
	 * 
	 * @param s
	 *            a String to be encoded.
	 * @return A String with the Base64 encoded data.
	 */
	public static String encodeString(String s) {
		if (s == null || s.isEmpty()) {
			return null;
		}
		return encodeString(s, Config.CHARSET_ON_CLIENT);
	}
	
	public static String encodeString(byte[] in){
		return new String(encode(in));
	}

	/**
	 * Encodes a byte array into Base64 format. No blanks or line breaks are
	 * inserted.
	 * 
	 * @param in
	 *            an array containing the data bytes to be encoded.
	 * @return A character array with the Base64 encoded data.
	 */
	static char[] encode(byte[] in) {
		return encode(in, in.length);
	}

	/**
	 * Encodes a byte array into Base64 format. No blanks or line breaks are
	 * inserted.
	 * 
	 * @param in
	 *            an array containing the data bytes to be encoded.
	 * @param iLen
	 *            number of bytes to process in <code>in</code>.
	 * @return A character array with the Base64 encoded data.
	 */
	private static char[] encode(byte[] in, int iLen) {
		int oDataLen = (iLen * 4 + 2) / 3; // output length without padding
		int oLen = ((iLen + 2) / 3) * 4; // output length including padding
		char[] out = new char[oLen];
		int ip = 0;
		int op = 0;
		while (ip < iLen) {
			int i0 = in[ip++] & 0xff;
			int i1 = ip < iLen ? in[ip++] & 0xff : 0;
			int i2 = ip < iLen ? in[ip++] & 0xff : 0;
			int o0 = i0 >>> 2;
			int o1 = ((i0 & 3) << 4) | (i1 >>> 4);
			int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
			int o3 = i2 & 0x3F;
			out[op++] = map1[o0];
			out[op++] = map1[o1];
			out[op] = op < oDataLen ? map1[o2] : '=';
			op++;
			out[op] = op < oDataLen ? map1[o3] : '=';
			op++;
		}
		return out;
	}

	/*
	 * ********************************************************************************
	 * ********************************************************************************
	 */

	protected static String decodeString(String s, String charsetName) {
		String temp = null;
		try {
			temp = new String(decode(s), charsetName);
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * Decodes a string from Base64 format.
	 * 
	 * @param s
	 *            a Base64 String to be decoded.
	 * @return A String containing the decoded data.
	 * @throws IllegalArgumentException
	 *             if the input is not valid Base64 encoded data.
	 */
	public static String decodeString(String s) {
		if (s == null || s.isEmpty()) {
			return null;
		}
		return new String(decodeString(s, Config.CHARSET_ON_CLIENT));
	}

	/**
	 * Decodes a byte array from Base64 format.
	 * 
	 * @param s
	 *            a Base64 String to be decoded.
	 * @return An array containing the decoded data bytes.
	 * @throws IllegalArgumentException
	 *             if the input is not valid Base64 encoded data.
	 */
	static byte[] decode(String s) {
		return decode(s.toCharArray());
	}

	/**
	 * Decodes a byte array from Base64 format. No blanks or line breaks are
	 * allowed within the Base64 encoded data.
	 * 
	 * @param in
	 *            a character array containing the Base64 encoded data.
	 * @return An array containing the decoded data bytes.
	 * @throws IllegalArgumentException
	 *             if the input is not valid Base64 encoded data.
	 */
	static byte[] decode(char[] in) {
		int iLen = in.length;
		if (iLen % 4 != 0)
			throw new IllegalArgumentException(
					"Length of Base64 encoded input string is not a multiple of 4.");
		while (iLen > 0 && in[iLen - 1] == '=')
			iLen--;
		int oLen = (iLen * 3) / 4;
		byte[] out = new byte[oLen];
		int ip = 0;
		int op = 0;
		while (ip < iLen) {
			int i0 = in[ip++];
			int i1 = in[ip++];
			int i2 = ip < iLen ? in[ip++] : 'A';
			int i3 = ip < iLen ? in[ip++] : 'A';
			if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127)
				throw new IllegalArgumentException(
						"Illegal character in Base64 encoded data.");
			int b0 = map2[i0];
			int b1 = map2[i1];
			int b2 = map2[i2];
			int b3 = map2[i3];
			if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0)
				throw new IllegalArgumentException(
						"Illegal character in Base64 encoded data.");
			int o0 = (b0 << 2) | (b1 >>> 4);
			int o1 = ((b1 & 0xf) << 4) | (b2 >>> 2);
			int o2 = ((b2 & 3) << 6) | b3;
			out[op++] = (byte) o0;
			if (op < oLen)
				out[op++] = (byte) o1;
			if (op < oLen)
				out[op++] = (byte) o2;
		}
		return out;
	}
	
	public static String simpleEncode(String message){
		return simpleEncode(message,SIMPLE_KEY);
	}
	public static String simpleEncode(String message, String key){
		String encoded=xorMessage(message,key);
		return encodeString(encoded);
	}
	public static String simpleDecode(String message){
		return simpleDecode(message,SIMPLE_KEY);
	}
	public static String simpleDecode(String message, String key){
		String decoded=decodeString(message);
		return xorMessage(decoded,key);
	}

	private static String xorMessage(String message, String key) {
		try {
			if (message == null || key == null){
				return null;
			}
			char[] keys = key.toCharArray();
			char[] mesg = message.toCharArray();

			int ml = mesg.length;
			int kl = keys.length;
			char[] newmsg = new char[ml];

			for (int i = 0; i < ml; i++) {
				newmsg[i] = (char) (mesg[i] ^ keys[i % kl]);
			}// for i
			mesg = null;
			keys = null;
			return new String(newmsg);
		} catch (Exception e) {
			return null;
		}
	}// xorMessage

	public static void main(String[] args) {
		System.out.println(encodeString("张三"));
		System.out.println(decodeString("5byg5LiJ"));
		String encoded=simpleEncode("123");
		System.out.println(encoded);
		String decoded=simpleDecode(encoded);
		System.out.println(decoded);
	}
} // end class Base64Coder
