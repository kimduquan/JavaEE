package erp.base.schema.ir.ui;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Groups;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_ui_view")
@Description("View")
public class View {
	
	public enum ViewType {
		@Description("List")
		list,
		@Description("Form")
        form,
        @Description("Graph")
        graph,
        @Description("Pivot")
        pivot,
        @Description("Calendar")
        calendar,
        @Description("Kanban")
        kanban,
        @Description("Search")
        search,
        @Description("QWeb")
        qweb
	}
	
	public enum ViewInheritanceMode {
		@Description("Base view")
		primary,
		@Description("Extension View")
		extension
	}
	
	@Id
	private int id;
	
	@Column(nullable = false)
	@NotNull
	@Description("View Name")
	private String name;
	
	@Column
	private String model;
	
	@Column
	private String key;
	
	@Column(nullable = false)
	@NotNull
	@DefaultValue("16")
	@Description("Sequence")
	private Integer priority = 16;
	
	@Column
	@Enumerated(EnumType.STRING)
	@Description("View Type")
	private ViewType type;
	
	@Transient
	@Description("View Architecture")
	private String arch;
	
	@Transient
	@Description("Base View Architecture")
	private String arch_base;
	
	@Column
	@Description("Arch Blob")
	private String arch_db;
	
	@Column
	@Description("Arch Filename")
	private String arch_fs;
	
	@Column
	@Description("Modified Architecture")
	private Boolean arch_updated;
	
	@Column
	@Description("Previous View Architecture")
	private String arch_prev;
	
	@Transient
	private Integer inherit_id;

	@ManyToOne(targetEntity = View.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "inherit_id", referencedColumnName = "id")
	@Description("Inherited View")
	private View inherit;
	
	@Transient
	private List<Integer> inherit_children_ids;

	@OneToMany(targetEntity = View.class)
	@JoinColumn(name = "inherit_id", referencedColumnName = "id")
	@Description("Views which inherit from this one")
	private List<View> inherit_childrens;
	
	@Transient
	@Description("Model Data")
	private Integer model_data_id;
	
	@Transient
	@Description("External ID")
	private String xml_id;
	
	@Transient
	private List<Integer> groups_id;
	
	@ManyToMany(targetEntity = Groups.class)
	@JoinTable(name = "ir_ui_view_group_rel", joinColumns = {@JoinColumn(name = "view_id")}, inverseJoinColumns = {@JoinColumn(name = "group_id")})
	@Description("Groups")
	private List<Groups> groups;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("primary")
	@Description("View inheritance mode")
	private ViewInheritanceMode mode = ViewInheritanceMode.primary;
	
	@Column
	@DefaultValue("true")
	private Boolean active = true;
	
	@Transient
	@Description("Model of the view")
	private Integer model_id;

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

	public View getInherit() {
		return inherit;
	}

	public void setInherit(View inherit) {
		this.inherit = inherit;
	}

	public List<Integer> getInherit_children_ids() {
		return inherit_children_ids;
	}

	public void setInherit_children_ids(List<Integer> inherit_children_ids) {
		this.inherit_children_ids = inherit_children_ids;
	}

	public List<View> getInherit_childrens() {
		return inherit_childrens;
	}

	public void setInherit_childrens(List<View> inherit_childrens) {
		this.inherit_childrens = inherit_childrens;
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

	public List<Groups> getGroups() {
		return groups;
	}

	public void setGroups(List<Groups> groups) {
		this.groups = groups;
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
}
