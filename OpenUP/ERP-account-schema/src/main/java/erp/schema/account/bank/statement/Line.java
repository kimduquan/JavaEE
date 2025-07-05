package erp.schema.account.bank.statement;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Company;
import erp.base.schema.res.Currency;
import erp.base.schema.res.Partner;
import erp.schema.account.Journal;
import erp.schema.account.Move;
import erp.schema.account.Payment;
import erp.schema.account.bank.Statement;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "account_bank_statement_line")
@Description("Bank Statement Line")
public class Line extends Move {
	
	@Transient
	private Integer move_id;

	@ManyToOne(targetEntity = Move.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "move_id", nullable = false, updatable = false)
	@NotNull
	@Description("Journal Entry")
	private Move move;
	
	@Transient
	private Integer journal_id;
	
	@ManyToOne(targetEntity = Journal.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "journal_id", nullable = false)
	@NotNull
	private Journal journal;
	
	@Transient
	private Integer company_id;
	
	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	@NotNull
	private Journal company;
	
	@Transient
	private Integer statement_id;
	
	@Column
	@ManyToOne(targetEntity = Statement.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "statement_id")
	@Description("Statement")
	private String statement;
	
	@Transient
	private List<Integer> payment_ids;
	
	@Column
	@ManyToMany(targetEntity = Payment.class, fetch = FetchType.LAZY)
	@Description("Auto-generated Payments")
	private List<Payment> payments;
	
	@Column
	@DefaultValue("1")
	private Integer sequence = 1;
	
	@Transient
	private Integer partner_id;
	
	@ManyToOne(targetEntity = Partner.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "partner_id")
	@Description("Partner")
	private Partner partner;
	
	@Column
	@Description("Bank Account Number")
	private String account_number;
	
	@Column
	private String partner_name;
	
	@Column
	private String transaction_type;
	
	@Column
	@Description("Label")
	private String payment_ref;
	
	@Transient
	private Integer currency_id;
	
	@ManyToOne(targetEntity = Currency.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "currency_id")
	@Description("Journal Currency")
	private Currency currency;
	
	@Column
	private String amount;
	
	@Column
	private String running_balance;
	
	@Transient
	private Integer foreign_currency_id;
	
	@ManyToOne(targetEntity = Currency.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "foreign_currency_id")
	@Description("Foreign Currency")
	private Currency foreign_currency;
	
	@Column
	@Description("Amount in Currency")
	private String amount_currency;
	
	@Column
	@Description("Residual Amount")
	private Float amount_residual;
	
	@Column
	private String country_code;
	
	@Column
	@Description("Internal Reference")
	private String internal_index;
	
	@Column
	@Description("Is Reconciled")
	private Boolean is_reconciled;
	
	@Column
	private Boolean statement_complete;
	
	@Column
	private Boolean statement_valid;
	
	@Column
	private String statement_balance_end_real;
	
	@Column
	@Description("Statement Name")
	private String statement_name;
	
	@Column(updatable = false)
	private Object transaction_details;
}
