package components;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import app.App;
import controllers.FavoriteListContentHandler;
import controllers.LookUpToken;
import controllers.MainHandler;
import models.Dictionary;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FavoriteListContent {
	private JPanel favoriteListContentPanel;

	private JPanel titlePanel;
	private JLabel tableName;

	private JPanel favoriteListDetailsPanel;
	private static DefaultTableModel tableModel;
	private static final String[] columnTitles = { "STT", "Từ", "Loại tra cứu", "Ngày thêm", "Ghi chú" };
	private JScrollPane scrollPane;
	private JTable favoriteTable;
	private static ArrayList<FavoriteItemView> tableDataStored;

	private JPanel optionPanel;
	private JPanel sortPanel;
	private JLabel sortLabel;
	private JMenuBar sortMenuBar;
	private JMenu sortMenu;
	private JMenuItem defaultOption, AtoZOption, ZtoAOption;
	private static JMenuItem currentOption;
	private JButton btnSort;

	private JPanel delAndClsPanel;
	private JButton viewMeaning;
	private JButton delete;
	private JButton clearList;

	private final Color VIEWMEANING_BUTTON_COLOR = Color.getHSBColor(120f / 360f, 0.5f, 0.8f);
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
		favoriteTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// Cài font chữ đậm cho header columns
		JTableHeader tableHeader = favoriteTable.getTableHeader();
		tableHeader.setFont(tableHeader.getFont().deriveFont(Font.BOLD));

		favoriteTable.setPreferredScrollableViewportSize(new Dimension(800, 265));
		scrollPane = new JScrollPane(favoriteTable);
		favoriteTable.setFillsViewportHeight(true);

		optionPanel = new JPanel(new GridLayout(1, 2));

		sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		sortLabel = new JLabel("Sắp xếp theo Từ: ");
		sortMenuBar = new JMenuBar();
		sortMenuBar.setBackground(Color.LIGHT_GRAY);
		sortMenu = new JMenu("Thứ tự mặc định");
		defaultOption = new JMenuItem("Thứ tự mặc định");
		AtoZOption = new JMenuItem("Thứ tự từ A đến Z");
		ZtoAOption = new JMenuItem("Thứ tự từ Z đến A");
		currentOption = defaultOption;
		btnSort = new JButton("Sắp xếp");
		btnSort.setBackground(Color.getHSBColor(120f / 360f, 0.5f, 0.8f));

		delAndClsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		viewMeaning = new JButton("Xem nghĩa");
		viewMeaning.setBackground(VIEWMEANING_BUTTON_COLOR);
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

		sortPanel.add(sortLabel);
		sortMenu.add(defaultOption);
		sortMenu.add(AtoZOption);
		sortMenu.add(ZtoAOption);
		sortMenuBar.add(sortMenu);
		sortPanel.add(sortMenuBar);
		sortPanel.add(btnSort);

		delAndClsPanel.add(viewMeaning);
		delAndClsPanel.add(delete);
		delAndClsPanel.add(clearList);

		optionPanel.add(sortPanel);
		optionPanel.add(delAndClsPanel);
		favoriteListContentPanel.add(optionPanel, BorderLayout.SOUTH);

		registerListenerHandlers();

		return favoriteListContentPanel;
	}

	private void registerListenerHandlers() {
		// Button sort
		btnSort.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String option = currentOption.getText();
				String[][] tableData = FavoriteListContentHandler.sortTableByWord(option);
				if (tableData != null) {
					tableModel.setDataVector(tableData, columnTitles);
				} else {
					System.out.println("Error btnSort");
				}
			}
		});

		// Menu sort option
		defaultOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentOption = defaultOption;
				sortMenu.setText(currentOption.getText());
			}
		});
		AtoZOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentOption = AtoZOption;
				sortMenu.setText(currentOption.getText());
			}
		});
		ZtoAOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentOption = ZtoAOption;
				sortMenu.setText(currentOption.getText());
			}
		});

		// Button view meaning
		viewMeaning.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] selectedRows = favoriteTable.getSelectedRows();
				String word = (String) favoriteTable.getValueAt(selectedRows[0], 1);
				String lookupType = (String) favoriteTable.getValueAt(selectedRows[0], 2);
				Dictionary dictionary = new Dictionary();
				if (lookupType.equals("Anh->Việt")) {
					dictionary = App.getDictionaryEngViet();
				} else if (lookupType.equals("iệt->Anh")) {
					dictionary = App.getDictionaryVietEng();
				}
				LookUpToken token = new LookUpToken(false, "");
				token = MainHandler.lookup(word, dictionary);
				if (token.getStatus()) {
					new DisplayMeaningWindow(word, token.getMessage()).setVisible(true);
				} else {
					JOptionPane.showMessageDialog(
							null,
							"Không tìm thấy từ \"" + word + "\" trong từ điển",
							"Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// Button delete a row
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] selectedRows = favoriteTable.getSelectedRows();
				for (int i = selectedRows.length - 1; i >= 0; i--) {
					String word = (String) favoriteTable.getValueAt(selectedRows[i], 1);
					String lookupType = (String) favoriteTable.getValueAt(selectedRows[i], 2);
					String note = (String) favoriteTable.getValueAt(selectedRows[i], 4);
					tableModel.removeRow(selectedRows[i]);
					FavoriteListContentHandler.removeAWordFromFavoritesList(word, lookupType, note);
				}
				updateTableData();
			}
		});

		// Button clear favorites list
		clearList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(
						null,
						"Bạn có muốn xóa toàn bộ danh sách yêu thích?",
						"Xác nhận",
						JOptionPane.OK_CANCEL_OPTION);
				if (choice == JOptionPane.OK_OPTION) {
					for (FavoriteItemView itemView : tableDataStored) {
						FavoriteListContentHandler.removeAWordFromFavoritesList(itemView.word, itemView.lookupType, itemView.note);
					}
					tableDataStored.clear();
					updateTableData();
				}
			}
		});
	}

	public static ArrayList<FavoriteItemView> getTableDataStored() {
		return tableDataStored;
	}

	public static void updateTableData() {
		tableDataStored = FavoriteItemView.getFavoritesListView();
		String[][] tableData = FavoriteListContentHandler.sortTableByWord(currentOption.getText());
		tableModel.setDataVector(tableData, columnTitles);
	}
}
