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

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public List<Integer> getField_id() {
		return field_id;
	}

	public void setField_id(List<Integer> field_id) {
		this.field_id = field_id;
	}

	public List<Fields> getFields() {
		return fields;
	}

	public void setFields(List<Fields> fields) {
		this.fields = fields;
	}

	public List<Integer> getInherited_model_ids() {
		return inherited_model_ids;
	}

	public void setInherited_model_ids(List<Integer> inherited_model_ids) {
		this.inherited_model_ids = inherited_model_ids;
	}

	public Type getState() {
		return state;
	}

	public void setState(Type state) {
		this.state = state;
	}

	public List<Integer> getAccess_ids() {
		return access_ids;
	}

	public void setAccess_ids(List<Integer> access_ids) {
		this.access_ids = access_ids;
	}

	public List<Access> getAccesses() {
		return accesses;
	}

	public void setAccesses(List<Access> accesses) {
		this.accesses = accesses;
	}

	public List<Integer> getRule_ids() {
		return rule_ids;
	}

	public void setRule_ids(List<Integer> rule_ids) {
		this.rule_ids = rule_ids;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	public Boolean getAbstract_() {
		return abstract_;
	}

	public void setAbstract_(Boolean abstract_) {
		this.abstract_ = abstract_;
	}

	public Boolean getTransient_() {
		return transient_;
	}

	public void setTransient_(Boolean transient_) {
		this.transient_ = transient_;
	}

	public String getModules() {
		return modules;
	}

	public void setModules(String modules) {
		this.modules = modules;
	}

	public List<Integer> getView_ids() {
		return view_ids;
	}

	public void setView_ids(List<Integer> view_ids) {
		this.view_ids = view_ids;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
