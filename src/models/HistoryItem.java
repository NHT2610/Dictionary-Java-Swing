package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import app.App;

public class HistoryItem implements ReadAndWriteItem<HistoryItem> {
	private String date;
	private HashMap<LookupInformation, Integer> historyOfThatDate;

	public HistoryItem() {
		date = App.DATE_FORMAT.format(new Date());
		historyOfThatDate = new HashMap<LookupInformation, Integer>();
	}

	public HistoryItem(String date, HashMap<LookupInformation, Integer> historyOfThatDate) {
		this.date = date;
		this.historyOfThatDate = historyOfThatDate;
	}

	public String getDate() {
		return date;
	}

	public HashMap<LookupInformation, Integer> getHistoryOfThatDate() {
		return historyOfThatDate;
	}

	public void increaseTheNumberOfLookups(LookupInformation word) {
		Integer current = historyOfThatDate.get(word);
		current += 1;
		historyOfThatDate.put(word, current);
	}

	@Override
	public HistoryItem readItem(BufferedReader br) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'readItem'");
	}

	@Override
	public boolean writeItem(BufferedWriter bw) {
		try {
			String itemLine = "";
			ArrayList<String> wordList = new ArrayList<String>();
			for (LookupInformation key : historyOfThatDate.keySet()) {
				String element = key.getWord() + "$" + historyOfThatDate.get(key).toString() + "&" + key.getLookupType();
				wordList.add(element);
			}
			itemLine = String.join("##", wordList);
			bw.write(date + "=>" + itemLine);
			return true;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return false;
		}
	}

}
