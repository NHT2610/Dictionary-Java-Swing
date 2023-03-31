package app;

import javax.swing.*;
import components.*;
import components.MenuBar;
import components.SplashScreen;
import controllers.ReadAndWriteItem;
import models.*;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class App extends JPanel {
	private static SplashScreen splash;
	// ========== Views components ==========
	private JPanel header;
	private JPanel menuBar;
	private JPanel mainCard;
	private JPanel footer;
	// ========== Data ==========
	private static Dictionary dictionaryEngViet;
	private static Dictionary dictionaryVietEng;
	private static ArrayList<FavoriteItem> favorites;
	private static HashMap<String, HashMap<LookupInformation, Integer>> history;
	private static ArrayList<UserWord> userWordsList;
	// ========== Constants ==========
	public static final String XML_FILE_PATH = "./data/dictionary files/";
	public static final String DICTIONARIES_STORAGES_PATH = "./data/storages/dictionaries/";
	public static final String HISTORY_STORAGES_PATH = "./data/storages/history/";
	public static final String FAVORITES_STORAGES_PATH = "./data/storages/favorites/";
	public static final String USER_WORDS_PATH = "./data/storages/user words/";
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	// ========== Modified track flags ==========
	public static boolean dictionaryEngViet_flag;
	public static boolean dictionaryVietEng_flag;
	public static boolean favorites_flag;
	public static boolean history_flag;
	public static boolean userWordsList_flag;

	private static void prepareData() {
		// ==================== LOAD DICTIONARIES DATA ====================
		String path1 = DICTIONARIES_STORAGES_PATH + "Anh_Viet.xml";
		if (dictionaryEngViet.loadDataFromXML(path1)) {
			System.out.println("Load English-Vietnamese successfully!");
		} else {
			System.out.println("English-Vietnamese dictionary loading failed");
		}
		String path2 = DICTIONARIES_STORAGES_PATH + "Viet_Anh.xml";
		if (dictionaryVietEng.loadDataFromXML(path2)) {
			System.out.println("Load Vietnamese-English successfully!");
		} else {
			System.out.println("Vietnamese-English dictionary loading failed");
		}

		// ==================== LOAD HISTORY DATA ====================
		String path3 = HISTORY_STORAGES_PATH + "history.txt";
		boolean check = ReadAndWriteItem.readHashMap(path3);
		if (!check) {
			System.out.println("History data loading failed");
		} else {
			System.out.println("Load history data successfully!");
		}
		if (!history.containsKey(DATE_FORMAT.format(new Date()))) {
			HistoryItem newItem = new HistoryItem();
			history.put(newItem.getDate(), newItem.getHistoryOfThatDate());
		}

		// ==================== LOAD FAVORITES DATA ====================
		String path4 = FAVORITES_STORAGES_PATH + "favorites.txt";
		check = ReadAndWriteItem.readArrayList(path4, favorites, FavoriteItem.class);
		if (!check) {
			System.out.println("Favorites data loading failed");
		} else {
			System.out.println("Load favorites data successfully!");
		}

		// ==================== LOAD FAVORITES DATA ====================
		String path5 = USER_WORDS_PATH + "user_words.txt";
		check = ReadAndWriteItem.readArrayList(path5, userWordsList, UserWord.class);
		if (!check) {
			System.out.println("User words data loading failed");
		} else {
			System.out.println("Load user words data successfully!");
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

		dictionaryEngViet = new Dictionary(DICTIONARIES_STORAGES_PATH);
		dictionaryVietEng = new Dictionary(DICTIONARIES_STORAGES_PATH);
		favorites = new ArrayList<FavoriteItem>();
		history = new HashMap<String, HashMap<LookupInformation, Integer>>();
		userWordsList = new ArrayList<UserWord>();

		dictionaryEngViet_flag = false;
		dictionaryVietEng_flag = false;
		favorites_flag = false;
		history_flag = false;
		userWordsList_flag = false;
	}

	public static void saveData() {
		if (dictionaryEngViet_flag) {
			getDictionaryEngViet().saveDataToXML("Anh_Viet.xml");
		}
		if (dictionaryVietEng_flag) {
			getDictionaryVietEng().saveDataToXML("Viet_Anh.xml");
		}
		if (history_flag) {
			ReadAndWriteItem.writeHashMap(HISTORY_STORAGES_PATH + "history.txt", history);
		}
		if (favorites_flag) {
			ReadAndWriteItem.writeArrayList(FAVORITES_STORAGES_PATH + "favorites.txt", favorites);
		}
		if (userWordsList_flag) {
			ReadAndWriteItem.writeArrayList(USER_WORDS_PATH + "user_words.txt", userWordsList);
		}
	}

	public static Dictionary getDictionaryEngViet() {
		return dictionaryEngViet;
	}

	public static Dictionary getDictionaryVietEng() {
		return dictionaryVietEng;
	}

	public static ArrayList<FavoriteItem> getFavorites() {
		return favorites;
	}

	public static HashMap<String, HashMap<LookupInformation, Integer>> getHistory() {
		return history;
	}

	public static ArrayList<UserWord> getUserWordsList() {
		return userWordsList;
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
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					saveData();
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
				}
			}
		});
	}
}
