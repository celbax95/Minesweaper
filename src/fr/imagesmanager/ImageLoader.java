package fr.imagesmanager;

import fr.logger.Logger;

public class ImageLoader {

	ImageManager manager = ImageManager.getInstance();

	public ImageLoader() {

	}

	public void load(String name, String path) {
		if (!this.manager.contains(name)) {
			Logger.inf("Chargement de l'image \"" + name + "\" -> \"" + path + "\"");
			this.manager.add(name, path);
		}
	}

	public void load(String[] names, String[] paths) {
		assert names.length == paths.length;

		int nb = names.length;

		for (int i = 0; i < nb; i++) {
			this.load(names[i], paths[i]);
		}
	}
}
