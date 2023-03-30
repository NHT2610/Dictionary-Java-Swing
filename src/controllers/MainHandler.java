package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import app.App;
import models.Dictionary;
import models.FavoriteItem;
import models.LookupInformation;

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
			// ===== Delete in favorites list =====
			ArrayList<FavoriteItem> list = App.getFavorites();
			FavoriteItem delItem = list.remove(FavoriteItem.getIndexOfElement(list, delWord, lookupType));
			return (delWord != null && delWord.equals(word) && delItem != null && delItem.getWord().equals(word));
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
}
