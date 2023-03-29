package controllers;

import java.util.Date;
import java.util.HashMap;

import app.App;
import models.Dictionary;
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
		//System.out.println("recording");
		String lookupType = "";
		if (choice.equals("Tiếng Anh (Mặc định)")) {
			lookupType = "Anh-Việt";
		} else if (choice.equals("Tiếng Việt")) {
			lookupType = "Việt-Anh";
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
}
