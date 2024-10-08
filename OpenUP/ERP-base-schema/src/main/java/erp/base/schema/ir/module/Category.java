package erp.base.schema.ir.module;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_module_category")
@Description("Application")
public class Category {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Name")
	private String name;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Category.class)
	@Description("Parent Application")
	private String parent_id;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = Category.class)
	@ElementCollection(targetClass = Category.class)
	@CollectionTable(name = "ir_module_category")
	@Description("Child Applications")
	private List<String> child_ids;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = Module.class)
	@ElementCollection(targetClass = Module.class)
	@CollectionTable(name = "ir_module_module")
	@Description("Modules")
	private List<String> module_ids;
	
	/**
	 * 
	 */
	@Column
	@Description("Description")
	private String description;
	
	/**
	 * 
	 */
	@Column
	@Description("Sequence")
	private Integer sequence;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Visible")
	private Boolean visible = true;
	
	/**
	 * 
	 */
	@Column
	@Description("Exclusive")
	private Boolean exclusive;
	
	/**
	 * 
	 */
	@Column
	@Description("External ID")
	private String xml_id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public List<String> getChild_ids() {
		return child_ids;
	}

	public void setChild_ids(List<String> child_ids) {
		this.child_ids = child_ids;
	}

	public List<String> getModule_ids() {
		return module_ids;
	}

	public void setModule_ids(List<String> module_ids) {
		this.module_ids = module_ids;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Boolean getExclusive() {
		return exclusive;
	}

	public void setExclusive(Boolean exclusive) {
		this.exclusive = exclusive;
	}

	public String getXml_id() {
		return xml_id;
	}

	public void setXml_id(String xml_id) {
		this.xml_id = xml_id;
	}
}
