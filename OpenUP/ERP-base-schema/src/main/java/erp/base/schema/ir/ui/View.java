package erp.base.schema.ir.ui;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import erp.base.schema.ir.model.Model;
import erp.base.schema.res.groups.Groups;
import erp.base.schema.ir.model.Data;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@NodeEntity("View")
public class View {
	
	/**
	 * 
	 */
	public enum ViewType {
		/**
		 * 
		 */
		tree,
        /**
         * 
         */
        form,
        /**
         * 
         */
        graph,
        /**
         * 
         */
        pivot,
        /**
         * 
         */
        calendar,
        /**
         * 
         */
        gantt,
        /**
         * 
         */
        kanban,
        /**
         * 
         */
        search,
        /**
         * 
         */
        qweb
	}
	
	/**
	 * 
	 */
	public enum ViewInheritanceMode {
		/**
		 * 
		 */
		primary,
		/**
		 * 
		 */
		extension
	}
	
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
	@Description("View Name")
	@Property
	private String name;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String model;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String key;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("16")
	@Description("Sequence")
	@Property
	private Integer priority = 16;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Description("View Type")
	@Property
	private ViewType type;
	
	/**
	 * 
	 */
	@Column
	@Description("View Architecture")
	@Property
	private String arch;
	
	/**
	 * 
	 */
	@Column
	@Description("Base View Architecture")
	@Property
	private String arch_base;
	
	/**
	 * 
	 */
	@Column
	@Description("Arch Blob")
	@Property
	private String arch_db;
	
	/**
	 * 
	 */
	@Column
	@Description("Arch Filename")
	@Property
	private String arch_fs;
	
	/**
	 * 
	 */
	@Column
	@Description("Modified Architecture")
	@Property
	private Boolean arch_updated;
	
	/**
	 * 
	 */
	@Column
	@Description("Previous View Architecture")
	@Property
	private String arch_prev;
	
	/**
	 * 
	 */
	@Column(insertable = false, updatable = false)
	@Description("Inherited View")
	@Transient
	private Integer inherit_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = View.class)
	@JoinColumn(name = "inherit_id")
	@Relationship(type = "INHERITED_VIEW")
	private View inherit;
	
	/**
	 * 
	 */
	@ElementCollection
	@CollectionTable(name = "ir_ui_view")
	@Description("Views which inherit from this one")
	@Transient
	private List<Integer> inherit_children_ids;

	/**
	 * 
	 */
	@OneToMany(targetEntity = View.class)
	@JoinColumn(name = "inherit_id")
	@Relationship(type = "INHERITED_CHILDRENS")
	private List<View> inherit_childrens;
	
	/**
	 * 
	 */
	@Column(insertable = false, updatable = false)
	@Description("Model Data")
	@Transient
	private Integer model_data_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Data.class)
	@JoinColumn(name = "model_data_id")
	@Relationship(type = "MODEL_DATA")
	private Data model_data;
	
	/**
	 * 
	 */
	@Column
	@Description("External ID")
	@Property
	private String xml_id;
	
	/**
	 * 
	 */
	@ElementCollection
	@CollectionTable(name = "ir_ui_view_group_rel", joinColumns = {@JoinColumn(name = "view_id")})
	@Column(name = "group_id")
	@Description("Groups")
	@Transient
	private List<Integer> groups_id;
	
	/**
	 * 
	 */
	@ManyToMany(targetEntity = Groups.class)
	@JoinTable(name = "ir_ui_view_group_rel", joinColumns = {@JoinColumn(name = "view_id")}, inverseJoinColumns = {@JoinColumn(name = "group_id")})
	@Relationship(type = "GROUPS")
	private List<Groups> groups;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("primary")
	@Description("View inheritance mode")
	@Property
	private ViewInheritanceMode mode = ViewInheritanceMode.primary;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Property
	private Boolean active = true;
	
	/**
	 * 
	 */
	@Column(insertable = false, updatable = false)
	@Description("Model of the view")
	@Transient
	private Integer model_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Model.class)
	@JoinColumn(name = "model_id")
	@Relationship(type = "MODEL_OF_THE_VIEW")
	private String _model;

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

	public ViewType getType() {
		return type;
	}

	public void setType(ViewType type) {
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

	public Integer getInherit_id() {
		return inherit_id;
	}

	public void setInherit_id(Integer inherit_id) {
		this.inherit_id = inherit_id;
	}

	public List<Integer> getInherit_children_ids() {
		return inherit_children_ids;
	}

	public void setInherit_children_ids(List<Integer> inherit_children_ids) {
		this.inherit_children_ids = inherit_children_ids;
	}

	public Integer getModel_data_id() {
		return model_data_id;
	}

	public void setModel_data_id(Integer model_data_id) {
		this.model_data_id = model_data_id;
	}

	public String getXml_id() {
		return xml_id;
	}

	public void setXml_id(String xml_id) {
		this.xml_id = xml_id;
	}

	public List<Integer> getGroups_id() {
		return groups_id;
	}

	public void setGroups_id(List<Integer> groups_id) {
		this.groups_id = groups_id;
	}

	public ViewInheritanceMode getMode() {
		return mode;
	}

	public void setMode(ViewInheritanceMode mode) {
		this.mode = mode;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getModel_id() {
		return model_id;
	}

	public void setModel_id(Integer model_id) {
		this.model_id = model_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public View getInherit() {
		return inherit;
	}

	public void setInherit(View inherit) {
		this.inherit = inherit;
	}

	public List<View> getInherit_childrens() {
		return inherit_childrens;
	}

	public void setInherit_childrens(List<View> inherit_childrens) {
		this.inherit_childrens = inherit_childrens;
	}

	public Data getModel_data() {
		return model_data;
	}

	public void setModel_data(Data model_data) {
		this.model_data = model_data;
	}

	public List<Groups> getGroups() {
		return groups;
	}

	public void setGroups(List<Groups> groups) {
		this.groups = groups;
	}

	public String get_model() {
		return _model;
	}

	public void set_model(String _model) {
		this._model = _model;
	}
}
