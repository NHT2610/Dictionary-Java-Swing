package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Date;

import app.App;

public class FavoriteItem implements ReadAndWriteItem<FavoriteItem> {
	private String word;
	private String lookupType;
	private Date dateAdded;

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

	@Override
	public FavoriteItem readItem(BufferedReader br, String line) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'readItem'");
	}

	@Override
	public boolean writeItem(BufferedWriter bw) {
		try {
			String line = word + "##" + lookupType + "##" + dateAdded.toString();
			bw.write(line);
			return true;
		} catch (Exception e) {
			System.out.println("Error reading favorite item: " + e.getMessage());
			return false;
		}
	}

}
