package fr.state.game.board;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.util.point.Point;

public abstract class Tile {
	protected Board board;

	protected Point pos;
	protected Point size;
	protected Point tilePos;

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

	public boolean isXAligned(int x) {
		return x > this.pos.x && x < this.pos.x + this.size.x;
	}

	public boolean isYAligned(int y) {
		return y > this.pos.y && y < this.pos.y + this.size.y;
	}

	public abstract void onCoveredChanged();

	public void setCovered(boolean covered) {
		if (this.covered != covered) {
			this.covered = covered;
			this.onCoveredChanged();
		}
	}

	public abstract void setNbBombsASide(int nb);
}
