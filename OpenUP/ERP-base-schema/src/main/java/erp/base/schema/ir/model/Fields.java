package erp.base.schema.ir.model;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import erp.base.schema.res.groups.Groups;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_model_fields")
@Description("Fields")
@NodeEntity("Fields")
public class Fields {
	
	/**
	 * 
	 */
	public enum Type {
		/**
		 * 
		 */
		manual,
		/**
		 * 
		 */
		base
	}
	
	/**
	 * 
	 */
	public enum OnDelete {
		/**
		 * 
		 */
		cascade,
		/**
		 * 
		 */
		set_null,
		/**
		 * 
		 */
		restrict
	}
	
	/**
	 * 
	 */
	@jakarta.persistence.Id
	@Id
	private int id;

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("x_")
	@Description("Field Name")
	@Property
	private String name = "x_";
	
	/**
	 * 
	 */
	@Column
	@Property
	private String complete_name;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Model Name")
	@Property
	private String model;
	
	/**
	 * 
	 */
	@Column
	@Description("Related Model")
	@Property
	private String relation;
	
	/**
	 * 
	 */
	@Column
	@Description("For one2many fields, the field on the target model that implement the opposite many2one relationship")
	@Property
	private String relation_field;
	
	/**
	 * 
	 */
	@Column
	@Description("Relation field")
	@Transient
	private Integer relation_field_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Fields.class)
	@JoinColumn(name = "relation_field_id")
	@Relationship(type = "RELATION_FIELD")
	private Fields _relation_field;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Model")
	@Transient
	private Integer model_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Model.class)
	@JoinColumn(name = "model_id", nullable = false)
	@NotNull
	@Relationship(type = "MODEL")
	private Model _model;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("")
	@Description("Field Label")
	@Property
	private String field_description = "";
	
	/**
	 * 
	 */
	@Column
	@Description("Field Help")
	@Property
	private String help;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Field Type")
	@Property
	private String ttype;
	
	/**
	 * 
	 */
	@Column
	@Description("Selection Options (Deprecated)")
	@Property
	private String selection;
	
	/**
	 * 
	 */
	@ElementCollection
	@CollectionTable(name = "ir_model_fields_selection", joinColumns = {
			@JoinColumn(name = "field_id")
	})
	@Description("Selection Options")
	@Transient
	private List<Integer> selection_ids;

	/**
	 * 
	 */
	@OneToMany(targetEntity = Selection.class, mappedBy = "field_id")
	@Relationship(type = "SELECTION_OPTIONS")
	private List<Selection> selections;
	
	/**
	 * 
	 */
	@Column
	@Description("Copied")
	@Property
	private Boolean copied;
	
	/**
	 * 
	 */
	@Column
	@Description("Related Field")
	@Property
	private String related;
	
	/**
	 * 
	 */
	@Column
	@Description("Related field")
	@Transient
	private Integer related_field_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Fields.class)
	@JoinColumn(name = "related_field_id")
	@Relationship(type = "RELATED_FIELD")
	private Fields related_field;
	
	/**
	 * 
	 */
	@Column
	@Property
	private Boolean required;
	
	/**
	 * 
	 */
	@Column
	@Property
	private Boolean readonly;
	
	/**
	 * 
	 */
	@Column
	@Description("Indexed")
	@Property
	private Boolean index;
	
	/**
	 * 
	 */
	@Column
	@Description("Translatable")
	@Property
	private Boolean translate;
	
	/**
	 * 
	 */
	@Column
	@Property
	private Integer size;
	
	/**
	 * 
	 */
	@Column(nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("manual")
	@Description("Type")
	@Property
	private Type state = Type.manual;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("set null")
	@Description("On Delete")
	@Property
	private OnDelete on_delete = OnDelete.set_null;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("[]")
	@Description("The optional domain to restrict possible values for relationship fields, specified as a Python expression defining a list of triplets. For example: [('color','=','red')]")
	@Property
	private String domain = "[]";
	
	/**
	 * 
	 */
	@ManyToMany(targetEntity = Groups.class)
	@JoinTable(name = "ir_model_fields_group_rel", joinColumns = {@JoinColumn(name = "field_id")}, inverseJoinColumns = {@JoinColumn(name = "group_id")})
	@Relationship(type = "GROUPS")
	private List<Groups> groups;
	
	/**
	 * 
	 */
	@Column
	@Description("Expand Groups")
	@Property
	private Boolean group_expand;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Property
	private Boolean selectable = true;
	
	/**
	 * 
	 */
	@Column
	@Description("In Apps")
	@Property
	private String modules;
	
	/**
	 * 
	 */
	@Column
	@Description("Used for custom many2many fields to define a custom relation table name")
	@Property
	private String relation_table;
	
	/**
	 * 
	 */
	@Column
	@Description("Column 1")
	@Property
	private String column1;
	
	/**
	 * 
	 */
	@Column
	@Description("Column 2")
	@Property
	private String column2;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String compute;
	
	/**
	 * 
	 */
	@Column
	@Description("Dependencies")
	@Property
	private String depends;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Stored")
	@Property
	private Boolean store = true;
	
	/**
	 * 
	 */
	@Column
	@Description("Currency field")
	@Property
	private String currency_field;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Sanitize HTML")
	@Property
	private Boolean sanitize = true;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("false")
	@Description("Sanitize HTML overridable")
	@Property
	private Boolean sanitize_overridable = false;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Sanitize HTML Tags")
	@Property
	private Boolean sanitize_tags = true;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Sanitize HTML Attributes")
	@Property
	private Boolean sanitize_attributes = true;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("false")
	@Description("Sanitize HTML Style")
	@Property
	private Boolean sanitize_style = false;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Sanitize HTML Form")
	@Property
	private Boolean sanitize_form = true;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("false")
	@Description("Strip Style Attribute")
	@Property
	private Boolean strip_style = false;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("false")
	@Description("Strip Class Attribute")
	@Property
	private Boolean strip_classes = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComplete_name() {
		return complete_name;
	}

	public void setComplete_name(String complete_name) {
		this.complete_name = complete_name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getRelation_field() {
		return relation_field;
	}

	public void setRelation_field(String relation_field) {
		this.relation_field = relation_field;
	}

	public Integer getRelation_field_id() {
		return relation_field_id;
	}

	public void setRelation_field_id(Integer relation_field_id) {
		this.relation_field_id = relation_field_id;
	}

	public Integer getModel_id() {
		return model_id;
	}

	public void setModel_id(Integer model_id) {
		this.model_id = model_id;
	}

	public String getField_description() {
		return field_description;
	}

	public void setField_description(String field_description) {
		this.field_description = field_description;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public String getTtype() {
		return ttype;
	}

	public void setTtype(String ttype) {
		this.ttype = ttype;
	}

	public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}

	public List<Integer> getSelection_ids() {
		return selection_ids;
	}

	public void setSelection_ids(List<Integer> selection_ids) {
		this.selection_ids = selection_ids;
	}

	public Boolean getCopied() {
		return copied;
	}

	public void setCopied(Boolean copied) {
		this.copied = copied;
	}

	public String getRelated() {
		return related;
	}

	public void setRelated(String related) {
		this.related = related;
	}

	public Integer getRelated_field_id() {
		return related_field_id;
	}

	public void setRelated_field_id(Integer related_field_id) {
		this.related_field_id = related_field_id;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public Boolean getReadonly() {
		return readonly;
	}

	public void setReadonly(Boolean readonly) {
		this.readonly = readonly;
	}

	public Boolean getIndex() {
		return index;
	}

	public void setIndex(Boolean index) {
		this.index = index;
	}

	public Boolean getTranslate() {
		return translate;
	}

	public void setTranslate(Boolean translate) {
		this.translate = translate;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Type getState() {
		return state;
	}

	public void setState(Type state) {
		this.state = state;
	}

	public OnDelete getOn_delete() {
		return on_delete;
	}

	public void setOn_delete(OnDelete on_delete) {
		this.on_delete = on_delete;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public List<Groups> getGroups() {
		return groups;
	}

	public void setGroups(List<Groups> groups) {
		this.groups = groups;
	}

	public Boolean getGroup_expand() {
		return group_expand;
	}

	public void setGroup_expand(Boolean group_expand) {
		this.group_expand = group_expand;
	}

	public Boolean getSelectable() {
		return selectable;
	}

	public void setSelectable(Boolean selectable) {
		this.selectable = selectable;
	}

	public String getModules() {
		return modules;
	}

	public void setModules(String modules) {
		this.modules = modules;
	}

	public String getRelation_table() {
		return relation_table;
	}

	public void setRelation_table(String relation_table) {
		this.relation_table = relation_table;
	}

	public String getColumn1() {
		return column1;
	}

	public void setColumn1(String column1) {
		this.column1 = column1;
	}

	public String getColumn2() {
		return column2;
	}

	public void setColumn2(String column2) {
		this.column2 = column2;
	}

	public String getCompute() {
		return compute;
	}

	public void setCompute(String compute) {
		this.compute = compute;
	}

	public String getDepends() {
		return depends;
	}

	public void setDepends(String depends) {
		this.depends = depends;
	}

	public Boolean getStore() {
		return store;
	}

	public void setStore(Boolean store) {
		this.store = store;
	}

	public String getCurrency_field() {
		return currency_field;
	}

	public void setCurrency_field(String currency_field) {
		this.currency_field = currency_field;
	}

	public Boolean getSanitize() {
		return sanitize;
	}

	public void setSanitize(Boolean sanitize) {
		this.sanitize = sanitize;
	}

	public Boolean getSanitize_overridable() {
		return sanitize_overridable;
	}

	public void setSanitize_overridable(Boolean sanitize_overridable) {
		this.sanitize_overridable = sanitize_overridable;
	}

	public Boolean getSanitize_tags() {
		return sanitize_tags;
	}

	public void setSanitize_tags(Boolean sanitize_tags) {
		this.sanitize_tags = sanitize_tags;
	}

	public Boolean getSanitize_attributes() {
		return sanitize_attributes;
	}

	public void setSanitize_attributes(Boolean sanitize_attributes) {
		this.sanitize_attributes = sanitize_attributes;
	}

	public Boolean getSanitize_style() {
		return sanitize_style;
	}

	public void setSanitize_style(Boolean sanitize_style) {
		this.sanitize_style = sanitize_style;
	}

	public Boolean getSanitize_form() {
		return sanitize_form;
	}

	public void setSanitize_form(Boolean sanitize_form) {
		this.sanitize_form = sanitize_form;
	}

	public Boolean getStrip_style() {
		return strip_style;
	}

	public void setStrip_style(Boolean strip_style) {
		this.strip_style = strip_style;
	}

	public Boolean getStrip_classes() {
		return strip_classes;
	}

	public void setStrip_classes(Boolean strip_classes) {
		this.strip_classes = strip_classes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Fields get_relation_field() {
		return _relation_field;
	}

	public void set_relation_field(Fields _relation_field) {
		this._relation_field = _relation_field;
	}

	public Model get_model() {
		return _model;
	}

	public void set_model(Model _model) {
		this._model = _model;
	}

	public List<Selection> getSelections() {
		return selections;
	}

	public void setSelections(List<Selection> selections) {
		this.selections = selections;
	}

	public Fields getRelated_field() {
		return related_field;
	}

	public void setRelated_field(Fields related_field) {
		this.related_field = related_field;
	}
}
