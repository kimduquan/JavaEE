package erp.base.schema.ir.model;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.Model;
import erp.base.schema.res.Groups;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Entity
@Table(name = "ir_model_fields")
@Description("Fields")
public class Fields {
	
	public enum Type {
		@Description("Custom Field")
		manual,
		@Description("Base Field")
		base
	}
	
	public enum OnDelete {
		@Description("Cascade")
		cascade,
		@Description("Set NULL")
		set_null,
		@Description("Restrict")
		restrict
	}
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	@DefaultValue("x_")
	@Description("Field Name")
	private String name = "x_";
	
	@Column
	private String complete_name;
	
	@Column(nullable = false)
	@NotNull
	@Description("Model Name")
	private String model;
	
	@Column
	@Description("Related Model")
	private String relation;
	
	@Column
	@Description("For one2many fields, the field on the target model that implement the opposite many2one relationship")
	private String relation_field;
	
	@Transient
	private Integer relation_field_id;
	
	@ManyToOne(targetEntity = Fields.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "relation_field_id")
	@Description("Relation field")
	private Fields relation_field_;
	
	@Transient
	private Integer model_id;

	@ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "model_id", nullable = false)
	@NotNull
	@Description("Model")
	private Model model_;
	
	@Column(nullable = false)
	@NotNull
	@DefaultValue("")
	@Description("Field Label")
	private String field_description = "";
	
	@Column
	@Description("Field Help")
	private String help;
	
	@Column(nullable = false)
	@NotNull
	@Description("Field Type")
	private String ttype;
	
	@Transient
	@Description("Selection Options (Deprecated)")
	private String selection;
	
	@Transient
	private List<Integer> selection_ids;

	@OneToMany(targetEntity = Selection.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "field_id")
	@Description("Selection Options")
	private List<Selection> selections;
	
	@Column
	@Description("Copied")
	private Boolean copied;
	
	@Column
	@Description("Related Field Definition")
	private String related;
	
	@Transient
	private Integer related_field_id;

	@ManyToOne(targetEntity = Fields.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "related_field_id")
	@Description("Related field")
	private Fields related_field;
	
	@Column
	private Boolean required;
	
	@Column
	private Boolean readonly;
	
	@Column
	@Description("Indexed")
	private Boolean index;
	
	@Column
	@Description("Translatable")
	private Boolean translate;
	
	@Column(updatable = false)
	@Description("Company Dependent")
	private Boolean company_dependent;
	
	@Column
	private Integer size;
	
	@Column(nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("manual")
	@Description("Type")
	private Type state = Type.manual;
	
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("set null")
	@Description("On Delete")
	private OnDelete on_delete = OnDelete.set_null;
	
	@Column
	@DefaultValue("[]")
	private String domain = "[]";
	
	@ManyToMany(targetEntity = Groups.class, fetch = FetchType.LAZY)
	@JoinTable(name = "ir_model_fields_group_rel", joinColumns = {@JoinColumn(name = "field_id")}, inverseJoinColumns = {@JoinColumn(name = "group_id")})
	private List<Groups> groups;
	
	@Column
	@Description("Expand Groups")
	private Boolean group_expand;
	
	@Column
	@DefaultValue("true")
	private Boolean selectable = true;
	
	@Transient
	@Description("In Apps")
	private String modules;
	
	@Column
	private String relation_table;
	
	@Column
	@Description("Column 1")
	private String column1;
	
	@Column
	@Description("Column 2")
	private String column2;
	
	@Column
	private String compute;
	
	@Column
	@Description("Dependencies")
	private String depends;
	
	@Column
	@DefaultValue("true")
	@Description("Stored")
	private Boolean store = true;
	
	@Column
	@Description("Currency field")
	private String currency_field;
	
	@Column
	@DefaultValue("true")
	@Description("Sanitize HTML")
	private Boolean sanitize = true;
	
	@Column
	@DefaultValue("false")
	@Description("Sanitize HTML overridable")
	private Boolean sanitize_overridable = false;
	
	@Column
	@DefaultValue("true")
	@Description("Sanitize HTML Tags")
	private Boolean sanitize_tags = true;
	
	@Column
	@DefaultValue("true")
	@Description("Sanitize HTML Attributes")
	private Boolean sanitize_attributes = true;
	
	@Column
	@DefaultValue("false")
	@Description("Sanitize HTML Style")
	private Boolean sanitize_style = false;
	
	@Column
	@DefaultValue("true")
	@Description("Sanitize HTML Form")
	private Boolean sanitize_form = true;
	
	@Column
	@DefaultValue("false")
	@Description("Strip Style Attribute")
	private Boolean strip_style = false;
	
	@Column
	@DefaultValue("false")
	@Description("Strip Class Attribute")
	private Boolean strip_classes = false;

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

	public Fields getRelation_field_() {
		return relation_field_;
	}

	public void setRelation_field_(Fields relation_field_) {
		this.relation_field_ = relation_field_;
	}

	public Integer getModel_id() {
		return model_id;
	}

	public void setModel_id(Integer model_id) {
		this.model_id = model_id;
	}

	public Model getModel_() {
		return model_;
	}

	public void setModel_(Model model_) {
		this.model_ = model_;
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

	public List<Selection> getSelections() {
		return selections;
	}

	public void setSelections(List<Selection> selections) {
		this.selections = selections;
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

	public Fields getRelated_field() {
		return related_field;
	}

	public void setRelated_field(Fields related_field) {
		this.related_field = related_field;
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

	public Boolean getCompany_dependent() {
		return company_dependent;
	}

	public void setCompany_dependent(Boolean company_dependent) {
		this.company_dependent = company_dependent;
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
}
