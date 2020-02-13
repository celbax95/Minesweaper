package fr.state.game.board;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.util.point.Point;

public abstract class Tile {
	protected Board board;

	protected int bombsASide;

	protected Point pos;
	protected Point size;
	protected Point tilePos;

	boolean covered;

	boolean flagged;

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
		this.flagged = false;
	}

	protected void coveredDraw(Graphics2D g) {
		g.setColor(new Color(100, 100, 100));
		this.fillRect(g);
		if (this.flagged) {
			g.setColor(new Color(230, 0, 0));
			g.fillOval(this.pos.ix() + this.size.ix() / 6, this.pos.iy() + this.size.iy() / 6,
					this.size.ix() - this.size.ix() / 3, this.size.iy() - this.size.iy() / 3);
		}
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
	 * @return the bombsASide
	 */
	public int getBombsASide() {
		return this.bombsASide;
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

	/**
	 * @return the flagged
	 */
	public boolean isFlagged() {
		return this.flagged;
	}

	public boolean isXAligned(int x) {
		return x > this.pos.x && x < this.pos.x + this.size.x;
	}

	public boolean isYAligned(int y) {
		return y > this.pos.y && y < this.pos.y + this.size.y;
	}

	public abstract void onCoveredChanged();

	public void setCovered(boolean covered) {
		if (this.covered != covered && !this.flagged) {
			this.covered = covered;
			this.onCoveredChanged();
		}
	}

	public void setNbBombsASide(int nb) {
		this.bombsASide = nb;
	}

	public boolean toggleFlag() {
		this.flagged = !this.flagged;
		return this.flagged;
	}
}
