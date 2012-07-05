package de.berlin.fu.server;

import java.io.IOException;
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
import de.berlin.fu.data.dto.Action;
import de.berlin.fu.data.dto.Event;
import de.berlin.fu.data.dto.EventType;
import de.berlin.fu.data.dto.EventtypeHasAction;
import de.berlin.fu.data.dto.Property;
import de.berlin.fu.data.dto.Sensor;
import de.berlin.fu.data.exceptions.ActionDaoException;
import de.berlin.fu.data.exceptions.EventDaoException;
import de.berlin.fu.data.exceptions.EventTypeDaoException;
import de.berlin.fu.data.exceptions.EventtypeHasActionDaoException;
import de.berlin.fu.data.exceptions.PropertyDaoException;
import de.berlin.fu.data.exceptions.SensorDaoException;
import de.berlin.fu.data.factory.ActionDaoFactory;
import de.berlin.fu.data.factory.EventDaoFactory;
import de.berlin.fu.data.factory.EventTypeDaoFactory;
import de.berlin.fu.data.factory.EventtypeHasActionDaoFactory;
import de.berlin.fu.data.factory.PropertyDaoFactory;
import de.berlin.fu.data.factory.SensorDaoFactory;

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
		PropertyDao pdao = PropertyDaoFactory.create();
		float temp = Float.parseFloat((String) request.getParameter("temp"));
		float humid = Float.parseFloat((String) request.getParameter("humid"));
		float tilt = Float.parseFloat((String) request.getParameter("tilt"));
		float roll = Float.parseFloat((String) request.getParameter("roll"));

		try {
			// updating ip
			s = sdao.findWhereIdSensorEquals(deviceID)[0];
			s.setIp(request.getRemoteAddr());
			sdao.update(s.createPk(), s);
			// adding the fields
			Property prop = new Property();
			prop.setTimestamp(new Date());
			prop.setSensorIdsensor(deviceID);
			// temp
			prop.setPropertytypeIdpropertytype(1);
			prop.setValue(temp);
			pdao.insert(prop);
			// humid
			prop.setPropertytypeIdpropertytype(2);
			prop.setValue(humid);
			pdao.insert(prop);
			// tilt
			prop.setPropertytypeIdpropertytype(3);
			prop.setValue(tilt);
			pdao.insert(prop);
			// roll
			prop.setPropertytypeIdpropertytype(4);
			prop.setValue(roll);
			pdao.insert(prop);

		} catch (SensorDaoException e) {
			e.printStackTrace();
		} catch (PropertyDaoException e) {
			e.printStackTrace();
		}

	}
}
