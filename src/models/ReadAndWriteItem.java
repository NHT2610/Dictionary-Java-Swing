package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

import app.App;

public interface ReadAndWriteItem<Item> {
	Item readItem(BufferedReader br, String line);

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

	static <Item extends ReadAndWriteItem<Item>> boolean readArrayList(
			String path, ArrayList<Item> list, Class<Item> clazz) {
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(path), "utf-8"));
			String line = br.readLine();
			while (line != null) {
				Constructor<Item> constructor = clazz.getDeclaredConstructor();
				Item item = constructor.newInstance();
				item.readItem(br, line);
				list.add(item);
				line = br.readLine();
			}
			br.close();
			return true;
		} catch (Exception e) {
			System.out.println("Error readArrayList(): " + e.getMessage());
			return false;
		}
	}

	static <T extends ReadAndWriteItem<T>> boolean readHashMap(String path) {
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(path), "utf-8"));
			String line = br.readLine();
			while (line != null) {
				HistoryItem newItem = new HistoryItem();
				newItem.readItem(br, line);
				App.getHistory().put(newItem.getDate(), newItem.getHistoryOfThatDate());
				line = br.readLine();
			}
			br.close();
			return true;
		} catch (IOException e) {
			System.out.println("Error reading file " + e.getMessage());
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
				bw.newLine();
			}
			bw.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
