package fr.util;

import java.util.HashMap;
import java.util.Map;

public class DualMap<A, B> {

	private Map<A, B> ab;
	private Map<B, A> ba;

	public DualMap() {
		this.ab = new HashMap<>();
		this.ba = new HashMap<>();
	}

	public boolean containsBackwardKey(B b) {
		return this.ba.containsKey(b);
	}

	public boolean containsForwardKey(A a) {
		return this.ab.containsKey(a);
	}

	public A getBackward(B b) {
		return this.ba.get(b);
	}

	public B getForward(A a) {
		return this.ab.get(a);
	}

	public void putBackward(B b, A a) {
		if (!this.containsBackwardKey(b)) {
			this.putForward(a, b);
		}
	}

	public void putForward(A a, B b) {
		if (!this.containsForwardKey(a)) {
			this.ab.put(a, b);
			this.ba.put(b, a);
		}
	}

	public void removeBackward(B b) {
		if (this.containsBackwardKey(b)) {
			this.removeForward(this.ba.get(b));
		}
	}

	public void removeForward(A a) {
		B b = this.ab.remove(a);
		this.ba.remove(b);
	}
}
