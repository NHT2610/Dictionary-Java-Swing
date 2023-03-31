package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;

import controllers.ReadAndWriteItem;

public class UserWord implements ReadAndWriteItem<UserWord> {
	private String word;
	private String lookupType;
	private boolean isFavorited;

	public UserWord() {

	}

	public UserWord(String word, String lookupType) {
		this.word = word;
		this.lookupType = lookupType;
		this.isFavorited = false;
	}

	public UserWord(String word, String lookupType, boolean isFavorited) {
		this.word = word;
		this.lookupType = lookupType;
		this.isFavorited = isFavorited;
	}

	public void setFavorite(boolean opt) {
		this.isFavorited = opt;
	}

	public boolean equals(Object obj) {
		if (obj instanceof UserWord) {
			UserWord other = (UserWord) obj;
			return (this.word.equals(other.word) && this.lookupType.equals(other.lookupType));
		}
		return false;
	}

	public int hashCode() {
		return word.hashCode() + lookupType.hashCode();
	}

	public String getWord() {
		return word;
	}

	public String getLookupType() {
		return lookupType;
	}

	public boolean getIsFavoritedValue() {
		return isFavorited;
	}

	public void setIsFavorited() {
		this.isFavorited = true;
	}

	public static int getIndexOfElement(ArrayList<UserWord> list, String word, String lookupType) {
		UserWord newUserWord = new UserWord(word, lookupType);
		if (list.contains(newUserWord)) {
			return list.indexOf(newUserWord);
		}
		return -1;
	}

	@Override
	public UserWord readItem(BufferedReader br, String line) {
		String[] parts = line.split("##");
		if (parts.length != 3) {
			return null;
		}
		this.word = parts[0];
		this.lookupType = parts[1];
		try {
			if (parts[2].equals("true")) {
				this.isFavorited = true;
			} else if (parts[2].equals("false")) {
				this.isFavorited = false;
			}
		} catch (Exception e) {
			System.out.println("Error readItem(): " + e.getMessage());
		}
		return this;
	}

	@Override
	public boolean writeItem(BufferedWriter bw) {
		try {
			String line = word + "##" + lookupType + "##" + Boolean.toString(isFavorited);
			bw.write(line);
			return true;
		} catch (Exception e) {
			System.out.println("Error writeItem(): " + e.getMessage());
			return false;
		}
	}


}
