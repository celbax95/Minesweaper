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
}
