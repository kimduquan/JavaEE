package epf.persistence.internal;

import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import epf.persistence.util.SQLStateExceptionMapper;

@Provider
public class PersistenceExceptionMapper extends SQLStateExceptionMapper implements ExceptionMapper<Exception> {

    private static final long serialVersionUID = 1L;
}
