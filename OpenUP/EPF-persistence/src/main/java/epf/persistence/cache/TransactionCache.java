package epf.persistence.cache;

import java.util.Objects;
import java.util.Optional;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.jnosql.mapping.keyvalue.KeyValueTemplate;
import epf.persistence.internal.EntityTransaction;
import epf.util.json.ext.Decoder;
import epf.util.json.ext.Encoder;

@ApplicationScoped
public class TransactionCache {
	
	private transient final Encoder encoder = new Encoder();
	
	private transient final Decoder decoder = new Decoder();
	
	@Inject
	private KeyValueTemplate cache;

	public void put(final EntityTransaction transaction) throws Exception {
		Objects.requireNonNull(transaction, "EntityTransaction");
		Objects.requireNonNull(transaction.getId(), "EntityTransaction.Id");
		final CacheEntry value = new CacheEntry();
		value.setKey(transaction.getId());
		value.setValue(encoder.encode(transaction));
		cache.put(value);
	}
	
	public EntityTransaction remove(final String id) throws Exception {
		Objects.requireNonNull(id, "String");
		EntityTransaction transaction = null;
		final Optional<CacheEntry> value = cache.get(id, CacheEntry.class);
		if(value.isPresent()) {
			transaction = (EntityTransaction) decoder.decode(value.get().getValue());
		}
		return transaction;
	}
}
