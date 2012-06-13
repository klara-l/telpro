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

import de.berlin.fu.data.dao.SensorHasTriggerDao;
import de.berlin.fu.data.dto.SensorHasTrigger;
import de.berlin.fu.data.dto.SensorHasTriggerPk;
import de.berlin.fu.data.exceptions.SensorHasTriggerDaoException;

public class SensorHasTriggerDaoImpl extends AbstractDAO implements
		SensorHasTriggerDao {
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
	protected final String SQL_SELECT = "SELECT Sensor_idSensor, Trigger_idTrigger FROM "
			+ getTableName() + "";

	/**
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/**
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName()
			+ " ( Sensor_idSensor, Trigger_idTrigger ) VALUES ( ?, ? )";

	/**
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE "
			+ getTableName()
			+ " SET Sensor_idSensor = ?, Trigger_idTrigger = ? WHERE Sensor_idSensor = ? AND Trigger_idTrigger = ?";

	/**
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName()
			+ " WHERE Sensor_idSensor = ? AND Trigger_idTrigger = ?";

	/**
	 * Index of column Sensor_idSensor
	 */
	protected static final int COLUMN_SENSOR_IDSENSOR = 1;

	/**
	 * Index of column Trigger_idTrigger
	 */
	protected static final int COLUMN_TRIGGER_IDTRIGGER = 2;

	/**
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 2;

	/**
	 * Index of primary-key column Sensor_idSensor
	 */
	protected static final int PK_COLUMN_SENSOR_IDSENSOR = 1;

	/**
	 * Index of primary-key column Trigger_idTrigger
	 */
	protected static final int PK_COLUMN_TRIGGER_IDTRIGGER = 2;

	/**
	 * Inserts a new row in the Sensor_has_Trigger table.
	 */
	public SensorHasTriggerPk insert(SensorHasTrigger dto)
			throws SensorHasTriggerDaoException {
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
			stmt.setString(index++, dto.getSensorIdsensor());
			stmt.setInt(index++, dto.getTriggerIdtrigger());
			System.out.println("Executing " + SQL_INSERT + " with DTO: " + dto);
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			System.out.println(rows + " rows affected (" + (t2 - t1) + " ms)");
			reset(dto);
			return dto.createPk();
		} catch (Exception _e) {
			_e.printStackTrace();
			throw new SensorHasTriggerDaoException("Exception: "
					+ _e.getMessage(), _e);
		} finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}

		}

	}

	/**
	 * Updates a single row in the Sensor_has_Trigger table.
	 */
	public void update(SensorHasTriggerPk pk, SensorHasTrigger dto)
			throws SensorHasTriggerDaoException {
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
			stmt.setString(index++, dto.getSensorIdsensor());
			stmt.setInt(index++, dto.getTriggerIdtrigger());
			stmt.setString(3, pk.getSensorIdsensor());
			stmt.setInt(4, pk.getTriggerIdtrigger());
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			System.out.println(rows + " rows affected (" + (t2 - t1) + " ms)");
		} catch (Exception _e) {
			_e.printStackTrace();
			throw new SensorHasTriggerDaoException("Exception: "
					+ _e.getMessage(), _e);
		} finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}

		}

	}

	/**
	 * Deletes a single row in the Sensor_has_Trigger table.
	 */
	public void delete(SensorHasTriggerPk pk)
			throws SensorHasTriggerDaoException {
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
			stmt.setString(1, pk.getSensorIdsensor());
			stmt.setInt(2, pk.getTriggerIdtrigger());
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			System.out.println(rows + " rows affected (" + (t2 - t1) + " ms)");
		} catch (Exception _e) {
			_e.printStackTrace();
			throw new SensorHasTriggerDaoException("Exception: "
					+ _e.getMessage(), _e);
		} finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}

		}

	}

	/**
	 * Returns the rows from the Sensor_has_Trigger table that matches the
	 * specified primary-key value.
	 */
	public SensorHasTrigger findByPrimaryKey(SensorHasTriggerPk pk)
			throws SensorHasTriggerDaoException {
		return findByPrimaryKey(pk.getSensorIdsensor(),
				pk.getTriggerIdtrigger());
	}

	/**
	 * Returns all rows from the Sensor_has_Trigger table that match the
	 * criteria 'Sensor_idSensor = :sensorIdsensor AND Trigger_idTrigger =
	 * :triggerIdtrigger'.
	 */
	public SensorHasTrigger findByPrimaryKey(String sensorIdsensor,
			int triggerIdtrigger) throws SensorHasTriggerDaoException {
		SensorHasTrigger ret[] = findByDynamicSelect(SQL_SELECT
				+ " WHERE Sensor_idSensor = ? AND Trigger_idTrigger = ?",
				new Object[] { sensorIdsensor, new Integer(triggerIdtrigger) });
		return ret.length == 0 ? null : ret[0];
	}

	/**
	 * Returns all rows from the Sensor_has_Trigger table that match the
	 * criteria ''.
	 */
	public SensorHasTrigger[] findAll() throws SensorHasTriggerDaoException {
		return findByDynamicSelect(SQL_SELECT
				+ " ORDER BY Sensor_idSensor, Trigger_idTrigger", null);
	}

	/**
	 * Returns all rows from the Sensor_has_Trigger table that match the
	 * criteria 'Sensor_idSensor = :sensorIdsensor'.
	 */
	public SensorHasTrigger[] findBySensor(String sensorIdsensor)
			throws SensorHasTriggerDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE Sensor_idSensor = ?",
				new Object[] { sensorIdsensor });
	}

	/**
	 * Returns all rows from the Sensor_has_Trigger table that match the
	 * criteria 'Trigger_idTrigger = :triggerIdtrigger'.
	 */
	public SensorHasTrigger[] findByTrigger(int triggerIdtrigger)
			throws SensorHasTriggerDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE Trigger_idTrigger = ?",
				new Object[] { new Integer(triggerIdtrigger) });
	}

	/**
	 * Returns all rows from the Sensor_has_Trigger table that match the
	 * criteria 'Sensor_idSensor = :sensorIdsensor'.
	 */
	public SensorHasTrigger[] findWhereSensorIdsensorEquals(
			String sensorIdsensor) throws SensorHasTriggerDaoException {
		return findByDynamicSelect(SQL_SELECT
				+ " WHERE Sensor_idSensor = ? ORDER BY Sensor_idSensor",
				new Object[] { sensorIdsensor });
	}

	/**
	 * Returns all rows from the Sensor_has_Trigger table that match the
	 * criteria 'Trigger_idTrigger = :triggerIdtrigger'.
	 */
	public SensorHasTrigger[] findWhereTriggerIdtriggerEquals(
			int triggerIdtrigger) throws SensorHasTriggerDaoException {
		return findByDynamicSelect(SQL_SELECT
				+ " WHERE Trigger_idTrigger = ? ORDER BY Trigger_idTrigger",
				new Object[] { new Integer(triggerIdtrigger) });
	}

	/**
	 * Method 'SensorHasTriggerDaoImpl'
	 * 
	 */
	public SensorHasTriggerDaoImpl() {
	}

	/**
	 * Method 'SensorHasTriggerDaoImpl'
	 * 
	 * @param userConn
	 */
	public SensorHasTriggerDaoImpl(final java.sql.Connection userConn) {
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
		return "telpro.Sensor_has_Trigger";
	}

	/**
	 * Fetches a single row from the result set
	 */
	protected SensorHasTrigger fetchSingleResult(ResultSet rs)
			throws SQLException {
		if (rs.next()) {
			SensorHasTrigger dto = new SensorHasTrigger();
			populateDto(dto, rs);
			return dto;
		} else {
			return null;
		}

	}

	/**
	 * Fetches multiple rows from the result set
	 */
	protected SensorHasTrigger[] fetchMultiResults(ResultSet rs)
			throws SQLException {
		Collection<SensorHasTrigger> resultList = new ArrayList<SensorHasTrigger>();
		while (rs.next()) {
			SensorHasTrigger dto = new SensorHasTrigger();
			populateDto(dto, rs);
			resultList.add(dto);
		}

		SensorHasTrigger ret[] = new SensorHasTrigger[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}

	/**
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(SensorHasTrigger dto, ResultSet rs)
			throws SQLException {
		dto.setSensorIdsensor(rs.getString(COLUMN_SENSOR_IDSENSOR));
		dto.setTriggerIdtrigger(rs.getInt(COLUMN_TRIGGER_IDTRIGGER));
	}

	/**
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(SensorHasTrigger dto) {
	}

	/**
	 * Returns all rows from the Sensor_has_Trigger table that match the
	 * specified arbitrary SQL statement
	 */
	public SensorHasTrigger[] findByDynamicSelect(String sql, Object[] sqlParams)
			throws SensorHasTriggerDaoException {
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
			throw new SensorHasTriggerDaoException("Exception: "
					+ _e.getMessage(), _e);
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}

		}

	}

	/**
	 * Returns all rows from the Sensor_has_Trigger table that match the
	 * specified arbitrary SQL statement
	 */
	public SensorHasTrigger[] findByDynamicWhere(String sql, Object[] sqlParams)
			throws SensorHasTriggerDaoException {
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
			throw new SensorHasTriggerDaoException("Exception: "
					+ _e.getMessage(), _e);
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}

		}

	}

}
