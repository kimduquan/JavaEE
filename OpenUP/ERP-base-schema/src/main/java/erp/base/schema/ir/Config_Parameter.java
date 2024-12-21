package erp.base.schema.ir;

import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_config_parameter")
@Description("System Parameter")
public class Config_Parameter {
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	private String key;
	
	@Column(nullable = false)
	@NotNull
	private String value;
}
