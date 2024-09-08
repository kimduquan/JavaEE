package erp.base.schema.ir.actions;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import erp.base.schema.ir.ui.View;
import erp.base.schema.res.groups.Groups;
import erp.schema.util.EnumAttributeConverter;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
@Table(name = "ir_act_window")
@Description("Action Window")
@NodeEntity("Action Window")
public class ActWindow extends Actions {
	
	/**
	 * 
	 */
	public enum TargetWindow {
		/**
		 * 
		 */
		current,
		/**
		 * 
		 */
		_new,
		/**
		 * 
		 */
		inline,
		/**
		 * 
		 */
		fullscreen,
		/**
		 * 
		 */
		main
	}
	
	/**
	 * 
	 */
	public class TargetWindowAttributeConverter extends EnumAttributeConverter<TargetWindow> {
		/**
		 * 
		 */
		public TargetWindowAttributeConverter() {
			super(TargetWindow.class, "_", null, null);
		}
	}

	/**
	 * 
	 */
	@Column
	@DefaultValue("ir.actions.act_window")
	@Property
	private String type = "ir.actions.act_window";
	
	/**
	 * 
	 */
	@Column(insertable = false, updatable = false)
	@Description("View Ref.")
	@Transient
	private Integer view_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = View.class)
	@JoinColumn(name = "view_id")
	@Relationship(type = "VIEW_REF")
	private View view;
	
	/**
	 * 
	 */
	@Column
	@Description("Domain Value")
	@Property
	private String domain;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Context Value")
	@DefaultValue("{}")
	@Property
	private String context = "{}";
	
	/**
	 * 
	 */
	@Column
	@Description("Record ID")
	@Property
	private Integer res_id;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Destination Model")
	@Property
	private String res_model;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Convert(converter = TargetWindowAttributeConverter.class)
	@Description("Target Window")
	@DefaultValue("current")
	@Property
	private TargetWindow target = TargetWindow.current;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("")
	@DefaultValue("tree,form")
	@Property
	private String view_mode = "tree,form";
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("kanban")
	@Description("First view mode in mobile and small screen environments (default='kanban'). If it can't be found among available view modes, the same mode as for wider screens is used)")
	@Property
	private String mobile_view_mode = "kanban";
	
	/**
	 * 
	 */
	@Column
	@Description("Action Usage")
	@Property
	private String usage;
	
	/**
	 * 
	 */
	@ElementCollection
	@CollectionTable(name = "ir_act_window_view", joinColumns = {
			@JoinColumn(name = "act_window_id")
	})
	@Description("No of Views")
	@Transient
	private List<Integer> view_ids;

	/**
	 * 
	 */
	@OneToMany(targetEntity = ActWindowView.class)
	@JoinTable(name = "ir_act_window_view", joinColumns = {
			@JoinColumn(name = "act_window_id")
	})
	@Relationship(type = "NO_OF_VIEWS")
	private List<ActWindowView> _views;
	
	/**
	 * 
	 */
	@Column
	@Description(
			"This function field computes the ordered list of views that should be enabled " +
            "when displaying the result of an action, federating view mode, views and " +
            "reference view. The result is returned as an ordered list of pairs (view_id,view_mode)."
            )
	@Property
	private byte[] views;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("80")
	@Description("Default limit for the list view")
	@Property
	private Integer limit = 80;
	
	/**
	 * 
	 */
	@ElementCollection
	@CollectionTable(name = "ir_act_window_group_rel", joinColumns = {
			@JoinColumn(name = "act_id")
	})
	@Column(name = "gid")
	@Description("Groups")
	@Transient
	private List<Integer> groups_id;

	/**
	 * 
	 */
	@ManyToMany(targetEntity = Groups.class)
	@JoinTable(name = "ir_act_window_group_rel", joinColumns = {@JoinColumn(name = "act_id")}, inverseJoinColumns = {@JoinColumn(name = "gid")})
	@Relationship(type = "GROUPS")
	private List<Groups> groups;
	
	/**
	 * 
	 */
	@Column(insertable = false, updatable = false)
	@Description("Search View Ref.")
	@Transient
	private Integer search_view_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = View.class)
	@JoinColumn(name = "search_view_id")
	@Relationship(type = "SEARCH_VIEW_REF")
	private View search_view;
	
	/**
	 * 
	 */
	@Column
	@Property
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

	public Integer getSearch_view_id() {
		return search_view_id;
	}

	public void setSearch_view_id(Integer search_view_id) {
		this.search_view_id = search_view_id;
	}

	public Boolean getFilter() {
		return filter;
	}

	public void setFilter(Boolean filter) {
		this.filter = filter;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public List<ActWindowView> get_views() {
		return _views;
	}

	public void set_views(List<ActWindowView> _views) {
		this._views = _views;
	}

	public List<Groups> getGroups() {
		return groups;
	}

	public void setGroups(List<Groups> groups) {
		this.groups = groups;
	}

	public View getSearch_view() {
		return search_view;
	}

	public void setSearch_view(View search_view) {
		this.search_view = search_view;
	}
}
