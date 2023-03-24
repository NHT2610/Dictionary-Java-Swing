package components;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.*;

public class Footer {
	private JPanel footerPanel;
	private JLabel footerText;

	public Footer() {
		footerPanel = new JPanel(new FlowLayout());
		footerText = new JLabel("Nguyễn Hữu Trực - 20120608");
	}

	public JPanel getFooterPanel() {
		footerPanel.add(footerText);
		return footerPanel;
	}
}
