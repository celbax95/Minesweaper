package fr.state.game;

import fr.logger.Logger;

public class GameLoop implements Runnable {

	private Thread loop;

	private GameState state;

	public GameLoop(GameState state) {
		this.loop = new Thread(this);
		this.loop.setName("GameLoop/loop");
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

					Thread.sleep(32);
				} catch (InterruptedException | NullPointerException e) {
					Logger.inf("Boucle principale du state game terminee");
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
