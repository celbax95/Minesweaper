package fr.state.game.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import fr.inputs.Input;
import fr.state.game.board.Board;

public class HUD {

	private Board board;

	private int bombsRemaining;

	public HUD(Board board) {
		this.board = board;

		this.bombsRemaining = 0;
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.white);

		g.setFont(new Font("Arial", Font.BOLD, 35));

		g.drawString(String.valueOf(this.bombsRemaining), 20, 20);
	}

	public void reset() {

	}

	public void update(Input input) {
		this.bombsRemaining = this.board.getNbOfBombs() - this.board.getNbOfFlags();
	}
}
