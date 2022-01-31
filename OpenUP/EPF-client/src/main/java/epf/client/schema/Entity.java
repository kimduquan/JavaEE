package epf.client.schema;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author PC
 *
 */
@XmlRootElement
public class Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private List<Attribute> attributes;
	
	/**
	 * 
	 */
	private String idType;
	
	/**
	 * 
	 */
	private String type;
	
	/**
	 * 
	 */
	private String name;
	
	/**
	 * 
	 */
	private EntityType entityType;
	
	/**
	 * 
	 */
	private boolean singleId;
	
	/**
	 * 
	 */
	private Attribute id;
	
	/**
	 * 
	 */
	private Table table;
	
	/**
	 * @return the attributes
	 */
	public List<Attribute> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(final List<Attribute> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the idType
	 */
	public String getIdType() {
		return idType;
	}

	/**
	 * @param idType the idType to set
	 */
	public void setIdType(final String idType) {
		this.idType = idType;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the entityType
	 */
	public EntityType getEntityType() {
		return entityType;
	}

	/**
	 * @param entityType the entityType to set
	 */
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
