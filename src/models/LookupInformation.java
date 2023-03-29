package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class LookupInformation implements ReadAndWriteItem<LookupInformation> {
	private String word;
	private String lookupType;

	public LookupInformation(String word, String lookupType) {
		this.word = word;
		this.lookupType = lookupType;
	}

	public String getWord() {
		return word;
	}

	public String getLookupType() {
		return lookupType;
	}

	public boolean equals(Object obj) {
		if (obj instanceof LookupInformation) {
			LookupInformation another = (LookupInformation) obj;
			return (this.word.equals(another.word) && this.lookupType.equals(another.lookupType));
		}
		return false;
	}

	public int hashCode() {
		return word.hashCode() + lookupType.hashCode();
	}

	@Override
	public LookupInformation readItem(BufferedReader br, String line) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'readItem'");
	}

	@Override
	public boolean writeItem(BufferedWriter bw) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'writeItem'");
	}
}
