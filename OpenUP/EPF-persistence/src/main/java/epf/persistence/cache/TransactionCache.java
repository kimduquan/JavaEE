package epf.persistence.cache;

import java.util.Objects;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import epf.persistence.internal.EntityTransaction;
import epf.util.json.ext.Decoder;
import epf.util.json.ext.Encoder;

@ApplicationScoped
public class TransactionCache {
	
	@Inject
	transient Cache cache;
	
	private transient final Encoder encoder = new Encoder();
	
	private transient final Decoder decoder = new Decoder();

	public void put(final EntityTransaction transaction) throws Exception {
		Objects.requireNonNull(transaction, "EntityTransaction");
		Objects.requireNonNull(transaction.getId(), "EntityTransaction.Id");
		final String key = transaction.getId();
		final String value = encoder.encode(transaction);
		cache.put(key, value);
	}
	
	public EntityTransaction remove(final String id) throws Exception {
		Objects.requireNonNull(id, "String");
		EntityTransaction transaction = null;
		final String value = cache.get(id);
		cache.remove(id);
		if(value != null) {
			transaction = (EntityTransaction) decoder.decode(value);
		}
		return transaction;
	}
}
