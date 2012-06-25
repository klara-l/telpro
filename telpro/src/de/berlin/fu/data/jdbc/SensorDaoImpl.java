/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package de.berlin.fu.data.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import de.berlin.fu.data.dao.SensorDao;
import de.berlin.fu.data.dto.Sensor;
import de.berlin.fu.data.dto.SensorPk;
import de.berlin.fu.data.exceptions.SensorDaoException;

public class SensorDaoImpl extends AbstractDAO implements SensorDao {
	/**
	 * The factory class for this DAO has two versions of the create() method -
	 * one that takes no arguments and one that takes a Connection argument. If
	 * the Connection version is chosen then the connection will be stored in
	 * this attribute and will be used by all calls to this DAO, otherwise a new
	 * Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	/**
	 * All finder methods in this class use this SELECT constant to build their
	 * queries
	 */
	protected final String SQL_SELECT = "SELECT idSensor, IP, Location FROM "
			+ getTableName() + "";

	/**
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/**
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName()
			+ " ( idSensor, IP, Location ) VALUES ( ?, ?, ? )";

	/**
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName()
			+ " SET idSensor = ?, IP = ?, Location = ? WHERE idSensor = ?";

	/**
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName()
			+ " WHERE idSensor = ?";

	/**
	 * Index of column idSensor
	 */
	protected static final int COLUMN_ID_SENSOR = 1;

	/**
	 * Index of column IP
	 */
	protected static final int COLUMN_IP = 2;

	/**
	 * Index of column Location
	 */
	protected static final int COLUMN_LOCATION = 3;

	/**
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 3;

	/**
	 * Index of primary-key column idSensor
	 */
	protected static final int PK_COLUMN_ID_SENSOR = 1;

	/**
	 * Inserts a new row in the Sensor table.
	 */
	public SensorPk insert(Sensor dto) throws SensorDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			// get the user-specified connection or get a connection from the
			// ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();

			stmt = conn.prepareStatement(SQL_INSERT);
			int index = 1;
			stmt.setString(index++, dto.getIdSensor());
			if (dto.isIpNull()) {
				stmt.setNull(index++, java.sql.Types.INTEGER);
			} else {
				stmt.setInt(index++, dto.getIp());
			}

