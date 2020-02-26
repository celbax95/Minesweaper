package fr.state.game.board;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import fr.inputs.Input;
import fr.inputs.keyboard.KeyboardEvent;
import fr.inputs.mouse.MouseEvent;
import fr.util.point.Point;

public class Board {

	private static final int spaceBetween = 0;

	private static final int FRAME_SIZE = 6;

	private static final Color FINISHED_COLOR = new Color(0, 200, 0);
	private static final Color GAME_OVER_COLOR = new Color(200, 0, 0);
	private static final Color FRAME_COLOR = new Color(70, 70, 70);

	public static void addBombs(Tile[][] board, Point sizeTile, int nbOfBombs) {
		Random r = new Random();

		for (int i = 0; i < nbOfBombs; i++) {
			Tile tile = board[r.nextInt(sizeTile.ix())][r.nextInt(sizeTile.iy())];

			if (tile.isBomb()) {
				i--;
			} else {
				board[tile.getTilePos().ix()][tile.getTilePos().iy()] = new BombTile(tile.getBoard(), tile.getPos(),
						tile.getSize(), tile.getTilePos());
			}
		}
	}

	private static Tile[][] getEmptyBoard(Board parent, Point pos, Point size, int tileSize) {
		Tile[][] board = new Tile[size.ix()][size.iy()];

		Point tilePos = pos.clone();

		for (int y = 0; y < size.y; y++) {
			for (int x = 0; x < size.x; x++) {
				board[x][y] = new InfoTile(parent, tilePos.clone(), new Point(tileSize, tileSize), new Point(x, y));

				tilePos.x += tileSize + spaceBetween;
			}
			tilePos.y += tileSize + spaceBetween;
			tilePos.x = pos.x;
		}

		return board;
	}

	private static int getNbOfBombsAround(Tile[][] board, Point tilePos) {
		int x = tilePos.ix() - 1;
		int y = tilePos.iy() - 1;

		int nbOfBombsAround = 0;

		for (int ix = 0; ix < 3; ix++) {
			for (int iy = 0; iy < 3; iy++) {
				try {
					if (board[x + ix][y + iy].isBomb()) {
						nbOfBombsAround++;
					}
				} catch (Exception e) {
				}
			}
		}

		return nbOfBombsAround;
	}

	private static void initInfoTiles(Tile[][] board) {
		for (Tile[] tiles : board) {
			for (Tile tile : tiles) {
				tile.setNbBombsASide(getNbOfBombsAround(board, tile.getTilePos()));
			}
		}
	}

	private static void startingPoint(Tile[][] board, Point startingPoint) {

		Point boardSize = new Point();

		try {
			boardSize = new Point(board.length, board[0].length);
		} catch (Exception e) {
			return;
		}

		if (board[startingPoint.ix()][startingPoint.iy()].isBomb()
				|| board[startingPoint.ix()][startingPoint.iy()].getBombsASide() != 0) {
			Random r = new Random();

			Point min = startingPoint.clone().sub(new Point(1, 1));

			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					Tile tile = board[min.ix() + x][min.iy() + y];
					if (tile.isBomb()) {
						boolean replaced = false;

						while (!replaced) {
							Point randomPoint = new Point(r.nextInt(boardSize.ix()), r.nextInt(boardSize.iy()));

							Tile newTile = board[randomPoint.ix()][randomPoint.iy()];

							if (!newTile.isBomb()) {
								board[min.ix() + x][min.iy() + y] = new InfoTile(tile.getBoard(), tile.getPos(),
										tile.getSize(), tile.getTilePos());

								board[randomPoint.ix()][randomPoint.iy()] = new BombTile(newTile.getBoard(),
										newTile.getPos(), newTile.getSize(), newTile.getTilePos());

								replaced = true;
							}
						}
					}
				}
			}

