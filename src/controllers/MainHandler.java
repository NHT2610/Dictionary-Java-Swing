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
			App.history_flag = true;
		} else {
			Integer numberOfLookups = temp.get(newLookup);
			numberOfLookups += 1;
			temp.put(newLookup, numberOfLookups);
			App.history_flag = true;
		}
	}

	public static int addANewWordToDictionary(
			Dictionary dictionary, String word, String meaning, String lookupType, int option) {
		switch (option) {
			case 0:
				if (dictionary.getDictionaryData().containsKey(word)) {
					return 0;
				}
			case 1:
				dictionary.getDictionaryData().put(word, meaning);
				if (lookupType.equals("Anh->Việt")) {
					App.dictionaryEngViet_flag = true;
				} else if (lookupType.equals("Việt->Anh")) {
					App.dictionaryVietEng_flag = true;
				}
				App.getUserWordsList().add(new UserWord(word, lookupType));
				App.userWordsList_flag = true;
				return 1;
			case 2:
				dictionary.getDictionaryData().put(word, meaning);
				if (lookupType.equals("Anh->Việt")) {
					App.dictionaryEngViet_flag = true;
				} else if (lookupType.equals("Việt->Anh")) {
					App.dictionaryVietEng_flag = true;
				}
				return 1;
			default:
				return -1;
		}
	}

	public static int addAWordToFavoritesList(String word, String lookupType, int option) {
		ArrayList<FavoriteItem> favoritesList = App.getFavorites();
		switch (option) {
			case 0:
				if (favoritesList.contains(new FavoriteItem(word, lookupType))) {
					return 0;
				}
			case 1:
				favoritesList.add(new FavoriteItem(word, lookupType));
				App.favorites_flag = true;
				if (App.getUserWordsList().contains(new UserWord(word, lookupType))) {
					ArrayList<UserWord> userWordsList = App.getUserWordsList();
					userWordsList.get(UserWord.getIndexOfElement(userWordsList, word, lookupType)).setIsFavorited();
					App.userWordsList_flag = true;
				}
				return 1;
			case 2:
				favoritesList.add(new FavoriteItem(word, lookupType));
				App.favorites_flag = true;
				return 1;
			default:
				return -1;
		}
	}

	public static boolean removeAWordFromDictionary(Dictionary dictionary, String word, String lookupType) {
		try {
			if (!dictionary.getDictionaryData().containsKey(word)) {
				return false;
			}
			// ===== Delete in dictionary =====
			String delWord = dictionary.removeAWord(word);
			if (lookupType.equals("Anh->Việt")) {
				App.dictionaryEngViet_flag = true;
			} else if (lookupType.equals("Việt->Anh")) {
				App.dictionaryVietEng_flag = true;
			}

			// ===== Delete in user words list if it's a user word =====
			ArrayList<UserWord> userWordsList = App.getUserWordsList();
			int index = UserWord.getIndexOfElement(userWordsList, delWord, lookupType);
			if (index != -1) {
				userWordsList.remove(index);
				App.userWordsList_flag = true;
			}
			// ===== Delete in favorites list if it exists =====
			ArrayList<FavoriteItem> favoritesList = App.getFavorites();
			index = FavoriteItem.getIndexOfElement(favoritesList, delWord, lookupType);
			if (index != -1) {
				favoritesList.remove(index);
				App.favorites_flag = true;
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
		App.userWordsList_flag = true;
	}

	public static void removeAllUserWordsInFavoritesList(
			ArrayList<UserWord> userWordsList, ArrayList<FavoriteItem> favoritesList, String lookupType) {
		for (UserWord word : userWordsList) {
			if (word.getLookupType().equals(lookupType) && word.getIsFavoritedValue()) {
				favoritesList.remove(favoritesList.indexOf(new FavoriteItem(word.getWord(), word.getLookupType())));
				word.setFavorite(false);
				if (!App.userWordsList_flag) {
					App.userWordsList_flag = true;
				}
				if (!App.favorites_flag) {
					App.favorites_flag = true;
				}
			}
		}
	}

	public static void removeAllUserWordsByLookupType(ArrayList<UserWord> userWordsList, String lookupType) {
		ArrayList<FavoriteItem> favoritesList = App.getFavorites();
		for (UserWord word : userWordsList) {
			// ===== Remove user word in user words list =====
			if (word.getLookupType().equals(lookupType)) {
				userWordsList.remove(UserWord.getIndexOfElement(userWordsList, word.getWord(), lookupType));
				if (!App.userWordsList_flag) {
					App.userWordsList_flag = true;
				}
				// ===== Remove user word in favorites list if it exists
				if (isExistedInFavoritesList(favoritesList, new FavoriteItem(word.getWord(), lookupType))) {
					favoritesList.remove(FavoriteItem.getIndexOfElement(favoritesList, word.getWord(), lookupType));
					if (!App.favorites_flag) {
						App.favorites_flag = true;
					}
				}
			}
		}
	}
}
