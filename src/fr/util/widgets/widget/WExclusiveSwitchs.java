package fr.util.widgets.widget;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.inputs.Input;
import fr.util.point.Point;
import fr.util.widgets.Widget;
import fr.util.widgets.WidgetHolder;

public abstract class WExclusiveSwitchs implements Widget {

	private class GroupableSwitch extends WSwitch {

		private Point originalPos;

		private String name;

		public GroupableSwitch(String name, WSwitch other) {
			super(other);
			name = name.toLowerCase();
			this.name = name;
			this.originalPos = other.getPos().clone();
		}

		@Override
		public void actionOff() {
			WExclusiveSwitchs.this.selectedChanged(this.name, false);
			if (WExclusiveSwitchs.this.selectedSwitch == this) {
				WExclusiveSwitchs.this.selectedSwitch = null;
				this.setEnabled(true);
			}
		}

		@Override
		public void actionOn() {
			if (WExclusiveSwitchs.this.selectedSwitch != null) {
				WExclusiveSwitchs.this.selectedSwitch.setEnabled(!WExclusiveSwitchs.this.canNoSelect);
				WExclusiveSwitchs.this.selectedSwitch.setActive(false);
			}

			WExclusiveSwitchs.this.selectedSwitch = this;
			WExclusiveSwitchs.this.selectedSwitch.setEnabled(WExclusiveSwitchs.this.canNoSelect);

			WExclusiveSwitchs.this.selectedChanged(this.name, true);
		}

		public String getName() {
			return this.name;
		}

		public Point getOriginalPos() {
			return this.originalPos;
		}

		public void setOriginalPos(Point originalPos) {
			WExclusiveSwitchs.this.pos = originalPos.clone();
		}
	}

	private Map<String, Integer> switchMap;

	private List<GroupableSwitch> switchList;

	private Point pos;

	private boolean visible;

	private WidgetHolder holder;

	private boolean canNoSelect;

	private GroupableSwitch selectedSwitch;

	public WExclusiveSwitchs(WidgetHolder w) {
		this.holder = w;
		this.pos = new Point();
		this.visible = true;
		this.switchMap = new HashMap<>();
		this.switchList = new ArrayList<>();
		this.selectedSwitch = null;
		this.canNoSelect = false;
	}

	public void add(String name, WSwitch w) {
		name = name.toLowerCase();
		if (w != null && !this.switchMap.containsKey(name)) {
			Point wPos = w.getPos().clone();
			wPos.add(this.pos);
			w.setPos(wPos);

			this.switchMap.put(name, this.switchList.size());
			GroupableSwitch gSwitch = new GroupableSwitch(name, w);
			this.switchList.add(gSwitch);

			if (this.canNoSelect == false && this.selectedSwitch == null) {
				gSwitch.setActive(true);
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
		if (!this.visible)
			return;

		for (WSwitch wSwitch : this.switchList) {
			wSwitch.draw(g);
		}
	}

	/**
	 * @return the holder
	 */
	public WidgetHolder getHolder() {
		return this.holder;
	}

	@Override
	public Point getPos() {
		return this.pos.clone();
	}

	/**
	 * @return the canNoSelect
	 */
	public boolean isCanNoSelect() {
		return this.canNoSelect;
	}

	@Override
	public boolean isVisible() {
		return this.visible;
	}

	public void remove(String name) {
		name = name.toLowerCase();
		if (this.switchMap.containsKey(name)) {
			this.switchList.remove((int) this.switchMap.get(name));
			this.switchMap.remove(name);
		}
	}

	public abstract void selectedChanged(String selected, boolean state);

	/**
	 * @param canNoSelect the canNoSelect to set
	 */
	public void setCanNoSelect(boolean canNoSelect) {
		this.canNoSelect = canNoSelect;
		if (canNoSelect == false && this.switchList.size() != 0) {
			this.switchList.get(0).setActive(true);
		} else if (canNoSelect && this.selectedSwitch != null) {
			this.selectedSwitch.setEnabled(true);
		}
	}

	/**
	 * @param holder the holder to set
	 */
	public void setHolder(WidgetHolder holder) {
		this.holder = holder;
	}

	@Override
	public void setPos(Point pos) {
		this.pos = pos.clone();
		Point vectToNewPos = pos.clone().sub(this.pos);
		this.pos.set(pos);

		for (GroupableSwitch sw : this.switchList) {
			sw.setOriginalPos(sw.getOriginalPos().add(vectToNewPos));
			sw.setPos(sw.getPos().add(vectToNewPos));
		}
	}

	public void setSelected(String name, boolean state) {
		name = name.toLowerCase();
		if (this.canNoSelect == false && state == false && this.selectedSwitch != null
				&& this.selectedSwitch.getName() == name)
			return;
		else {
			GroupableSwitch gSwitch = this.switchList.get(this.switchMap.get(name));

			gSwitch.setActive(state);
		}
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public void update(Input input) {
		if (!this.visible)
			return;

		for (GroupableSwitch wSwitch : this.switchList) {
			wSwitch.update(input);
		}
	}
}
