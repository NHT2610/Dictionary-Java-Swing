package controllers;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import components.FavoriteItemView;

public class FavoriteListContentHandler {
	public static String[][] sortTableByWord(String option) {
		ArrayList<FavoriteItemView> listItemView = FavoriteItemView.getFavoritesListView();
		if (option.equals("Thứ tự mặc định")) {
			return FavoriteItemView.convertArrayListToArray(listItemView);

		} else if (option.equals("Thứ tự từ A đến Z")) {
			Collator collator = Collator.getInstance(
					new Locale.Builder().setLanguage("vi").setRegion("VN").build());
			Comparator<FavoriteItemView> comparator = new Comparator<FavoriteItemView>() {
				@Override
				public int compare(FavoriteItemView item1, FavoriteItemView item2) {
					return collator.compare(item1.word, item2.word);
				}
			};
			Collections.sort(listItemView, comparator);
			String[][] result = FavoriteItemView.convertArrayListToArray(listItemView);
			return result;
			
		} else if (option.equals("Thứ tự từ Z đến A")) {
			Collator collator = Collator.getInstance(
					new Locale.Builder().setLanguage("vi").setRegion("VN").build());
			Comparator<FavoriteItemView> comparator = new Comparator<FavoriteItemView>() {
				@Override
				public int compare(FavoriteItemView item1, FavoriteItemView item2) {
					return collator.compare(item2.word, item1.word);
				}
			};
			Collections.sort(listItemView, comparator);
			String[][] result = FavoriteItemView.convertArrayListToArray(listItemView);
			return result;
		}
		return null;
	}
}
