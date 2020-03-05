package fr.state.game;

import fr.datafilesmanager.Cryptor;
import fr.datafilesmanager.DatafilesManager;
import fr.datafilesmanager.XMLManager;

public class ConfGame {

	private static final long KEY = 2921073625518553L;

	private static final byte[] ENCODE_KEY;

	private static final int SCRAMBLER = 3;

	private static final Cryptor CRYPTOR;

	static {
		CRYPTOR = Cryptor.getInstance();
		CRYPTOR.setBase64Encoding(true);
	}

	private static DatafilesManager dfm;
	private static XMLManager xmlManager;

	static {
		dfm = DatafilesManager.getInstance();
		xmlManager = DatafilesManager.getInstance().getXmlManager();

		ENCODE_KEY = CRYPTOR.getKey(KEY);
	}

	public static int getBestScore(int width, int height, int bombs) {

		Object file = dfm.getFile("score");

		CRYPTOR.setScrambler(0);
		String paramName = CRYPTOR.encrypt(width + " " + height + " " + bombs, ENCODE_KEY);

		CRYPTOR.setScrambler(SCRAMBLER);
		return CRYPTOR.decrypt(xmlManager.getParam(file, paramName, 0).toString(), -1, ENCODE_KEY);
	}

	public static int getBombes() {

		Object file = dfm.getFile("game");

		return (int) xmlManager.getParam(file, "bombs", 83);
	}

	public static double getBombsDensity() {

		Object file = dfm.getFile("game");

		return (double) xmlManager.getParam(file, "bombsDensity", 0.001);
	}

	public static int getHeight() {

		Object file = dfm.getFile("game");

		return (int) xmlManager.getParam(file, "height", 20);
	}

	public static int getMultiKey() {

		Object file = dfm.getFile("controls");

		return (int) xmlManager.getParam(file, "multiKey", 65);
	}

	public static int getWidth() {

		Object file = dfm.getFile("game");

		return (int) xmlManager.getParam(file, "width", 20);
	}

	public static boolean isUsingDensity() {
		DatafilesManager dfm = DatafilesManager.getInstance();
		Object file = dfm.getFile("game");

		return (boolean) xmlManager.getParam(file, "useDensityInsteadOfAbsolute", true);
	}

	public static void setBestScore(int width, int height, int bombs, long newValue) {

		Object score = dfm.getFile("score");

		CRYPTOR.setScrambler(0);
		String paramName = CRYPTOR.encrypt(width + " " + height + " " + bombs, ENCODE_KEY);

		CRYPTOR.setScrambler(SCRAMBLER);
		String value = CRYPTOR.encrypt((int) newValue, ENCODE_KEY);

		xmlManager.setParam(score, paramName, value);
		dfm.saveFile(score);
	}
}
