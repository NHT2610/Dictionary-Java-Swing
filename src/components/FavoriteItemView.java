package components;

import java.util.ArrayList;

import app.App;
import models.FavoriteItem;
import models.UserWord;

public class FavoriteItemView {
	public String id;
	public String word;
	public String lookupType;
	public String dateAdded;
	public String note;

	public FavoriteItemView() {

	}

	public FavoriteItemView(String id, String word, String lookupType, String dateAdded, String note) {
		this.id = id;
		this.word = word;
		this.lookupType = lookupType;
		this.dateAdded = dateAdded;
		this.note = note;
	}

	public static ArrayList<FavoriteItemView> getFavoritesListView() {
		ArrayList<FavoriteItemView> result = new ArrayList<FavoriteItemView>();
		ArrayList<FavoriteItem> favoritesList = App.getFavorites();
		for (FavoriteItem item : favoritesList) {
			FavoriteItemView newItem = new FavoriteItemView();
			newItem.id = Integer.toString(favoritesList.indexOf(item) + 1);
			newItem.word = item.getWord();
			newItem.lookupType = item.getLookupType();
			newItem.dateAdded = App.DATE_FORMAT.format(item.getDateAdded());
			UserWord userWord = new UserWord(item.getWord(), item.getLookupType());
			if (App.getUserWordsList().contains(userWord)) {
				newItem.note = "Từ của người dùng";
			} else {
				newItem.note = "";
			}
			result.add(newItem);
		}
		return result;
	}

	public static String[][] convertArrayListToArray(ArrayList<FavoriteItemView> list) {
		String[][] result = new String[list.size()][5];
		for (int i = 0; i < list.size(); i++) {
			FavoriteItemView item = list.get(i);
			result[i][0] = Integer.toString(i + 1);
			result[i][1] = item.word;
			result[i][2] = item.lookupType;
			result[i][3] = item.dateAdded;
			result[i][4] = item.note;
		}
		return result;
	}
}
