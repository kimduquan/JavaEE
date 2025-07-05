package erp.schema.account;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Company;
import erp.base.schema.res.Currency;
import erp.schema.account.account.Tag;
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
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "account_account")
@Description("Account")
public class Account {
	
	public enum AccountType {
		@Description("Receivable")
		asset_receivable,
		@Description("Bank and Cash")
		asset_cash,
		@Description("Current Assets")
		asset_current,
		@Description("Non-current Assets")
		asset_non_current,
		@Description("Prepayments")
		asset_prepayments,
		@Description("Fixed Assets")
		asset_fixed,
		@Description("Payable")
		liability_payable,
		@Description("Credit Card")
		liability_credit_card,
		@Description("Current Liabilities")
		liability_current,
		@Description("Non-current Liabilities")
		liability_non_current,
		@Description("Equity")
		equity,
		@Description("Current Year Earnings")
		equity_unaffected,
		@Description("Income")
		income,
		@Description("Other Income")
		income_other,
		@Description("Expenses")
		expense,
		@Description("Depreciation")
		expense_depreciation,
		@Description("Cost of Revenue")
		expense_direct_cost,
		@Description("Off-Balance Sheet")
		off_balance
	}
	
	public enum InternalGroup {
		@Description("Equity")
		equity,
		@Description("Asset")
		asset,
		@Description("Liability")
		liability,
		@Description("Income")
		income,
		@Description("Expense")
		expense,
		@Description("Off Balance")
		off
	}

	@Column(nullable = false)
	@NotNull
	@Description("Account Name")
	private String name;
	
	@Transient
	private Integer currency_id;
	
	@ManyToOne(targetEntity = Currency.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "currency_id")
	@Description("Account Currency")
	private Currency currency;
	
	@Transient
	private Integer company_currency_id;
	
	@ManyToOne(targetEntity = Currency.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_currency_id")
	private Currency company_currency;
	
	@Column
	private String company_fiscal_country_code;
	
	@Column(length = 64)
	@Description("Code")
	private String code;
	
	@Column
	private String code_store;
	
	@Column
	@Description("Display code")
	private String placeholder_code;
	
	@Column
	@DefaultValue("false")
	private Boolean deprecated = false;
	
	@Column
	private Boolean used;
	
	@Column(nullable = false)
	@NotNull
	@Enumerated(EnumType.STRING)
	@Description("Type")
	private AccountType account_type;
	
	@Column
	@Description("Bring Accounts Balance Forward")
	private Boolean include_initial_balance;
	
	@Column
	@Enumerated(EnumType.STRING)
	@Description("Internal Group")
	private InternalGroup internal_group;
	
	@Column
	@Description("Allow Reconciliation")
	private Boolean reconcile;
	
	@Transient
	private List<Integer> tax_ids;
	
	@Column
	@ManyToMany(targetEntity = Tax.class, fetch = FetchType.LAZY)
	@JoinTable(name = "account_account_tax_default_rel", joinColumns = {@JoinColumn(name = "account_id")}, inverseJoinColumns = {@JoinColumn(name = "tax_id")})
	@Description("Default Taxes")
	private List<Tax> taxes;
	
	@Column
	@Description("Internal Notes")
	private String note;
	
	@Transient
	private List<Integer> company_ids;
	
	@ManyToMany(targetEntity = Company.class, fetch = FetchType.LAZY)
	@NotNull
	@Description("Companies")
	private List<Company> companies;
	
	@Transient
	private List<Integer> code_mapping_ids;
	
	@OneToMany(targetEntity = CodeMapping.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	private List<CodeMapping> code_mappings;
	
	@Transient
	private List<Integer> tag_ids;
	
	@ManyToMany(targetEntity = Tag.class, fetch = FetchType.LAZY)
	@JoinTable(name = "account_account_account_tag")
	@Description("Tags")
	private List<Tag> tags;
	
	@Transient
	private Integer group_id;
	
	@ManyToOne(targetEntity = Group.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	private Group group;
	
	@Transient
	private Integer root_id;
	
	@ManyToOne(targetEntity = Root.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "root_id")
	private Root root;
	
	@Transient
	private List<Integer> allowed_journal_ids;
	
	@ManyToMany(targetEntity = Journal.class, fetch = FetchType.LAZY)
	@Description("Allowed Journals")
	private List<String> allowed_journals;
	
	@Column
	@Description("Opening Debit")
	private String opening_debit;
	
	@Column
	@Description("Opening Credit")
	private String opening_credit;
	
	@Column
	@Description("Opening Balance")
	private String opening_balance;
	
	@Column
	private Float current_balance;
	
	@Column
	private Integer related_taxes_amount;
	
	@Column
	@DefaultValue("false")
	private Boolean non_trade = false;
}
