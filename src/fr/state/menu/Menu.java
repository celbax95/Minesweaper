package fr.state.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.List;

import fr.imagesmanager.ImageManager;
import fr.inputs.Input;
import fr.inputs.keyboard.KeyboardEvent;
import fr.statepanel.IAppState;
import fr.statepanel.StatePanel;
import fr.util.DualMap;
import fr.util.point.Point;
import fr.util.widgets.Widget;
import fr.util.widgets.WidgetHolder;
import fr.util.widgets.widget.WButton;
import fr.util.widgets.widget.WElement;
import fr.util.widgets.widget.WExclusiveSwitchs;
import fr.util.widgets.widget.WSwitch;
import fr.util.widgets.widget.data.TextData;
import fr.util.widgets.widget.drawelements.DEImage;
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

	private static final DualMap<String, Integer> SIZES;
	private static final DualMap<String, Integer> DIFFICULTIES;

	static {
		SIZES = new DualMap<String, Integer>() {
			private static final long serialVersionUID = 1L;
			{
				int i = 0;
				this.putForward("small", i++);
				this.putForward("medium", i++);
				this.putForward("large", i++);
			}
		};
		DIFFICULTIES = new DualMap<String, Integer>() {
			private static final long serialVersionUID = 1L;
			{
				int i = 0;
				this.putForward("easy", i++);
				this.putForward("normal", i++);
				this.putForward("hard", i++);
			}
		};
	}

	private MenuState state;

	private WinData winData;

	public Menu(MenuState state, WinData wd) {
		this.state = state;
		this.winData = wd;

		this.load();
	}

	public Widget createBestLabel() {
		WElement w = new WElement(this);

		ImageManager im = ImageManager.getInstance();

		Font font = new Font(new HashMap<TextAttribute, Object>() {
			private static final long serialVersionUID = 1L;
			{
				this.put(TextAttribute.TRACKING, 0.02);
				this.put(TextAttribute.FAMILY, "Tw Cen MT");
				this.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
				this.put(TextAttribute.SIZE, 40);
			}
		});

		TextData label = new TextData(new Point(), font, "Best : 999", new Color(230, 230, 230), 3);

		DEImage i = new DEImage(new Point(), im.get("largeButton"), null, label);

		w.setDrawElement(i);

		w.setPos(new Point(82, 542));

		return w;
	}

	public Widget createDifficultySwitchs() {
		WExclusiveSwitchs es = new WExclusiveSwitchs(this) {
			@Override
			public void selectedChanged(String selected, boolean state) {
				System.out.println(selected + " est " + (state ? "selectionne" : "deselectionne"));
			}
		};

		es.add("easy", this.createSwitch("EASY", new Point(416, 222)));
		es.add("normal", this.createSwitch("NORMAL", new Point(416, 310)));
		es.add("hard", this.createSwitch("HARD", new Point(416, 398)));

		es.setCanNoSelect(false);

		int difficulty = ConfMenu.getDifficulty();
		if (DIFFICULTIES.containsBackwardKey(difficulty)) {
			es.setSelected(DIFFICULTIES.getBackward(difficulty), true);
		}
		return es;
	}

	public Widget createPlayButton() {
		WButton w = new WButton(this) {
			@Override
			public void action() {
				StatePanel sp = Menu.this.state.getStatePanel();
				IAppState nextState = sp.getAppStateManager().getState("game");
				sp.setState(nextState);
			}
		};

		ImageManager im = ImageManager.getInstance();

		Font font = new Font(new HashMap<TextAttribute, Object>() {
			private static final long serialVersionUID = 1L;
			{
				this.put(TextAttribute.TRACKING, 0.035);
				this.put(TextAttribute.FAMILY, "Tw Cen MT");
				this.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
				this.put(TextAttribute.SIZE, 50);
			}
		});

		TextData label = new TextData(new Point(), font, "PLAY", new Color(230, 230, 230), 3);

		DEImage i = new DEImage(new Point(), im.get("largeButton"), null, label);
		w.setStdDrawElement(i);

		i = (DEImage) i.clone();
		i.setImage(im.get("largeButton_pressed"));
		w.setPressedDrawElement(i);

		w.setHitboxFromDrawElement();

		w.setPos(new Point(456, 542));

		return w;
	}

	public Widget createSizeSwitchs() {
		WExclusiveSwitchs es = new WExclusiveSwitchs(this) {
			@Override
			public void selectedChanged(String selected, boolean state) {
				System.out.println(selected + " est " + (state ? "selectionne" : "deselectionne"));
			}
		};

		es.add("small", this.createSwitch("SMALL", new Point(192, 222)));
		es.add("medium", this.createSwitch("MEDIUM", new Point(192, 310)));
		es.add("large", this.createSwitch("LARGE", new Point(192, 398)));

		es.setCanNoSelect(false);

		int size = ConfMenu.getSize();
		if (SIZES.containsBackwardKey(size)) {
			es.setSelected(SIZES.getBackward(size), true);
		}

		return es;
	}

	public WSwitch createSwitch(String name, Point pos) {
		WSwitch w = new WSwitch(this) {
			@Override
			public void actionOff() {
			}

			@Override
			public void actionOn() {
			}
		};

		ImageManager im = ImageManager.getInstance();

		Font font = new Font(new HashMap<TextAttribute, Object>() {
			private static final long serialVersionUID = 1L;
			{
				this.put(TextAttribute.TRACKING, 0.02);
				this.put(TextAttribute.FAMILY, "Tw Cen MT");
				this.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
				this.put(TextAttribute.SIZE, 40);
			}
		});

		TextData label = new TextData(new Point(), font, name, new Color(230, 230, 230), 3);

		DEImage i = new DEImage(new Point(), im.get("button"), null, label);

		w.setOffDrawElement(i);

		i = (DEImage) i.clone();
		i.setImage(im.get("button_pressed"));

		w.setPressedOffDrawElement(i);
		w.setPressedOnDrawElement(i);

		i = (DEImage) i.clone();
		i.setImage(im.get("button_selected"));

		w.setOnDrawElement(i);

		w.setPos(pos);

		w.setHitboxFromDrawElement();

		return w;
	}

	private Widget createTitle() {
		WElement title = new WElement(this);

		Font font = new Font(new HashMap<TextAttribute, Object>() {
			private static final long serialVersionUID = 1L;
			{
				this.put(TextAttribute.KERNING, TextAttribute.KERNING_ON);
				this.put(TextAttribute.FAMILY, "Tw Cen MT");
				this.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
				this.put(TextAttribute.SIZE, 88);
			}
		});

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

		widgets.add(this.createSizeSwitchs());
		widgets.add(this.createDifficultySwitchs());

		widgets.add(this.createBestLabel());
		widgets.add(this.createPlayButton());

		return widgets;
	}

	@Override
	public void draw(Graphics2D g) {
		this.drawWidgets(g);
	}

	private double getBombDensityFromConf(int difficulty) {
		switch (difficulty) {
		case 0:
			return ConfMenu.getDifficultyEasy();
		case 1:
			return ConfMenu.getDifficultyNormal();
		case 2:
			return ConfMenu.getDifficultyHard();
		default:
			return 0;
		}
	}

	@Override
	public Data getData() {
		return data;
	}

	private Point getSizeFromConf(int size) {
		switch (size) {
		case 0:
			return ConfMenu.getSizeSmall();
		case 1:
			return ConfMenu.getSizeMedium();
		case 2:
			return ConfMenu.getSizeLarge();
		default:
			return new Point();
		}
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
