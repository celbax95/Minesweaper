package fr.state.menu;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import fr.inputs.Input;
import fr.logger.Logger;
import fr.util.collider.AABB;
import fr.util.collider.Collider;
import fr.util.point.Point;
import fr.util.widgets.WidgetHolder;
import fr.util.widgets.widget.WButton;

public abstract class WCoverButton extends WButton {

	private static final int PADDING = -1;

	private Point size;

	private int slideState;

	private int slideRate = 7;

	private boolean slideDown;

	private boolean slideUp;

	private boolean down;

	private boolean up;

	public WCoverButton(WButton other) {
		super(other);

		this.init();
	}

	public WCoverButton(WidgetHolder p) {
		super(p);

		this.init();
	}

	@Override
	public void draw(Graphics2D g) {
		Shape oldClip = g.getClip();

		g.clip(new Rectangle2D.Double(this.pos.x, this.pos.y, this.size.x, this.size.y));

		if (this.currentDE != null) {
			this.currentDE.draw(g, this.pos.clone().sub(new Point(0, this.size.iy() - this.slideState)));
		} else {
			Logger.err("Un " + this.getClass().getSimpleName() + " n'a pas de drawElement");
		}

		g.setClip(oldClip);
	}

	private void init() {
		this.size = new Point();

		this.slideState = PADDING;

		this.slideDown = false;
		this.slideUp = false;
		this.up = true;
		this.down = false;
	}

	@Override
	public void setHitbox(AABB hitbox) {
		super.setHitbox(hitbox);
		this.size = hitbox.getSize();
	}

	@Override
	public void setHitboxFromDrawElement() {
		super.setHitboxFromDrawElement();
		this.size = this.hitbox.getSize();
	}

	private void slide() {
		if (this.slideDown) {
			if (this.slideState + this.slideRate < this.size.iy()) {
				this.slideState += this.slideRate;
			} else {
				this.slideState = this.size.iy();
				this.slideDown = false;
				this.down = true;
			}
		}
		if (this.slideUp) {
			if (this.slideState - this.slideRate > PADDING) {
				this.slideState -= this.slideRate;
			} else {
				this.slideState = PADDING;
				this.slideUp = false;
				this.up = true;
			}
		}
	}

	public void slideDown() {
		if (!this.slideDown && !this.down) {
			this.up = false;
			this.down = false;
			this.slideUp = false;
			this.slideDown = true;
		}
	}

	public void slideUp() {
		if (!this.slideUp && !this.up) {
			this.up = false;
			this.down = false;
			this.slideUp = true;
			this.slideDown = false;
		}
	}

	@Override
	public void update(Input input) {
		if (!this.visible)
			return;

		this.slide();

		if (!this.canPressed)
			return;

		if (Collider.AABBvsPoint(this.hitbox, input.mousePos)) {
			this.slideDown();
		} else {
			this.slideUp();
		}

		if (this.down) {
			super.update(input);
		}
	}
}
