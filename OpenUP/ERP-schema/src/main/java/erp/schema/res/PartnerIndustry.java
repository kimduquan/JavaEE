package erp.schema.res;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "res_partner_industry")
@Description("Industry")
public class PartnerIndustry {

	/**
	 * 
	 */
	@Column
	@Description("Name")
	private String name;
	
	/**
	 * 
	 */
	@Column
	@Description("Full Name")
	private String full_name;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Active")
	private Boolean active = true;
}
