package erp.base.schema.ir;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.model.Access;
import erp.base.schema.ir.model.Fields;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_model")
@Description("Models")
public class Model {
	
	public enum Type {
		@Description("Custom Object")
		manual,
		@Description("Base Object")
		base
	}
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	@Description("Model Description")
	private String name;
	
	@Column(nullable = false)
	@NotNull
	@DefaultValue("x_")
	private String model = "x_";
	
	@Column(nullable = false)
	@NotNull
	@DefaultValue("id")
	@Description("Order")
	private String order = "id";
	
	@Column
	@Description("Information")
	private String info;
	
	@Transient
	private List<Integer> field_id;
	
	@OneToMany(targetEntity = Fields.class)
	@JoinColumn(name = "model_id")
	@NotNull
	@Description("Fields")
	private List<Fields> fields;
	
	@Transient
	@Description("Inherited models")
	private List<Integer> inherited_model_ids;
	
	@Column(updatable = false)
	@Enumerated(EnumType.STRING)
	@DefaultValue("manual")
	@Description("Type")
	private Type state = Type.manual;
	
	@Transient
	private List<Integer> access_ids;
	
	@OneToMany(targetEntity = Access.class)
	@JoinColumn(name = "model_id")
	@Description("Access")
	private List<Access> accesses;
	
	@Transient
	private List<Integer> rule_ids;
	
	@OneToMany(targetEntity = Rule.class)
	@JoinColumn(name = "model_id")
	@Description("Record Rules")
	private List<Rule> rules;
	
	@Transient
	@Description("Abstract Model")
	private Boolean abstract_;
	
	@Column(name = "transient")
	@Description("Transient Model")
	private Boolean transient_;
	
	@Transient
	@Description("In Apps")
	private String modules;
	
	@Transient
	@Description("Views")
	private List<Integer> view_ids;
	
	@Transient
	@Description("Count (Incl. Archived)")
	private Integer count;
}
