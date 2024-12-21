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
	
	
}
