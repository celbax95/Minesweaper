package fr.state.menu;

import fr.datafilesmanager.DatafilesManager;
import fr.datafilesmanager.XMLManager;

public class ConfMenu {

	private static final int ENCODE_KEY = 983211;

	private static DatafilesManager dfm;
	private static XMLManager xmlManager;

	static {
		dfm = DatafilesManager.getInstance();
		xmlManager = DatafilesManager.getInstance().getXmlManager();
	}

	public static int getDifficulty() {

		Object file = dfm.getFile("menu");

		return (int) xmlManager.getParam(file, "difficulty", 0);
	}

	public static int getSize() {

		Object file = dfm.getFile("menu");

		return (int) xmlManager.getParam(file, "size", 0);
	}

	public static void setDifficulty(int difficulty) {

		Object score = dfm.getFile("menu");

		xmlManager.setParam(score, "difficulty", difficulty);
		dfm.saveFile(score);
	}

	public static void setSize(int size) {

		Object score = dfm.getFile("menu");

		xmlManager.setParam(score, "size", size);
		dfm.saveFile(score);
	}
}
