package erp.base.schema.ir;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.actions.Actions;
import erp.base.schema.res.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_filters")
@Description("Filters")
public class Filters {
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	@Description("Filter Name")
	private String name;
	
	@Transient
	private Integer user_id;

	@ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@Description("User")
	private Users user;
	
	@Column(nullable = false)
	@NotNull
	@DefaultValue("[]")
	private String domain = "[]";
	
	@Column(nullable = false)
	@NotNull
	@DefaultValue("{}")
	private String context = "{}";
	
	@Column(nullable = false)
	@NotNull
	@DefaultValue("[]")
	private String sort = "[]";
	
	@Column(nullable = false)
	@NotNull
	@Description("Model")
	private String model_id;
	
	@Column
	@Description("Default Filter")
	private Boolean is_default;
	
	@Transient
	private Integer action_id;

	@ManyToOne(targetEntity = Actions.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "action_id")
	@Description("Action")
	private Actions action;
	
	@Transient
	private Integer embedded_action_id;
	
	@ManyToOne(targetEntity = erp.base.schema.ir.embedded.Actions.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "embedded_action_id")
	private erp.base.schema.ir.embedded.Actions embedded_action;
	
	@Column
	private Integer embedded_parent_res_id;
	
	@Column
	@DefaultValue("true")
	private Boolean active = true;

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

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

	public Boolean getIs_default() {
		return is_default;
	}

	public void setIs_default(Boolean is_default) {
		this.is_default = is_default;
	}

	public Integer getAction_id() {
		return action_id;
	}

	public void setAction_id(Integer action_id) {
		this.action_id = action_id;
	}

	public Actions getAction() {
		return action;
	}

	public void setAction(Actions action) {
		this.action = action;
	}

	public Integer getEmbedded_action_id() {
		return embedded_action_id;
	}

	public void setEmbedded_action_id(Integer embedded_action_id) {
		this.embedded_action_id = embedded_action_id;
	}

	public erp.base.schema.ir.embedded.Actions getEmbedded_action() {
		return embedded_action;
	}

	public void setEmbedded_action(erp.base.schema.ir.embedded.Actions embedded_action) {
		this.embedded_action = embedded_action;
	}

	public Integer getEmbedded_parent_res_id() {
		return embedded_parent_res_id;
	}

	public void setEmbedded_parent_res_id(Integer embedded_parent_res_id) {
		this.embedded_parent_res_id = embedded_parent_res_id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
}
