package erp.base.schema.res.partner;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Company;
import erp.base.schema.res.Currency;
import erp.base.schema.res.Partner;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "res_partner_bank")
@Description("Bank Accounts")
public class Bank {
	
	@Id
	private int id;

	@Column
	@DefaultValue("true")
	private Boolean active = true;
	
	@Transient
	@Description("Type")
	private String acc_type;
	
	@Column(nullable = false)
	@NotNull
	@Description("Account Number")
	private String acc_number;
	
	@Description("Sanitized Account Number")
	private String sanitized_acc_number;
	
	@Description("Account Holder Name")
	private String acc_holder_name;
	
	@Transient
	private Integer partner_id;

	@ManyToOne(targetEntity = Partner.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "partner_id", nullable = false)
	@NotNull
	@Description("Account Holder")
	private Partner partner;
	
	@Column
	@DefaultValue("false")
	@Description("Send Money")
	private Boolean allow_out_payment = false;
	
	@Transient
	private Integer bank_id;

	@ManyToOne(targetEntity = Bank.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "bank_id")
	@Description("Bank")
	private Bank bank;
	
	@Transient
	private String bank_name;
	
	@Transient
	private String bank_bic;
	
	@Column
	private Integer sequence = 10;
	
	@Transient
	private Integer currency_id;
	
	@ManyToOne(targetEntity = Currency.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "currency_id")
	@Description("Currency")
	private Currency currency;
	
	@Transient
	private Integer company_id;
	
	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", updatable = false)
	@Description("Company")
	private Company company;
	
	@Transient
	@Description("Country Code")
	private String country_code;
}
