package epf.persistence.schema;

import java.io.Serializable;
import java.util.List;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Embeddable implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Attribute> attributes;
	private String type;
	
	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(final List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}
}
