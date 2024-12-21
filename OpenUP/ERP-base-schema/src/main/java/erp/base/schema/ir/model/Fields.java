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
	
	@Description("Selection Options (Deprecated)")
	private String selection;
	
	@Transient
	private List<Integer> selection_ids;

	@OneToMany(targetEntity = Selection.class, fetch = FetchType.LAZY)
	@JoinTable(name = "ir_model_fields_selection", joinColumns = {
			@JoinColumn(name = "field_id", referencedColumnName = "id")
	})
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
	
	@ManyToMany(targetEntity = Groups.class)
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
}
