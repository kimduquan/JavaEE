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
	
	@Transient
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
	
	@Transient
	@Description("Sample Payload")
	private String webhook_sample_payload;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Usage getUsage() {
		return usage;
	}

	public void setUsage(Usage usage) {
		this.usage = usage;
	}

	public Type getState() {
		return state;
	}

	public void setState(Type state) {
		this.state = state;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Integer getModel_id() {
		return model_id;
	}

	public void setModel_id(Integer model_id) {
		this.model_id = model_id;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public List<Integer> getAvailable_model_ids() {
		return available_model_ids;
	}

	public void setAvailable_model_ids(List<Integer> available_model_ids) {
		this.available_model_ids = available_model_ids;
	}

	public String getModel_name() {
		return model_name;
	}

	public void setModel_name(String model_name) {
		this.model_name = model_name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Integer> getChild_ids() {
		return child_ids;
	}

	public void setChild_ids(List<Integer> child_ids) {
		this.child_ids = child_ids;
	}

	public List<Server> getChilds() {
		return childs;
	}

	public void setChilds(List<Server> childs) {
		this.childs = childs;
	}

	public Integer getCrud_model_id() {
		return crud_model_id;
	}

	public void setCrud_model_id(Integer crud_model_id) {
		this.crud_model_id = crud_model_id;
	}

	public Model getCrud_model() {
		return crud_model;
	}

	public void setCrud_model(Model crud_model) {
		this.crud_model = crud_model;
	}

	public String getCrud_model_name() {
		return crud_model_name;
	}

	public void setCrud_model_name(String crud_model_name) {
		this.crud_model_name = crud_model_name;
	}

	public Integer getLink_field_id() {
		return link_field_id;
	}

	public void setLink_field_id(Integer link_field_id) {
		this.link_field_id = link_field_id;
	}

	public Fields getLink_field() {
		return link_field;
	}

	public void setLink_field(Fields link_field) {
		this.link_field = link_field;
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

	public Integer getUpdate_field_id() {
		return update_field_id;
	}

	public void setUpdate_field_id(Integer update_field_id) {
		this.update_field_id = update_field_id;
	}

	public Fields getUpdate_field() {
		return update_field;
	}

	public void setUpdate_field(Fields update_field) {
		this.update_field = update_field;
	}

	public String getUpdate_path() {
		return update_path;
	}

	public void setUpdate_path(String update_path) {
		this.update_path = update_path;
	}

	public Integer getUpdate_related_model_id() {
		return update_related_model_id;
	}

	public void setUpdate_related_model_id(Integer update_related_model_id) {
		this.update_related_model_id = update_related_model_id;
	}

	public Model getUpdate_related_model() {
		return update_related_model;
	}

	public void setUpdate_related_model(Model update_related_model) {
		this.update_related_model = update_related_model;
	}

	public String getUpdate_field_type() {
		return update_field_type;
	}

	public void setUpdate_field_type(String update_field_type) {
		this.update_field_type = update_field_type;
	}

	public Many2ManyOperations getUpdate_m2m_operation() {
		return update_m2m_operation;
	}

	public void setUpdate_m2m_operation(Many2ManyOperations update_m2m_operation) {
		this.update_m2m_operation = update_m2m_operation;
	}

	public String getUpdate_boolean_value() {
		return update_boolean_value;
	}

	public void setUpdate_boolean_value(String update_boolean_value) {
		this.update_boolean_value = update_boolean_value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public EvaluationType getEvaluation_type() {
		return evaluation_type;
	}

	public void setEvaluation_type(EvaluationType evaluation_type) {
		this.evaluation_type = evaluation_type;
	}

	public String getResource_ref() {
		return resource_ref;
	}

	public void setResource_ref(String resource_ref) {
		this.resource_ref = resource_ref;
	}

	public Selection getSelection_value() {
		return selection_value;
	}

	public void setSelection_value(Selection selection_value) {
		this.selection_value = selection_value;
	}

	public ValueFieldToShow getValue_field_to_show() {
		return value_field_to_show;
	}

	public void setValue_field_to_show(ValueFieldToShow value_field_to_show) {
		this.value_field_to_show = value_field_to_show;
	}

	public String getWebhook_url() {
		return webhook_url;
	}

	public void setWebhook_url(String webhook_url) {
		this.webhook_url = webhook_url;
	}

	public List<Integer> getWebhook_field_ids() {
		return webhook_field_ids;
	}

	public void setWebhook_field_ids(List<Integer> webhook_field_ids) {
		this.webhook_field_ids = webhook_field_ids;
	}

	public List<Fields> getWebhook_fields() {
		return webhook_fields;
	}

	public void setWebhook_fields(List<Fields> webhook_fields) {
		this.webhook_fields = webhook_fields;
	}

	public String getWebhook_sample_payload() {
		return webhook_sample_payload;
	}

	public void setWebhook_sample_payload(String webhook_sample_payload) {
		this.webhook_sample_payload = webhook_sample_payload;
	}
}
