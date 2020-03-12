package fr.datafilesmanager;

import java.security.Key;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class Cryptor {

	private static final int KEY_LENGTH = 16;

	private static final String ALGORITHM = "AES";

	/**
	 * static Singleton instance.
	 */
	private static volatile Cryptor instance;

	/**
	 * Return a singleton instance of Encryptor.
	 */
	public static Cryptor getInstance() {
		// Double lock for thread safety.
		if (instance == null) {
			synchronized (Cryptor.class) {
				if (instance == null) {
					instance = new Cryptor();
				}
			}
		}
		return instance;
	}

	private static char randomChar() {
		return (char) (new Random().nextInt(93) + 33);
	}

	private boolean base64Encoding;

	private int scrambler;

	/**
	 * Private constructor for singleton.
	 */
	private Cryptor() {
		this.base64Encoding = false;
		this.scrambler = 0;
	}

	private String decodeFromBase64(String string) {
		string += "==";

		return new String(Base64.decode(string));
	}

	boolean decrypt(String value, boolean def, byte[] key) {
		try {
			boolean ret = Boolean.valueOf(this.decrypt(value, String.valueOf(def), key));

			return ret;
		} catch (Exception e) {
			return def;
		}
	}

	public char decrypt(String value, char def, byte[] key) {
		try {
			char ret = this.decrypt(value, String.valueOf(def), key).charAt(0);

			return ret;
		} catch (Exception e) {
			return def;
		}
	}

	public double decrypt(String value, double def, byte[] key) {
		try {
			double ret = Double.valueOf(this.decrypt(value, String.valueOf(def), key));

			return ret;
		} catch (Exception e) {
			return def;
		}
	}

	public float decrypt(String value, float def, byte[] key) {
		try {
			float ret = Float.valueOf(this.decrypt(value, String.valueOf(def), key));

			return ret;
		} catch (Exception e) {
			return def;
		}
	}

	public int decrypt(String value, int def, byte[] key) {
		try {
			int ret = Integer.valueOf(this.decrypt(value, String.valueOf(def), key));

			return ret;
		} catch (Exception e) {
			return def;
		}
	}

	public long decrypt(String value, long def, byte[] key) {
		try {
			long ret = Long.valueOf(this.decrypt(value, String.valueOf(def), key));

			return ret;
		} catch (Exception e) {
			return def;
		}
	}

	public String decrypt(String value, String def, byte[] key) {
		SecretKeySpec k = new SecretKeySpec(key, "AES");

		String decryptedValue = "";

		if (this.base64Encoding) {
			value = this.decodeFromBase64(value);
		}

		try {
			Cipher c = Cipher.getInstance(ALGORITHM);

			c.init(Cipher.DECRYPT_MODE, k);

			decryptedValue = new String(c.doFinal(value.getBytes()));

		} catch (Exception e) {
			return def;
		}

		if (this.scrambler > 0)
			return this.unScramble(decryptedValue);
		else
			return decryptedValue;
	}

	private String encodeToBase64(String string) {
		String encodedValue = Base64.encode(string.getBytes());

		return encodedValue.substring(0, encodedValue.length() - 2);
	}

	public String encrypt(boolean value, byte[] key) {
		return this.encrypt(String.valueOf(value), key);
	}

	public String encrypt(char value, byte[] key) {
		return this.encrypt(String.valueOf(value), key);
	}

	public String encrypt(double value, byte[] key) {
		return this.encrypt(String.valueOf(value), key);
	}

	public String encrypt(float value, byte[] key) {
		return this.encrypt(String.valueOf(value), key);
	}

	public String encrypt(int value, byte[] key) {
		return this.encrypt(String.valueOf(value), key);
	}

	public String encrypt(long value, byte[] key) {
		return this.encrypt(String.valueOf(value), key);
	}

	public String encrypt(String value, byte[] key) {

		String encoded = "";
		String tmp = "";

		do {
			tmp = value;

			if (this.scrambler > 0) {
				tmp = this.scramble(tmp);
			}

			SecretKeySpec k = new SecretKeySpec(key, "AES");

			String encryptedValue = "";

			try {
				Cipher c = Cipher.getInstance(ALGORITHM);

				c.init(Cipher.ENCRYPT_MODE, k);

				encryptedValue = new String(c.doFinal(tmp.getBytes()));
			} catch (Exception e) {
				return null;
			}

			if (this.base64Encoding) {
				encoded = this.encodeToBase64(encryptedValue);
			} else {
				encoded = encryptedValue;
			}

		} while (!this.decrypt(encoded, value + "_", key).equals(value));

		return encoded;
	}

	private Key getKey(byte[] key) {
		return new SecretKeySpec(key, ALGORITHM);
	}

	public byte[] getKey(int key) {
		return this.getKey((long) key);
	}

	public byte[] getKey(long key) {
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

	public int getScrambler() {
		return this.scrambler;
	}

	public boolean isBase64Encoding() {
		return this.base64Encoding;
	}

	public String scramble(String value) {
		for (int i = 0; i < this.scrambler; i++) {
			value = randomChar() + value + randomChar();
		}

		return value;
	}

	public void setBase64Encoding(boolean state) {
		this.base64Encoding = state;
	}

	public void setScrambler(int scrambler) {
		this.scrambler = Math.abs(scrambler);
	}

	public String unScramble(String value) {
		return value.substring(this.scrambler, value.length() - this.scrambler);
	}
}