			initInfoTiles(board);
		}
	}

	private int multiKey;

	private Tile[][] tiles;

	private Point pos;

	private Point size;

	private Point sizeTile;

	private int tileSize;

	private int nbOfBombs;

	private boolean init;

	private boolean full;

	private int nbOfFlags;

	private int nbOfTiles;

	private int uncoveredTiles;

	private long startingTime;

	private boolean finished;

	private boolean gameOver;

	private Map<Integer, Tile.Actions> mouseActions;
	private Map<Integer, Tile.Actions> keyboardActions;

	public Board() {
		this.init = false;
		this.multiKey = 0;
		this.full = true;
		this.nbOfFlags = 0;
		this.startingTime = 0;
		this.createActions();
	}

	private void actionOnTile(Tile tile, Tile.Actions action) {
		if (action == null)
			return;

		if (tile.isCovered()) {
			if (action == Tile.Actions.UNCOVER) {
				if (this.full) {
					startingPoint(this.tiles, tile.getTilePos());
					tile = this.tiles[tile.getTilePos().ix()][tile.getTilePos().iy()];
					this.uncoverAround(tile);

					this.startingTime = System.currentTimeMillis();

					this.full = false;
				} else {
					tile.setCovered(false);
					this.uncoveredTiles++;
				}
			} else if (action == Tile.Actions.FLAG || action == Tile.Actions.MULTI) {
				if (tile.toggleFlag()) {
					this.nbOfFlags++;
				} else {
					this.nbOfFlags--;
				}
			}
		} else {
			if (action == Tile.Actions.MULTI) {
				if (this.getFlagsAround(tile) == tile.getBombsASide()) {
					this.uncoverAround(tile);
				}
			}
		}
	}

	public void chainedUncover(Tile tile) {
		int x = tile.getTilePos().ix() - 1;
		int y = tile.getTilePos().iy() - 1;

		for (int ix = 0; ix < 3; ix++) {
			for (int iy = 0; iy < 3; iy++) {
				try {
					Tile selectedTile = this.tiles[x + ix][y + iy];
					if (!selectedTile.isBomb() && selectedTile.isCovered()) {
						selectedTile.setCovered(false);
						this.uncoveredTiles++;
					}
				} catch (Exception e) {
				}
			}
		}
	}

	private void createActions() {
		this.mouseActions = new HashMap<>();
		this.mouseActions.put(MouseEvent.LEFT_PRESSED, Tile.Actions.UNCOVER);
		this.mouseActions.put(MouseEvent.RIGHT_PRESSED, Tile.Actions.FLAG);
		this.mouseActions.put(MouseEvent.MIDDLE_PRESSED, Tile.Actions.MULTI);
		this.keyboardActions = new HashMap<>();
		this.keyboardActions.put(KeyEvent.VK_SPACE, Tile.Actions.MULTI);
	}

	public void createBoard(Point pos, Point sizeTile, int tileSize, int nbOfBombs) {
		this.init = false;

		this.pos = pos.clone();
		this.sizeTile = sizeTile.clone();
		this.nbOfTiles = sizeTile.ix() * sizeTile.iy();

		this.tileSize = tileSize;
		this.nbOfBombs = nbOfBombs;

		this.size = this.sizeTile.clone().mult(this.tileSize)
				.add(this.sizeTile.clone().sub(new Point(1, 1)).mult(spaceBetween));

		this.reset();
	}

	public void draw(Graphics2D g) {

		Color frameColor;

		if (this.gameOver) {
			frameColor = GAME_OVER_COLOR;
		} else if (this.finished) {
			frameColor = FINISHED_COLOR;
		} else {
			frameColor = FRAME_COLOR;
		}

		g.setColor(frameColor);

		g.fillRect(this.pos.ix() - FRAME_SIZE, this.pos.iy() - FRAME_SIZE, this.size.ix() + FRAME_SIZE * 2,
				this.size.iy() + FRAME_SIZE * 2);

		for (Tile[] list : this.tiles) {
			for (Tile tile : list) {
				tile.draw(g);
			}
		}
	}

	private void gameOver() {
		this.gameOver = true;
	}

	private int getFlagsAround(Tile tile) {
		int x = tile.getTilePos().ix() - 1;
		int y = tile.getTilePos().iy() - 1;

		int flagCount = 0;

		for (int ix = 0; ix < 3; ix++) {
			for (int iy = 0; iy < 3; iy++) {
				try {
					if (this.tiles[x + ix][y + iy].isFlagged()) {
						flagCount++;
					}
				} catch (Exception e) {
				}
			}
		}

		return flagCount;
	}

	/**
	 * @return the nbOfBombs
	 */
	public int getNbOfBombs() {
		return this.nbOfBombs;
	}

	/**
	 * @return the nbOfFlags
	 */
	public int getNbOfFlags() {
		return this.nbOfFlags;
	}

	public Tile getPointedTile(Point p) {
		for (Tile[] tiles2 : this.tiles) {
			for (Tile tile : tiles2) {
				if (!tile.isXAligned(p.ix())) {
					break;
				}
				if (tile.isYAligned(p.iy()))
					return tile;
			}
		}
		return null;
	}

	/**
	 * @return the pos
	 */
	public Point getPos() {
		return this.pos;
	}

	/**
	 * @return the startingTime
	 */
	public long getStartingTime() {
		return this.startingTime;
	}

	/**
	 * @return the tileSize
	 */
	public int getTileSize() {
		return this.tileSize;
	}

	/**
	 * @return the finished
	 */
	public boolean isFinished() {
		return this.finished;
	}

	/**
	 * @return the isFull
	 */
	public boolean isFull() {
		return this.full;
	}

	/**
	 * @return the finished
	 */
	public boolean isGameOver() {
		return this.gameOver;
	}

	public boolean isInBoard(Point p) {
		if (p == null)
			return false;
		return p.x > this.pos.x && p.y > this.pos.y && p.x < this.pos.x + this.size.x && p.y < this.pos.y + this.size.y;
	}

	/**
	 * @return the init
	 */
	public boolean isInit() {
		return this.init;
	}

	public void reset() {
		this.init = false;

		this.nbOfFlags = 0;
		this.gameOver = false;
		this.uncoveredTiles = 0;
		this.finished = false;
		this.startingTime = System.currentTimeMillis();

		this.tiles = getEmptyBoard(this, this.pos, this.sizeTile, this.tileSize);
		addBombs(this.tiles, this.sizeTile, this.nbOfBombs);
		initInfoTiles(this.tiles);

		this.full = true;

		this.init = true;
	}

	/**
	 * @param pos the pos to set
	 */
	public void setPos(Point pos) {
		this.pos = pos;
	}

	/**
	 * @param tileSize the tileSize to set
	 */
	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}

	public void uncover() {
		for (Tile[] tiles : this.tiles) {
			for (Tile tile : tiles) {
				if (tile.isCovered()) {
					tile.setCovered(false);
					this.uncoveredTiles++;
				}
			}
		}

		this.gameOver();
	}

	private void uncoverAround(Tile tile) {
		int x = tile.getTilePos().ix() - 1;
		int y = tile.getTilePos().iy() - 1;

		for (int ix = 0; ix < 3; ix++) {
			for (int iy = 0; iy < 3; iy++) {
				try {
					Tile selectedTile = this.tiles[x + ix][y + iy];
					if (selectedTile.isCovered()) {
						selectedTile.setCovered(false);
						this.uncoveredTiles++;
					}
				} catch (Exception e) {
				}
			}
		}
	}

	public void update(Input input) {

		// Mouse events
		for (MouseEvent e : input.mouseEvents) {
			if (this.mouseActions.containsKey(e.id)) {
				if (!this.gameOver && this.isInBoard(e.pos)) {
					Tile tile = this.getPointedTile(e.pos);
					if (tile != null) {
						this.actionOnTile(tile, this.mouseActions.get(e.id));
					}
				} else if (this.gameOver) {
					this.reset();
				}
			}
		}

		// Keyboard events
		for (KeyboardEvent e : input.keyboardEvents) {
			if (e.pressed && this.keyboardActions.containsKey(e.key)) {
				if (!this.gameOver && this.isInBoard(e.mousePos)) {
					Tile tile = this.getPointedTile(e.mousePos);
					if (tile != null) {
						this.actionOnTile(tile, this.keyboardActions.get(e.key));
					}
				} else if (this.gameOver) {
					this.reset();
				}
			} else if (e.pressed && e.key == KeyEvent.VK_BACK_SPACE) {
				this.reset();
			}
		}

		if (this.uncoveredTiles == this.nbOfTiles - this.nbOfBombs) {

			this.finished = true;
		}
	}
}
