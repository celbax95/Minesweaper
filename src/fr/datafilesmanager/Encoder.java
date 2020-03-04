package fr.datafilesmanager;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encoder {

	private static final int KEY_LENGTH = 16;

	private static final String ALGORITHM = "AES";

	public static boolean decode(String value, boolean def, byte[] key) {
		try {
			boolean ret = Boolean.valueOf(decode(value, String.valueOf(def), key));

			return ret;
		} catch (Exception e) {
			return def;
		}
	}

	public static char decode(String value, char def, byte[] key) {
		try {
			char ret = decode(value, String.valueOf(def), key).charAt(0);

			return ret;
		} catch (Exception e) {
			return def;
		}
	}

	public static double decode(String value, double def, byte[] key) {
		try {
			double ret = Double.valueOf(decode(value, String.valueOf(def), key));

			return ret;
		} catch (Exception e) {
			return def;
		}
	}

	public static float decode(String value, float def, byte[] key) {
		try {
			float ret = Float.valueOf(decode(value, String.valueOf(def), key));

			return ret;
		} catch (Exception e) {
			return def;
		}
	}

	public static int decode(String value, int def, byte[] key) {
		try {
			int ret = Integer.valueOf(decode(value, String.valueOf(def), key));

			return ret;
		} catch (Exception e) {
			return def;
		}
	}

	public static long decode(String value, long def, byte[] key) {
		try {
			long ret = Long.valueOf(decode(value, String.valueOf(def), key));

			return ret;
		} catch (Exception e) {
			return def;
		}
	}

	public static String decode(String value, String def, byte[] key) {
		SecretKeySpec k = new SecretKeySpec(key, "AES");

		try {
			Cipher c = Cipher.getInstance(ALGORITHM);

			c.init(Cipher.DECRYPT_MODE, k);

			return new String(c.doFinal(String.valueOf(value).getBytes()));
		} catch (Exception e) {
			return null;
		}
	}

	public static String encode(boolean value, byte[] key) {
		return encode(String.valueOf(value), key);
	}

	public static String encode(char value, byte[] key) {
		return encode(String.valueOf(value), key);
	}

	public static String encode(double value, byte[] key) {
		return encode(String.valueOf(value), key);
	}

	public static String encode(float value, byte[] key) {
		return encode(String.valueOf(value), key);
	}

	public static String encode(int value, byte[] key) {
		return encode(String.valueOf(value), key);
	}

	public static String encode(long value, byte[] key) {
		return encode(String.valueOf(value), key);
	}

	public static String encode(String value, byte[] key) {
		SecretKeySpec k = new SecretKeySpec(key, "AES");

		try {
			Cipher c = Cipher.getInstance(ALGORITHM);

			c.init(Cipher.ENCRYPT_MODE, k);

			return new String(c.doFinal(String.valueOf(value).getBytes()));
		} catch (Exception e) {
			return null;
		}
	}

	private static Key getKey(byte[] key) {
		return new SecretKeySpec(key, ALGORITHM);
	}

	public static byte[] getKey(int key) {
		return getKey((long) key);
	}

	public static byte[] getKey(long key) {
		String stringKey = String.valueOf(key);
		int keyLenght = stringKey.length();

		if (keyLenght < KEY_LENGTH) {
			int fillUp = KEY_LENGTH - keyLenght;

			for (int i = 0; i < fillUp; i++) {
				stringKey += "0";
			}
		}
		return stringKey.getBytes();
	}
}
