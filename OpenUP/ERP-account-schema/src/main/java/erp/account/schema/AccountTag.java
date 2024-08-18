package erp.account.schema;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
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
@Table(name = "account_account_tag")
@Description("Account Tag")
public class AccountTag {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Tag Name")
	private String name;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("accounts")	
	private String applicability = "accounts";
	
	/**
	 * 
	 */
	@Column
	@Description("Color Index")
	private Integer color;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Set active to false to hide the Account Tag without removing it.")
	private Boolean active = true;
	
	/**
	 * 
	 */
	@Column
	@Description("Negate Tax Balance")
	private Boolean tax_negate; 
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Company.class)
	@Description("Country")
	private String country_id;
}
