package epf.transaction.schema;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.schema.utility.EPFEntity;

/**
 * 
 */
@Type(TransactionSchema.TRANSACTION)
@Schema(name = TransactionSchema.TRANSACTION, title = "Transaction")
@Entity(name = TransactionSchema.TRANSACTION)
@Table(schema = TransactionSchema.SCHEMA, name = "TRANSACTION", indexes = {@Index(columnList = "ID")})
@NamedNativeQuery(name = TransactionSchema.Query.CURRENT_TRANSACTION_ID, query = "SELECT trx_id FROM information_schema.innodb_trx WHERE innodb_trx.trx_mysql_thread_id = connection_id()")
@NamedNativeQuery(name = TransactionSchema.Query.COMMIT_TRANSACTION, query = "SELECT * FROM TRANSACTION WHERE id = ? FOR UPDATE;")
public class Transaction extends EPFEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
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
