package erp.base.schema.res;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
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
@Table(name = "res_partner_bank")
@Description("Bank Accounts")
public class PartnerBank {

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
	@Enumerated(EnumType.STRING)
	@Description("Type")
	private String acc_type;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Account Number")
	private String acc_number;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Sanitized Account Number")
	private String sanitized_acc_number;
	
	/**
	 * 
	 */
	@Column
	@Description("Account Holder Name")
	private String acc_holder_name;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = Partner.class)
	@NotNull
	@Description("Account Holder")
	private String partner_id;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("false")
	@Description("Send Money")
	private Boolean allow_out_payment = false;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Bank.class)
	@Description("Bank")
	private String bank_id;
	
	/**
	 * 
	 */
	@Column
	private String bank_name;
	
	/**
	 * 
	 */
	@Column
	private String bank_bic;
	
	/**
	 * 
	 */
	@Column
	private Integer sequence = 10;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Currency.class)
	@Description("Currency")
	private String currency_id;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@ManyToOne(targetEntity = Company.class)
	private String company_id;
}
