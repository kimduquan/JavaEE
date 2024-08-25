package erp.base.schema.ir.ui;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.model.Model;
import erp.base.schema.res.groups.Groups;
import erp.base.schema.ir.model.Data;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_ui_view")
@Description("View")
public class View {
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("View Name")
	private String name;
	
	/**
	 * 
	 */
	@Column
	private String model;
	
	/**
	 * 
	 */
	@Column
	private String key;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("16")
	@Description("Sequence")
	private Integer priority = 16;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Description("View Type")
	private String type;
	
	/**
	 * 
	 */
	@Column
	@Description("View Architecture")
	private String arch;
	
	/**
	 * 
	 */
	@Column
	@Description("Base View Architecture")
	private String arch_base;
	
	/**
	 * 
	 */
	@Column
	@Description("Arch Blob")
	private String arch_db;
	
	/**
	 * 
	 */
	@Column
	@Description("Arch Filename")
	private String arch_fs;
	
	/**
	 * 
	 */
	@Column
	@Description("Modified Architecture")
	private Boolean arch_updated;
	
	/**
	 * 
	 */
	@Column
	@Description("Previous View Architecture")
	private String arch_prev;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = View.class)
	@Description("Inherited View")
	private String inherit_id;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = View.class)
	@ElementCollection(targetClass = View.class)
	@CollectionTable(name = "ir_ui_view")
	@Description("Views which inherit from this one")
	private List<String> inherit_children_ids;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Data.class)
	@Description("Model Data")
	private String model_data_id;
	
	/**
	 * 
	 */
	@Column
	@Description("External ID")
	private String xml_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Groups.class)
	@ElementCollection(targetClass = Groups.class)
	@CollectionTable(name = "res_groups")
	@Description("Groups")
	private List<String> groups_id;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("primary")
	@Description("View inheritance mode")
	private String mode = "primary";
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	private Boolean active = true;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Model.class)
	@Description("Model of the view")
	private String model_id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getArch() {
		return arch;
	}

	public void setArch(String arch) {
		this.arch = arch;
	}

	public String getArch_base() {
		return arch_base;
	}

	public void setArch_base(String arch_base) {
		this.arch_base = arch_base;
	}

	public String getArch_db() {
		return arch_db;
	}

	public void setArch_db(String arch_db) {
		this.arch_db = arch_db;
	}

	public String getArch_fs() {
		return arch_fs;
	}

	public void setArch_fs(String arch_fs) {
		this.arch_fs = arch_fs;
	}

	public Boolean getArch_updated() {
		return arch_updated;
	}

	public void setArch_updated(Boolean arch_updated) {
		this.arch_updated = arch_updated;
	}

	public String getArch_prev() {
		return arch_prev;
	}

	public void setArch_prev(String arch_prev) {
		this.arch_prev = arch_prev;
	}

	public String getInherit_id() {
		return inherit_id;
	}

	public void setInherit_id(String inherit_id) {
		this.inherit_id = inherit_id;
	}

	public List<String> getInherit_children_ids() {
		return inherit_children_ids;
	}

	public void setInherit_children_ids(List<String> inherit_children_ids) {
		this.inherit_children_ids = inherit_children_ids;
	}

	public String getModel_data_id() {
		return model_data_id;
	}

	public void setModel_data_id(String model_data_id) {
		this.model_data_id = model_data_id;
	}

	public String getXml_id() {
		return xml_id;
	}

	public void setXml_id(String xml_id) {
		this.xml_id = xml_id;
	}

	public List<String> getGroups_id() {
		return groups_id;
	}

	public void setGroups_id(List<String> groups_id) {
		this.groups_id = groups_id;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}
}
