package models;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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

	public boolean changeStoragePath(String newPath) {
		storagePath = newPath;
		return true;
	}

	public String removeAWord(String word) {
		String meaningDelWord = dictionary.get(word);
		String delWord = dictionary.remove(word);
		if (delWord != null && delWord.equals(meaningDelWord)) {
			return word;
		}
		return null;
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

	public boolean saveDataToXML(String fileName) {
		try {
			String path = storagePath + fileName;
			// Tạo một đối tượng DocumentBuilderFactory để tạo đối tượng DocumentBuilder
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Tạo một Document mới
			Document doc = builder.newDocument();

			// Tạo một phần tử "dictionary" là phần tử gốc của Document
			Element rootElement = doc.createElement("dictionary");
			doc.appendChild(rootElement);

			// Duyệt qua các cặp key-value trong HashMap và tạo các phần tử "word" và "meaning" tương ứng
			for (Map.Entry<String, String> entry: dictionary.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();

				Element wordElement = doc.createElement("word");
				wordElement.appendChild(doc.createTextNode(key));

				Element meaningElement = doc.createElement("meaning");
				meaningElement.appendChild(doc.createTextNode(value));

				Element recordElement = doc.createElement("record");
				recordElement.appendChild(wordElement);
				recordElement.appendChild(meaningElement);

				rootElement.appendChild(recordElement);
			}

			// Tạo một đối tượng Transformer để ghi đối tượng DOMSource ra file .xml
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(path));
			transformer.transform(source, result);

			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
