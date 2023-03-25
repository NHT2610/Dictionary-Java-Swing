package components;

import javax.swing.JPanel;
import java.awt.*;

public class MainCardManager {
	private CardLayout cardLayout;
	private JPanel cardPanel;
	private JPanel mainContent;
	private JPanel statishcialContent;
	private JPanel addNewWordContent;
	private JPanel favoriteListContent;

	public MainCardManager() {
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);

		MainContent mainContentObject = new MainContent();
		mainContent = mainContentObject.getMainContentPanel();

		StatiscialContent statishcialContentObject = new StatiscialContent();
		statishcialContent = statishcialContentObject.getStatiscialContentPanel();

		AddNewWordContent addNewWordContentObject = new AddNewWordContent();
		addNewWordContent = addNewWordContentObject.getAddNewWordContentPanel();

		FavoriteListContent favoriteListContentObject = new FavoriteListContent();
		favoriteListContent = favoriteListContentObject.getFavoriteListContentPanel();
	}

	public JPanel getMainCardManagerPanel() {
		cardPanel.add(mainContent, "Look Up");
		cardPanel.add(statishcialContent, "Statiscial");
		cardPanel.add(addNewWordContent, "Add A New Word");
		cardPanel.add(favoriteListContent, "Favorite List");
		return cardPanel;
	}

	public CardLayout getMainCardLayout() {
		return this.cardLayout;
	}
}
