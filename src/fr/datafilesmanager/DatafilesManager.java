package fr.datafilesmanager;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.logger.Logger;
import fr.xmlmanager.XMLManagerDOM;

/**
 * Gestionnaire des fichiers de configuration
 *
 * @author Loic.MACE
 *
 */
public class DatafilesManager {

	private static boolean initialized = false;

	/**
	 * static Singleton instance.
	 */
	private static volatile DatafilesManager instance;

	/**
	 * Return a singleton instance of XMLReader.
	 */
	public static DatafilesManager getInstance() {
		// Double lock for thread safety.
		if (instance == null) {
			synchronized (XMLManagerDOM.class) {
				if (instance == null) {
					instance = new DatafilesManager();
				}
			}
		}
		return instance;
	}

	// Accesseur aux fichiers de configuration
	private XMLManagerBackend xmlManager;

	// Fichiers sauvegardes
	private Map<String, String> files;

	private List<String> readOnlyFiles;

	private DatafilesManager() {
		this.files = new HashMap<>();
		this.readOnlyFiles = new ArrayList<>();
	}

	/**
	 * Ajoute un fichier au gestionnaire
	 *
	 * @param name : nom du fichier
	 * @param path : path du fichier
	 */
	public void addFile(String name, String path) {

		this.files.put(name, path);

		if (this.xmlManager != null) {
			if (!this.readOnlyFiles.contains(name)) {
				this.xmlManager.saveFile(this.getFile(name));
			}
		} else {
			Logger.warn(
					"Pour une execution optimise, il est conseillé d'ajouter l'XML Manager avant d'ajouter les fichiers");
		}
	}

	/**
	 * Recupere l'extension d'un fichier
	 *
	 * @param file : fichier a analyser
	 *
	 * @return l'extension du fichier / une chaine vide si le fichier est invalide
	 */
	public String getExtension(File file) {

		if (file != null && file.exists()) {

			String fileName = file.getName();

			return fileName.substring(fileName.lastIndexOf("."));
		}
		return "";
	}

	/**
	 *
	 * Recupere un document a partir de la liste de fichiers sauvegardes
	 *
	 * @param name : nom du fichier
	 * @return : le document (racine) du fichier de configuration
	 */
	public Object getFile(String name) {
		try {
			Object doc = null;

			if (this.readOnlyFiles.contains(name)) {
				InputStream is = DatafilesManager.class.getResourceAsStream(this.files.get(name));

				doc = this.xmlManager.getDocument(is);
			} else {
				doc = this.xmlManager.getDocument(this.files.get(name));
			}

			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("La valeur \"" + name + "\" n'existe pas dans les fichiers connus.");
			System.exit(0);
		}
		return null; // Inaccessible
	}

	/**
	 * Recupere la totalitee des fichiers sauvegardes
	 *
	 * @return une Map qui lie le nom du fichier a son chemin (path) d'acces
	 */
	public Map<String, String> getFiles() {
		Map<String, String> m = new HashMap<>();

		m.putAll(this.files);

		return m;
	}

	/**
	 * Recupere le xmlReader permettant d'acceder aux informations des fichiers de
	 * configuration
	 *
	 * @return
	 * @throws RuntimeException
	 */
	public XMLManager getXmlManager() throws RuntimeException {
		if (!initialized)
			throw new RuntimeException("Appeler la methode init() pour initialiser DatafilesManager");
		return this.xmlManager;
	}

	/**
	 * Methode a appeler avant utilisation
	 *
	 * Affecte un Accesseur de XML au Gestionnaire
	 *
	 * @param xmlReader : accesseur de XML a affecter
	 */
	public void init(XMLManagerBackend xmlReader) {
		this.xmlManager = xmlReader;
		initialized = true;
	}

	public boolean isReadOnlyFile(String fileName) {
		return this.readOnlyFiles.contains(fileName);
	}

	public void saveFile(Object document) {
		this.xmlManager.saveFile(document);
	}

	public void setReadOnlyFile(String fileName, boolean state) {
		if (state && !this.readOnlyFiles.contains(fileName)) {
			this.readOnlyFiles.add(fileName);
		} else if (!state && this.readOnlyFiles.contains(fileName)) {
			this.readOnlyFiles.remove(fileName);
		}
	}
}
