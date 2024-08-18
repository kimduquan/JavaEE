package erp.account.schema;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.partner.Partner;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "account_bank_statement_line")
@Description("Bank Statement Line")
public class BankStatementLine extends Move {

	@Column(updatable = false)
	@ManyToOne(targetEntity = Move.class)
	@NotNull
	@Description("Journal Entry")
	private String move_id;
	
	@Column
	@ManyToOne(targetEntity = BankStatement.class)
	@Description("Statement")
	private String statement_id;
	
	@Column
	@ManyToMany(targetEntity = Payment.class)
	@Description("Auto-generated Payments")
	private List<String> payment_ids;
	
	@Column
	@DefaultValue("1")
	private Integer sequence = 1;
	
	@Column
	@ManyToOne(targetEntity = Partner.class)
	@Description("Partner")
	private String partner_id;
	
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
	
	@Column
	@ManyToOne(targetEntity = Currency.class)
	private String currency_id;
}
