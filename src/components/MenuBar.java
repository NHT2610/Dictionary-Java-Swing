package components;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

public class MenuBar {
	private JPanel menuBarPanel;
	private JButton btnTranslate;
	private JButton btnStatiscial;
	private JButton btnAddNewWord;
	private JButton btnFavoriteList;

	MainCardManager cardPanelObject;
	private JPanel cardPanel;
	private final Color BUTTON_COLOR = Color.getHSBColor(240f / 360f, 0.25f, 0.95f);
	private final Color ACTIVE_BUTTON_COLOR = Color.getHSBColor(200f / 360f, 0.25f, 0.95f);
	private JButton activeButton;

	public MenuBar() {
		menuBarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		btnTranslate = new JButton("TRA CỨU");
		btnTranslate.setBackground(BUTTON_COLOR);
		btnStatiscial = new JButton("THỐNG KÊ TRA CỨU");
		btnStatiscial.setBackground(BUTTON_COLOR);
		btnAddNewWord = new JButton("THÊM TỪ MỚI");
		btnAddNewWord.setBackground(BUTTON_COLOR);
		btnFavoriteList = new JButton("DANH SÁCH TỪ YÊU THÍCH");
		btnFavoriteList.setBackground(BUTTON_COLOR);

		cardPanelObject = new MainCardManager();
		cardPanel = cardPanelObject.getMainCardManagerPanel();
		activeButton = btnTranslate;
		activeButton.setBackground(ACTIVE_BUTTON_COLOR);

		registerListenerHandlers(cardPanelObject.getMainCardLayout());
	}

	public JPanel getMenuBarPanel() {
		menuBarPanel.add(btnTranslate);
		menuBarPanel.add(btnStatiscial);
		menuBarPanel.add(btnAddNewWord);
		menuBarPanel.add(btnFavoriteList);
		return menuBarPanel;
	}

	public JPanel getCardPanel() {
		return cardPanel;
	}

	private void registerListenerHandlers(CardLayout cardLayout) {
		// ==================== LOOK UP ====================
		btnTranslate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Look Up");
				activeButton.setBackground(BUTTON_COLOR);
				btnTranslate.setBackground(ACTIVE_BUTTON_COLOR);
				activeButton = btnTranslate;
			}
		});

		// ==================== STATISCIAL ====================
		btnStatiscial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Statiscial");
				activeButton.setBackground(BUTTON_COLOR);
				btnStatiscial.setBackground(ACTIVE_BUTTON_COLOR);
				activeButton = btnStatiscial;
			}
		});

		// ==================== ADD A NEW WORD ====================
		btnAddNewWord.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Add A New Word");
				activeButton.setBackground(BUTTON_COLOR);
				btnAddNewWord.setBackground(ACTIVE_BUTTON_COLOR);
				activeButton = btnAddNewWord;
			}
		});

		// ==================== FAVORITES ====================
		btnFavoriteList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Favorite List");
				activeButton.setBackground(BUTTON_COLOR);
				btnFavoriteList.setBackground(ACTIVE_BUTTON_COLOR);
				activeButton = btnFavoriteList;
				FavoriteListContent.updateTableData();
			}
		});
	}
}
