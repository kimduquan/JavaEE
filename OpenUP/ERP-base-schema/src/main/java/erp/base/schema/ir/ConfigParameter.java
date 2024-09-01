package erp.base.schema.ir;

import org.eclipse.microprofile.graphql.Description;
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
@Table(name = "ir_config_parameter")
@Description("System Parameter")
@NodeEntity("System Parameter")
public class ConfigParameter {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Property
	private String key;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Property
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
