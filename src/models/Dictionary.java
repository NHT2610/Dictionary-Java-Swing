package models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Dictionary {
	private HashMap<String, String> dictionary;
	private String storagePath;

	public Dictionary() {
		dictionary = new HashMap<String, String>();
		storagePath = "./data/storages/dictionaries/";
	}

	public Dictionary(String storagePath) {
		dictionary = new HashMap<String, String>();
		this.storagePath = storagePath;
	}

	public boolean loadDataFromXML(String filePath) {
		try {
			// Tạo một đối tượng DocumentBuilderFactory để tạo đối tượng DocumentBuilder
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Đọc nội dung từ tệp XML
			Document doc = builder.parse(filePath);

			// Lấy danh sách các phần tử "record"
			NodeList recordList = doc.getElementsByTagName("record");

			// Lặp qua từng phần tử của record
			for (int i = 0; i < recordList.getLength(); i++) {
				Element record = (Element) recordList.item(i);
				// Lấy giá trị thẻ "word"
				String word = record.getElementsByTagName("word").item(0).getTextContent();
				// Lấy giá trị thẻ "meaning"
				String meaning = record.getElementsByTagName("meaning").item(0).getTextContent();
				// Thêm cặp <key, value> mới <=> <word, meaning> vào dictionary
				dictionary.put(word, meaning);
			}
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public HashMap<String, String> getDictionaryData() {
		return dictionary;
	}

	public boolean saveDataToFile(String fileName) {
		try {
			String path = storagePath + fileName;
			FileWriter fileWriter = new FileWriter(path);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			for (String key : dictionary.keySet()) {
				String value = dictionary.get(key);
				String line = key + "=" + value;
				bufferedWriter.write(line);
				bufferedWriter.newLine();
			}
			bufferedWriter.close();
			fileWriter.close();
			return true;

		} catch (IOException e) {
			System.out.println("Error writing file: " + e.getMessage());
			return false;
		}
	}

}
