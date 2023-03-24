package components;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;

public class Header {
	private JPanel headerPanel;
	private JLabel headerText;

	public Header() {
		headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		headerText = new JLabel("Từ điển Anh-Việt/Việt-Anh");
		headerText.setFont(new Font("Arial", Font.BOLD, 26));
	}

	public JPanel getHeaderPanel() {
		headerPanel.add(headerText);
		return headerPanel;
	}
}
