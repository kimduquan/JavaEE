package epf.transaction.schema;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * 
 */
@Type(TransactionSchema.TRANSACTION)
@Schema(name = TransactionSchema.TRANSACTION, title = "Transaction")
@Entity(name = TransactionSchema.TRANSACTION)
@Table(schema = TransactionSchema.SCHEMA, name = "TRANSACTION", indexes = {@Index(columnList = "ID")})
public class Transaction {

	@Id
	private String id;
	
	/**
	 *
	 */
	@Column
	private Boolean rollbackOnly;
	
	/**
	 *
	 */
	@Column
	private Boolean active;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public Boolean getRollbackOnly() {
		return rollbackOnly;
	}

	public void setRollbackOnly(final Boolean rollbackOnly) {
		this.rollbackOnly = rollbackOnly;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(final Boolean active) {
		this.active = active;
	}
}
