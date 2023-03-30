package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Date;

import app.App;

public class FavoriteItem implements ReadAndWriteItem<FavoriteItem> {
	private String word;
	private String lookupType;
	private Date dateAdded;

	public FavoriteItem() {

	}

	public FavoriteItem(String word, String lookupType, Date dateAdded) {
		this.word = word;
		this.lookupType = lookupType;
		this.dateAdded = dateAdded;
	}

	public String getWord() {
		return word;
	}

	public String getMeaning() {
		if (lookupType.equals("Anh-Việt")) {
			return App.getDictionaryEngViet().getDictionaryData().get(word);
		} else if (lookupType.equals("Việt-Anh")) {
			return App.getDictionaryVietEng().getDictionaryData().get(word);
		}
		return "";
	}

	public String getLookupType() {
		return lookupType;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public static int getIndexOfElement(ArrayList<FavoriteItem> list, String word, String lookupType) {
		for (FavoriteItem item : list) {
			if (item.getWord().equals(word) && item.getLookupType().equals(lookupType)) {
				return list.indexOf(item);
			}
		}
		return -1;
	}

	public boolean equals(Object obj) {
		if (obj instanceof FavoriteItem) {
			FavoriteItem other = (FavoriteItem) obj;
			return (this.word.equals(other.word) && this.lookupType.equals(other.lookupType));
		}
		return false;
	}

	public int hashCode() {
		return word.hashCode() + lookupType.hashCode();
	}

	@Override
	public FavoriteItem readItem(BufferedReader br, String line) {
		String[] parts = line.split("##");
		this.word = parts[0];
		this.lookupType = parts[1];
		try {
			this.dateAdded = App.DATE_FORMAT.parse(parts[2]);
		} catch (Exception e) {
			System.out.println("Error readItem(): " + e.getMessage());
		}
		return this;
	}

	@Override
	public boolean writeItem(BufferedWriter bw) {
		try {
			String line = word + "##" + lookupType + "##" + App.DATE_FORMAT.format(dateAdded);
			bw.write(line);
			return true;
		} catch (Exception e) {
			System.out.println("Error reading favorite item: " + e.getMessage());
			return false;
		}
	}

}
