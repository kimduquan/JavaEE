package epf.persistence.remote;

import java.rmi.RemoteException;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class RemoteConnectionObject<U extends Connection, V extends RemoteConnection> extends RemoteWrapperObject<U, V> implements RemoteConnection {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RemoteConnectionObject(final U connection, final V _connection) throws RemoteException {
		super(connection, _connection);
	}

	@Override
	public Statement createStatement() throws SQLException {
		try {
			return _object._createStatement();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		try {
			return _object._prepareStatement(sql);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		try {
			return _object._prepareCall(sql);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public String nativeSQL(String sql) throws SQLException {
		try {
			return _object._nativeSQL(sql);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		try {
			_object._setAutoCommit(autoCommit);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		try {
			return _object._getAutoCommit();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void commit() throws SQLException {
		try {
			_object._commit();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void rollback() throws SQLException {
		try {
			_object._rollback();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void close() throws SQLException {
		try {
			_object._close();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public boolean isClosed() throws SQLException {
		try {
			return _object._isClosed();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		try {
			return _object._getMetaData();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setReadOnly(boolean readOnly) throws SQLException {
		try {
			_object._setReadOnly(readOnly);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		try {
			return _object._isReadOnly();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setCatalog(String catalog) throws SQLException {
		try {
			_object._setCatalog(catalog);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public String getCatalog() throws SQLException {
		try {
			return _object._getCatalog();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setTransactionIsolation(int level) throws SQLException {
		try {
			_object._setTransactionIsolation(level);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		try {
			return _object._getTransactionIsolation();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		try {
			return _object._getWarnings();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void clearWarnings() throws SQLException {
		try {
			_object._clearWarnings();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		try {
			return _object._createStatement(resultSetType, resultSetConcurrency);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		try {
			return _object._prepareStatement(sql, resultSetType, resultSetConcurrency);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		try {
			return _object._prepareCall(sql, resultSetType, resultSetConcurrency);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		try {
			return _object._getTypeMap();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		try {
			_object._setTypeMap(map);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setHoldability(int holdability) throws SQLException {
		try {
			_object._setHoldability(holdability);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public int getHoldability() throws SQLException {
		try {
			return _object._getHoldability();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		try {
			return _object._setSavepoint();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public Savepoint setSavepoint(String name) throws SQLException {
		try {
			return _object._setSavepoint(name);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void rollback(Savepoint savepoint) throws SQLException {
		try {
			_object._rollback(savepoint);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		try {
			_object._releaseSavepoint(savepoint);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		try {
			return _object._createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		try {
			return _object._prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		try {
			return _object._prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		try {
			return _object._prepareStatement(sql, autoGeneratedKeys);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		try {
			return _object._prepareStatement(sql, columnIndexes);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		try {
			return _object._prepareStatement(sql, columnNames);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public Clob createClob() throws SQLException {
		try {
			return _object._createClob();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public Blob createBlob() throws SQLException {
		try {
			return _object._createBlob();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public NClob createNClob() throws SQLException {
		try {
			return _object._createNClob();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		try {
			return _object._createSQLXML();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public boolean isValid(int timeout) throws SQLException {
		try {
			return _object._isValid(timeout);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		try {
			_object._setClientInfo(name, value);
		}
		catch(RemoteException ex) {
			throw new SQLClientInfoException();
		}
	}

	@Override
	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		try {
			_object._setClientInfo(properties);
		}
		catch(RemoteException ex) {
			throw new SQLClientInfoException();
		}
	}

	@Override
	public String getClientInfo(String name) throws SQLException {
		try {
			return _object._getClientInfo(name);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		try {
			return _object._getClientInfo();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		try {
			return _object._createArrayOf(typeName, elements);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		try {
			return _object._createStruct(typeName, attributes);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setSchema(String schema) throws SQLException {
		try {
			_object._setSchema(schema);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public String getSchema() throws SQLException {
		try {
			return _object._getSchema();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void abort(Executor executor) throws SQLException {
		try {
			_object._abort(executor);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		try {
			_object._setNetworkTimeout(executor, milliseconds);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		try {
			return _object._getNetworkTimeout();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public RemoteStatement _createStatement() throws RemoteException, SQLException {
		return new RemoteStatementObject<>(object.createStatement(), null);
	}

	@Override
	public RemotePreparedStatement _prepareStatement(String sql) throws RemoteException, SQLException {
		try {
			return new RemotePreparedStatementObject<>(object.prepareStatement(sql), null);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public RemoteCallableStatement _prepareCall(String sql) throws RemoteException, SQLException {
		try {
			return new RemoteCallableStatementObject<>(object.prepareCall(sql), null);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public String _nativeSQL(String sql) throws RemoteException, SQLException {
		return object.nativeSQL(sql);
	}

	@Override
	public void _setAutoCommit(boolean autoCommit) throws RemoteException, SQLException {
		object.setAutoCommit(autoCommit);
	}

	@Override
	public boolean _getAutoCommit() throws RemoteException, SQLException {
		return object.getAutoCommit();
	}

	@Override
	public void _commit() throws RemoteException, SQLException {
		object.commit();
	}

	@Override
	public void _rollback() throws RemoteException, SQLException {
		object.rollback();
	}

	@Override
	public void _close() throws RemoteException, SQLException {
		object.close();
	}

	@Override
	public boolean _isClosed() throws RemoteException, SQLException {
		return object.isClosed();
	}

	@Override
	public RemoteDatabaseMetaData _getMetaData() throws RemoteException, SQLException {
		return null;
	}

	@Override
	public void _setReadOnly(boolean readOnly) throws RemoteException, SQLException {
		object.setReadOnly(readOnly);
	}

	@Override
	public boolean _isReadOnly() throws RemoteException, SQLException {
		return object.isReadOnly();
	}

	@Override
	public void _setCatalog(String catalog) throws RemoteException, SQLException {
		object.setCatalog(catalog);
	}

	@Override
	public String _getCatalog() throws RemoteException, SQLException {
		return object.getCatalog();
	}

	@Override
	public void _setTransactionIsolation(int level) throws RemoteException, SQLException {
		object.setTransactionIsolation(level);
	}

	@Override
	public int _getTransactionIsolation() throws RemoteException, SQLException {
		return object.getTransactionIsolation();
	}

	@Override
	public SQLWarning _getWarnings() throws RemoteException, SQLException {
		return object.getWarnings();
	}

	@Override
	public void _clearWarnings() throws RemoteException, SQLException {
		object.clearWarnings();
	}

	@Override
	public RemoteStatement _createStatement(int resultSetType, int resultSetConcurrency)
			throws RemoteException, SQLException {
		return new RemoteStatementObject<>(object.createStatement(resultSetType, resultSetConcurrency), null);
	}

	@Override
	public RemotePreparedStatement _prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws RemoteException, SQLException {
		return new RemotePreparedStatementObject<>(object.prepareStatement(sql, resultSetType, resultSetConcurrency), null);
	}

	@Override
	public RemoteCallableStatement _prepareCall(String sql, int resultSetType, int resultSetConcurrency)
			throws RemoteException, SQLException {
		return new RemoteCallableStatementObject<>(object.prepareCall(sql, resultSetType, resultSetConcurrency), null);
	}

	@Override
	public Map<String, Class<?>> _getTypeMap() throws RemoteException, SQLException {
		return object.getTypeMap();
	}

	@Override
	public void _setTypeMap(Map<String, Class<?>> map) throws RemoteException, SQLException {
		object.setTypeMap(map);
	}

	@Override
	public void _setHoldability(int holdability) throws RemoteException, SQLException {
		object.setHoldability(holdability);
	}

	@Override
	public int _getHoldability() throws RemoteException, SQLException {
		return object.getHoldability();
	}

	@Override
	public RemoteSavepoint _setSavepoint() throws RemoteException, SQLException {
		return new RemoteSavepointObject<>(object.setSavepoint(), null);
	}

	@Override
	public RemoteSavepoint _setSavepoint(String name) throws RemoteException, SQLException {
		return new RemoteSavepointObject<>(object.setSavepoint(name), null);
	}

	@Override
	public void _rollback(Savepoint savepoint) throws RemoteException, SQLException {
		object.rollback();
	}

	@Override
	public void _releaseSavepoint(Savepoint savepoint) throws RemoteException, SQLException {
		object.releaseSavepoint(savepoint);
	}

	@Override
	public RemoteStatement _createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws RemoteException, SQLException {
		return new RemoteStatementObject<>(object.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability), null);
	}

	@Override
	public RemotePreparedStatement _prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws RemoteException, SQLException {
		return new RemotePreparedStatementObject<>(object.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability), null);
	}

	@Override
	public RemoteCallableStatement _prepareCall(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws RemoteException, SQLException {
		return new RemoteCallableStatementObject<>(object.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability), null);
	}

	@Override
	public RemotePreparedStatement _prepareStatement(String sql, int autoGeneratedKeys) throws RemoteException, SQLException {
		return new RemotePreparedStatementObject<>(object.prepareStatement(sql, autoGeneratedKeys), null);
	}

	@Override
	public RemotePreparedStatement _prepareStatement(String sql, int[] columnIndexes) throws RemoteException, SQLException {
		return new RemotePreparedStatementObject<>(object.prepareStatement(sql, columnIndexes), null);
	}

	@Override
	public RemotePreparedStatement _prepareStatement(String sql, String[] columnNames) throws RemoteException, SQLException {
		return new RemotePreparedStatementObject<>(object.prepareStatement(sql, columnNames), null);
	}

	@Override
	public RemoteClob _createClob() throws RemoteException, SQLException {
		return null;
	}

	@Override
	public RemoteBlob _createBlob() throws RemoteException, SQLException {
		return null;
	}

	@Override
	public RemoteNClob _createNClob() throws RemoteException, SQLException {
		return null;
	}

	@Override
	public RemoteSQLXML _createSQLXML() throws RemoteException, SQLException {
		return null;
	}

	@Override
	public boolean _isValid(int timeout) throws RemoteException, SQLException {
		return object.isValid(timeout);
	}

	@Override
	public void _setClientInfo(String name, String value) throws RemoteException, SQLClientInfoException {
		object.setClientInfo(name, value);
	}

	@Override
	public void _setClientInfo(Properties properties) throws RemoteException, SQLClientInfoException {
		object.setClientInfo(properties);
	}

	@Override
	public String _getClientInfo(String name) throws RemoteException, SQLException {
		return object.getClientInfo(name);
	}

	@Override
	public Properties _getClientInfo() throws RemoteException, SQLException {
		return object.getClientInfo();
	}

	@Override
	public RemoteArray _createArrayOf(String typeName, Object[] elements) throws RemoteException, SQLException {
		return null;
	}

	@Override
	public RemoteStruct _createStruct(String typeName, Object[] attributes) throws RemoteException, SQLException {
		return null;
	}

	@Override
	public void _setSchema(String schema) throws RemoteException, SQLException {
		object.setSchema(schema);
	}

	@Override
	public String _getSchema() throws RemoteException, SQLException {
		return object.getSchema();
	}

	@Override
	public void _abort(Executor executor) throws RemoteException, SQLException {
		object.abort(executor);
	}

	@Override
	public void _setNetworkTimeout(Executor executor, int milliseconds) throws RemoteException, SQLException {
		object.setNetworkTimeout(executor, milliseconds);
	}

	@Override
	public int _getNetworkTimeout() throws RemoteException, SQLException {
		return object.getNetworkTimeout();
	}
}
