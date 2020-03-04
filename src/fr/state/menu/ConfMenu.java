package fr.state.menu;

import fr.datafilesmanager.DatafilesManager;
import fr.datafilesmanager.XMLManager;
import fr.util.point.Point;

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

	public static Double getDifficultyEasy() {
		Object file = dfm.getFile("gameData");

		return (Double) xmlManager.getParam(file, "difficultyEasy", 0);
	}

	public static Double getDifficultyHard() {
		Object file = dfm.getFile("gameData");

		return (Double) xmlManager.getParam(file, "difficultyHard", 0);
	}

	public static Double getDifficultyNormal() {
		Object file = dfm.getFile("gameData");

		return (Double) xmlManager.getParam(file, "difficultyNormal", 0);
	}

	public static int getSize() {

		Object file = dfm.getFile("menu");

		return (int) xmlManager.getParam(file, "size", 0);
	}

	public static Point getSizeLarge() {
		Object file = dfm.getFile("gameData");

		String[] str = ((String) xmlManager.getParam(file, "sizeLarge", 0)).split("x");

		return new Point(Double.valueOf(str[0]), Double.valueOf(str[1]));
	}

	public static Point getSizeMedium() {
		Object file = dfm.getFile("gameData");

		String[] str = ((String) xmlManager.getParam(file, "sizeMedium", 0)).split("x");

		return new Point(Double.valueOf(str[0]), Double.valueOf(str[1]));
	}

	public static Point getSizeSmall() {
		Object file = dfm.getFile("gameData");

		String[] str = ((String) xmlManager.getParam(file, "sizeSmall", 0)).split("x");

		return new Point(Double.valueOf(str[0]), Double.valueOf(str[1]));
	}

	public static void setDifficulty(int difficulty) {

		Object file = dfm.getFile("menu");

		xmlManager.setParam(file, "difficulty", difficulty);
		dfm.saveFile(file);
	}

	public static void setSize(int size) {

		Object file = dfm.getFile("menu");

		xmlManager.setParam(file, "size", size);
		dfm.saveFile(file);
	}
}
