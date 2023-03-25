package components;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
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
	private JPanel newKeyWordPanel;
	private JPanel meaningOfTheNewWordPanel;
	private JLabel newKeyWordLabel;
	private JLabel meaningOfTheNewWordLabel;
	private JTextField newKeyWordText;
	private JTextField meaningOfTheNewWordText;

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

		addNewWordFormPanel = new JPanel(new GridLayout(1, 2));

		newKeyWordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		newKeyWordLabel = new JLabel("Từ khóa: ");
		newKeyWordText = new JTextField(20);
		newKeyWordText.setFont(new Font("Arial", Font.PLAIN, 17));
		newKeyWordText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		
		meaningOfTheNewWordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		meaningOfTheNewWordLabel = new JLabel("Nghĩa: ");
		meaningOfTheNewWordText = new JTextField(20);
		meaningOfTheNewWordText.setFont(new Font("Arial", Font.PLAIN, 17));
		meaningOfTheNewWordText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

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

		newKeyWordPanel.add(newKeyWordLabel);
		newKeyWordPanel.add(newKeyWordText);
		addNewWordFormPanel.add(newKeyWordPanel);

		meaningOfTheNewWordPanel.add(meaningOfTheNewWordLabel);
		meaningOfTheNewWordPanel.add(meaningOfTheNewWordText);
		addNewWordFormPanel.add(meaningOfTheNewWordPanel);

		addNewWordContentPanel.add(addNewWordFormPanel, BorderLayout.CENTER);

		btnAddNewWordPanel.add(btnAddNewWord);
		addNewWordContentPanel.add(btnAddNewWordPanel, BorderLayout.SOUTH);

		return addNewWordContentPanel;
	}

}
