package components;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.*;

public class FavoriteListContent {
	private JPanel favoriteListContentPanel;

	private JPanel titlePanel;
	private JLabel tableName;

	private JPanel favoriteListDetailsPanel;
	private final String[] columnTitles = { "STT", "Từ", "Nghĩa", "Loại tra cứu", "Lựa chọn" };
	private JScrollPane scrollPane;
	private JTable favoriteTable;

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
		favoriteTable = new JTable(dataTest, columnTitles);
		scrollPane = new JScrollPane(favoriteTable);
	}

	public JPanel getFavoriteListContentPanel() {
		titlePanel.add(tableName);
		favoriteListContentPanel.add(titlePanel, BorderLayout.NORTH);

		favoriteListDetailsPanel.add(scrollPane);
		favoriteListContentPanel.add(favoriteListDetailsPanel, BorderLayout.CENTER);

		return favoriteListContentPanel;
	}
}
