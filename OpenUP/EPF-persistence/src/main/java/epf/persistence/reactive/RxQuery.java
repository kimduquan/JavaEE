package epf.persistence.reactive;

import java.util.List;
import java.util.concurrent.CompletionStage;
import org.hibernate.reactive.mutiny.Mutiny.Query;

public class RxQuery<R> implements epf.persistence.util.Query<R> {
	
	/**
	 * 
	 */
	private transient Query<R> query;
	
	/**
	 * @param query
	 */
	public RxQuery(final Query<R> query) {
		this.query = query;
	}

	@Override
	public epf.persistence.util.Query<R> setParameter(final int parameter, final Object argument) {
		query = query.setParameter(parameter, argument);
		return this;
	}

	@Override
	public epf.persistence.util.Query<R> setParameter(final String parameter, final Object argument) {
		query = query.setParameter(parameter, argument);
		return this;
	}

	@Override
	public epf.persistence.util.Query<R> setMaxResults(int maxResults) {
		query = query.setMaxResults(maxResults);
		return this;
	}

	@Override
	public epf.persistence.util.Query<R> setFirstResult(int firstResult) {
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
