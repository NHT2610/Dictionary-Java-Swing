package components;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;

public class FavoriteListContent {
	private JPanel favoriteListContentPanel;

	private JPanel titlePanel;
	private JLabel tableName;

	private JPanel favoriteListDetailsPanel;
	private DefaultTableModel tableModel;
	private final String[] columnTitles = { "STT", "Từ", "Nghĩa", "Loại tra cứu", "Lựa chọn" };
	private JScrollPane scrollPane;
	private JTable favoriteTable;

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
		String[][] dataTest = {
				{ "1", "Anh", "Anh", "Anh->Việt", "" },
				{ "2", "Yêu", "Yêu", "Anh->Việt", "" },
				{ "3", "Em", "Em", "Việt->Anh", "" },
				{ "4", "Nhiều", "Anh", "Anh->Việt", "" },
		};

		tableModel = new DefaultTableModel(dataTest, columnTitles);
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

		return favoriteListContentPanel;
	}
}
