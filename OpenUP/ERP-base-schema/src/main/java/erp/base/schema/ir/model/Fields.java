package erp.base.schema.ir.model;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

import erp.base.schema.res.groups.Groups;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Fields {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("x_")
	@Description("Field Name")
	private String name = "x_";
	
	/**
	 * 
	 */
	@Column
	private String complete_name;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Model Name")
	private String model;
	
	/**
	 * 
	 */
	@Column
	@Description("Related Model")
	private String relation;
	
	/**
	 * 
	 */
	@Column
	@Description("For one2many fields, the field on the target model that implement the opposite many2one relationship")
	private String relation_field;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Fields.class)
	@Description("Relation field")
	private String relation_field_id;
	
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
	@Column(nullable = false)
	@NotNull
	@DefaultValue("")
	@Description("Field Label")
	private String field_description = "";
	
	/**
	 * 
	 */
	@Column
	@Description("Field Help")
	private String help;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Description("Field Type")
	private String ttype;
	
	/**
	 * 
	 */
	@Column
	@Description("Selection Options (Deprecated)")
	private String selection;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = Selection.class)
	@ElementCollection(targetClass = Selection.class)
	@CollectionTable(name = "ir_model_fields_selection")
	@Description("Selection Options")
	private List<String> selection_ids;
	
	/**
	 * 
	 */
	@Column
	@Description("Copied")
	private Boolean copied;
	
	/**
	 * 
	 */
	@Column
	@Description("Related Field")
	private String related;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Fields.class)
	@Description("Related field")
	private String related_field_id;
	
	/**
	 * 
	 */
	@Column
	private Boolean required;
	
	/**
	 * 
	 */
	@Column
	private Boolean readonly;
	
	/**
	 * 
	 */
	@Column
	@Description("Indexed")
	private Boolean index;
	
	/**
	 * 
	 */
	@Column
	@Description("Translatable")
	private Boolean translate;
	
	/**
	 * 
	 */
	@Column
	private Integer size;
	
	/**
	 * 
	 */
	@Column(nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("manual")
	@Description("Type")
	private String state = "manual";
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("set null")
	@Description("On Delete")
	private String on_delete = "set null";
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("[]")
	@Description("The optional domain to restrict possible values for relationship fields, specified as a Python expression defining a list of triplets. For example: [('color','=','red')]")
	private String domain = "[]";
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Groups.class)
	@ElementCollection(targetClass = Groups.class)
	@CollectionTable(name = "res_groups")
	private List<String> groups;
	
	/**
	 * 
	 */
	@Column
	@Description("Expand Groups")
	private Boolean group_expand;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	private Boolean selectable = true;
	
	/**
	 * 
	 */
	@Column
	@Description("In Apps")
	private String modules;
	
	/**
	 * 
	 */
	@Column
	@Description("Used for custom many2many fields to define a custom relation table name")
	private String relation_table;
	
	/**
	 * 
	 */
	@Column
	@Description("Column 1")
	private String column1;
	
	/**
	 * 
	 */
	@Column
	@Description("Column 2")
	private String column2;
	
	/**
	 * 
	 */
	@Column
	private String compute;
	
	/**
	 * 
	 */
	@Column
	@Description("Dependencies")
	private String depends;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Stored")
	private Boolean store = true;
	
	/**
	 * 
	 */
	@Column
	@Description("Currency field")
	private String currency_field;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Sanitize HTML")
	private Boolean sanitize = true;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("false")
	@Description("Sanitize HTML overridable")
	private Boolean sanitize_overridable = false;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Sanitize HTML Tags")
	private Boolean sanitize_tags = true;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Sanitize HTML Attributes")
	private Boolean sanitize_attributes = true;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("false")
	@Description("Sanitize HTML Style")
	private Boolean sanitize_style = false;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Sanitize HTML Form")
	private Boolean sanitize_form = true;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("false")
	@Description("Strip Style Attribute")
	private Boolean strip_style = false;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("false")
	@Description("Strip Class Attribute")
	private Boolean strip_classes = false;
}
