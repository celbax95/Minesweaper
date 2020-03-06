package fr.state.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Map;

import fr.inputs.Input;
import fr.inputs.keyboard.KeyboardEvent;
import fr.state.game.board.Board;
import fr.state.game.hud.HUD;
import fr.statepanel.IAppState;
import fr.statepanel.StatePanel;
import fr.util.point.Point;
import fr.window.WinData;

public class Game {

	private class InitData {
		Point size;
		Integer bombsAmount;
	}

	private GameState gameState;

	private Board board;

	private WinData winData;

	private HUD hud;

	public Game(GameState gs, WinData wd, Map<String, Object> initData) {
		this.gameState = gs;
		this.winData = wd;

		int tileSize = 30;

		int yOffset = 22;

		InitData data;

		if (initData == null) {
			data = this.initFromConf();
		} else {
			data = this.initFromInitData(initData);
		}

		this.board = new Board();
		this.board
				.createBoard(
						this.winData.getHalfWindowSize().clone()
								.sub(new Point(data.size.x * tileSize, data.size.y * tileSize).div(2)
										.sub(new Point(0, yOffset))),
						new Point(data.size.x, data.size.y), tileSize, data.bombsAmount);

		this.hud = new HUD(this.board, this.winData);
	}

	public void draw(Graphics2D g) {
		if (this.board.isInit()) {
			this.board.draw(g);

			this.hud.draw(g);
		}
	}

	public GameState getMenuState() {
		return this.gameState;
	}

	private InitData initFromConf() {
		InitData data = new InitData();

		data.size = new Point(ConfGame.getWidth(), ConfGame.getHeight());

		if (ConfGame.isUsingDensity()) {
			data.bombsAmount = (int) Math.floor(data.size.x * data.size.x * ConfGame.getBombsDensity());
		} else {
			data.bombsAmount = ConfGame.getBombes();
		}

		return data;
	}

	private InitData initFromInitData(Map<String, Object> initData) {
		InitData data = new InitData();

		data.size = (Point) initData.get("size");
		data.bombsAmount = (Integer) initData.get("bombs");

		if (data.size == null || data.bombsAmount == null)
			return this.initFromConf();

		return data;
	}

	public void setMenuState(GameState menuState) {
		this.gameState = menuState;
	}

	public void update(Input input) {
		for (KeyboardEvent e : input.keyboardEvents) {
			if (e.key == KeyEvent.VK_ESCAPE) {
				StatePanel sp = this.gameState.getStatePanel();
				IAppState nextState = sp.getAppStateManager().getState("menu");

				nextState.setInitData(null);

				sp.setState(nextState);
			}
		}

		this.board.update(input);

		this.hud.update(input);
	}
}
