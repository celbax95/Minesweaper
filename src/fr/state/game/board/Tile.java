package fr.state.game.board;

import java.awt.Graphics2D;

import fr.util.point.Point;

public abstract class Tile {
	private Board board;

	private Point pos;
	private Point size;
	private Point tilePos;

	boolean covered;

	/**
	 * @param board
	 * @param pos
	 * @param size
	 * @param tilePos
	 */
	public Tile(Board board, Point pos, Point size, Point tilePos) {
		super();
		this.board = board;
		this.pos = pos;
		this.size = size;
		this.tilePos = tilePos;
	}

	public abstract void draw(Graphics2D g);

	public abstract void onCoveredChanged();

	public void setCovered(boolean covered) {
		this.covered = covered;
	}

	public abstract void setNbBombsASide(int nb);
}
