package fr.state.game.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import fr.inputs.Input;
import fr.state.game.board.Board;
import fr.util.point.Point;
import fr.window.WinData;

public class HUD {

	private static final String bestScoreFilledTemplate = "Best : 000";
	private static final String bestScoreTemplate = "Best : ";
	private static final String noBestScore = "Best : None";

	private Board board;

	private int bombsRemaining;

	private long startedFor;

	private WinData winData;

	private Font font;

	private Point bombCountPos;
	private Point timerPos;
	private Point bestScorePos;

	private int bestScore;

	private AffineTransform af;
	private Font bestFont;

	public HUD(Board board, WinData winData) {
		this.board = board;
		this.winData = winData;

		this.bombsRemaining = 0;

		this.bestScore = -1;

		this.af = null;

		this.setLabelsPos();
	}

	public void draw(Graphics2D g) {

		if (this.af != null) {

			g.setColor(Color.white);

			g.setFont(this.font);

			String br = String.valueOf(this.bombsRemaining);
			for (int i = br.length(); i < 3; i++) {
				br = "0" + br;
			}

			String sf = String.valueOf(this.startedFor);
			for (int i = sf.length(); i < 3; i++) {
				sf = "0" + sf;
			}

			g.drawString(br, this.bombCountPos.ix(), this.bombCountPos.iy());
			g.drawString(sf, this.timerPos.ix(), this.timerPos.iy());

			g.setFont(this.bestFont);
			g.drawString(this.bestScore == -1 ? noBestScore : bestScoreTemplate + this.bestScore,
					this.bestScorePos.ix(), this.bestScorePos.iy());
		} else {
			this.af = g.getTransform();
			this.setLabelsPos();
		}
	}

	private void setLabelsPos() {
		this.font = new Font("Arial", Font.BOLD, 35);
		this.bestFont = new Font("Arial", Font.BOLD, 34);

		FontRenderContext frc = new FontRenderContext(this.af, true, true);

		Point digit3Pos = new Point(this.font.getStringBounds("000", frc).getWidth(),
				this.font.getStringBounds("000", frc).getHeight());

		Point bestScorePos = new Point(this.font.getStringBounds(bestScoreFilledTemplate, frc).getWidth(),
				this.font.getStringBounds(bestScoreFilledTemplate, frc).getHeight());

		Point noBestScorePos = new Point(this.font.getStringBounds(bestScoreFilledTemplate, frc).getWidth(),
				this.font.getStringBounds(noBestScore, frc).getHeight());

		this.bombCountPos = this.winData.getHalfWindowSize().clone();
		this.bombCountPos.x -= digit3Pos.x / 2;
		this.bombCountPos.y = 50;

		this.timerPos = this.winData.getHalfWindowSize().clone();
		this.timerPos.x += this.winData.getWindowSize().x / 4 - digit3Pos.x / 2;
		this.timerPos.y = 50;

		this.bestScorePos = this.winData.getHalfWindowSize().clone();
		if (this.bestScore == -1) {
			this.bestScorePos.x -= this.winData.getWindowSize().x / 4 + noBestScorePos.x / 2;
		} else {
			this.bestScorePos.x -= this.winData.getWindowSize().x / 4 + bestScorePos.x / 2;
		}
		this.bestScorePos.y = 50;
	}

	public void update(Input input) {
		this.bombsRemaining = this.board.getNbOfBombs() - this.board.getNbOfFlags();
		if (!this.board.isGameOver() && !this.board.isFinished() && !this.board.isFull()) {
			if (this.startedFor < 999) {
				this.startedFor = (System.currentTimeMillis() - this.board.getStartingTime()) / 1000;
			}
		}

		if (this.board.isFull()) {
			this.startedFor = 0;
			this.bestScore = this.board.getBestCurrentScore();
			this.setLabelsPos();
		}
	}
}
