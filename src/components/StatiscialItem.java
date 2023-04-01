package components;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import app.App;
import models.*;

public class StatiscialItem {
	public String id;
	public String word;
	public String lookupType;
	public String numberOfLookups;
	public String note;

	public StatiscialItem() {

	}

	public StatiscialItem(String id, String word, String lookupType, String numberOfLookups, String note) {
		this.id = id;
		this.word = word;
		this.lookupType = lookupType;
		this.numberOfLookups = numberOfLookups;
		this.note = note;
	}

	public static ArrayList<StatiscialItem> getStatiscialList(Date start, Date end) {
		ArrayList<StatiscialItem> result = new ArrayList<StatiscialItem>();
		ArrayList<String> dates = getDatesBetween(start, end);
		ArrayList<HashMap<LookupInformation, Integer>> historyList = getHistoryListByDates(dates);
		HashMap<LookupInformation, Integer> statiscialMap = statiscial(historyList);
		int count = 1;
		for (LookupInformation keyWord : statiscialMap.keySet()) {
			String id = Integer.toString(count);
			String word = keyWord.getWord();
			String lookupType = keyWord.getLookupType();
			String numberOfLookups = Integer.toString(statiscialMap.get(keyWord));
			String note = "";
			if (App.getUserWordsList().contains(new UserWord(word, lookupType))) {
				note = "Từ của người dùng";
			}
			StatiscialItem newItem = new StatiscialItem(id, word, lookupType, numberOfLookups, note);
			result.add(newItem);
		}
		return result;
	}

	public static String[][] convertArrayListToArray(ArrayList<StatiscialItem> list) {
		String[][] result = new String[list.size()][5];
		for (int i = 0; i < list.size(); i++) {
			result[i][0] = Integer.toString(i + 1);
			result[i][1] = list.get(i).word;
			result[i][2] = list.get(i).lookupType;
			result[i][3] = list.get(i).numberOfLookups;
			result[i][4] = list.get(i).note;
		}
		return result;
	}

	private static ArrayList<HashMap<LookupInformation, Integer>> getHistoryListByDates(ArrayList<String> dates) {
		ArrayList<HashMap<LookupInformation, Integer>> result = new ArrayList<HashMap<LookupInformation, Integer>>();
		for (String date : dates) {
			if (App.getHistory().containsKey(date)) {
				result.add(App.getHistory().get(date));
			}
		}
		return result;
	}

	private static ArrayList<String> getDatesBetween(Date start, Date end) {
		ArrayList<String> dates = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);

		while (cal.getTime().before(end) || cal.getTime().equals(end)) {
			dates.add(App.DATE_FORMAT.format(cal.getTime()));
			cal.add(Calendar.DATE, 1);
		}
		return dates;
	}

	private static HashMap<LookupInformation, Integer> statiscial(ArrayList<HashMap<LookupInformation, Integer>> list) {
		HashMap<LookupInformation, Integer> result = new HashMap<LookupInformation, Integer>();
		for (int i = 0; i < list.size(); i++) {
			for (LookupInformation keyWord : list.get(i).keySet()) {
				if (result.containsKey(keyWord)) {
					Integer numberOfLookups = result.get(keyWord) + list.get(i).get(keyWord);
					result.put(keyWord, numberOfLookups);
				} else {
					LookupInformation newObj = new LookupInformation(keyWord.getWord(), keyWord.getLookupType());
					Integer numberOfLookups = list.get(i).get(keyWord);
					result.put(newObj, numberOfLookups);
				}
			}
		}
		return result;
	}
}
