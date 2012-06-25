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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import de.berlin.fu.data.dao.EventDao;
import de.berlin.fu.data.dto.Event;
import de.berlin.fu.data.dto.EventPk;
import de.berlin.fu.data.exceptions.EventDaoException;

public class EventDaoImpl extends AbstractDAO implements EventDao {
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
	protected final String SQL_SELECT = "SELECT idEvent, EventType_idEventType, Timestamp, Sensor_idSensor, Trigger_idTrigger, Value FROM "
			+ getTableName() + "";

	/**
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/**
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO "
			+ getTableName()
			+ " ( idEvent, EventType_idEventType, Timestamp, Sensor_idSensor, Trigger_idTrigger, Value ) VALUES ( ?, ?, ?, ?, ?, ? )";

	/**
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE "
			+ getTableName()
			+ " SET idEvent = ?, EventType_idEventType = ?, Timestamp = ?, Sensor_idSensor = ?, Trigger_idTrigger = ?, Value = ? WHERE idEvent = ? AND EventType_idEventType = ?";

	/**
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName()
			+ " WHERE idEvent = ? AND EventType_idEventType = ?";

	/**
	 * Index of column idEvent
	 */
	protected static final int COLUMN_ID_EVENT = 1;

	/**
	 * Index of column EventType_idEventType
	 */
	protected static final int COLUMN_EVENTTYPE_IDEVENTTYPE = 2;

	/**
	 * Index of column Timestamp
	 */
	protected static final int COLUMN_TIMESTAMP = 3;

	/**
	 * Index of column Sensor_idSensor
	 */
	protected static final int COLUMN_SENSOR_IDSENSOR = 4;

	/**
	 * Index of column Trigger_idTrigger
	 */
	protected static final int COLUMN_TRIGGER_IDTRIGGER = 5;

	/**
	 * Index of column Value
	 */
	protected static final int COLUMN_VALUE = 6;

	/**
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 6;

	/**
	 * Index of primary-key column idEvent
	 */
	protected static final int PK_COLUMN_ID_EVENT = 1;

	/**
	 * Index of primary-key column EventType_idEventType
	 */
	protected static final int PK_COLUMN_EVENTTYPE_IDEVENTTYPE = 2;

	/**
	 * Inserts a new row in the Event table.
	 */
	@Override
	public EventPk insert(Event dto) throws EventDaoException {
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// get the user-specified connection or get a connection from the
			// ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();

			stmt = conn.prepareStatement(SQL_INSERT,
					Statement.RETURN_GENERATED_KEYS);
			int index = 1;
			stmt.setInt(index++, dto.getIdEvent());
			stmt.setInt(index++, dto.getEventtypeIdeventtype());
			stmt.setTimestamp(index++, dto.getTimestamp() == null ? null
					: new java.sql.Timestamp(dto.getTimestamp().getTime()));
			stmt.setString(index++, dto.getSensorIdsensor());
			if (dto.isTriggerIdtriggerNull()) {
				stmt.setNull(index++, java.sql.Types.INTEGER);
			} else {
				stmt.setInt(index++, dto.getTriggerIdtrigger());
			}

			stmt.setString(index++, dto.getValue());
			System.out.println("Executing " + SQL_INSERT + " with DTO: " + dto);
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			System.out.println(rows + " rows affected (" + (t2 - t1) + " ms)");

			// retrieve values from auto-increment columns
			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				dto.setIdEvent(rs.getInt(1));
			}

