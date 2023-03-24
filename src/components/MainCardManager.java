package components;

import javax.swing.JPanel;
import java.awt.*;

public class MainCardManager {
	private CardLayout cardLayout;
	private JPanel cardPanel;
	private JPanel mainContent;
	private JPanel statishcialContent;
	
	public MainCardManager() {
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);
		
		MainContent mainContentObject = new MainContent();
		mainContent = mainContentObject.getMainContentPanel();

		StatiscialContent statishcialContentObject = new StatiscialContent();
		statishcialContent = statishcialContentObject.getStatiscialContentPanel();
		
	}

	public JPanel getMainCardManagerPanel() {
		cardPanel.add(mainContent, "Look Up");
		cardPanel.add(statishcialContent, "Statiscial");
		return cardPanel;
	}

	public CardLayout getMainCardLayout() {
		return this.cardLayout;
	}
}
