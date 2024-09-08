package erp.base.schema.ir.model;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_model_data")
@Description("Model Data")
@NodeEntity("Model Data")
public class Data {
	
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
	@Description("External Identifier")
	@Property
	private String name;
	
	/**
	 * 
	 */
	@Column
	@Description("Complete ID")
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
	@Column(nullable = false)
	@NotNull
	@DefaultValue("")
	@Property
	private String module = "";
	
	/**
	 * 
	 */
	@Column
	@Description("Record ID")
	@Property
	private Integer res_id;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("false")
	@Description("Non Updatable")
	@Property
	private Boolean noupdate = false;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Reference")
	@Property
	private String reference;

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

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Integer getRes_id() {
		return res_id;
	}

	public void setRes_id(Integer res_id) {
		this.res_id = res_id;
	}

	public Boolean getNoupdate() {
		return noupdate;
	}

	public void setNoupdate(Boolean noupdate) {
		this.noupdate = noupdate;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
