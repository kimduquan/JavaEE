package erp.base.schema.ir.embedded;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.Filters;
import erp.base.schema.ir.actions.Act_Window;
import erp.base.schema.res.Groups;
import erp.base.schema.res.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity(name = "EmbeddedActions")
@Table(name = "ir_embedded_actions")
@Description("Embedded Actions")
public class Actions {

	@Id
	private int id;
	
	@Column
	private String name;
	
	@Column
	private Integer sequence;
	
	@Transient
	private Integer parent_action_id;
	
	@ManyToOne(targetEntity = Act_Window.class, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "parent_action_id", nullable = false)
	@NotNull
	@Description("Parent Action")
	private Act_Window parent_action;
	
	@Column
	@Description("Active Parent Id")
	private Integer parent_res_id;
	
	@Column(nullable = false)
	@NotNull
	@Description("Active Parent Model")
	private String parent_res_model;
	
	@Transient
	private String action_id;
	
	@ManyToOne(targetEntity = erp.base.schema.ir.actions.Actions.class)
	@JoinColumn(name = "action_id")
	@Description("Action")
	private Actions action;
	
	@Column
	private String python_method;
	
	@Transient
	private String user_id;
	
	@ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@Description("User")
	private Users user;
	
	@Transient
	private boolean is_deletable;
	
	@Column
	@Description("Default View")
	private String default_view_mode;
	
	@Transient
	private List<Integer> filter_ids;
	
	@OneToMany(targetEntity = Filters.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "embedded_action_id")
	private List<Filters> filters;
	
	@Transient
	@Description("Embedded visibility")
	private boolean is_visible;
	
	@Column
	@DefaultValue("[]")
	private String domain = "[]";
	
	@Column
	@DefaultValue("{}")
	private String context = "{}";
	
	@Transient
	private List<Integer> groups_ids;
	
	@ManyToMany(targetEntity = Groups.class, fetch = FetchType.LAZY)
	@JoinTable(name = "ir_embedded_actions_res_groups_rel", joinColumns = {
			@JoinColumn(name = "ir_embedded_actions_id")
	}, inverseJoinColumns = {
			@JoinColumn(name = "res_groups_id")
	})
	private List<Groups> groups;

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

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Integer getParent_action_id() {
		return parent_action_id;
	}

	public void setParent_action_id(Integer parent_action_id) {
		this.parent_action_id = parent_action_id;
	}

	public Act_Window getParent_action() {
		return parent_action;
	}

	public void setParent_action(Act_Window parent_action) {
		this.parent_action = parent_action;
	}

	public Integer getParent_res_id() {
		return parent_res_id;
	}

	public void setParent_res_id(Integer parent_res_id) {
		this.parent_res_id = parent_res_id;
	}

	public String getParent_res_model() {
		return parent_res_model;
	}

	public void setParent_res_model(String parent_res_model) {
		this.parent_res_model = parent_res_model;
	}

	public String getAction_id() {
		return action_id;
	}

	public void setAction_id(String action_id) {
		this.action_id = action_id;
	}

	public Actions getAction() {
		return action;
	}

	public void setAction(Actions action) {
		this.action = action;
	}

	public String getPython_method() {
		return python_method;
	}

	public void setPython_method(String python_method) {
		this.python_method = python_method;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public boolean isIs_deletable() {
		return is_deletable;
	}

	public void setIs_deletable(boolean is_deletable) {
		this.is_deletable = is_deletable;
	}

	public String getDefault_view_mode() {
		return default_view_mode;
	}

	public void setDefault_view_mode(String default_view_mode) {
		this.default_view_mode = default_view_mode;
	}

	public List<Integer> getFilter_ids() {
		return filter_ids;
	}

	public void setFilter_ids(List<Integer> filter_ids) {
		this.filter_ids = filter_ids;
	}

	public List<Filters> getFilters() {
		return filters;
	}

	public void setFilters(List<Filters> filters) {
		this.filters = filters;
	}

	public boolean isIs_visible() {
		return is_visible;
	}

	public void setIs_visible(boolean is_visible) {
		this.is_visible = is_visible;
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

	public List<Integer> getGroups_ids() {
		return groups_ids;
	}

	public void setGroups_ids(List<Integer> groups_ids) {
		this.groups_ids = groups_ids;
	}

	public List<Groups> getGroups() {
		return groups;
	}

	public void setGroups(List<Groups> groups) {
		this.groups = groups;
	}
}
