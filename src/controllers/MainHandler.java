package controllers;

import models.Dictionary;

public class MainHandler {
	public static LookUpToken lookup(String keyWord, Dictionary dictionary) {
		String meaning = dictionary.getDictionaryData().get(keyWord);
		if (meaning != null) {
			return new LookUpToken(true, meaning);
		}
		return new LookUpToken(false, "");
	}
}
