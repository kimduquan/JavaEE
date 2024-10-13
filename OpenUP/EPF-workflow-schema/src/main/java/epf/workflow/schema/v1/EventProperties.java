package epf.workflow.schema.v1;

import org.eclipse.microprofile.graphql.Description;

@Description("An event object typically includes details such as the event type, source, timestamp, and unique identifier along with any relevant data payload.")
public class EventProperties {

	@Description("Identifies the event. source + id is unique for each distinct event.")
	private String id;
	
	@Description("An URI formatted string, or runtime expression, that identifies the context in which an event happened. source + id is unique for each distinct event.")
	private String source;
	
	@Description("Describes the type of event related to the originating occurrence.")
	private String type;
	
	@Description("A string, or runtime expression, representing the timestamp of when the occurrence happened.")
	private String time;
	
	@Description("Describes the subject of the event in the context of the event producer.")
	private String subject;
	
	@Description("Content type of data value. If omitted, it implies the data is a JSON value conforming to the \"application/json\" media type.")
	private String datacontenttype;
	
	@Description("An URI formatted string, or runtime expression, that identifies the schema that data adheres to.")
	private String dataschema;
	
	@Description("The event payload.")
	private Object data;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDatacontenttype() {
		return datacontenttype;
	}

	public void setDatacontenttype(String datacontenttype) {
		this.datacontenttype = datacontenttype;
	}

	public String getDataschema() {
		return dataschema;
	}

	public void setDataschema(String dataschema) {
		this.dataschema = dataschema;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
