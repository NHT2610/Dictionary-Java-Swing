package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

public interface ReadAndWriteItem<Item> {
	Item readItem(BufferedReader br);

	boolean writeItem(BufferedWriter bw);

	static <Item extends ReadAndWriteItem<Item>> boolean writeArrayList(String path, ArrayList<Item> list) {
		try {
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(path), "utf-8"));
			for (Item item : list) {
				item.writeItem(bw);
				bw.newLine();
			}
			bw.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	static <T extends ReadAndWriteItem<T>> boolean writeHashMap(
			String path, HashMap<String, HashMap<LookupInformation, Integer>> map) {
		try {
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(path), "utf-8"));
			for (String key : map.keySet()) {
				new HistoryItem(key, map.get(key)).writeItem(bw);
			}
			bw.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
