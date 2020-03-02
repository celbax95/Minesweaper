package fr.state.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.inputs.Input;
import fr.inputs.keyboard.KeyboardEvent;
import fr.util.point.Point;
import fr.util.widgets.Widget;
import fr.util.widgets.WidgetHolder;
import fr.util.widgets.widget.WElement;
import fr.util.widgets.widget.data.TextData;
import fr.util.widgets.widget.drawelements.DELabel;
import fr.window.WinData;

public class Menu extends WidgetHolder {

	private static final Data data = new Data() {
		{
			this.holderName = "menu";

			this.resScheme = "/resources/" + this.holderName + "/*.png";
			this.resources = new String[] {
					"button",
					"button_pressed",
					"button_selected",
					"close",
					"close_pressed",
					"largeButton",
					"largeButton_pressed" };
		}
	};

	private static final String TITLE = "MINESWEAPER";

	private MenuState state;

	private WinData winData;

	public Menu(MenuState state, WinData wd) {
		this.state = state;
		this.winData = wd;

		this.load();
	}

	private Widget createTitle() {
		WElement title = new WElement(this);

		Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>() {
			private static final long serialVersionUID = 1L;
			{
				this.put(TextAttribute.TRACKING, 0.02);
				this.put(TextAttribute.KERNING, TextAttribute.KERNING_ON);
				this.put(TextAttribute.FAMILY, "Tw Cen MT");
				this.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
				this.put(TextAttribute.SIZE, 88);
			}
		};

		Font font = new Font(attributes);

		TextData td = new TextData(new Point(), font, TITLE, new Color(230, 230, 230), 1);

		DELabel txt = new DELabel(new Point(), td);
		txt.setSupBound(this.winData.getWindowSize());

		title.setDrawElement(txt);
		title.setPos(new Point(0, 55));

		return title;
	}

	@Override
	public List<Widget> createWidgets(List<Widget> widgets) {

		widgets.add(this.createTitle());

		return widgets;
	}

	@Override
	public void draw(Graphics2D g) {
		this.drawWidgets(g);
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

		this.updateWidgets(input);
	}
}
