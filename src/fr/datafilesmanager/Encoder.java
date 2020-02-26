package fr.datafilesmanager;

import java.nio.charset.StandardCharsets;

public class Encoder {

	public static int decodeToInt(String value, int def, int key) {
		if (value == null || value.trim() == "")
			return def;

		try {

			String[] in = value.split(" ");

			byte[] inb = new byte[in.length];

			for (int i = 0; i < inb.length; i++) {
				inb[i] = (byte) (Integer.valueOf(in[i]) / key);
			}

			int ret = Integer.valueOf(new String(inb, StandardCharsets.UTF_8));

			return ret;
		} catch (Exception e) {
			return def;
		}
	}

	public static String encode(int value, int key) {
		byte[] bs = ("" + value).getBytes();

		String out = "";

		for (byte b : bs) {
			out += b * key + " ";
		}

		out = out.substring(0, out.length() - 1);

		return out;
	}
}
