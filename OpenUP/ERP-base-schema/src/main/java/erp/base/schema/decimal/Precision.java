package erp.base.schema.decimal;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "decimal_precision")
@Description("Decimal Precision")
public class Precision {
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	@Description("Usage")
	private String name;
	
	@Column(nullable = false)
	@NotNull
	@Description("Digits")
	@DefaultValue("2")
	private Integer digits = 2;
}
