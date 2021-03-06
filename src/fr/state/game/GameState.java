package fr.state.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Map;

import fr.imagesmanager.ImageManager;
import fr.inputs.Input;
import fr.statepanel.IAppState;
import fr.statepanel.StatePanel;

public class GameState implements IAppState {

	private Game game;

	private StatePanel sp;

	private Input input;

	private GameLoop loop;

	private final int GRAY = 40;

	private final Color BACKGROUND = new Color(this.GRAY, this.GRAY, this.GRAY);

	Map<String, Object> initData;

	public GameState() {
		this.initData = null;
	}

	@Override
	public void draw(Graphics2D g) {
		this.game.draw(g);
	}

	public void getInput() {
		this.input.getInput();
	}

	@Override
	public String getName() {
		return "game";
	}

	public StatePanel getStatePanel() {
		return this.sp;
	}

	@Override
	public void setInitData(Map<String, Object> data) {
		this.initData = data;
	}

	@Override
	public void start(StatePanel panel) {
		this.sp = panel;

		ImageManager.getInstance().removeAll();

		this.game = new Game(this, this.sp.getWinData(), this.initData);

		this.sp.setBackground(this.BACKGROUND);

		this.input = new Input(this.sp.getWinData());

		this.input.setMouseDataInKeyboardEvent(true);

		this.sp.addKeyboardListener(this.input.getKeyboardEventListener());
		this.sp.addKeyboardListener(this.input.getKeyboardMirrorListener());
		this.sp.addMouseListener(this.input.getMouseEventListener());
		this.sp.addMouseListener(this.input.getMouseMirrorListener());

		this.loop = new GameLoop(this);
		this.loop.start();
	}

	@Override
	public void stop() {
		this.loop.stop();
		this.sp.removeKeyboardListener(this.input.getKeyboardEventListener());
		this.sp.removeKeyboardListener(this.input.getKeyboardMirrorListener());
		this.sp.removeMouseListener(this.input.getMouseEventListener());
		this.sp.removeMouseListener(this.input.getMouseMirrorListener());
		this.game = null;
		this.sp = null;
		this.input = null;
		this.loop = null;
	}

	@Override
	public void update() {
		this.game.update(this.input);
	}
}
