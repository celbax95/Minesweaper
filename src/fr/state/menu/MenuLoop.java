package fr.state.menu;

import fr.logger.Logger;

public class MenuLoop implements Runnable {

	private static final int LOOP_RATE = 10;// 32;

	private Thread loop;

	private MenuState state;

	public MenuLoop(MenuState state) {
		this.loop = new Thread(this);
		this.loop.setName("MenuLoop/loop");
		this.state = state;
	}

	@Override
	public void run() {
		while (Thread.currentThread().isInterrupted() == false) {
			synchronized (this) {

				if (this.loop.isInterrupted()) {
					break;
				}

				try {
					this.state.getInput();

					this.state.update();

					this.state.getStatePanel().repaint();

					Thread.sleep(LOOP_RATE);
				} catch (InterruptedException | NullPointerException e) {
					Logger.inf("Boucle principale du state menu terminee");
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	public void start() {
		this.loop.start();
	}

	public void stop() {
		synchronized (this) {
			this.loop.interrupt();
		}
	}
}
