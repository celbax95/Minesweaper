package fr.state.game.board;

import java.awt.Graphics2D;

import fr.util.point.Point;

public class BombTile extends Tile {

	public BombTile(Board board, Point pos, Point size, Point tilePos) {
		super(board, pos, size, tilePos);
	}

	@Override
	public void draw(Graphics2D g) {
	}

	@Override
	public void onCoveredChanged() {
	}

	@Override
	public void setNbBombsASide(int nb) {
	}

}
