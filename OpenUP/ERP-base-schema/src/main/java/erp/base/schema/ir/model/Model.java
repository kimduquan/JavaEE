package erp.base.schema.ir.model;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import erp.base.schema.ir.Rule;
import erp.base.schema.ir.ui.View;
import erp.schema.util.NameAttributeConverter;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_model")
@Description("Models")
@NodeEntity("Models")
public class Model {
	
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
	@Description("Model Description")
	@Property
	@Convert(NameAttributeConverter.class)
	private String name;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("x_")
	@Property
	private String model = "x_";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("id")
	@Description("Order")
	@Property
	private String order = "id";
	
	/**
	 * 
	 */
	@Column
	@Description("Information")
	@Property
	private String info;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@OneToMany(targetEntity = Fields.class)
	@ElementCollection(targetClass = Fields.class)
	@CollectionTable(name = "ir_model_fields")
	@NotNull
	@Description("Fields")
	@Property
	@Relationship(type = "FIELD")
	private List<String> field_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Model.class)
	@ElementCollection(targetClass = Model.class)
	@CollectionTable(name = "ir_model")
	@Description("Inherited models")
	@Property
	@Relationship(type = "INHERITED_MODELS")
	private List<String> inherited_model_ids;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Enumerated(EnumType.STRING)
	@DefaultValue("manual")
	@Description("Type")
	@Property
	private String state = "manual";
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = Access.class)
	@ElementCollection(targetClass = Access.class)
	@CollectionTable(name = "ir_model_access")
	@Description("Access")
	@Property
	@Relationship(type = "ACCESSES")
	private List<String> access_ids;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = Rule.class)
	@ElementCollection(targetClass = Rule.class)
	@CollectionTable(name = "ir_rule")
	@Description("Record Rules")
	@Property
	@Relationship(type = "RULES")
	private List<String> rule_ids;
	
	/**
	 * 
	 */
	@Column(name = "transient")
	@Description("Transient Model")
	@Property
	private Boolean _transient;
	
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
	@OneToMany(targetEntity = View.class)
	@ElementCollection(targetClass = View.class)
	@CollectionTable(name = "ir_ui_view")
	@Description("Views")
	@Property
	@Relationship(type = "VIEWS")
	private List<String> view_ids;
	
	/**
	 * 
	 */
	@Column
	@Description("Count (Incl. Archived)")
	@Property
	private Integer count;

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

	public List<String> getField_id() {
		return field_id;
	}

	public void setField_id(List<String> field_id) {
		this.field_id = field_id;
	}

	public List<String> getInherited_model_ids() {
		return inherited_model_ids;
	}

	public void setInherited_model_ids(List<String> inherited_model_ids) {
		this.inherited_model_ids = inherited_model_ids;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<String> getAccess_ids() {
		return access_ids;
	}

	public void setAccess_ids(List<String> access_ids) {
		this.access_ids = access_ids;
	}

	public List<String> getRule_ids() {
		return rule_ids;
	}

	public void setRule_ids(List<String> rule_ids) {
		this.rule_ids = rule_ids;
	}

	public Boolean get_transient() {
		return _transient;
	}

	public void set_transient(Boolean _transient) {
		this._transient = _transient;
	}

	public String getModules() {
		return modules;
	}

	public void setModules(String modules) {
		this.modules = modules;
	}

	public List<String> getView_ids() {
		return view_ids;
	}

	public void setView_ids(List<String> view_ids) {
		this.view_ids = view_ids;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
