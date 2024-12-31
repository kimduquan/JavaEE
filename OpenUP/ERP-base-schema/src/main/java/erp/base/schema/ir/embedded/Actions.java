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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_embedded_actions")
@Description("Embedded Actions")
public class Actions {

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
			@JoinColumn(name = "res_groups_id", referencedColumnName = "ir_embedded_actions_id")
	})
	private List<Groups> groups;
}
