package erp.base.schema.ir.module;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import erp.schema.util.NameAttributeConverter;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity(name = "ModuleCategory")
@Table(name = "ir_module_category")
@Description("Application")
@NodeEntity("Application")
public class Category {
	
	/**
	 * 
	 */
	@jakarta.persistence.Id
	@Id
	private int id;

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Name")
	@Property
	@Convert(NameAttributeConverter.class)
	private String name;
	
	/**
	 * 
	 */
	@Column(insertable = false, updatable = false)
	@Description("Parent Application")
	@Transient
	private Integer parent_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Category.class)
	@JoinColumn(name = "parent_id")
	@Relationship(type = "PARENT_APPLICATION")
	private Category parent;
	
	/**
	 * 
	 */
	@ElementCollection
	@CollectionTable(name = "ir_module_category", joinColumns = {
			@JoinColumn(name = "parent_id")
	})
	@Description("Child Applications")
	@Transient
	private List<Integer> child_ids;

	/**
	 * 
	 */
	@OneToMany(targetEntity = Category.class, mappedBy = "parent_id")
	@Relationship(type = "CHILD_APPLICATIONS")
	private List<Category> childs;
	
	/**
	 * 
	 */
	@ElementCollection
	@CollectionTable(name = "ir_module_module", joinColumns = {
			@JoinColumn(name = "category_id")
	})
	@Description("Modules")
	@Transient
	private List<Integer> module_ids;
	
	/**
	 * 
	 */
	@OneToMany(targetEntity = Module.class, mappedBy = "category_id")
	@Relationship(type = "MODULES")
	private List<Module> modules;
	
	/**
	 * 
	 */
	@Column
	@Description("Description")
	@Property
	private String description;
	
	/**
	 * 
	 */
	@Column
	@Description("Sequence")
	@Property
	private Integer sequence;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Visible")
	@Property
	private Boolean visible = true;
	
	/**
	 * 
	 */
	@Column
	@Description("Exclusive")
	@Property
	private Boolean exclusive;
	
	/**
	 * 
	 */
	@Column
	@Description("External ID")
	@Property
	private String xml_id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

	public List<Integer> getChild_ids() {
		return child_ids;
	}

	public void setChild_ids(List<Integer> child_ids) {
		this.child_ids = child_ids;
	}

	public List<Integer> getModule_ids() {
		return module_ids;
	}

	public void setModule_ids(List<Integer> module_ids) {
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public List<Category> getChilds() {
		return childs;
	}

	public void setChilds(List<Category> childs) {
		this.childs = childs;
	}

	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
}
