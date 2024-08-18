package erp.base.schema.ir.model;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
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
public class Data {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("External Identifier")
	private String name;
	
	/**
	 * 
	 */
	@Column
	@Description("Complete ID")
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
	@Column(nullable = false)
	@NotNull
	@DefaultValue("")
	private String module = "";
	
	/**
	 * 
	 */
	@Column
	@Description("Record ID")
	private String res_id;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("false")
	@Description("Non Updatable")
	private Boolean noupdate = false;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Reference")
	private String reference;
}
