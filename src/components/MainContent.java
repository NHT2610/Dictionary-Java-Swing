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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import app.App;
import controllers.LookUpToken;
import controllers.MainHandler;
import models.FavoriteItem;

import java.awt.event.*;
import java.util.Date;
import java.awt.*;

public class MainContent {
	private JPanel mainContentPanel;

	private JPanel languagePickerAndButtons;
	private JPanel languagePickerPanel;
	private JLabel languagePickerLabel;
	private JMenuBar languagePickerMenuBar;
	private JMenu languagePickerMenu;
	private JMenuItem english, vietnamese;
	private JMenuItem currentOption;

	private JPanel favoriteAndRemoveButtonPanel;
	private JButton btnAddToFavorite;
	private JButton btnRemoveFromDictionaries;

	private JPanel translatePanel;
	private JPanel inputPanel;
	private JLabel inputLabel;
	private JTextField inputText;
	private JButton btnLookUp;
	private JPanel outputPanel;
	private JLabel outputLabel;
	private JScrollPane scrollPane;
	private JTextArea outputTextArea;

	private JPanel resetPanel;
	private JButton btnReset;

	public MainContent() {
		mainContentPanel = new JPanel(new BorderLayout());
		mainContentPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));

		languagePickerAndButtons = new JPanel(new GridLayout(1, 2));

		languagePickerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		languagePickerLabel = new JLabel("Ngôn ngữ tra cứu: ");
		languagePickerMenuBar = new JMenuBar();
		languagePickerMenuBar.setBackground(Color.LIGHT_GRAY);
		languagePickerMenu = new JMenu("Tiếng Anh (Mặc định)");
		english = new JMenuItem("Tiếng Anh (Mặc định)");
		vietnamese = new JMenuItem("Tiếng Việt");
		currentOption = english;

		favoriteAndRemoveButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnAddToFavorite = new JButton("Yêu thích");
		btnAddToFavorite.setBackground(Color.getHSBColor(204, 18, 69));
		btnRemoveFromDictionaries = new JButton("Xóa khỏi từ điển");
		btnRemoveFromDictionaries.setBackground(Color.getHSBColor(0, 33, 78));

		translatePanel = new JPanel(new BorderLayout());
		inputPanel = new JPanel(new FlowLayout());
		inputLabel = new JLabel("Từ khóa: ");
		inputText = new JTextField(40);
		inputText.setFont(new Font("Arial", Font.PLAIN, 17));
		inputText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		btnLookUp = new JButton("Tra cứu");
		btnLookUp.setBackground(Color.getHSBColor(120f / 360f, 0.5f, 0.8f));

		outputPanel = new JPanel(new FlowLayout());
		outputLabel = new JLabel("Nghĩa: ");
		outputTextArea = new JTextArea(13, 60);
		outputTextArea.setLineWrap(false);
		outputTextArea.setFont(new Font("Arial", Font.PLAIN, 15));
		outputTextArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		scrollPane = new JScrollPane(outputTextArea);
		scrollPane.setViewportView(outputTextArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		resetPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnReset = new JButton("Tải lại từ điển");
		btnReset.setBackground(Color.getHSBColor(100 / 360f, 0.2f, 0.8f));
	}

	public JPanel getMainContentPanel() {
		// ==================== LANGUAGE PICKER SIDE ====================
		languagePickerPanel.add(languagePickerLabel);
		languagePickerMenu.add(english);
		languagePickerMenu.add(vietnamese);
		languagePickerMenuBar.add(languagePickerMenu);
		languagePickerPanel.add(languagePickerMenuBar);

		favoriteAndRemoveButtonPanel.add(btnAddToFavorite);
		favoriteAndRemoveButtonPanel.add(btnRemoveFromDictionaries);

		languagePickerAndButtons.add(languagePickerPanel);
		languagePickerAndButtons.add(favoriteAndRemoveButtonPanel);
		mainContentPanel.add(languagePickerAndButtons, BorderLayout.NORTH);

		// ==================== CONTENT SIDE ====================
		inputPanel.add(inputLabel);
		inputPanel.add(inputText);
		inputPanel.add(btnLookUp);
		translatePanel.add(inputPanel, BorderLayout.NORTH);
		outputPanel.add(outputLabel);
		outputPanel.add(scrollPane);
		translatePanel.add(outputPanel);
		mainContentPanel.add(translatePanel, BorderLayout.CENTER);

		// ==================== RESET SIDE ====================
		resetPanel.add(btnReset);
		mainContentPanel.add(resetPanel, BorderLayout.SOUTH);

		// ==================== EVENT HANDLER ====================
		registerListenerHandlers();

		return mainContentPanel;
	}

	private void registerListenerHandlers() {
		// Menu language picker
		english.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentOption = english;
				languagePickerMenu.setText(currentOption.getText());
			}
		});
		vietnamese.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentOption = vietnamese;
				languagePickerMenu.setText(currentOption.getText());
			}
		});

		// ----- Button look up -----
		btnLookUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String keyWord = inputText.getText();
				if (keyWord.equals("")) {
					JOptionPane.showMessageDialog(
							null,
							"Bạn chưa nhập từ khóa",
							"Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				String choose = currentOption.getText();
				LookUpToken result = new LookUpToken(false, "");
				if (choose.equals("Tiếng Anh (Mặc định)")) {
					result = MainHandler.lookup(keyWord, App.getDictionaryEngViet());
				} else if (choose.equals("Tiếng Việt")) {
					result = MainHandler.lookup(keyWord, App.getDictionaryVietEng());
				}
				if (result.getStatus()) {
					outputTextArea.setText(result.getMessage());
				} else {
					JOptionPane.showMessageDialog(
							null,
							"Không tìm thấy từ \"" + keyWord + "\" trong từ điển",
							"Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// ----- Button add a word to favorite -----
		btnAddToFavorite.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String keyWord = inputText.getText();
				if (keyWord.equals("")) {
					JOptionPane.showMessageDialog(
							null,
							"Bạn chưa nhập từ khóa",
							"Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}
				String choose = currentOption.getText();
				String lookupType = "";
				LookUpToken result = new LookUpToken(false, "");
				if (choose.equals("Tiếng Anh (Mặc định)")) {
					lookupType = "Anh->Việt";
					result = MainHandler.lookup(keyWord, App.getDictionaryEngViet());
				} else if (choose.equals("Tiếng Việt")) {
					lookupType = "Việt->Anh";
					result = MainHandler.lookup(keyWord, App.getDictionaryVietEng());
				}
				if (result.getStatus()) {
					FavoriteItem newFavoriteItem = new FavoriteItem(keyWord, result.getMessage(), lookupType, new Date());
					App.getFavorites().add(newFavoriteItem);
					JOptionPane.showMessageDialog(
							null,
							"Đã thêm từ \"" + keyWord + "\" vào danh sách yêu thích",
							"Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(
							null,
							"Không tìm thấy từ \"" + keyWord + "\" trong từ điển",
							"Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// ----- Button remove word from dictionary
		btnRemoveFromDictionaries.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showConfirmDialog(
						null,
						"Bạn có muốn xóa từ ra khỏi từ điển",
						"Xác nhận",
						JOptionPane.OK_CANCEL_OPTION);
			}
		});
	}
}
