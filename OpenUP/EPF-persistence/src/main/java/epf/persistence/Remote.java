package epf.persistence;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import javax.enterprise.inject.spi.CDI;
import epf.client.persistence.PersistenceInterface;
import epf.client.persistence.SearchData;

/**
 * @author PC
 *
 */
public class Remote implements PersistenceInterface {

	@Override
	public <T extends Serializable> T persist(final String schema, final String entity, final T object) throws RemoteException {
		return CDI.current().select(PersistenceInterface.class).get().persist(schema, entity, object);
	}

	@Override
	public <T extends Serializable> void merge(final String schema, final String entity, final String entityId, final T object)
			throws RemoteException {
		CDI.current().select(PersistenceInterface.class).get().merge(schema, entity, entityId, object);
	}

	@Override
	public void remove(final String schema, final String entity, final String entityId) throws RemoteException {
		CDI.current().select(PersistenceInterface.class).get().remove(schema, entity, entityId);
	}

	@Override
	public <T extends Serializable> T find(final String schema, final String entity, final String entityId) throws RemoteException {
		return CDI.current().select(PersistenceInterface.class).get().find(schema, entity, entityId);
	}

	@Override
	public List<SearchData> search(final String text, final Integer firstResult, final Integer maxResults) throws RemoteException {
		return CDI.current().select(PersistenceInterface.class).get().search(text, firstResult, maxResults);
	}

}
