package fr.state.game.board;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import fr.util.point.Point;

public class InfoTile extends Tile {
	private int bombsASide;

	Font font;
	private Point labelPos;

	public InfoTile(Board board, Point pos, Point size, Point tilePos) {
		super(board, pos, size, tilePos);
		this.font = new Font("Arial", Font.PLAIN, 20);
		this.setLabelPos();
	}

	@Override
	public void draw(Graphics2D g) {
		if (this.covered) {
			this.coveredDraw(g);
		} else {
			g.setColor(new Color(160, 160, 160));
			this.fillRect(g);
			if (this.bombsASide != 0) {
				g.setColor(Color.black);
				g.setFont(this.font);

				g.drawString(String.valueOf(this.bombsASide), this.labelPos.ix(), this.labelPos.iy());
			}
		}
	}

	@Override
	public boolean isBomb() {
		return false;
	}

	@Override
	public void onCoveredChanged() {
		if (this.bombsASide == 0) {
			this.board.chainedUncover(this);
		}
	}

	private void setLabelPos() {
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, true, true);

		Point labelSize = new Point(this.font.getStringBounds("0", frc).getWidth(),
				this.font.getStringBounds("0", frc).getHeight() * 0.71);

		Point offset = this.size.clone().sub(labelSize).div(2);

		this.labelPos = new Point(this.pos.x + offset.x, this.pos.y + this.size.y - labelSize.y + offset.y);
	}

	@Override
	public void setNbBombsASide(int nb) {
		this.bombsASide = nb;
	}
}
