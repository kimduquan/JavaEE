package erp.base.schema.ir.model;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_model_fields_selection")
@Description("Fields Selection")
public class Selection {

	@Column(nullable = false)
	@ManyToOne(targetEntity = Fields.class)
	@NotNull
	private String field_id;
	
	@Column(nullable = false)
	@NotNull
	private String value;
	
	@Column(nullable = false)
	@NotNull
	private String name;
	
	@Column
	@DefaultValue("1000")
	private Integer sequence = 1000;

	public String getField_id() {
		return field_id;
	}

	public void setField_id(String field_id) {
		this.field_id = field_id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
}
