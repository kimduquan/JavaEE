package erp.schema.account;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
@Description("Incoterms")
public class Incoterms {

	@Column(nullable = false)
	@NotNull
	@Description("Name")
	private String name;
	
	@Column(nullable = false, length = 3)
	@NotNull
	@Description("Code")
	private String code;
	
	@Column
	@DefaultValue("true")
	@Description("Active")
	private Boolean active = true;
}
