package fr.util.widgets.widget;

import java.awt.Graphics2D;

import fr.util.point.Point;

public interface DrawElement {
	DrawElement clone();

	void draw(Graphics2D g, Point ref);

	Point getPos();

	Point getSize();

	String getText();

	boolean isLocked();

	void lock();

	void setPos(Point pos);

	void setText(String text);
}
