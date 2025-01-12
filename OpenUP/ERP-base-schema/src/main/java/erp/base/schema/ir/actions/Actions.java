package erp.base.schema.ir.actions;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.Model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "ir_actions")
@Description("Actions")
public class Actions {
	
	public enum BindingType {
		@Description("Action")
		action,
		@Description("Report")
        report
	}
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	@Description("Action Name")
	private String name;
	
	@Column(nullable = false)
	@NotNull
	@Description("Action Type")
	private String type;
	
	@Transient
	@Description("External ID")
	private String xml_id;
	
	@Column
	private String path;
	
	@Column
	@Description("Action Description")
	private String help;
	
	@Transient
	private Integer binding_model_id;
	
	@ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "binding_model_id")
	private Model binding_model;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("action")
	private BindingType binding_type = BindingType.action;
	
	@Column
	@DefaultValue("list,form")
	private String binding_view_types = "list,form";

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getXml_id() {
		return xml_id;
	}

	public void setXml_id(String xml_id) {
		this.xml_id = xml_id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public Integer getBinding_model_id() {
		return binding_model_id;
	}

	public void setBinding_model_id(Integer binding_model_id) {
		this.binding_model_id = binding_model_id;
	}

	public Model getBinding_model() {
		return binding_model;
	}

	public void setBinding_model(Model binding_model) {
		this.binding_model = binding_model;
	}

	public BindingType getBinding_type() {
		return binding_type;
	}

	public void setBinding_type(BindingType binding_type) {
		this.binding_type = binding_type;
	}

	public String getBinding_view_types() {
		return binding_view_types;
	}

	public void setBinding_view_types(String binding_view_types) {
		this.binding_view_types = binding_view_types;
	}
}
