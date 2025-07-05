package erp.base.schema.ir.actions;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.actions.act_window.View;
import erp.base.schema.res.Groups;
import erp.schema.util.EnumAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_act_window")
@Description("Action Window")
public class Act_Window extends Actions {
	
	public enum TargetWindow {
		@Description("Current Window")
		current,
		@Description("New Window")
		new_,
		@Description("Full Screen")
		fullscreen,
		@Description("Main action of Current Window")
		main
	}
	
	public class TargetWindowAttributeConverter extends EnumAttributeConverter<TargetWindow> {
		public TargetWindowAttributeConverter() {
			super(TargetWindow.class, "_", null, null);
		}
	}

	@Column
	@DefaultValue("ir.actions.act_window")
	private String type = "ir.actions.act_window";
	
	@Transient
	private Integer view_id;
	
	@ManyToOne(targetEntity = erp.base.schema.ir.ui.View.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "view_id")
	@Description("View Ref.")
	private erp.base.schema.ir.ui.View view;
	
	@Column
	@Description("Domain Value")
	private String domain;
	
	@Column(nullable = false)
	@NotNull
	@Description("Context Value")
	@DefaultValue("{}")
	private String context = "{}";
	
	@Column
	@Description("Record ID")
	private Integer res_id;
	
	@Column(nullable = false)
	@NotNull
	@Description("Destination Model")
	private String res_model;
	
	@Column
	@Enumerated(EnumType.STRING)
	@Convert(converter = TargetWindowAttributeConverter.class)
	@Description("Target Window")
	@DefaultValue("current")
	private TargetWindow target = TargetWindow.current;
	
	@Column(nullable = false)
	@NotNull
	@DefaultValue("list,form")
	private String view_mode = "list,form";
	
	@Column
	@DefaultValue("kanban")
	private String mobile_view_mode = "kanban";
	
	@Column
	@Description("Action Usage")
	private String usage;
	
	@Transient
	private List<Integer> view_ids;

	@OneToMany(targetEntity = View.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "act_window_id")
	@Description("No of Views")
	private List<View> views_;
	
	@Transient
	private byte[] views;
	
	@Column
	@DefaultValue("80")
	@Description("Default limit for the list view")
	private Integer limit = 80;
	
	@Transient
	private List<Integer> groups_id;

	@ManyToMany(targetEntity = Groups.class, fetch = FetchType.LAZY)
	@JoinTable(name = "ir_act_window_group_rel", joinColumns = {@JoinColumn(name = "act_id")}, inverseJoinColumns = {@JoinColumn(name = "gid")})
	@Description("Groups")
	private List<Groups> groups;
	
	@Transient
	private Integer search_view_id;
	
	@ManyToOne(targetEntity = View.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "search_view_id")
	@Description("Search View Ref.")
	private View search_view;
	
	@Column
	private Boolean filter;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getView_id() {
		return view_id;
	}

	public void setView_id(Integer view_id) {
		this.view_id = view_id;
	}

	public erp.base.schema.ir.ui.View getView() {
		return view;
	}

	public void setView(erp.base.schema.ir.ui.View view) {
		this.view = view;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Integer getRes_id() {
		return res_id;
	}

	public void setRes_id(Integer res_id) {
		this.res_id = res_id;
	}

	public String getRes_model() {
		return res_model;
	}

	public void setRes_model(String res_model) {
		this.res_model = res_model;
	}

	public TargetWindow getTarget() {
		return target;
	}

	public void setTarget(TargetWindow target) {
		this.target = target;
	}

	public String getView_mode() {
		return view_mode;
	}

	public void setView_mode(String view_mode) {
		this.view_mode = view_mode;
	}

	public String getMobile_view_mode() {
		return mobile_view_mode;
	}

	public void setMobile_view_mode(String mobile_view_mode) {
		this.mobile_view_mode = mobile_view_mode;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public List<Integer> getView_ids() {
		return view_ids;
	}

	public void setView_ids(List<Integer> view_ids) {
		this.view_ids = view_ids;
	}

	public List<View> getViews_() {
		return views_;
	}

	public void setViews_(List<View> views_) {
		this.views_ = views_;
	}

	public byte[] getViews() {
		return views;
	}

	public void setViews(byte[] views) {
		this.views = views;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
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

	public Integer getSearch_view_id() {
		return search_view_id;
	}

	public void setSearch_view_id(Integer search_view_id) {
		this.search_view_id = search_view_id;
	}

	public View getSearch_view() {
		return search_view;
	}

	public void setSearch_view(View search_view) {
		this.search_view = search_view;
	}

	public Boolean getFilter() {
		return filter;
	}

	public void setFilter(Boolean filter) {
		this.filter = filter;
	}
}
