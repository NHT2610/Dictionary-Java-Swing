package components;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.*;

public class AddNewWordContent {
	private JPanel addNewWordContentPanel;

	private JPanel titlePanel;
	private JLabel titleText;

	private JPanel dictionaryPickerPanel;
	private JLabel pickerLabel;
	private JMenuBar dictionaryPickerMenuBar;
	private JMenu dictionaryPickerMenu;
	private JMenuItem english, vietnamese;

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
		dictionaryPickerMenu = new JMenu("Tiếng Anh");
		english = new JMenuItem("Tiếng Anh");
		vietnamese = new JMenuItem("Tiếng Việt");

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

		return addNewWordContentPanel;
	}

}
