package erp.schema.ir.actions;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

import erp.schema.ir.Model;
import erp.schema.ir.ModelFields;
import erp.schema.ir.ModelSelection;
import erp.schema.res.Groups;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_act_server")
public class Server extends Actions {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	private String name;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("ir.actions.server")
	private String type = "ir.actions.server";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Description("Usage")
	@DefaultValue("ir_actions_server")
	private String usage = "ir_actions_server";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Description("Type")
	@DefaultValue("object_write")
	private String state = "object_write";
	
	/**
	 * 
	 */
	@Column
	@Description(
			"When dealing with multiple actions, the execution order is " + 
            "based on the sequence. Low number means high priority."
			)
	@DefaultValue("5")
	private Integer sequence = 5;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = Model.class)
	@NotNull
	@Description("Model")
	private String model_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Model.class)
	@ElementCollection(targetClass = Model.class)
	@CollectionTable(name = "ir_model")
	@Description("Available Models")
	private List<String> available_model_ids;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Model Name")
	private String model_name;
	
	/**
	 * 
	 */
	@Column
	@Description("Python Code")
	private String code;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Server.class)
	@ElementCollection(targetClass = Server.class)
	@CollectionTable(joinColumns = {
			@JoinColumn(name = "server_id"),
			@JoinColumn(name = "action_id")
	})
	@Description("Child Actions")
	private List<String> child_ids;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Model.class)
	@Description("Record to Create")
	private String crud_model_id;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Target Model Name")
	private String crud_model_name;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = ModelFields.class)
	@Description("Link Field")
	private String link_field_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Groups.class)
	@ElementCollection(targetClass = Groups.class)
	@CollectionTable(name = "res_groups")
	@Description("Allowed Groups")
	private List<String> groups_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = ModelFields.class)
	@Description("Field to Update")
	private String update_field_id;
	
	/**
	 * 
	 */
	@Column
	@Description("Field to Update Path")
	private String update_path;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Model.class)
	private String update_related_model_id;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Enumerated(EnumType.STRING)
	private String update_field_type;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Description("Many2many Operations")
	@DefaultValue("add")
	private String update_m2m_operation = "add";
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Description("Boolean Value")
	@DefaultValue("true")
	private String update_boolean_value = "true";
	
	/**
	 * 
	 */
	@Column
	private String value;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Description("Value Type")
	@DefaultValue("value")
	private String evaluation_type = "value";
	
	/**
	 * 
	 */
	@Column
	@Description("Record")
	private String resource_ref;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = ModelSelection.class)
	@Description("Custom Value")
	private String selection_value;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	private String value_field_to_show;
	
	/**
	 * 
	 */
	@Column
	@Description("Webhook URL")
	private String webhook_url;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = ModelFields.class)
	@Description("Webhook Fields")
	private List<String> webhook_field_ids;
	
	/**
	 * 
	 */
	@Column
	@Description("Sample Payload")
	private String webhook_sample_payload;
}
