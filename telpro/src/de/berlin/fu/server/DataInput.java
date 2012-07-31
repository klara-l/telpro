package de.berlin.fu.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.berlin.fu.data.dao.ActionDao;
import de.berlin.fu.data.dao.EventDao;
import de.berlin.fu.data.dao.EventTypeDao;
import de.berlin.fu.data.dao.EventtypeHasActionDao;
import de.berlin.fu.data.dao.PropertyDao;
import de.berlin.fu.data.dao.SensorDao;
import de.berlin.fu.data.dao.TriggerDao;
import de.berlin.fu.data.dto.Action;
import de.berlin.fu.data.dto.Event;
import de.berlin.fu.data.dto.EventType;
import de.berlin.fu.data.dto.EventtypeHasAction;
import de.berlin.fu.data.dto.Property;
import de.berlin.fu.data.dto.Sensor;
import de.berlin.fu.data.dto.Trigger;
import de.berlin.fu.data.exceptions.ActionDaoException;
import de.berlin.fu.data.exceptions.EventDaoException;
import de.berlin.fu.data.exceptions.EventTypeDaoException;
import de.berlin.fu.data.exceptions.EventtypeHasActionDaoException;
import de.berlin.fu.data.exceptions.PropertyDaoException;
import de.berlin.fu.data.exceptions.SensorDaoException;
import de.berlin.fu.data.exceptions.TriggerDaoException;
import de.berlin.fu.data.factory.ActionDaoFactory;
import de.berlin.fu.data.factory.EventDaoFactory;
import de.berlin.fu.data.factory.EventTypeDaoFactory;
import de.berlin.fu.data.factory.EventtypeHasActionDaoFactory;
import de.berlin.fu.data.factory.PropertyDaoFactory;
import de.berlin.fu.data.factory.SensorDaoFactory;
import de.berlin.fu.data.factory.TriggerDaoFactory;
import de.berlin.fu.data.jdbc.ResourceManager;

public class DataInput extends HttpServlet {

	private static final int KEY_EVENT = 1;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1104114233075007233L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String deviceID = (String) request.getParameter("deviceID");
		String action = (String) request.getParameter("action");

		if (action.equalsIgnoreCase("property")) {
			doProperty(deviceID, request, response);
		} else if (action.equalsIgnoreCase("keyevent")) {
			doKeyEvent(deviceID, request, response);
		}

