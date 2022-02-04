package epf.persistence.reactive;

import java.util.List;
import java.util.concurrent.CompletionStage;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.reactive.stage.Stage.Query;
import org.hibernate.reactive.stage.Stage.Session;

public class RxQuery<R> implements epf.persistence.util.Query<R> {
	
	/**
	 * 
	 */
	private transient CompletionStage<Query<R>> query;
	
	/**
	 * @param session
	 * @param criteria
	 */
	public RxQuery(final CompletionStage<Session> session, final CriteriaQuery<R> criteria) {
		this.query = session.thenApply(ss -> ss.createQuery(criteria));
	}
	
	/**
	 * @param session
	 * @param nativeSql
	 */
	public RxQuery(final CompletionStage<Session> session, final String nativeSql) {
		this.query = session.thenApply(ss -> ss.createNativeQuery(nativeSql));
	}

	@Override
	public epf.persistence.util.Query<R> setParameter(final int parameter, final Object argument) {
		query = query.thenApply(q -> q.setParameter(parameter, argument));
		return this;
	}

	@Override
	public epf.persistence.util.Query<R> setParameter(final String parameter, final Object argument) {
		query = query.thenApply(q -> q.setParameter(parameter, argument));
		return this;
	}

	@Override
	public epf.persistence.util.Query<R> setMaxResults(int maxResults) {
		query = query.thenApply(q -> q.setMaxResults(maxResults));
		return this;
	}

	@Override
	public epf.persistence.util.Query<R> setFirstResult(int firstResult) {
		query = query.thenApply(q -> q.setFirstResult(firstResult));
		return this;
	}

	@Override
	public CompletionStage<R> getSingleResult() {
		return query.thenCompose(q -> q.getSingleResult());
	}

	@Override
	public CompletionStage<List<R>> getResultList() {
		return query.thenCompose(q -> q.getResultList());
	}

	@Override
	public CompletionStage<Integer> executeUpdate() {
		return query.thenCompose(q -> q.executeUpdate());
	}
}
