package fr.state.game;

import java.awt.Graphics2D;

import fr.inputs.Input;
import fr.state.game.board.Board;
import fr.util.point.Point;

public class Game {

	private GameState gameState;

	private Board board;

	public Game(GameState gs) {
		this.gameState = gs;

		int tileSize = 32;

		this.board = new Board();
		this.board.createBoard(new Point(0, 0), new Point(20, 20), tileSize, 60);
	}

	public void draw(Graphics2D g) {
		if (this.board.isInit()) {
			this.board.draw(g);
		}
	}

	public GameState getMenuState() {
		return this.gameState;
	}

	public void setMenuState(GameState menuState) {
		this.gameState = menuState;
	}

	public void update(Input input) {
		this.board.update(input);
	}
}
