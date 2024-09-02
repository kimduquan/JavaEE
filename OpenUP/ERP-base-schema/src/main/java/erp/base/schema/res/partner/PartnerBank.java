package erp.base.schema.res.partner;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import erp.base.schema.res.Bank;
import erp.base.schema.res.Company;
import erp.base.schema.res.currency.Currency;
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
@NodeEntity("Bank Accounts")
public class PartnerBank {
	
	/**
	 * 
	 */
	@jakarta.persistence.Id
	@Id
	private int id;

	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Property
	private Boolean active = true;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Description("Type")
	@Property
	private String acc_type;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Account Number")
	@Property
	private String acc_number;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Sanitized Account Number")
	@Property
	private String sanitized_acc_number;
	
	/**
	 * 
	 */
	@Column
	@Description("Account Holder Name")
	@Property
	private String acc_holder_name;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = Partner.class)
	@NotNull
	@Description("Account Holder")
	@Property
	@Relationship(type = "ACCOUNT_HOLDER")
	private String partner_id;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("false")
	@Description("Send Money")
	@Property
	private Boolean allow_out_payment = false;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Bank.class)
	@Description("Bank")
	@Property
	@Relationship(type = "BANK")
	private String bank_id;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String bank_name;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String bank_bic;
	
	/**
	 * 
	 */
	@Column
	@Property
	private Integer sequence = 10;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Currency.class)
	@Description("Currency")
	@Property
	@Relationship(type = "CURRENCY")
	private String currency_id;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@ManyToOne(targetEntity = Company.class)
	@Property
	@Relationship(type = "COMPANY")
	private String company_id;

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

	public String getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}

	public Boolean getAllow_out_payment() {
		return allow_out_payment;
	}

	public void setAllow_out_payment(Boolean allow_out_payment) {
		this.allow_out_payment = allow_out_payment;
	}

	public String getBank_id() {
		return bank_id;
	}

	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
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

	public String getCurrency_id() {
		return currency_id;
	}

	public void setCurrency_id(String currency_id) {
		this.currency_id = currency_id;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
