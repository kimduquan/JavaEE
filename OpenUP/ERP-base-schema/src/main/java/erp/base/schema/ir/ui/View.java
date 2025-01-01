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
}
