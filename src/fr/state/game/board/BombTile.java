package fr.state.game.board;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.util.point.Point;

public class BombTile extends Tile {

	public BombTile(Board board, Point pos, Point size, Point tilePos) {
		super(board, pos, size, tilePos);
	}

	@Override
	public void draw(Graphics2D g) {
		if (this.covered) {
			this.coveredDraw(g);
		} else {
			g.setColor(new Color(160, 160, 160));
			this.fillRect(g);
			g.setColor(Color.black);
			this.fillRect(g, 4);
		}
	}

	@Override
	public boolean isBomb() {
		return true;
	}

	@Override
	public void onCoveredChanged() {
		this.board.uncover();
	}
}
