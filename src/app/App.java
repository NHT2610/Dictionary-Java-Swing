package app;

import javax.swing.*;
import components.*;
import components.MenuBar;
import components.SplashScreen;
import models.*;

import java.awt.*;

public class App extends JPanel {
	private static SplashScreen splash;
	private JPanel header;
	private JPanel menuBar;
	private JPanel mainCard;
	private JPanel footer;

	private static Dictionary dictionaryEngViet;
	private static Dictionary dictionaryVietEng;

	private static final String XML_FILE_PATH = "./data/dictionary files/";

	private static void prepareData() {
		String path1 = XML_FILE_PATH + "Anh_Viet.xml";
		if (dictionaryEngViet.loadDataFromXML(path1)) {
			System.out.println("Load English-Vietnamese successfully!");
		} else {
			JOptionPane.showInternalMessageDialog(
					null,
					"Tải dữ liệu từ điển Anh Việt bị lỗi",
					"Lỗi",
					JOptionPane.ERROR_MESSAGE);
		}
		String path2 = XML_FILE_PATH + "Viet_Anh.xml";
		if (dictionaryVietEng.loadDataFromXML(path2)) {
			System.out.println("Load Vietnamese-English successfully!");
		} else {
			JOptionPane.showInternalMessageDialog(
					null,
					"Tải dữ liệu từ điển Việt Anh bị lỗi",
					"Lỗi",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Từ Điển Anh-Việt/Việt-Anh");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		App app = new App();
		prepareData();
		app.addComponentsToPane(frame.getContentPane());
		// frame.pack();
		frame.setSize(1000, 510);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	public void addComponentsToPane(Container pane) {
		// ==================== HEADER SIDE ====================
		JPanel headerAndMenuBar = new JPanel(new BorderLayout());
		Header headerObject = new Header();
		header = headerObject.getHeaderPanel();
		headerAndMenuBar.add(header, BorderLayout.NORTH);

		// ==================== MENU BAR SIDE ====================
		MenuBar menuBarObject = new MenuBar();
		menuBar = menuBarObject.getMenuBarPanel();
		headerAndMenuBar.add(menuBar, BorderLayout.SOUTH);

		pane.add(headerAndMenuBar, BorderLayout.NORTH);

		// ==================== CONTENT SIDE ====================
		mainCard = menuBarObject.getCardPanel();
		pane.add(mainCard, BorderLayout.CENTER);

		// ==================== FOOTER SIDE ====================
		Footer footerObject = new Footer();
		footer = footerObject.getFooterPanel();
		pane.add(footer, BorderLayout.SOUTH);
	}

	public App() {
		header = new JPanel();
		menuBar = new JPanel();
		footer = new JPanel();

		dictionaryEngViet = new Dictionary();
		dictionaryVietEng = new Dictionary();
	}

	public static Dictionary getDictionaryEngViet() {
		return dictionaryEngViet;
	}

	public static Dictionary getDictionaryVietEng() {
		return dictionaryVietEng;
	}

	public static void main(String[] args) throws Exception {
		splash = new SplashScreen();
		Thread.sleep(3000);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
				splash.dispose();
			}
		});
	}
}
