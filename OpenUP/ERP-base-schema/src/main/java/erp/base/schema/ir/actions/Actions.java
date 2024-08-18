package erp.base.schema.ir.actions;

import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.model.Model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_actions")
@Description("Actions")
public class Actions {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Action Name")
	private String name;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Action Type")
	private String type;
	
	/**
	 * 
	 */
	@Column
	@Description("External ID")
	private String xml_id;
	
	/**
	 * 
	 */
	@Column
	@Description("Action Description")
	private String help;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Model.class)
	@Description("Setting a value makes this action available in the sidebar for the given model.")
	private String binding_model_id;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private String binding_type = "action";
	
	/**
	 * 
	 */
	@Column
	private String binding_view_types = "list,form";

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

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public String getBinding_model_id() {
		return binding_model_id;
	}

	public void setBinding_model_id(String binding_model_id) {
		this.binding_model_id = binding_model_id;
	}

	public String getBinding_type() {
		return binding_type;
	}

	public void setBinding_type(String binding_type) {
		this.binding_type = binding_type;
	}

	public String getBinding_view_types() {
		return binding_view_types;
	}

	public void setBinding_view_types(String binding_view_types) {
		this.binding_view_types = binding_view_types;
	}
}
