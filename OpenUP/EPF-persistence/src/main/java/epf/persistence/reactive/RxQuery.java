package epf.persistence.reactive;

import java.util.List;
import java.util.concurrent.CompletionStage;
import epf.persistence.ext.Query;

public class RxQuery<R> implements epf.persistence.ext.Query<R> {
	
	/**
	 * 
	 */
	private transient org.hibernate.reactive.mutiny.Mutiny.Query<R> query;
	
	/**
	 * @param query
	 */
	RxQuery(final org.hibernate.reactive.mutiny.Mutiny.Query<R> query) {
		this.query = query;
	}

	@Override
	public Query<R> setParameter(final int parameter, final Object argument) {
		query = query.setParameter(parameter, argument);
		return this;
	}

	@Override
	public Query<R> setParameter(final String parameter, final Object argument) {
		query = query.setParameter(parameter, argument);
		return this;
	}

	@Override
	public Query<R> setMaxResults(int maxResults) {
		query = query.setMaxResults(maxResults);
		return this;
	}

	@Override
	public Query<R> setFirstResult(int firstResult) {
		query = query.setFirstResult(firstResult);
		return this;
	}

	@Override
	public CompletionStage<R> getSingleResult() {
		return query.getSingleResult().subscribeAsCompletionStage();
	}

	@Override
	public CompletionStage<List<R>> getResultList() {
		return query.getResultList().subscribeAsCompletionStage();
	}

	@Override
	public CompletionStage<Integer> executeUpdate() {
		return query.executeUpdate().subscribeAsCompletionStage();
	}
}