			stmt.setString(index++, dto.getLocation());
			System.out.println("Executing " + SQL_INSERT + " with DTO: " + dto);
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			System.out.println(rows + " rows affected (" + (t2 - t1) + " ms)");
			reset(dto);
			return dto.createPk();
		} catch (Exception _e) {
			_e.printStackTrace();
			throw new SensorDaoException("Exception: " + _e.getMessage(), _e);
		} finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}

		}

	}

	/**
	 * Updates a single row in the Sensor table.
	 */
	public void update(SensorPk pk, Sensor dto) throws SensorDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			// get the user-specified connection or get a connection from the
			// ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();

			System.out.println("Executing " + SQL_UPDATE + " with DTO: " + dto);
			stmt = conn.prepareStatement(SQL_UPDATE);
			int index = 1;
			stmt.setString(index++, dto.getIdSensor());
			if (dto.isIpNull()) {
				stmt.setNull(index++, java.sql.Types.INTEGER);
			} else {
				stmt.setInt(index++, dto.getIp());
			}

			stmt.setString(index++, dto.getLocation());
			stmt.setString(4, pk.getIdSensor());
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			System.out.println(rows + " rows affected (" + (t2 - t1) + " ms)");
		} catch (Exception _e) {
			_e.printStackTrace();
			throw new SensorDaoException("Exception: " + _e.getMessage(), _e);
		} finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}

		}

	}

	/**
	 * Deletes a single row in the Sensor table.
	 */
	public void delete(SensorPk pk) throws SensorDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			// get the user-specified connection or get a connection from the
			// ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();

			System.out.println("Executing " + SQL_DELETE + " with PK: " + pk);
			stmt = conn.prepareStatement(SQL_DELETE);
			stmt.setString(1, pk.getIdSensor());
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			System.out.println(rows + " rows affected (" + (t2 - t1) + " ms)");
		} catch (Exception _e) {
			_e.printStackTrace();
			throw new SensorDaoException("Exception: " + _e.getMessage(), _e);
		} finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}

		}

	}

	/**
	 * Returns the rows from the Sensor table that matches the specified
	 * primary-key value.
	 */
	public Sensor findByPrimaryKey(SensorPk pk) throws SensorDaoException {
		return findByPrimaryKey(pk.getIdSensor());
	}

	/**
	 * Returns all rows from the Sensor table that match the criteria 'idSensor
	 * = :idSensor'.
	 */
	public Sensor findByPrimaryKey(String idSensor) throws SensorDaoException {
		Sensor ret[] = findByDynamicSelect(SQL_SELECT + " WHERE idSensor = ?",
				new Object[] { idSensor });
		return ret.length == 0 ? null : ret[0];
	}

	/**
	 * Returns all rows from the Sensor table that match the criteria ''.
	 */
	public Sensor[] findAll() throws SensorDaoException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY idSensor", null);
	}

	/**
	 * Returns all rows from the Sensor table that match the criteria 'idSensor
	 * = :idSensor'.
	 */
	public Sensor[] findWhereIdSensorEquals(String idSensor)
			throws SensorDaoException {
		return findByDynamicSelect(SQL_SELECT
				+ " WHERE idSensor = ? ORDER BY idSensor",
				new Object[] { idSensor });
	}

	/**
	 * Returns all rows from the Sensor table that match the criteria 'IP =
	 * :ip'.
	 */
	public Sensor[] findWhereIpEquals(int ip) throws SensorDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE IP = ? ORDER BY IP",
				new Object[] { Integer.valueOf(ip) });
	}

	/**
	 * Returns all rows from the Sensor table that match the criteria 'Location
	 * = :location'.
	 */
	public Sensor[] findWhereLocationEquals(String location)
			throws SensorDaoException {
		return findByDynamicSelect(SQL_SELECT
				+ " WHERE Location = ? ORDER BY Location",
				new Object[] { location });
	}

	/**
	 * Method 'SensorDaoImpl'
	 * 
	 */
	public SensorDaoImpl() {
	}

	/**
	 * Method 'SensorDaoImpl'
	 * 
	 * @param userConn
	 */
	public SensorDaoImpl(final java.sql.Connection userConn) {
		this.userConn = userConn;
	}

	/**
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	/**
	 * Gets the value of maxRows
	 */
	public int getMaxRows() {
		return maxRows;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName() {
		return "telpro.Sensor";
	}

	/**
	 * Fetches a single row from the result set
	 */
	protected Sensor fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()) {
			Sensor dto = new Sensor();
			populateDto(dto, rs);
			return dto;
		} else {
			return null;
		}

	}

	/**
	 * Fetches multiple rows from the result set
	 */
	protected Sensor[] fetchMultiResults(ResultSet rs) throws SQLException {
		Collection<Sensor> resultList = new ArrayList<Sensor>();
		while (rs.next()) {
			Sensor dto = new Sensor();
			populateDto(dto, rs);
			resultList.add(dto);
		}

		Sensor ret[] = new Sensor[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}

	/**
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(Sensor dto, ResultSet rs) throws SQLException {
		dto.setIdSensor(rs.getString(COLUMN_ID_SENSOR));
		dto.setIp(rs.getInt(COLUMN_IP));
		if (rs.wasNull()) {
			dto.setIpNull(true);
		}

		dto.setLocation(rs.getString(COLUMN_LOCATION));
	}

	/**
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(Sensor dto) {
	}

	/**
	 * Returns all rows from the Sensor table that match the specified arbitrary
	 * SQL statement
	 */
	public Sensor[] findByDynamicSelect(String sql, Object[] sqlParams)
			throws SensorDaoException {
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// get the user-specified connection or get a connection from the
			// ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();

			// construct the SQL statement
			final String SQL = sql;

			System.out.println("Executing " + SQL);
			// prepare statement
			stmt = conn.prepareStatement(SQL);
			stmt.setMaxRows(maxRows);

			// bind parameters
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {
				stmt.setObject(i + 1, sqlParams[i]);
			}

			rs = stmt.executeQuery();

			// fetch the results
			return fetchMultiResults(rs);
		} catch (Exception _e) {
			_e.printStackTrace();
			throw new SensorDaoException("Exception: " + _e.getMessage(), _e);
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}

		}

	}

	/**
	 * Returns all rows from the Sensor table that match the specified arbitrary
	 * SQL statement
	 */
	public Sensor[] findByDynamicWhere(String sql, Object[] sqlParams)
			throws SensorDaoException {
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// get the user-specified connection or get a connection from the
			// ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();

			// construct the SQL statement
			final String SQL = SQL_SELECT + " WHERE " + sql;

			System.out.println("Executing " + SQL);
			// prepare statement
			stmt = conn.prepareStatement(SQL);
			stmt.setMaxRows(maxRows);

			// bind parameters
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {
				stmt.setObject(i + 1, sqlParams[i]);
			}

			rs = stmt.executeQuery();

			// fetch the results
			return fetchMultiResults(rs);
		} catch (Exception _e) {
			_e.printStackTrace();
			throw new SensorDaoException("Exception: " + _e.getMessage(), _e);
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}

		}

	}

}
