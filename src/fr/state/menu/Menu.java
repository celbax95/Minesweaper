package fr.state.menu;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.List;

import fr.inputs.Input;
import fr.inputs.keyboard.KeyboardEvent;
import fr.util.widgets.Widget;
import fr.util.widgets.WidgetHolder;
import fr.window.WinData;

public class Menu extends WidgetHolder {

	private static final Data data = new Data() {
		{
			this.holderName = "menu";

			this.resScheme = "/resources/menu/" + this.holderName + "/*.png";

			this.resources = new String[] {

			};
		}
	};

	private MenuState state;

	private WinData winData;

	public Menu(MenuState state, WinData wd) {
		this.state = state;
		this.winData = wd;
	}

	@Override
	public List<Widget> createWidgets(List<Widget> widgets) {
		return widgets;
	}

	@Override
	public void draw(Graphics2D g) {
	}

	@Override
	public Data getData() {
		return data;
	}

	public MenuState getState() {
		return this.state;
	}

	public void setState(MenuState state) {
		this.state = state;
	}

	@Override
	public void update(Input input) {
		for (KeyboardEvent e : input.keyboardEvents) {
			if (e.key == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
		}
	}
}
