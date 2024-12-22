package erp.schema.account;

import java.util.Date;
import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Company;
import erp.base.schema.res.Partner;
import erp.base.schema.res.partner.Bank;
import erp.schema.account.payment.Method;
import erp.schema.account.payment.method.Line;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Description("Journal")
public class Journal {
	
	public enum Type {
		@Description("Sales")
		sale,
		@Description("Purchase")
		purchase,
		@Description("Cash")
		cash,
		@Description("Bank")
		bank,
		@Description("Credit Card")
		credit,
		@Description("Miscellaneous")
		general,
	}
	
	public enum InvoiceReferenceType {
		@Description("Based on Customer")
		partner,
		@Description("Based on Invoice")
		invoice
	}
	
	public enum InvoiceReferenceModel {
		@Description("Full Reference (INV/2024/00001)")
		odoo,
		@Description("European (RF83INV202400001)")
		euro,
		@Description("Numbers only (202400001)")
		number
	}

	@Column(nullable = false)
	@NotNull
	@Description("Journal Name")
	private String name;
	
	@Column(length = 5, nullable = false)
	@NotNull
	@Description("Short Code")
	private String code;
	
	@Column
	@DefaultValue("true")
	private Boolean active = true;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private Type type;
	
	@Column
	@DefaultValue("true")
	@Description("Auto-Check on Post")
	private Boolean autocheck_on_post = true;
	
	@Transient
	private List<Integer> account_control_ids;
	
	@ManyToMany(targetEntity = Account.class, fetch = FetchType.LAZY)
	@JoinTable(name = "journal_account_control_rel", joinColumns = {@JoinColumn(name = "journal_id")}, inverseJoinColumns = {@JoinColumn(name = "account_id")})
	@Description("Allowed accounts")
	private List<Account> account_controls;
	
	@Column
	private String default_account_type;
	
	@Transient 
	private Integer default_account_id;
	
	@ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "default_account_id")
	@Description("Default Account")
	private Account default_account;
	
	@Transient
	private Integer suspense_account_id;
	
	@ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "suspense_account_id")
	private Account suspense_account;
	
	@Column
	@Description("Secure Posted Entries with Hash")
	private Boolean restrict_mode_hash_table;
	
	@Column
	@DefaultValue("10")
	private Integer sequence = 10;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("invoice")
	@Description("Communication Type")
	private InvoiceReferenceType invoice_reference_type = InvoiceReferenceType.invoice;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Description("Communication Standard")
	private InvoiceReferenceModel invoice_reference_model = InvoiceReferenceModel.odoo;
	
	@Transient
	private Integer currency_id;
	
	@ManyToOne(targetEntity = Currency.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "currency_id")
	@Description("Currency")
	private Currency currency;
	
	@Transient
	private Integer company_id;
	
	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false, updatable = false)
	@NotNull
	@Description("Company")
	private Company company;
	
	@Column(updatable = false)
	private String country_code;
	
	@Column
	@Description("Dedicated Credit Note Sequence")
	private Boolean refund_sequence;
	
	@Column
	@Description("Dedicated Payment Sequence")
	private Boolean payment_sequence;
	
	@Column
	private String sequence_override_regex;
	
	@Transient
	private List<Integer> inbound_payment_method_line_ids;
	
	@OneToMany(targetEntity = Line.class, fetch = FetchType.LAZY)
	@Description("Inbound Payment Methods")
	private List<Line> inbound_payment_method_lines;
	
	@Transient
	private List<Integer> outbound_payment_method_line_ids;
	
	@OneToMany(targetEntity = Line.class, fetch = FetchType.LAZY)
	@Description("Outbound Payment Methods")
	private List<Line> outbound_payment_method_lines;
	
	@Transient
	private Integer profit_account_id;
	
	@ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "profit_account_id")
	@Description("Profit Account")
	private Account profit_account;
	
	@Transient
	private Integer loss_account_id;
	
	@ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "loss_account_id")
	@Description("Loss Account")
	private Account loss_account;
	
	@Transient
	private Integer company_partner_id;
	
	@ManyToOne(targetEntity = Partner.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_partner_id", updatable = false)
	@Description("Account Holder")
	private Partner company_partner;
	
	@Transient
	private Integer bank_account_id;
	
	@ManyToOne(targetEntity = Bank.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "bank_account_id")
	@Description("Bank Account")
	private Bank bank_account;
	
	@Column
	@DefaultValue("undefined")
	@Description("Bank Feeds")
	private String bank_statements_source = "undefined";
	
	@Column
	private String bank_acc_number;
	
	@Transient
	private Integer bank_id;
	
	@ManyToOne(targetEntity = Bank.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "bank_id")
	private Bank bank;
	
	@Column
	private String alias_id;
	
	@Transient
	private List<Integer> journal_group_ids;
	
	@ManyToMany(targetEntity = erp.schema.account.journal.Group.class, fetch = FetchType.LAZY)
	@Description("Ledger Group")
	private List<erp.schema.account.journal.Group> journal_groups;
	
	@Transient
	private List<Integer> available_payment_method_ids;
	
	@ManyToMany(targetEntity = Method.class, fetch = FetchType.LAZY)
	private List<Method> available_payment_methods;
	
	@Column
	private String selected_payment_method_codes;
	
	@Column
	private Date accounting_date;
	
	@Column
	private Boolean display_alias_fields;
}
