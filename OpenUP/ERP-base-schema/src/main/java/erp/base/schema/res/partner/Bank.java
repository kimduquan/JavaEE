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

@Entity(name = "PartnerBank")
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getAcc_type() {
		return acc_type;
	}

	public void setAcc_type(String acc_type) {
		this.acc_type = acc_type;
	}

	public String getAcc_number() {
		return acc_number;
	}

	public void setAcc_number(String acc_number) {
		this.acc_number = acc_number;
	}

	public String getSanitized_acc_number() {
		return sanitized_acc_number;
	}

	public void setSanitized_acc_number(String sanitized_acc_number) {
		this.sanitized_acc_number = sanitized_acc_number;
	}

	public String getAcc_holder_name() {
		return acc_holder_name;
	}

	public void setAcc_holder_name(String acc_holder_name) {
		this.acc_holder_name = acc_holder_name;
	}

	public Integer getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(Integer partner_id) {
		this.partner_id = partner_id;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public Boolean getAllow_out_payment() {
		return allow_out_payment;
	}

	public void setAllow_out_payment(Boolean allow_out_payment) {
		this.allow_out_payment = allow_out_payment;
	}

	public Integer getBank_id() {
		return bank_id;
	}

	public void setBank_id(Integer bank_id) {
		this.bank_id = bank_id;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getBank_bic() {
		return bank_bic;
	}

	public void setBank_bic(String bank_bic) {
		this.bank_bic = bank_bic;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Integer getCurrency_id() {
		return currency_id;
	}

	public void setCurrency_id(Integer currency_id) {
		this.currency_id = currency_id;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Integer getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
}
