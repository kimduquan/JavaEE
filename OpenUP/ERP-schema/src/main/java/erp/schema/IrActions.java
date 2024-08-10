package erp.schema;

import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "ir_actions")
@Description("Actions")
public class IrActions {

	@Column(nullable = false)
	@Description("Action Name")
	private String name;
}
