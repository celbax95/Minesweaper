package fr.datafilesmanager;

public interface XMLManagerBackend extends XMLManager {
	/**
	 * Sauvegarde un fichier precedement modifie
	 *
	 * @param doc : le fichier a sauvegarder
	 */
	void saveFile(Object doc);
}