			reset(dto);
			return dto.createPk();
		} catch (Exception _e) {
			_e.printStackTrace();
			throw new EventDaoException("Exception: " + _e.getMessage(), _e);
		} finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}

		}

	}

	/**
	 * Updates a single row in the Event table.
	 */
	@Override
	public void update(EventPk pk, Event dto) throws EventDaoException {
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
			stmt.setInt(index++, dto.getIdEvent());
			stmt.setInt(index++, dto.getEventtypeIdeventtype());
			stmt.setTimestamp(index++, dto.getTimestamp() == null ? null
					: new java.sql.Timestamp(dto.getTimestamp().getTime()));
			stmt.setString(index++, dto.getSensorIdsensor());
			if (dto.isTriggerIdtriggerNull()) {
				stmt.setNull(index++, java.sql.Types.INTEGER);
			} else {
				stmt.setInt(index++, dto.getTriggerIdtrigger());
			}

			stmt.setString(index++, dto.getValue());
			stmt.setInt(7, pk.getIdEvent());
			stmt.setInt(8, pk.getEventtypeIdeventtype());
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			System.out.println(rows + " rows affected (" + (t2 - t1) + " ms)");
		} catch (Exception _e) {
			_e.printStackTrace();
			throw new EventDaoException("Exception: " + _e.getMessage(), _e);
		} finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}

		}

	}

	/**
	 * Deletes a single row in the Event table.
	 */
	@Override
	public void delete(EventPk pk) throws EventDaoException {
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
			stmt.setInt(1, pk.getIdEvent());
			stmt.setInt(2, pk.getEventtypeIdeventtype());
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			System.out.println(rows + " rows affected (" + (t2 - t1) + " ms)");
		} catch (Exception _e) {
			_e.printStackTrace();
			throw new EventDaoException("Exception: " + _e.getMessage(), _e);
		} finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}

		}

	}

	/**
	 * Returns the rows from the Event table that matches the specified
	 * primary-key value.
	 */
	@Override
	public Event findByPrimaryKey(EventPk pk) throws EventDaoException {
		return findByPrimaryKey(pk.getIdEvent(), pk.getEventtypeIdeventtype());
	}

	/**
	 * Returns all rows from the Event table that match the criteria 'idEvent =
	 * :idEvent AND EventType_idEventType = :eventtypeIdeventtype'.
	 */
	@Override
	public Event findByPrimaryKey(int idEvent, int eventtypeIdeventtype)
			throws EventDaoException {
		Event ret[] = findByDynamicSelect(
				SQL_SELECT + " WHERE idEvent = ? AND EventType_idEventType = ?",
				new Object[] { Integer.valueOf(idEvent),
						Integer.valueOf(eventtypeIdeventtype) });
		return ret.length == 0 ? null : ret[0];
	}

	/**
	 * Returns all rows from the Event table that match the criteria ''.
	 */
	@Override
	public Event[] findAll() throws EventDaoException {
		return findByDynamicSelect(SQL_SELECT
				+ " ORDER BY idEvent, EventType_idEventType", null);
	}

	/**
	 * Returns all rows from the Event table that match the criteria
	 * 'EventType_idEventType = :eventtypeIdeventtype'.
	 */
	@Override
	public Event[] findByEventType(int eventtypeIdeventtype)
			throws EventDaoException {
		return findByDynamicSelect(SQL_SELECT
				+ " WHERE EventType_idEventType = ?",
				new Object[] { Integer.valueOf(eventtypeIdeventtype) });
	}

	/**
	 * Returns all rows from the Event table that match the criteria
	 * 'Sensor_idSensor = :sensorIdsensor'.
	 */
	@Override
	public Event[] findBySensor(String sensorIdsensor) throws EventDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE Sensor_idSensor = ?",
				new Object[] { sensorIdsensor });
	}

	/**
	 * Returns all rows from the Event table that match the criteria
	 * 'Trigger_idTrigger = :triggerIdtrigger'.
	 */
	@Override
	public Event[] findByTrigger(int triggerIdtrigger) throws EventDaoException {
		return findByDynamicSelect(SQL_SELECT + " WHERE Trigger_idTrigger = ?",
				new Object[] { Integer.valueOf(triggerIdtrigger) });
	}

	/**
	 * Returns all rows from the Event table that match the criteria 'idEvent =
	 * :idEvent'.
	 */
	@Override
	public Event[] findWhereIdEventEquals(int idEvent) throws EventDaoException {
		return findByDynamicSelect(SQL_SELECT
				+ " WHERE idEvent = ? ORDER BY idEvent",
				new Object[] { Integer.valueOf(idEvent) });
	}

	/**
	 * Returns all rows from the Event table that match the criteria
	 * 'EventType_idEventType = :eventtypeIdeventtype'.
	 */
	@Override
	public Event[] findWhereEventtypeIdeventtypeEquals(int eventtypeIdeventtype)
			throws EventDaoException {
		return findByDynamicSelect(
				SQL_SELECT
						+ " WHERE EventType_idEventType = ? ORDER BY EventType_idEventType",
				new Object[] { Integer.valueOf(eventtypeIdeventtype) });
	}

	/**
	 * Returns all rows from the Event table that match the criteria 'Timestamp
	 * = :timestamp'.
	 */
	@Override
	public Event[] findWhereTimestampEquals(Date timestamp)
			throws EventDaoException {
		return findByDynamicSelect(SQL_SELECT
				+ " WHERE Timestamp = ? ORDER BY Timestamp",
				new Object[] { timestamp == null ? null
						: new java.sql.Timestamp(timestamp.getTime()) });
	}

	/**
	 * Returns all rows from the Event table that match the criteria
	 * 'Sensor_idSensor = :sensorIdsensor'.
	 */
	@Override
	public Event[] findWhereSensorIdsensorEquals(String sensorIdsensor)
			throws EventDaoException {
		return findByDynamicSelect(SQL_SELECT
				+ " WHERE Sensor_idSensor = ? ORDER BY Sensor_idSensor",
				new Object[] { sensorIdsensor });
	}

	/**
	 * Returns all rows from the Event table that match the criteria
	 * 'Trigger_idTrigger = :triggerIdtrigger'.
	 */
	@Override
	public Event[] findWhereTriggerIdtriggerEquals(int triggerIdtrigger)
			throws EventDaoException {
		return findByDynamicSelect(SQL_SELECT
				+ " WHERE Trigger_idTrigger = ? ORDER BY Trigger_idTrigger",
				new Object[] { Integer.valueOf(triggerIdtrigger) });
	}

	/**
	 * Returns all rows from the Event table that match the criteria 'Value =
	 * :value'.
	 */
	@Override
	public Event[] findWhereValueEquals(String value) throws EventDaoException {
		return findByDynamicSelect(SQL_SELECT
				+ " WHERE Value = ? ORDER BY Value", new Object[] { value });
	}

	/**
	 * Method 'EventDaoImpl'
	 * 
	 */
	public EventDaoImpl() {
	}

	/**
	 * Method 'EventDaoImpl'
	 * 
	 * @param userConn
	 */
	public EventDaoImpl(final java.sql.Connection userConn) {
		this.userConn = userConn;
	}

	/**
	 * Sets the value of maxRows
	 */
	@Override
	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	/**
	 * Gets the value of maxRows
	 */
	@Override
	public int getMaxRows() {
		return maxRows;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName() {
		return "telpro.Event";
	}

	/**
	 * Fetches a single row from the result set
	 */
	protected Event fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()) {
			Event dto = new Event();
			populateDto(dto, rs);
			return dto;
		} else {
			return null;
		}

	}

	/**
	 * Fetches multiple rows from the result set
	 */
	protected Event[] fetchMultiResults(ResultSet rs) throws SQLException {
		Collection<Event> resultList = new ArrayList<Event>();
		while (rs.next()) {
			Event dto = new Event();
			populateDto(dto, rs);
			resultList.add(dto);
		}

		Event ret[] = new Event[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}

	/**
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(Event dto, ResultSet rs) throws SQLException {
		dto.setIdEvent(rs.getInt(COLUMN_ID_EVENT));
		dto.setEventtypeIdeventtype(rs.getInt(COLUMN_EVENTTYPE_IDEVENTTYPE));
		dto.setTimestamp(rs.getTimestamp(COLUMN_TIMESTAMP));
		dto.setSensorIdsensor(rs.getString(COLUMN_SENSOR_IDSENSOR));
		dto.setTriggerIdtrigger(rs.getInt(COLUMN_TRIGGER_IDTRIGGER));
		if (rs.wasNull()) {
			dto.setTriggerIdtriggerNull(true);
		}

		dto.setValue(rs.getString(COLUMN_VALUE));
	}

	/**
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(Event dto) {
	}

	/**
	 * Returns all rows from the Event table that match the specified arbitrary
	 * SQL statement
	 */
	@Override
	public Event[] findByDynamicSelect(String sql, Object[] sqlParams)
			throws EventDaoException {
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
			throw new EventDaoException("Exception: " + _e.getMessage(), _e);
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}

		}

	}

	/**
	 * Returns all rows from the Event table that match the specified arbitrary
	 * SQL statement
	 */
	@Override
	public Event[] findByDynamicWhere(String sql, Object[] sqlParams)
			throws EventDaoException {
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
			throw new EventDaoException("Exception: " + _e.getMessage(), _e);
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}

		}

	}

}
