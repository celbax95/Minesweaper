package fr.inputs.keyboard;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;

import fr.inputs.mouse.MouseMirror;

/**
 * Clavier
 */
public class KeyboardEventList implements Keyboard {

	private List<KeyboardEvent> events;

	private MouseMirror mouse;

	public KeyboardEventList() {
		this.reset();
		this.mouse = null;
	}

	public List<KeyboardEvent> getAndResetEvents() {
		List<KeyboardEvent> tmp = this.events;
		this.events = new Vector<>();
		return tmp;
	}

	public List<KeyboardEvent> getEvents() {
		return this.events;
	}

	private char getValidKeyChar(char keyChar) {
		return Character.isDefined(keyChar) ? keyChar : Character.MIN_VALUE;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		this.events.add(new KeyboardEvent(arg0.getKeyCode(), this.getValidKeyChar(arg0.getKeyChar()),
				this.mouse == null ? null : this.mouse.getPos(), arg0.isShiftDown(), arg0.isControlDown(),
				arg0.isAltDown(), true));
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		this.events.add(new KeyboardEvent(arg0.getKeyCode(), this.getValidKeyChar(arg0.getKeyChar()),
				this.mouse == null ? null : this.mouse.getPos(), arg0.isShiftDown(), arg0.isControlDown(),
				arg0.isAltDown(), false));
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	public void reset() {
		this.events = new Vector<>();
	}

	public void setMouse(MouseMirror mouse) {
		this.mouse = mouse;
	}
}
