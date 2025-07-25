package erp.base.schema.ir.module;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity(name = "ModuleCategory")
@Table(name = "ir_module_category")
@Description("Application")
public class Category {
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	@Description("Name")
	private String name;
	
	@Transient
	private Integer parent_id;
	
	@ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@Description("Parent Application")
	private Category parent;
	
	@Transient
	private List<Integer> child_ids;

	@OneToMany(targetEntity = Category.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@Description("Child Applications")
	private List<Category> childs;
	
	@Transient
	private List<Integer> module_ids;
	
	@OneToMany(targetEntity = Module.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	@Description("Modules")
	private List<Module> modules;
	
	@Column
	@Description("Description")
	private String description;
	
	@Column
	@Description("Sequence")
	private Integer sequence;
	
	@Column
	@DefaultValue("true")
	@Description("Visible")
	private Boolean visible = true;
	
	@Column
	@Description("Exclusive")
	private Boolean exclusive;
	
	@Transient
	@Description("External ID")
	private String xml_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public List<Integer> getChild_ids() {
		return child_ids;
	}

	public void setChild_ids(List<Integer> child_ids) {
		this.child_ids = child_ids;
	}

	public List<Category> getChilds() {
		return childs;
	}

	public void setChilds(List<Category> childs) {
		this.childs = childs;
	}

	public List<Integer> getModule_ids() {
		return module_ids;
	}

	public void setModule_ids(List<Integer> module_ids) {
		this.module_ids = module_ids;
	}

	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
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
