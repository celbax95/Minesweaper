package fr.util.widgets;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import fr.imagesmanager.ImageLoader;
import fr.inputs.Input;

public abstract class WidgetHolder {

	public static class Data {
		public String resScheme;
		public String[] resources;
		public String holderName;
	}

	private boolean loaded;

	private List<Widget> widgets;

	protected WidgetHolder() {
		this.loaded = false;
		this.widgets = new ArrayList<>();
	}

	public abstract List<Widget> createWidgets(List<Widget> widgets);

	public abstract void draw(Graphics2D g);

	public abstract Data getData();

	public boolean isLoaded() {
		return this.loaded;
	}

	public void load() {
		this.load(this.getData());
	}

	private void load(Data d) {
		this.loadResources(d);

		this.widgets = this.createWidgets(this.widgets);

		this.loaded = true;
	}

	public void loadByThread() {
		Data d = this.getData();

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				WidgetHolder.this.load(d);
			}
		});
		thread.setName(d.holderName + "/load");
		thread.start();
	}

	private void loadResources(Data d) {
		String[] resPaths = new String[d.resources.length];

		for (int i = 0; i < d.resources.length; i++) {
			resPaths[i] = d.resScheme.replace("*", d.resources[i]);
		}

		ImageLoader il = new ImageLoader();

		il.load(d.resources, resPaths);
	}

	public void update(Input input) {
		for (Widget w : this.widgets) {
			w.update(input);
		}
	}
}
