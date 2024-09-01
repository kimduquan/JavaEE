package erp.base.schema.ir.act;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import erp.base.schema.ir.actions.Actions;
import erp.base.schema.ir.ui.View;
import erp.base.schema.res.groups.Groups;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
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
	@Column
	@DefaultValue("ir.actions.act_window")
	@Property
	private String type = "ir.actions.act_window";
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = View.class)
	@Description("View Ref.")
	@Property
	@Relationship(type = "VIEW")
	private String view_id;
	
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
	@Description("Target Window")
	@DefaultValue("current")
	@Property
	private String target = "current";
	
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
	@Column
	@OneToMany(targetEntity = ActWindowView.class)
	@ElementCollection(targetClass = ActWindowView.class)
	@CollectionTable(name = "ir_actions_act_window_view", joinColumns = {
			@JoinColumn(referencedColumnName = "act_window_id")
	})
	@Description("No of Views")
	@Property
	@Relationship(type = "VIEWS")
	private List<String> view_ids;
	
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
	@Column
	@ManyToMany(targetEntity = Groups.class)
	@ElementCollection(targetClass = Groups.class)
	@CollectionTable(joinColumns = {
			@JoinColumn(name = "act_id", referencedColumnName = "gid")
	})
	@Description("Groups")
	@Property
	@Relationship(type = "GROUPS")
	private List<String> groups_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = View.class)
	@Description("Search View Ref.")
	@Property
	private String search_view_id;
	
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

	public String getView_id() {
		return view_id;
	}

	public void setView_id(String view_id) {
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

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
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

	public List<String> getView_ids() {
		return view_ids;
	}

	public void setView_ids(List<String> view_ids) {
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

	public List<String> getGroups_id() {
		return groups_id;
	}

	public void setGroups_id(List<String> groups_id) {
		this.groups_id = groups_id;
	}

	public String getSearch_view_id() {
		return search_view_id;
	}

	public void setSearch_view_id(String search_view_id) {
		this.search_view_id = search_view_id;
	}

	public Boolean getFilter() {
		return filter;
	}

	public void setFilter(Boolean filter) {
		this.filter = filter;
	}
}
