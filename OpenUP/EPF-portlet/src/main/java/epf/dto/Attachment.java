package epf.dto;

import java.io.File;
import java.io.Serializable;

public class Attachment implements Serializable {

	private static final long serialVersionUID = 1572382923498234661L;

	private File file;
	private int index;

	public Attachment(File file, int index) {
		this.file = file;
		this.index = index;
	}

	public File getFile() {
		return file;
	}

	public int getIndex() {
		return index;
	}

	public String getName() {
		return file.getName();
	}

	public long getSize() {
		return file.length();
	}
}
