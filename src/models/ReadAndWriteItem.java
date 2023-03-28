package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

public interface ReadAndWriteItem<Item> {
	Item readItem(String path, BufferedReader br);

	boolean writeItem(String path, BufferedWriter bw);

	static <Item extends ReadAndWriteItem<Item>> boolean writeArrayList(String path, ArrayList<Item> list) {
		try {
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(path), "utf-8"));
			for (Item item : list) {
				item.writeItem(path, bw);
				bw.newLine();
			}
			bw.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	static <Item extends ReadAndWriteItem<Item>> boolean writeHashMap(String path, HashMap<String, Item> map) {
		return true;
	}
}
