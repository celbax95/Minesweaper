package fr.state.game.board;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.util.point.Point;

public class InfoTile extends Tile {
	private int bombsASide;

	public InfoTile(Board board, Point pos, Point size, Point tilePos) {
		super(board, pos, size, tilePos);
	}

	@Override
	public void draw(Graphics2D g) {
		if (this.covered) {
			this.coveredDraw(g);
		} else {
			g.setColor(new Color(160, 160, 160));
			this.fillRect(g);
		}
	}

	@Override
	public boolean isBomb() {
		return false;
	}

	@Override
	public void onCoveredChanged() {
	}

	@Override
	public void setNbBombsASide(int nb) {
		this.bombsASide = nb;
	}
}
