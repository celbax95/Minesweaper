package fr.state.game.board;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.Map;

import fr.util.point.Point;

public class InfoTile extends Tile {

	private static final Map<Integer, Color> infoColor;

	static {
		infoColor = new HashMap<>();

		int colorTone1 = 100;

		int colorTone2 = 70;

		infoColor.put(1, new Color(0, 0, colorTone1));
		infoColor.put(2, new Color(0, colorTone1, 0));
		infoColor.put(3, new Color(colorTone1, 0, 0));
		infoColor.put(4, new Color(colorTone1, 80, 0));
		infoColor.put(5, new Color(colorTone2, 0, 0));
		infoColor.put(6, new Color(0, colorTone2, colorTone2));
		infoColor.put(7, new Color(colorTone1, 0, colorTone1));
		infoColor.put(8, new Color(colorTone1, colorTone1, colorTone1));
	}

	Font font;
	private Point labelPos;

	public InfoTile(Board board, Point pos, Point size, Point tilePos) {
		super(board, pos, size, tilePos);
		this.font = new Font("Arial", Font.BOLD, 20);
		this.setLabelPos();
	}

	@Override
	public void draw(Graphics2D g) {
		if (this.covered) {
			this.coveredDraw(g);
		} else {
			g.setColor(this.getUncoveredColor());
			this.fillRect(g);
			if (this.bombsASide != 0) {

				g.setColor(infoColor.get(this.bombsASide));
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

		this.labelPos = new Point(this.pos.x + offset.x + 0.5, this.pos.y + this.size.y - labelSize.y + offset.y + 2);
	}
}
