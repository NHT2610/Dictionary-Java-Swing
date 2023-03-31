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
import controllers.MainHandler;
import models.Dictionary;

import java.awt.*;
import java.awt.event.*;

public class AddNewWordContent {
	private JPanel addNewWordContentPanel;

	private JPanel titlePanel;
	private JLabel titleText;

	private JPanel dictionaryPickerPanel;
	private JLabel pickerLabel;
	private JMenuBar dictionaryPickerMenuBar;
	private JMenu dictionaryPickerMenu;
	private JMenuItem english, vietnamese;
	private JMenuItem currentOption;

	private JPanel addNewWordFormPanel;
	private GridBagConstraints gbc;
	private JLabel newKeyWordLabel;
	private JLabel meaningOfTheNewWordLabel;
	private JTextField newKeyWordText;
	private JScrollPane scrollPane;
	private JTextArea meaningOfTheNewWordText;

	private JPanel btnAddNewWordPanel;
	private JButton btnAddNewWord;

	public AddNewWordContent() {
		addNewWordContentPanel = new JPanel(new BorderLayout());
		addNewWordContentPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));

		titlePanel = new JPanel(new FlowLayout());
		titleText = new JLabel("THÊM TỪ MỚI");
		titleText.setFont(new Font("Arial", Font.BOLD, 20));

		dictionaryPickerPanel = new JPanel(new FlowLayout());
		pickerLabel = new JLabel("Loại từ điển: ");
		dictionaryPickerMenuBar = new JMenuBar();
		dictionaryPickerMenuBar.setBackground(Color.LIGHT_GRAY);
		dictionaryPickerMenu = new JMenu("Anh-Việt (Mặc định)");
		english = new JMenuItem("Anh-Việt (Mặc định)");
		vietnamese = new JMenuItem("Việt-Anh");
		currentOption = english;

		addNewWordFormPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();

		newKeyWordLabel = new JLabel("Từ khóa: ");
		newKeyWordText = new JTextField(35);
		newKeyWordText.setFont(new Font("Arial", Font.PLAIN, 17));
		newKeyWordText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

		meaningOfTheNewWordLabel = new JLabel("Nghĩa: ");
		meaningOfTheNewWordText = new JTextArea(10, 35);
		meaningOfTheNewWordText.setLineWrap(false);
		meaningOfTheNewWordText.setFont(new Font("Arial", Font.PLAIN, 17));
		meaningOfTheNewWordText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		scrollPane = new JScrollPane(meaningOfTheNewWordText);
		scrollPane.setViewportView(meaningOfTheNewWordText);
		// scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		btnAddNewWordPanel = new JPanel(new FlowLayout());
		btnAddNewWord = new JButton("Thêm");
		btnAddNewWord.setBackground(Color.getHSBColor(120f / 360f, 0.5f, 0.8f));
	}

	public JPanel getAddNewWordContentPanel() {
		titlePanel.add(titleText);
		JPanel titleAndPickerPanel = new JPanel(new BorderLayout());
		titleAndPickerPanel.add(titlePanel, BorderLayout.NORTH);

		dictionaryPickerPanel.add(pickerLabel);
		dictionaryPickerMenu.add(english);
		dictionaryPickerMenu.add(vietnamese);
		dictionaryPickerMenuBar.add(dictionaryPickerMenu);
		dictionaryPickerPanel.add(dictionaryPickerMenuBar);
		titleAndPickerPanel.add(dictionaryPickerPanel, BorderLayout.SOUTH);

		addNewWordContentPanel.add(titleAndPickerPanel, BorderLayout.NORTH);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.weightx = 0.1;
		gbc.insets = new Insets(0, 0, 5, 0);
		addNewWordFormPanel.add(newKeyWordLabel, gbc);

		gbc.gridx = 1;
		gbc.gridwidth = 5;
		gbc.weightx = 0.9;
		gbc.insets = new Insets(0, 0, 5, 40);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		addNewWordFormPanel.add(newKeyWordText, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.weightx = 0.1;
		gbc.insets = new Insets(5, 65, 10, 0);
		addNewWordFormPanel.add(meaningOfTheNewWordLabel, gbc);

		gbc.gridx = 1;
		gbc.gridwidth = 5;
		gbc.insets = new Insets(5, 0, 10, 40);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.9; // Giãn rộng theo chiều ngang khi panel được kéo dài
		gbc.weighty = 1.0; // Giãn rộng theo chiều dọc khi panel được kéo dài
		addNewWordFormPanel.add(scrollPane, gbc);

		addNewWordContentPanel.add(addNewWordFormPanel, BorderLayout.CENTER);

		btnAddNewWordPanel.add(btnAddNewWord);
		addNewWordContentPanel.add(btnAddNewWordPanel, BorderLayout.SOUTH);

		registerListenerHandlers();

		return addNewWordContentPanel;
	}

	private void registerListenerHandlers() {
		// Menu dictionary picker
		english.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentOption = english;
				dictionaryPickerMenu.setText(currentOption.getText());
			}
		});
		vietnamese.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentOption = vietnamese;
				dictionaryPickerMenu.setText(currentOption.getText());
			}
		});

		// Button add a new word
		btnAddNewWord.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String keyWord = newKeyWordText.getText();
				String meaning = meaningOfTheNewWordText.getText();

				// ----- Kiểm tra các ô nhập dữ liệu
				if (keyWord.equals("") && meaning.equals("")) {
					JOptionPane.showMessageDialog(
							null,
							"Bạn chưa nhập thông tin Từ khóa và Nghĩa",
							"Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else if (keyWord.equals("")) {
					JOptionPane.showMessageDialog(
							null,
							"Bạn chưa nhập Từ khóa",
							"Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else if (meaning.equals("")) {
					JOptionPane.showMessageDialog(
							null,
							"Bạn chưa nhập Nghĩa của từ \"" + keyWord + "\"",
							"Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				String dictionaryType = currentOption.getText();
				Dictionary dictionary = new Dictionary();
				String lookupType = "";

				// ----- Thêm từ mới vào từ điển
				if (dictionaryType.equals("Anh-Việt (Mặc định)")) {
					dictionaryType = "Anh-Việt";
					lookupType = "Anh->Việt";
					dictionary = App.getDictionaryEngViet();
					
				} else if (dictionaryType.equals("Việt-Anh")) {
					lookupType = "Việt->Anh";
					dictionary = App.getDictionaryVietEng();
				}
				int result = MainHandler.addANewWordToDictionary(
						dictionary, keyWord, meaning, lookupType, 0);
				if (result == 0) {
					int choice = JOptionPane.showConfirmDialog(
							null,
							"\"" + keyWord
									+ "\" đã tồn tại trong từ điển Anh-Việt\n"
									+ "Nhấn OK để đặt lại nghĩa cho từ \"" + keyWord + "\"",
							"Xác nhận",
							JOptionPane.OK_CANCEL_OPTION);
					if (choice == JOptionPane.OK_OPTION) {
						result = MainHandler.addANewWordToDictionary(
								dictionary, keyWord, meaning, lookupType, 2);
						if (result == 1) {
							JOptionPane.showMessageDialog(
									null,
									"Đã cập nhật lại nghĩa của từ \"" + keyWord + "\" trong từ điển " + dictionaryType,
									"Thông báo",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				} else if (result == 1) {
					JOptionPane.showMessageDialog(
							null,
							"Thêm thành công từ \"" + keyWord + "\" vào từ điển " + dictionaryType,
							"Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}
}
