package fr.state.game;

import fr.datafilesmanager.DatafilesManager;
import fr.datafilesmanager.Encoder;
import fr.datafilesmanager.XMLManager;

public class ConfGame {

	private static final int ENCODE_KEY = 983211;

	public static int getBestScore() {

		DatafilesManager dfm = DatafilesManager.getInstance();
		Object score = dfm.getFile("score");

		XMLManager m = DatafilesManager.getInstance().getXmlManager();

		return Encoder.decodeToInt(m.getParam(score, "bestScore", 0).toString(), 999, ENCODE_KEY);
	}

	public static int getBombes() {

		DatafilesManager dfm = DatafilesManager.getInstance();
		Object controls = dfm.getFile("game");

		XMLManager m = DatafilesManager.getInstance().getXmlManager();

		return (int) m.getParam(controls, "bombes", 83);
	}

	public static int getHeight() {

		DatafilesManager dfm = DatafilesManager.getInstance();
		Object controls = dfm.getFile("game");

		XMLManager m = DatafilesManager.getInstance().getXmlManager();

		return (int) m.getParam(controls, "height", 20);
	}

	public static int getMultiKey() {

		DatafilesManager dfm = DatafilesManager.getInstance();
		Object controls = dfm.getFile("controls");

		XMLManager m = DatafilesManager.getInstance().getXmlManager();

		return (int) m.getParam(controls, "multiKey", 65);
	}

	public static int getWidth() {

		DatafilesManager dfm = DatafilesManager.getInstance();
		Object controls = dfm.getFile("game");

		XMLManager m = DatafilesManager.getInstance().getXmlManager();

		return (int) m.getParam(controls, "width", 20);
	}

	public static void setBestScore(long newValue) {

		DatafilesManager dfm = DatafilesManager.getInstance();
		Object score = dfm.getFile("score");

		XMLManager m = DatafilesManager.getInstance().getXmlManager();

		m.setParam(score, "bestScore", Encoder.encode((int) newValue, ENCODE_KEY));
		m.saveFile(score);
	}
}
