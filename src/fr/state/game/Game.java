package fr.state.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import fr.inputs.Input;
import fr.inputs.keyboard.KeyboardEvent;
import fr.state.game.board.Board;
import fr.state.game.hud.HUD;
import fr.util.point.Point;
import fr.window.WinData;

public class Game {

	private GameState gameState;

	private Board board;

	private WinData winData;

	private HUD hud;

	public Game(GameState gs, WinData wd) {
		this.gameState = gs;
		this.winData = wd;

		int tileSize = 30;
		int nbTile = 20;

		int yOffset = 22;

		int bombsAmount = 83;

		this.board = new Board();
		this.board.createBoard(
				this.winData.getHalfWindowSize().clone()
						.sub(new Point(nbTile * tileSize, nbTile * tileSize).div(2).sub(new Point(0, yOffset))),
				new Point(nbTile, nbTile), tileSize, bombsAmount);

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

	public void setMenuState(GameState menuState) {
		this.gameState = menuState;
	}

	public void update(Input input) {
		for (KeyboardEvent e : input.keyboardEvents) {
			if (e.key == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
		}

		this.board.update(input);

		this.hud.update(input);
	}
}