		response.setStatus(200);
	}

	private void doKeyEvent(String deviceID, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Event e = new Event();
		EventDao edao = EventDaoFactory.create();
		e.setValue((String) request.getParameter("key"));
		e.setEventtypeIdeventtype(KEY_EVENT);
		e.setTimestamp(new Date());
		e.setSensorIdsensor(deviceID);
		try {
			edao.insert(e);
			handleEvent(e);
		} catch (EventDaoException e1) {
			e1.printStackTrace();
		}
	}

	private void handleEvent(Event e) {
		try {
			EventTypeDao etdao = EventTypeDaoFactory.create();
			EventtypeHasActionDao eadao = EventtypeHasActionDaoFactory.create();
			// getting the event type
			EventType et = etdao.findByPrimaryKey(e.getEventtypeIdeventtype());
			ActionDao adao = ActionDaoFactory.create();
			etdao.findWhereIdEventTypeEquals(e.getEventtypeIdeventtype());
			EventtypeHasAction[] actionKeys = eadao.findByEventType(e
					.getEventtypeIdeventtype());
			for (int i = 0; i < actionKeys.length; i++) {
				Action[] actions = adao.findWhereIdActionEquals(actionKeys[i]
						.getActionIdaction());
				for (Action a : actions) {
					if (a.isServerSide())
						a.execute(e, et);
				}
			}
		} catch (EventTypeDaoException e1) {
			e1.printStackTrace();
		} catch (EventtypeHasActionDaoException e2) {
			e2.printStackTrace();
		} catch (ActionDaoException e3) {
			e3.printStackTrace();
		}
	}

	private void doProperty(String deviceID, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Sensor s;
		SensorDao sdao = SensorDaoFactory.create();
		float temp = (float) (Math.round(1000.0 * Float
				.parseFloat((String) request.getParameter("temp"))) / 1000.0);
		float humid = (float) (Math.round(1000.0 * Float
				.parseFloat((String) request.getParameter("humid"))) / 1000.0);
		float tilt = (float) (Math.round(1000.0 * Float
				.parseFloat((String) request.getParameter("tilt"))) / 1000.0);
		float roll = (float) (Math.round(1000.0 * Float
				.parseFloat((String) request.getParameter("roll"))) / 1000.0);

		try {
			// updating ip
			s = sdao.findWhereIdSensorEquals(deviceID)[0];
			s.setIp(request.getRemoteAddr());
			sdao.update(s.createPk(), s);
			// adding the fields
			// temp
			processProperty(s, temp, 1);
			// humid
			processProperty(s, humid, 2);
			// tilt
			processProperty(s, tilt, 3);
			// roll
			processProperty(s, roll, 4);
		} catch (SensorDaoException e) {
			e.printStackTrace();
		}

	}

	private boolean isTriggerOn(Trigger t) {
		Connection c = null;
		PreparedStatement stmt = null;

		ResultSet rs = null;

		try {
			// get a connection from the ResourceManager
			c = ResourceManager.getConnection();

			// construct the SQL statement
			final String SQL = "SELECT (SELECT MAX(Timestamp) FROM Event WHERE Trigger_idTrigger = ? AND EventType_idEventType = 2) IS NOT NULL AND (TIMEDIFF( (SELECT MAX(Timestamp) FROM Event WHERE Trigger_idTrigger = ? AND EventType_idEventType = 2), (SELECT MAX(Timestamp)  FROM Event WHERE Trigger_idTrigger = ? AND EventType_idEventType = 3)) > 0 OR (SELECT MAX(Timestamp) FROM Event WHERE Trigger_idTrigger = ? AND EventType_idEventType = 3) IS NULL) ";
			Object[] sqlParams = { t.getIdTrigger(), t.getIdTrigger(),
					t.getIdTrigger(), t.getIdTrigger() };
			System.out.println("Executing " + SQL);
			// prepare statement
			stmt = c.prepareStatement(SQL);
			stmt.setMaxRows(1);

			// bind parameters
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {
				stmt.setObject(i + 1, sqlParams[i]);
			}

			rs = stmt.executeQuery();
			rs.first();
			boolean result = rs.getBoolean(1) && !rs.wasNull();
			return result;
		} catch (Exception _e) {
			_e.printStackTrace();
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			ResourceManager.close(c);
		}
		return false;
	}

	private void triggerEventStart(Sensor s, Trigger t, Property p) {
		try {
			Event e = new Event();
			e.setEventtypeIdeventtype(2); // prop start
			e.setSensorIdsensor(s.getIdSensor());
			e.setTriggerIdtrigger(t.getIdTrigger());
			e.setTimestamp(new Date());
			e.setValue(p.getValue() + "");
			EventDao edao = EventDaoFactory.create();
			edao.insert(e);
			handleEvent(e);
		} catch (EventDaoException e1) {
			e1.printStackTrace();
		}
	}

	private void triggerEventStop(Sensor s, Trigger t, Property p) {
		try {
			Event e = new Event();
			e.setEventtypeIdeventtype(3); // prop stop
			e.setSensorIdsensor(s.getIdSensor());
			e.setTriggerIdtrigger(t.getIdTrigger());
			e.setTimestamp(new Date());
			e.setValue(p.getValue() + "");
			EventDao edao = EventDaoFactory.create();
			edao.insert(e);
			handleEvent(e);
		} catch (EventDaoException e1) {
			e1.printStackTrace();
		}
	}

	private double getDelta(int type) {
		switch (type) {
		case 1:
			return 0.5;
		case 2:
			return 1.0;
		case 3:
		case 4:
			return 5.0;
		default:
			return 0;
		}
	}

	private void processProperty(Sensor s, float value, int type) {
		try {
			PropertyDao pdao = PropertyDaoFactory.create();
			TriggerDao tdao = TriggerDaoFactory.create();
			Property prop = new Property();
			prop.setTimestamp(new Date());
			prop.setSensorIdsensor(s.getIdSensor());
			prop.setPropertytypeIdpropertytype(type);
			prop.setValue(value);
			pdao.insert(prop);

			// find trigger for proptype
			Trigger[] t = tdao.findByPropertyType(type);
			for (int i = 0; i < t.length; i++) {
				switch (t[i].getTriggerType()) {
				case 1: // val >= threshold
					if (!isTriggerOn(t[i])) {
						// check if threshold was excessed
						if (value >= t[i].getThreshold()) {
							// insert event start stuff
							triggerEventStart(s, t[i], prop);
						}
					} else { // thresh was already excessed, now check if were
								// below again
						if (value <= (t[i].getThreshold() - getDelta(type))) {
							// insert event stop stuff
							triggerEventStop(s, t[i], prop);
						}
					}
					break;

				case 2: // val <= threshold
					if (!isTriggerOn(t[i])) {
						// check if threshold was excessed
						if (value <= t[i].getThreshold()) {
							// insert event start stuff
							triggerEventStart(s, t[i], prop);
						}
					} else { // thresh was already excessed, now check if were
								// below again
						// todo change for every property type
						if (value >= (t[i].getThreshold() + getDelta(type))) {
							// insert event stop stuff
							triggerEventStop(s, t[i], prop);
						}
					}
					break;

				default:
					break;
				}
			}
		} catch (PropertyDaoException e) {
			e.printStackTrace();
		} catch (TriggerDaoException e) {
			e.printStackTrace();
		}

	}
}
