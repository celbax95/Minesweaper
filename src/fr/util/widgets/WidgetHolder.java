package fr.util.widgets;

import java.awt.Graphics2D;

import fr.inputs.Input;

public interface WidgetHolder {

	void draw(Graphics2D g);

	boolean isLoaded();

	void load();

	void update(Input input);
}
