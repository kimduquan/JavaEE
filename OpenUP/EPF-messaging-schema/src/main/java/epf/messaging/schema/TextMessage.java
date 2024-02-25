package epf.messaging.schema;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;

/**
 * 
 */
@Entity
public class TextMessage extends Message {

	/**
	 * 
	 */
	@Column
	private String text;

	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}
}
