package erp.base.schema.ir.actions;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import erp.base.schema.ir.model.Model;
import erp.base.schema.ir.model.Fields;
import erp.base.schema.ir.model.Selection;
import erp.base.schema.res.groups.Groups;
import erp.schema.util.NameAttributeConverter;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_act_server")
@Description("Server Actions")
@NodeEntity("Server Actions")
public class Server extends Actions {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Property
	@Convert(NameAttributeConverter.class)
	private String name;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("ir.actions.server")
	@Property
	private String type = "ir.actions.server";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Description("Usage")
	@DefaultValue("ir_actions_server")
	@Property
	private String usage = "ir_actions_server";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Description("Type")
	@DefaultValue("object_write")
	@Property
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
	@Property
	private Integer sequence = 5;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Model")
	@Transient
	private String model_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Model.class)
	@JoinColumn(name = "model_id", nullable = false)
	@NotNull
	@Relationship(type = "MODEL")
	private Model model;
	
	/**
	 * 
	 */
	@ElementCollection(targetClass = Model.class)
	@CollectionTable(name = "ir_model")
	@Description("Available Models")
	@Transient
	private List<String> available_model_ids;

	/**
	 * 
	 */
	@ManyToMany(targetEntity = Model.class)
	@JoinTable(name = "ir_model")
	@Relationship(type = "AVAILABLE_MODELS")
	private List<Model> available_models;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Model Name")
	@Property
	private String model_name;
	
	/**
	 * 
	 */
	@Column
	@Description("Python Code")
	@Property
	private String code;
	
	/**
	 * 
	 */
	@ElementCollection(targetClass = Server.class)
	@CollectionTable(name = "rel_server_actions", joinColumns = {
			@JoinColumn(name = "server_id", referencedColumnName = "action_id")
	})
	@Description("Child Actions")
	@Transient
	private List<String> child_ids;

	/**
	 * 
	 */
	@ManyToMany(targetEntity = Server.class)
	@JoinTable(name = "rel_server_actions", joinColumns = {
			@JoinColumn(name = "server_id", referencedColumnName = "action_id")
	})
	@Relationship(type = "CHILD_ACTIONS")
	private List<Server> childs;
	
	/**
	 * 
	 */
	@Column
	@Description("Record to Create")
	@Transient
	private String crud_model_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Model.class)
	@JoinColumn(name = "crud_model_id")
	@Relationship(type = "CRUD_MODEL")
	private Model crud_model;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Target Model Name")
	@Property
	private String crud_model_name;
	
	/**
	 * 
	 */
	@Column
	@Description("Link Field")
	@Transient
	private String link_field_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Fields.class)
	@JoinColumn(name = "link_field_id")
	@Relationship(type = "LINK_FIELD")
	private String link_field;
	
	/**
	 * 
	 */
	@ElementCollection(targetClass = Groups.class)
	@CollectionTable(name = "ir_act_server_group_rel", joinColumns = {
			@JoinColumn(name = "act_id", referencedColumnName = "gid")
	})
	@Description("Allowed Groups")
	@Transient
	private List<String> groups_id;
	
	/**
	 * 
	 */
	@ManyToMany(targetEntity = Groups.class)
	@JoinTable(name = "ir_act_server_group_rel", joinColumns = {
			@JoinColumn(name = "act_id", referencedColumnName = "gid")
	})
	@Relationship(type = "GROUPS")
	private List<Groups> groups;
	
	/**
	 * 
	 */
	@Column
	@Description("Field to Update")
	@Transient
	private String update_field_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Fields.class)
	@JoinColumn(name = "update_field_id")
	@Relationship(type = "UPDATE_FIELD")
	private Fields update_field;
	
	/**
	 * 
	 */
	@Column
	@Description("Field to Update Path")
	@Property
	private String update_path;
	
	/**
	 * 
	 */
	@Column
	@Transient
	private String update_related_model_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Model.class)
	@JoinColumn(name = "update_related_model_id")
	@Relationship(type = "UPDATE_RELATED_MODEL")
	private Model update_related_model;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Enumerated(EnumType.STRING)
	@Property
	private String update_field_type;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Description("Many2many Operations")
	@DefaultValue("add")
	@Property
	private String update_m2m_operation = "add";
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Description("Boolean Value")
	@DefaultValue("true")
	@Property
	private String update_boolean_value = "true";
	
	/**
	 * 
	 */
	@Column
	@Property
	private String value;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Description("Value Type")
	@DefaultValue("value")
	@Property
	private String evaluation_type = "value";
	
	/**
	 * 
	 */
	@Column
	@Description("Record")
	@Property
	private String resource_ref;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Selection.class)
	@Description("Custom Value")
	@Relationship(type = "CUSTOM_VALUE")
	private Selection selection_value;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Property
	private String value_field_to_show;
	
	/**
	 * 
	 */
	@Column
	@Description("Webhook URL")
	@Property
	private String webhook_url;
	
	/**
	 * 
	 */
	@ElementCollection(targetClass = Fields.class)
	@CollectionTable(name = "ir_act_server_webhook_field_rel", joinColumns = {
			@JoinColumn(name = "server_id", referencedColumnName = "field_id")
	})
	@Description("Webhook Fields")
	@Transient
	private List<String> webhook_field_ids;

	/**
	 * 
	 */
	@ManyToMany(targetEntity = Fields.class)
	@JoinTable(name = "ir_act_server_webhook_field_rel", joinColumns = {
			@JoinColumn(name = "server_id", referencedColumnName = "field_id")
	})
	@Relationship(type = "WEBHOOK_FIELDS")
	private List<Fields> webhook_fields;
	
	/**
	 * 
	 */
	@Column
	@Description("Sample Payload")
	@Property
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

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

	public List<String> getAvailable_model_ids() {
		return available_model_ids;
	}

	public void setAvailable_model_ids(List<String> available_model_ids) {
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

	public List<String> getChild_ids() {
		return child_ids;
	}

	public void setChild_ids(List<String> child_ids) {
		this.child_ids = child_ids;
	}

	public String getCrud_model_id() {
		return crud_model_id;
	}

	public void setCrud_model_id(String crud_model_id) {
		this.crud_model_id = crud_model_id;
	}

	public String getCrud_model_name() {
		return crud_model_name;
	}

	public void setCrud_model_name(String crud_model_name) {
		this.crud_model_name = crud_model_name;
	}

	public String getLink_field_id() {
		return link_field_id;
	}

	public void setLink_field_id(String link_field_id) {
		this.link_field_id = link_field_id;
	}

	public List<String> getGroups_id() {
		return groups_id;
	}

	public void setGroups_id(List<String> groups_id) {
		this.groups_id = groups_id;
	}

	public String getUpdate_field_id() {
		return update_field_id;
	}

	public void setUpdate_field_id(String update_field_id) {
		this.update_field_id = update_field_id;
	}

	public String getUpdate_path() {
		return update_path;
	}

	public void setUpdate_path(String update_path) {
		this.update_path = update_path;
	}

	public String getUpdate_related_model_id() {
		return update_related_model_id;
	}

	public void setUpdate_related_model_id(String update_related_model_id) {
		this.update_related_model_id = update_related_model_id;
	}

	public String getUpdate_field_type() {
		return update_field_type;
	}

	public void setUpdate_field_type(String update_field_type) {
		this.update_field_type = update_field_type;
	}

	public String getUpdate_m2m_operation() {
		return update_m2m_operation;
	}

	public void setUpdate_m2m_operation(String update_m2m_operation) {
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

	public String getEvaluation_type() {
		return evaluation_type;
	}

	public void setEvaluation_type(String evaluation_type) {
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

	public String getValue_field_to_show() {
		return value_field_to_show;
	}

	public void setValue_field_to_show(String value_field_to_show) {
		this.value_field_to_show = value_field_to_show;
	}

	public String getWebhook_url() {
		return webhook_url;
	}

	public void setWebhook_url(String webhook_url) {
		this.webhook_url = webhook_url;
	}

	public List<String> getWebhook_field_ids() {
		return webhook_field_ids;
	}

	public void setWebhook_field_ids(List<String> webhook_field_ids) {
		this.webhook_field_ids = webhook_field_ids;
	}

	public String getWebhook_sample_payload() {
		return webhook_sample_payload;
	}

	public void setWebhook_sample_payload(String webhook_sample_payload) {
		this.webhook_sample_payload = webhook_sample_payload;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public List<Model> getAvailable_models() {
		return available_models;
	}

	public void setAvailable_models(List<Model> available_models) {
		this.available_models = available_models;
	}

	public List<Server> getChilds() {
		return childs;
	}

	public void setChilds(List<Server> childs) {
		this.childs = childs;
	}

	public Model getCrud_model() {
		return crud_model;
	}

	public void setCrud_model(Model crud_model) {
		this.crud_model = crud_model;
	}

	public String getLink_field() {
		return link_field;
	}

	public void setLink_field(String link_field) {
		this.link_field = link_field;
	}

	public List<Groups> getGroups() {
		return groups;
	}

	public void setGroups(List<Groups> groups) {
		this.groups = groups;
	}

	public Fields getUpdate_field() {
		return update_field;
	}

	public void setUpdate_field(Fields update_field) {
		this.update_field = update_field;
	}

	public Model getUpdate_related_model() {
		return update_related_model;
	}

	public void setUpdate_related_model(Model update_related_model) {
		this.update_related_model = update_related_model;
	}

	public List<Fields> getWebhook_fields() {
		return webhook_fields;
	}

	public void setWebhook_fields(List<Fields> webhook_fields) {
		this.webhook_fields = webhook_fields;
	}
}
