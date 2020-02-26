package fr.state.game.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import fr.inputs.Input;
import fr.state.game.board.Board;

public class HUD {

	private Board board;

	private int bombsRemaining;

	private long startedFor;

	public HUD(Board board) {
		this.board = board;

		this.bombsRemaining = 0;
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.white);

		g.setFont(new Font("Arial", Font.BOLD, 35));

		int y = 40;

		g.drawString(String.valueOf(this.bombsRemaining), 80, y);
		g.drawString(String.valueOf(this.startedFor), 600, y);
	}

	public void update(Input input) {
		this.bombsRemaining = this.board.getNbOfBombs() - this.board.getNbOfFlags();
		if (!this.board.isGameOver() && !this.board.isFinished() && !this.board.isFull()) {
			if (this.startedFor < 999) {
				this.startedFor = (System.currentTimeMillis() - this.board.getStartingTime()) / 1000;
			}
		}
	}
}
