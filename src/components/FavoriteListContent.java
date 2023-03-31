package components;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import app.App;

import java.awt.*;
import java.util.ArrayList;
import models.FavoriteItem;
import models.UserWord;

class FavoriteItemView {
	public String id;
	public String word;
	public String lookupType;
	public String dateAdded;
	public String note;

	public FavoriteItemView() {

	}

	public FavoriteItemView(String id, String word, String lookupType, String dateAdded, String note) {
		this.id = id;
		this.word = word;
		this.lookupType = lookupType;
		this.dateAdded = dateAdded;
		this.note = note;
	}

	public static ArrayList<FavoriteItemView> getFavoritesListView() {
		ArrayList<FavoriteItemView> result = new ArrayList<FavoriteItemView>();
		ArrayList<FavoriteItem> favoritesList = App.getFavorites();
		for (FavoriteItem item : favoritesList) {
			FavoriteItemView newItem = new FavoriteItemView();
			newItem.id = Integer.toString(favoritesList.indexOf(item) + 1);
			newItem.word = item.getWord();
			newItem.lookupType = item.getLookupType();
			newItem.dateAdded = App.DATE_FORMAT.format(item.getDateAdded());
			UserWord userWord = new UserWord(item.getWord(), item.getLookupType());
			if (App.getUserWordsList().contains(userWord)) {
				newItem.note = "Người dùng thêm";
			} else {
				newItem.note = "";
			}
			result.add(newItem);
		}
		return result;
	}

	public static String[][] convertArrayListToArray(ArrayList<FavoriteItemView> list) {
		String[][] result = new String[list.size()][5];
		for (int i = 0; i < list.size(); i++) {
			FavoriteItemView item = list.get(i);
			result[i][0] = item.id;
			result[i][1] = item.word;
			result[i][2] = item.lookupType;
			result[i][3] = item.dateAdded;
			result[i][4] = item.note;
		}
		return result;
	}
}

public class FavoriteListContent {
	private JPanel favoriteListContentPanel;

	private JPanel titlePanel;
	private JLabel tableName;

	private JPanel favoriteListDetailsPanel;
	private DefaultTableModel tableModel;
	private final String[] columnTitles = { "STT", "Từ", "Loại tra cứu", "Ngày thêm", "Ghi chú" };
	private JScrollPane scrollPane;
	private JTable favoriteTable;
	private ArrayList<FavoriteItemView> tableDataStored;

	private JPanel optionPanel;
	private JButton delete;
	private JButton clearList;

	private final Color DELETE_BUTTON_COLOR = Color.getHSBColor(0, 33, 78);
	private final Color CLEAR_BUTTON_COLOR = Color.getHSBColor(0, 0.5f, 0.85f);

	public FavoriteListContent() {
		favoriteListContentPanel = new JPanel(new BorderLayout());
		favoriteListContentPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));

		titlePanel = new JPanel(new FlowLayout());
		tableName = new JLabel("DANH SÁCH YÊU THÍCH");
		tableName.setFont(new Font("Arial", Font.BOLD, 20));

		favoriteListDetailsPanel = new JPanel(new FlowLayout());
		tableDataStored = FavoriteItemView.getFavoritesListView();
		String[][] tableData = FavoriteItemView.convertArrayListToArray(tableDataStored);
		
		tableModel = new DefaultTableModel(tableData, columnTitles);
		favoriteTable = new JTable(tableModel);
		// Cài font chữ đậm cho header columns
		JTableHeader tableHeader = favoriteTable.getTableHeader();
		tableHeader.setFont(tableHeader.getFont().deriveFont(Font.BOLD));

		favoriteTable.setPreferredScrollableViewportSize(new Dimension(800, 265));
		scrollPane = new JScrollPane(favoriteTable);
		favoriteTable.setFillsViewportHeight(true);

		optionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		delete = new JButton("Xóa");
		delete.setBackground(DELETE_BUTTON_COLOR);
		clearList = new JButton("Xóa danh sách");
		clearList.setBackground(CLEAR_BUTTON_COLOR);
	}

	public JPanel getFavoriteListContentPanel() {
		titlePanel.add(tableName);
		favoriteListContentPanel.add(titlePanel, BorderLayout.NORTH);

		favoriteListDetailsPanel.add(scrollPane);
		favoriteListContentPanel.add(favoriteListDetailsPanel, BorderLayout.CENTER);

		optionPanel.add(delete);
		optionPanel.add(clearList);
		favoriteListContentPanel.add(optionPanel, BorderLayout.SOUTH);

		registerListenerHandlers();

		return favoriteListContentPanel;
	}

	private void registerListenerHandlers() {
		
	}
}
