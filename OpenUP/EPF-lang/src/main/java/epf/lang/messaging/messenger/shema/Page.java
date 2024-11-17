package epf.lang.messaging.messenger.shema;

import java.util.List;

public class Page {

	private long time;
	private String id;
	private List<Message> messaging;
	
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Message> getMessaging() {
		return messaging;
	}
	public void setMessaging(List<Message> messaging) {
		this.messaging = messaging;
	}
}
