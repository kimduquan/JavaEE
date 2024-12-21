package erp.base.schema.ir.actions;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

import erp.base.schema.ir.Model;
import erp.base.schema.ir.model.Fields;
import erp.base.schema.ir.model.Selection;
import erp.base.schema.res.Groups;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_act_server")
@Description("Server Actions")
public class Server extends Actions {
	
	public enum Usage {
		@Description("Server Action")
		ir_actions_server,
		@Description("Scheduled Action")
    	ir_cron
	}
	
	public enum Type {
		@Description("Update Record")
		object_write,
		@Description("Create Record")
        object_create,
        @Description("Execute Code")
        code,
        @Description("Send Webhook Notification")
        webhook,
        @Description("Execute Existing Actions")
        multi
	}
	
	public enum Many2ManyOperations {
		@Description("Adding")
		add,
		@Description("Removing")
        remove,
        @Description("Setting it to")
        set,
        @Description("Clearing it")
        clear
	}
	
	public enum EvaluationType {
		@Description("Update")
		value,
		@Description("Compute")
        equation
	}
	
	public enum ValueFieldToShow {
		@Description("value")
		value,
		@Description("reference")
        resource_ref,
        @Description("update_boolean_value")
        update_boolean_value,
        @Description("selection_value")
        selection_value
	}

	@Column(nullable = false)
	@NotNull
	private String name;
	
	@Column
	@DefaultValue("ir.actions.server")
	private String type = "ir.actions.server";
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Description("Usage")
	@DefaultValue("ir_actions_server")
	private Usage usage = Usage.ir_actions_server;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Description("Type")
	@DefaultValue("object_write")
	private Type state = Type.object_write;
	
	@Column
	@DefaultValue("5")
	private Integer sequence = 5;
	
	@Transient
	private Integer model_id;

	@ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "model_id", nullable = false)
	@NotNull
	@Description("Model")
	private Model model;
	
	@Transient
	@Description("Available Models")
	private List<Integer> available_model_ids;
	
	@Column(updatable = false)
	@Description("Model Name")
	private String model_name;
	
	@Column
	@Description("Python Code")
	private String code;
	
	@Transient
	private List<Integer> child_ids;

	@ManyToMany(targetEntity = Server.class)
	@JoinTable(name = "rel_server_actions", joinColumns = {@JoinColumn(name = "server_id")}, inverseJoinColumns = {@JoinColumn(name = "action_id")})
	@Description("Child Actions")
	private List<Server> childs;
	
	@Transient
	private Integer crud_model_id;

	@ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "crud_model_id")
	@Description("Record to Create")
	private Model crud_model;
	
	@Transient
	@Description("Target Model Name")
	private String crud_model_name;
	
	@Transient
	private Integer link_field_id;

	@ManyToOne(targetEntity = Fields.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "link_field_id")
	@Description("Link Field")
	private Fields link_field;
	
	@Transient
	private List<Integer> groups_id;
	
	@ManyToMany(targetEntity = Groups.class)
	@JoinTable(name = "ir_act_server_group_rel", joinColumns = {@JoinColumn(name = "act_id")}, inverseJoinColumns = {@JoinColumn(name = "gid")})
	@Description("Allowed Groups")
	private List<Groups> groups;
	
	@Transient
	private Integer update_field_id;
	
	@ManyToOne(targetEntity = Fields.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "update_field_id")
	@Description("Field to Update")
	private Fields update_field;
	
	@Column
	@Description("Field to Update Path")
	private String update_path;
	
	@Transient
	private Integer update_related_model_id;
	
	@ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "update_related_model_id")
	private Model update_related_model;
	
	@Transient
	private String update_field_type;
	
	@Column
	@Enumerated(EnumType.STRING)
	@Description("Many2many Operations")
	@DefaultValue("add")
	private Many2ManyOperations update_m2m_operation = Many2ManyOperations.add;
	
	@Column
	@Description("Boolean Value")
	@DefaultValue("true")
	private String update_boolean_value = "true";
	
	@Column
	private String value;
	
	@Column
	@Enumerated(EnumType.STRING)
	@Description("Value Type")
	@DefaultValue("value")
	private EvaluationType evaluation_type = EvaluationType.value;
	
	@Column
	@Description("Record")
	private String resource_ref;
	
	@ManyToOne(targetEntity = Selection.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "selection_value")
	@Description("Custom Value")
	private Selection selection_value;
	
	@Column
	@Enumerated(EnumType.STRING)
	private ValueFieldToShow value_field_to_show;
	
	@Column
	@Description("Webhook URL")
	private String webhook_url;
	
	@Transient
	private List<Integer> webhook_field_ids;

	@ManyToMany(targetEntity = Fields.class)
	@JoinTable(name = "ir_act_server_webhook_field_rel", joinColumns = {@JoinColumn(name = "server_id")}, inverseJoinColumns = {@JoinColumn(name = "field_id")})
	@Description("Webhook Fields")
	private List<Fields> webhook_fields;
	
	@Description("Sample Payload")
	private String webhook_sample_payload;
}
