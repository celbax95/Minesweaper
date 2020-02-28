package fr.state.menu;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import fr.inputs.Input;
import fr.inputs.keyboard.KeyboardEvent;
import fr.window.WinData;

public class Menu {

	private MenuState state;

	private WinData winData;

	public Menu(MenuState state, WinData wd) {
		this.state = state;
		this.winData = wd;
	}

	public void draw(Graphics2D g) {
	}

	public MenuState getState() {
		return this.state;
	}

	public void setState(MenuState state) {
		this.state = state;
	}

	public void update(Input input) {
		for (KeyboardEvent e : input.keyboardEvents) {
			if (e.key == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
		}
	}
}
