package fr.inputs.keyboard;

import fr.util.point.Point;

public class KeyboardEvent {

	public int key;

	public char keyChar;

	public boolean pressed;

	public boolean shift;
	public boolean ctrl;
	public boolean alt;

	public Point mousePos;

	public KeyboardEvent(int key, char keyChar, Point mousePos, boolean shift, boolean ctrl, boolean alt,
			boolean pressed) {
		this.key = key;
		this.keyChar = keyChar;
		this.mousePos = mousePos;
		this.shift = shift;
		this.ctrl = ctrl;
		this.alt = alt;
		this.pressed = pressed;
	}
}
