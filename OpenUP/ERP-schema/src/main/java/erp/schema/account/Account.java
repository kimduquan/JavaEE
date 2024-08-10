package erp.schema.account;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.schema.resource.Company;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "account_account")
@Description("Account")
public class Account {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Account Name")
	private String name;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Currency.class)
	@Description("Account Currency")
	private String currency_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne
	private String company_currency_id;
	
	/**
	 * 
	 */
	@Column(length = 64, nullable = false)
	@NotNull
	private String code;
	
	/**
	 * 
	 */
	@Column
	private Boolean deprecated = false;
	
	/**
	 * 
	 */
	@Column
	private Boolean used;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Enumerated(EnumType.STRING)
	@Description("Type")
	private String account_type;
	
	/**
	 * 
	 */
	@Column
	@Description("Bring Accounts Balance Forward")
	private Boolean include_initial_balance;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Description("Internal Group")
	private String internal_group;
	
	/**
	 * 
	 */
	@Column
	@Description("Allow Reconciliation")
	private Boolean reconcile;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Tax.class)
	@ElementCollection(targetClass = Tax.class)
	@CollectionTable(name = "account_tax")
	@Description("Default Taxes")
	private List<String> tax_ids;
	
	/**
	 * 
	 */
	@Column
	@Description("Internal Notes")
	private String note;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = Company.class)
	@NotNull
	@Description("Company")
	private String company_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = AccountTag.class)
	@ElementCollection(targetClass = AccountTag.class)
	@CollectionTable(name = "account_account_tag")
	@Description("Tags")
	private List<String> tag_ids;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@ManyToOne(targetEntity = Group.class)
	@Description("Account prefixes can determine account groups.")
	private String group_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Root.class)
	private String root_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Journal.class)
	@ElementCollection(targetClass = Journal.class)
	@CollectionTable(name = "account_journal")
	@Description("Allowed Journals")
	private List<String> allowed_journal_ids;
	
	/**
	 * 
	 */
	@Column
	@Description("Opening Debit")
	private String opening_debit;
	
	/**
	 * 
	 */
	@Column
	@Description("Opening Credit")
	private String opening_credit;
	
	/**
	 * 
	 */
	@Column
	@Description("Opening Balance")
	private String opening_balance;
	
	/**
	 * 
	 */
	@Column
	private Float current_balance;
	
	/**
	 * 
	 */
	@Column
	private Integer related_taxes_amount;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("false")
	private Boolean non_trade = false;
}
