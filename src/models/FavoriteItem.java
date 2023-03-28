package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Date;

public class FavoriteItem implements ReadAndWriteItem<FavoriteItem> {
	private String word;
	private String meaning;
	private String lookupType;
	private Date dateAdded;

	public FavoriteItem(String word, String meaning, String lookupType, Date dateAdded) {
		this.word = word;
		this.meaning = meaning;
		this.lookupType = lookupType;
		this.dateAdded = dateAdded;
	}

	public String getWord() {
		return word;
	}

	public String getMeaning() {
		return meaning;
	}

	public String getLookupType() {
		return lookupType;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	@Override
	public FavoriteItem readItem(String path, BufferedReader br) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'readItem'");
	}

	@Override
	public boolean writeItem(String path, BufferedWriter bw) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'writeItem'");
	}

}
