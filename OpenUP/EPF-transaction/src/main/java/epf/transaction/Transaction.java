package epf.transaction;

import java.io.InputStream;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.faulttolerance.Fallback;
import epf.naming.Naming;
import epf.transaction.schema.TransactionSchema;

/**
 * 
 */
@Path(Naming.TRANSACTION)
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
@RequestScoped
public class Transaction implements epf.client.transaction.Transaction {

	/**
	 *
	 */
	private epf.transaction.schema.Transaction transaction;
	
	/**
	 *
	 */
	@PersistenceContext(unitName = "EPF-transaction")
	private transient EntityManager manager;

	@Override
	@Transactional
	@Fallback(fallbackMethod = "rollback")
	public Response commit(
			final SecurityContext context, 
			final HttpHeaders headers, 
			final UriInfo uriInfo, 
			final Request req,
			final InputStream body) throws Exception {
		final Object transactionId = manager.createNamedQuery(TransactionSchema.Query.CURRENT_TRANSACTION_ID).getSingleResult();
		transaction = new epf.transaction.schema.Transaction();
		transaction.setActive(true);
		transaction.setId(String.valueOf(transactionId));
		transaction.setRollbackOnly(false);
		manager.persist(transaction);
		
		manager.createNamedQuery(TransactionSchema.Query.COMMIT_TRANSACTION).getSingleResult();
		manager.remove(transaction);
		return Response.ok().build();
	}

	@Override
	public Response rollback(
			final SecurityContext context, 
			final HttpHeaders headers, 
			final UriInfo uriInfo, 
			final Request req,
			final InputStream body) throws Exception {
		transaction = null;
		return Response.serverError().build();
	}
}
