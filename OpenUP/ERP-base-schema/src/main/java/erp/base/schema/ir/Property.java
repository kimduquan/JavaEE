package erp.base.schema.ir;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.model.Fields;
import erp.base.schema.res.Company;
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
	@ManyToOne(targetEntity = Fields.class)
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRes_id() {
		return res_id;
	}

	public void setRes_id(String res_id) {
		this.res_id = res_id;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getFields_id() {
		return fields_id;
	}

	public void setFields_id(String fields_id) {
		this.fields_id = fields_id;
	}

	public Float getValue_float() {
		return value_float;
	}

	public void setValue_float(Float value_float) {
		this.value_float = value_float;
	}

	public Integer getValue_integer() {
		return value_integer;
	}

	public void setValue_integer(Integer value_integer) {
		this.value_integer = value_integer;
	}

	public String getValue_text() {
		return value_text;
	}

	public void setValue_text(String value_text) {
		this.value_text = value_text;
	}

	public byte[] getValue_binary() {
		return value_binary;
	}

	public void setValue_binary(byte[] value_binary) {
		this.value_binary = value_binary;
	}

	public String getValue_reference() {
		return value_reference;
	}

	public void setValue_reference(String value_reference) {
		this.value_reference = value_reference;
	}

	public String getValue_datetime() {
		return value_datetime;
	}

	public void setValue_datetime(String value_datetime) {
		this.value_datetime = value_datetime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
