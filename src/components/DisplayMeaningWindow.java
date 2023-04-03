package components;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.*;

public class DisplayMeaningWindow extends JFrame {
	private JPanel panel;
	private JLabel theWord;
	private JScrollPane scrollPane;
	private JTextArea meaningTextArea;

	public DisplayMeaningWindow(String word, String meaning) {
		setTitle("Xem nghĩa của từ");

		panel = new JPanel(new BorderLayout());
		JPanel theWordPanel = new JPanel(new FlowLayout());
		theWord = new JLabel("Nghĩa của từ: " + word);
		theWord.setFont(new Font("Arial", Font.BOLD, 16));
		theWordPanel.add(theWord);
		panel.add(theWordPanel, BorderLayout.NORTH);

		JPanel meaningPanel = new JPanel(new FlowLayout());
		meaningTextArea = new JTextArea(23, 50);
		meaningTextArea.setEditable(false);
		meaningTextArea.setLineWrap(false);
		meaningTextArea.setFont(new Font("Arial", Font.PLAIN, 15));
		meaningTextArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		scrollPane = new JScrollPane(meaningTextArea);
		meaningPanel.add(scrollPane);
		meaningTextArea.setText(meaning);
		panel.add(meaningPanel, BorderLayout.CENTER);

		add(panel);
		setSize(700, 500);
		setResizable(false);
		setLocationRelativeTo(null);

	}
}
