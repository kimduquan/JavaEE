package erp.schema.ir;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.schema.res.Company;
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
@Table(name = "ir_property")
@Description("Company Property")
public class Property {

	/**
	 * 
	 */
	@Column
	private String name;
	
	/**
	 * 
	 */
	@Column
	@Description("Resource")
	private String res_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Company.class)
	@Description("Company")
	private String company_id;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = ModelFields.class)
	@NotNull
	@Description("Field")
	private String fields_id;
	
	/**
	 * 
	 */
	@Column
	private Float value_float;
	
	/**
	 * 
	 */
	@Column
	private Integer value_integer;
	
	/**
	 * 
	 */
	@Column
	private String value_text;
	
	/**
	 * 
	 */
	@Column
	private byte[] value_binary;
	
	/**
	 * 
	 */
	@Column
	private String value_reference;
	
	/**
	 * 
	 */
	@Column
	private String value_datetime;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("many2one")
	private String type = "many2one";
}
