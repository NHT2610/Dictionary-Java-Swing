package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import app.App;
import models.Dictionary;
import models.FavoriteItem;
import models.LookupInformation;
import models.UserWord;

public class MainHandler {
	public static LookUpToken lookup(String keyWord, Dictionary dictionary) {
		String meaning = dictionary.getDictionaryData().get(keyWord);
		if (meaning != null) {
			return new LookUpToken(true, meaning);
		}
		return new LookUpToken(false, "");
	}

	public static void recordHistory(String word, String choice, Date date) {
		// System.out.println("recording");
		String lookupType = "";
		if (choice.equals("Tiếng Anh (Mặc định)")) {
			lookupType = "Anh->Việt";
		} else if (choice.equals("Tiếng Việt")) {
			lookupType = "Việt->Anh";
		}
		LookupInformation newLookup = new LookupInformation(word, lookupType);
		HashMap<LookupInformation, Integer> temp = App.getHistory().get(App.DATE_FORMAT.format(date));
		if (!temp.containsKey(newLookup)) {
			temp.put(newLookup, 1);
		} else {
			Integer numberOfLookups = temp.get(newLookup);
			numberOfLookups += 1;
			temp.put(newLookup, numberOfLookups);
		}
	}

	public static boolean removeAWordFromDictionary(Dictionary dictionary, String word, String lookupType) {
		try {
			// ===== Delete in dictionary =====
			String delWord = dictionary.removeAWord(word);

			// ===== Delete in user words list if it's a user word =====
			ArrayList<UserWord> userWordsList = App.getUserWordsList();
			int index = UserWord.getIndexOfElement(userWordsList, delWord, lookupType);
			if (index != -1) {
				userWordsList.remove(index);
			}
			// ===== Delete in favorites list if it exists =====
			ArrayList<FavoriteItem> favoritesList = App.getFavorites();
			index = FavoriteItem.getIndexOfElement(favoritesList, delWord, lookupType);
			if (index != -1) {
				favoritesList.remove(index);
			}
			return (delWord != null && delWord.equals(word));
		} catch (Exception e) {
			System.out.println("Error removeAWordFromDictionary(): " + e.getMessage());
			return false;
		}
	}

	public static boolean isExistedInFavoritesList(ArrayList<FavoriteItem> list, FavoriteItem newItem) {
		for (FavoriteItem item : list) {
			if (newItem.equals(item)) {
				return true;
			}
		}
		return false;
	}

	public static void markFavorite(ArrayList<UserWord> userWordsList, UserWord word) {
		userWordsList.get(userWordsList.indexOf(word)).setIsFavorited();
	}

	public static void removeAllUserWordsInFavoritesList(
			ArrayList<UserWord> userWordsList, ArrayList<FavoriteItem> favoritesList, String lookupType) {
		for (UserWord word : userWordsList) {
			if (word.getLookupType().equals(lookupType) && word.getIsFavoritedValue()) {
				favoritesList.remove(favoritesList.indexOf(new FavoriteItem(word.getWord(), word.getLookupType())));
			}
		}
	}

	public static void removeAllUserWordsByLookupType(ArrayList<UserWord> userWordsList, String lookupType) {
		ArrayList<FavoriteItem> favoritesList = App.getFavorites();
		for (UserWord word : userWordsList) {
			// ===== Remove user word in user words list =====
			if (word.getLookupType().equals(lookupType)) {
				userWordsList.remove(UserWord.getIndexOfElement(userWordsList, word.getWord(), lookupType));
				// ===== Remove user word in favorites list if it exists
				if (isExistedInFavoritesList(favoritesList, new FavoriteItem(word.getWord(), lookupType))) {
					favoritesList.remove(FavoriteItem.getIndexOfElement(favoritesList, word.getWord(), lookupType));
				}
			}
		}
	}
}
