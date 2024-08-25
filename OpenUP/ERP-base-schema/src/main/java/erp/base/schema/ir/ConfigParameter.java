package erp.base.schema.ir;

import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_config_parameter")
@Description("System Parameter")
public class ConfigParameter {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	private String key;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
