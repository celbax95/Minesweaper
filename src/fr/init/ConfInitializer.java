package fr.init;

import java.util.ArrayList;
import java.util.List;

import fr.datafilesmanager.DatafilesManager;
import fr.datafilesmanager.XMLManager;
import fr.state.game.GameState;
import fr.state.menu.MenuState;
import fr.statepanel.AppStateManager;
import fr.statepanel.IAppState;
import fr.statepanel.StatePanel;
import fr.util.point.Point;
import fr.window.WinData;
import fr.window.Window;
import fr.xmlmanager.XMLManagerDOM;

public class ConfInitializer {

	private static final String startingState = "menu";

	private static final List<IAppState> states = new ArrayList<IAppState>() {
		private static final long serialVersionUID = 1L;
		{
			this.add(new GameState());
			this.add(new MenuState());
		}
	};

	// Fichiers de configuration
	private DatafilesManager dfm;

	public ConfInitializer() {
		this.dfm = DatafilesManager.getInstance();
	}

	/**
	 * Recuperation de la racine du fichier xml de configuration de la fenetre
	 */
	private Object getWinConf() {
		Object winConf = null;
		winConf = this.dfm.getFile("winConf");

		return winConf;
	}

	private WinData getWinData(Object winConf) {
		XMLManager manager = this.dfm.getXmlManager();

		WinData w = new WinData();

		int width = (int) manager.getParam(winConf, "width", 1366);

		int height = (int) manager.getParam(winConf, "height", 768);

		w.setWindowSize(new Point(width, height));

		w.setMarginTop((int) manager.getParam(winConf, "marginTop", 0));
		w.setMarginBottom((int) manager.getParam(winConf, "marginBottom", 0));
		w.setMarginLeft((int) manager.getParam(winConf, "marginLeft", 0));
		w.setMarginRight((int) manager.getParam(winConf, "marginRight", 0));
		w.setMargin((int) manager.getParam(winConf, "margin", 0));

		w.setFullscreen((boolean) manager.getParam(winConf, "fullscreen", false));
		w.setBorderless((boolean) manager.getParam(winConf, "borderless", false));

		w.setAntialiasing((int) manager.getParam(winConf, "antialiasing", 0));

		w.setRendering((int) manager.getParam(winConf, "rendering", 0));
		w.setColorRendering((int) manager.getParam(winConf, "colorRendering", 0));
		w.setAlphaInterpolation((int) manager.getParam(winConf, "alphaInterpolation", 0));

		return w;
	}

	/**
	 * Ajout des fichiers de configuration
	 */
	private void initConfFiles() {
		this.dfm = DatafilesManager.getInstance();
		this.dfm.init(new XMLManagerDOM());

		ConfFiles.initConfFiles(this.dfm);

	}

	private void initFonts() {
		Fonts.loadFonts();
	}

	/**
	 * Creation des elements a partir de la conf
	 */
	public void start() {

		this.initConfFiles();
		this.initFonts();

		Window screen = this.windowInitializers(this.getWinConf());

		this.windowStart(screen);
	}

	/**
	 * Creation de l'ecran
	 *
	 * @param winConf : racine du fichier de configuration
	 */
	private Window windowInitializers(Object winConf) {
		final Window screen = new Window();
		final StatePanel mainPanel = new StatePanel(screen);
		final WinData winData = this.getWinData(winConf);
		final AppStateManager asm = new AppStateManager();

		// register states
		for (IAppState state : states) {
			asm.addState(state);
		}

		mainPanel.init(winData);
		mainPanel.setAppStateManager(asm);

		mainPanel.setState(asm.getState(startingState));

		screen.init(mainPanel, winData);

		return screen;
	}

	/**
	 * Lance l'affichage de la fenetre
	 *
	 * @param configRoot : racine du fichier de configuration
	 */
	private void windowStart(Window screen) {
		screen.start();
	}
}
