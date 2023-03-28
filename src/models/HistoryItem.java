package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.HashMap;

public class HistoryItem implements ReadAndWriteItem<HistoryItem> {
	private HashMap<String, Integer> historyOfThatDay;

	public HistoryItem() {
		historyOfThatDay = new HashMap<String, Integer>();
	}

	public void increaseTheNumberOfLookups(String word) {
		Integer current = historyOfThatDay.get(word);
		current += 1;
		historyOfThatDay.put(word, current);
	}

	@Override
	public HistoryItem readItem(String path, BufferedReader br) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'readItem'");
	}

	@Override
	public boolean writeItem(String path, BufferedWriter bw) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'writeItem'");
	}
	
}
