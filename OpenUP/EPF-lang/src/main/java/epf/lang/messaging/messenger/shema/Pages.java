package epf.lang.messaging.messenger.shema;

import java.util.List;

public class Pages {

	private String object = "page";
	private List<Page> entry;
	
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public List<Page> getEntry() {
		return entry;
	}
	public void setEntry(List<Page> entry) {
		this.entry = entry;
	}
}
