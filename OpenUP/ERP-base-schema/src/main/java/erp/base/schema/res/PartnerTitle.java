package erp.base.schema.res;

import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "res_partner_title")
@Description("Partner Title")
public class PartnerTitle {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Title")
	private String name;
	
	/**
	 * 
	 */
	@Column
	@Description("Abbreviation")
	private String shortcut;
}
