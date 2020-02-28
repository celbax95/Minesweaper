package fr.init;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Fonts {

	private static final String FONT_PATH = "/resources/font/";

	private static final List<String> FONTS = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			this.add("TCB.TTF");
		}
	};

	public static void loadFonts() {
		try {
			for (String fontName : FONTS) {
				Font customFont = Font.createFont(Font.TRUETYPE_FONT,
						Fonts.class.getResourceAsStream(FONT_PATH + fontName));
				GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFont);
			}
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
	}
}
