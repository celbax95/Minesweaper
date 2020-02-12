package fr.state.game;

import java.awt.Graphics2D;

import fr.inputs.Input;

public class Game {

	private GameState gameState;

	private long lastCall;

	public Game(GameState gs) {
		this.gameState = gs;
	}

	public void draw(Graphics2D g) {

	}

	public GameState getMenuState() {
		return this.gameState;
	}

	public void setMenuState(GameState menuState) {
		this.gameState = menuState;
	}

	public void update(Input input) {
	}
}
