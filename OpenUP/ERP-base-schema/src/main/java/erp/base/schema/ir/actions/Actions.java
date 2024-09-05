package erp.base.schema.ir.actions;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import erp.base.schema.ir.model.Model;
import erp.schema.util.NameAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "ir_actions")
@Description("Actions")
@NodeEntity("Actions")
public class Actions {
	
	/**
	 * 
	 */
	public enum BindingType {
		/**
		 * 
		 */
		action,
        /**
         * 
         */
        report
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
	@Description("Action Name")
	@Property
	@Convert(NameAttributeConverter.class)
	private String name;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Action Type")
	@Property
	private String type;
	
	/**
	 * 
	 */
	@Column
	@Description("External ID")
	@Property
	private String xml_id;
	
	/**
	 * 
	 */
	@Column
	@Description("Action Description")
	@Property
	private String help;
	
	/**
	 * 
	 */
	@Column
	@Description("Setting a value makes this action available in the sidebar for the given model.")
	@Transient
	private String binding_model_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Model.class)
	@JoinColumn(name = "binding_model_id")
	@Relationship(type = "BINDING_MODEL")
	private Model binding_model;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("action")
	@Property
	private BindingType binding_type = BindingType.action;
	
	/**
	 * 
	 */
	@Column
	@Property
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Model getBinding_model() {
		return binding_model;
	}

	public void setBinding_model(Model binding_model) {
		this.binding_model = binding_model;
	}
}
