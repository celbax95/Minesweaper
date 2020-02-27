package fr.datafilesmanager;

import java.nio.charset.StandardCharsets;

public class Encoder {

	public static boolean decode(String value, boolean def, int key) {
		try {
			boolean ret = Boolean.valueOf(decode(value, String.valueOf(def), key));

			return ret;
		} catch (Exception e) {
			return def;
		}
	}

	public static char decode(String value, char def, int key) {
		try {
			char ret = decode(value, String.valueOf(def), key).charAt(0);

			return ret;
		} catch (Exception e) {
			return def;
		}
	}

	public static double decode(String value, double def, int key) {
		try {
			double ret = Double.valueOf(decode(value, String.valueOf(def), key));

			return ret;
		} catch (Exception e) {
			return def;
		}
	}

	public static float decode(String value, float def, int key) {
		try {
			float ret = Float.valueOf(decode(value, String.valueOf(def), key));

			return ret;
		} catch (Exception e) {
			return def;
		}
	}

	public static int decode(String value, int def, int key) {
		try {
			int ret = Integer.valueOf(decode(value, String.valueOf(def), key));

			return ret;
		} catch (Exception e) {
			return def;
		}
	}

	public static long decode(String value, long def, int key) {
		try {
			long ret = Long.valueOf(decode(value, String.valueOf(def), key));

			return ret;
		} catch (Exception e) {
			return def;
		}
	}

	public static String decode(String value, String def, int key) {
		if (value == null || value.trim() == "")
			return def;

		try {

			String[] in = value.split(" ");

			byte[] inb = new byte[in.length];

			for (int i = 0; i < inb.length; i++) {
				inb[i] = (byte) (Integer.valueOf(in[i]) / key);
			}

			String ret = new String(inb, StandardCharsets.UTF_8);

			return ret;
		} catch (Exception e) {
			return def;
		}
	}

	public static String encode(boolean value, int key) {
		return encode(String.valueOf(value), key);
	}

	public static String encode(char value, int key) {
		return encode(String.valueOf(value), key);
	}

	public static String encode(double value, int key) {
		return encode(String.valueOf(value), key);
	}

	public static String encode(float value, int key) {
		return encode(String.valueOf(value), key);
	}

	public static String encode(int value, int key) {
		return encode(String.valueOf(value), key);
	}

	public static String encode(long value, int key) {
		return encode(String.valueOf(value), key);
	}

	public static String encode(String value, int key) {
		byte[] bs = value.getBytes();

		String out = "";

		for (byte b : bs) {
			out += b * key + " ";
		}

		out = out.substring(0, out.length() - 1);

		return out;
	}
}
