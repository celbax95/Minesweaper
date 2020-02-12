package fr.state.game.board;

import java.awt.Graphics2D;
import java.util.Random;

import fr.util.point.Point;

public class Board {

	private static final int spaceBetween = 1;

	public static void addBombs(Tile[][] board, Point sizeTile, int nbOfBombs) {
		Random r = new Random();

		for (int i = 0; i < nbOfBombs; i++) {
			Tile tile = board[r.nextInt(sizeTile.ix())][r.nextInt(sizeTile.iy())];

			board[tile.getTilePos().ix()][tile.getTilePos().iy()] = new BombTile(tile.getBoard(), tile.getPos(),
					tile.getSize(), tile.getTilePos());
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

	private Tile[][] tiles;

	private Point pos;

	private Point size;

	private Point sizeTile;

	private int tileSize;

	private int nbOfBombs;

	private boolean init;

	public Board() {
		this.init = false;
	}

	public void createBoard(Point pos, Point sizeTile, int tileSize, int nbOfBombs) {
		this.init = false;

		this.pos = pos.clone();
		this.sizeTile = sizeTile.clone();
		this.tileSize = tileSize;
		this.nbOfBombs = nbOfBombs;

		this.size = this.sizeTile.clone().mult(this.tileSize)
				.add(this.sizeTile.clone().sub(new Point(1, 1)).mult(spaceBetween));

		this.tiles = getEmptyBoard(this, this.pos, this.sizeTile, this.tileSize);
		addBombs(this.tiles, this.sizeTile, this.nbOfBombs);
		initInfoTiles(this.tiles);

		this.init = true;
	}

	public void draw(Graphics2D g) {
		for (Tile[] list : this.tiles) {
			for (Tile tile : list) {
				tile.draw(g);
			}
		}
	}

	/**
	 * @return the pos
	 */
	public Point getPos() {
		return this.pos;
	}

	/**
	 * @return the tileSize
	 */
	public int getTileSize() {
		return this.tileSize;
	}

	/**
	 * @return the init
	 */
	public boolean isInit() {
		return this.init;
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
				tile.setCovered(false);
			}
		}
	}
}
