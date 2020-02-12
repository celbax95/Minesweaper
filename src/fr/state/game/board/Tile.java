package fr.state.game.board;

import java.awt.Color;
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
		this.covered = true;
	}

	protected void coveredDraw(Graphics2D g) {
		g.setColor(new Color(100, 100, 100));
		this.fillRect(g);
	}

	public abstract void draw(Graphics2D g);

	protected void fillRect(Graphics2D g) {
		g.fillRect(this.pos.ix(), this.pos.iy(), this.size.ix(), this.size.iy());
	}

	/**
	 * @return the board
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * @return the pos
	 */
	public Point getPos() {
		return this.pos;
	}

	/**
	 * @return the size
	 */
	public Point getSize() {
		return this.size;
	}

	/**
	 * @return the tilePos
	 */
	public Point getTilePos() {
		return this.tilePos;
	}

	public abstract boolean isBomb();

	/**
	 * @return the covered
	 */
	public boolean isCovered() {
		return this.covered;
	}

	public abstract void onCoveredChanged();

	public void setCovered(boolean covered) {
		this.covered = covered;
	}

	public abstract void setNbBombsASide(int nb);
}
