package epf.messaging.schema;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;

@Entity
public class ByteMessage extends Message {

	@Column
	private byte[] bytes;

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(final byte[] bytes) {
		this.bytes = bytes;
	}
}
