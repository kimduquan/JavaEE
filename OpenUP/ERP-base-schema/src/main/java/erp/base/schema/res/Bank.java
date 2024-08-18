package erp.base.schema.res;

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
@Table(name = "res_bank")
@Description("Bank")
public class Bank {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	private String name;
	
	/**
	 * 
	 */
	@Column
	private String street;
	
	/**
	 * 
	 */
	@Column
	private String street2;
	
	/**
	 * 
	 */
	@Column
	private String zip;
	
	/**
	 * 
	 */
	@Column
	private String city;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = CountryState.class)
	@Description("Fed. State")
	private String state;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Country.class)
	private String country;
	
	/**
	 * 
	 */
	@Column
	private String email;
	
	/**
	 * 
	 */
	@Column
	private String phone;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	private Boolean active = true;
	
	/**
	 * 
	 */
	@Column
	@Description("Bank Identifier Code")
	private String bic;
}
