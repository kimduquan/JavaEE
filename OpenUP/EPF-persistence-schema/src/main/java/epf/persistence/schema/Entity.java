package epf.persistence.schema;

import java.io.Serializable;
import java.util.List;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Entity implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Attribute> attributes;
	private String idType;
	private String type;
	private String name;
	private EntityType entityType;
	private boolean singleId;
	private Attribute id;
	private Table table;
	
	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(final List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(final String idType) {
		this.idType = idType;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(final EntityType entityType) {
		this.entityType = entityType;
	}

	public Attribute getId() {
		return id;
	}

	public void setId(final Attribute id) {
		this.id = id;
	}

	public boolean isSingleId() {
		return singleId;
	}

	public void setSingleId(final boolean singleId) {
		this.singleId = singleId;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(final Table table) {
		this.table = table;
	}
}
