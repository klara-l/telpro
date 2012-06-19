package de.berlin.fu.server;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.berlin.fu.data.dao.ActionDao;
import de.berlin.fu.data.dao.TriggerDao;
import de.berlin.fu.data.dto.Action;
import de.berlin.fu.data.dto.Event;
import de.berlin.fu.data.dto.EventType;
import de.berlin.fu.data.dto.Property;
import de.berlin.fu.data.dto.PropertyType;
import de.berlin.fu.data.dto.Sensor;
import de.berlin.fu.data.dto.Trigger;
import de.berlin.fu.data.exceptions.ActionDaoException;
import de.berlin.fu.data.exceptions.EventDaoException;
import de.berlin.fu.data.exceptions.EventTypeDaoException;
import de.berlin.fu.data.exceptions.PropertyDaoException;
import de.berlin.fu.data.exceptions.PropertyTypeDaoException;
import de.berlin.fu.data.exceptions.SensorDaoException;
import de.berlin.fu.data.exceptions.TriggerDaoException;
import de.berlin.fu.data.factory.ActionDaoFactory;
import de.berlin.fu.data.factory.EventDaoFactory;
import de.berlin.fu.data.factory.EventTypeDaoFactory;
import de.berlin.fu.data.factory.PropertyDaoFactory;
import de.berlin.fu.data.factory.PropertyTypeDaoFactory;
import de.berlin.fu.data.factory.SensorDaoFactory;
import de.berlin.fu.data.factory.TriggerDaoFactory;
import de.berlin.fu.shared.MyServer;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MyServerImpl extends RemoteServiceServlet implements MyServer {

	@Override
	public List<Event> getEventList() {
		try {
			return Arrays.asList(EventDaoFactory.create().findAll());
		} catch (EventDaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Event> getNewEvents(int idEvent) {
		Integer[] param = { idEvent };
		try {
			return Arrays.asList(EventDaoFactory.create().findByDynamicWhere(
					"idEvent > ?", param));
		} catch (EventDaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Sensor> getSensors() {
		try {
			return Arrays.asList(SensorDaoFactory.create().findAll());
		} catch (SensorDaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Trigger> getTriggers() {
		try {
			return Arrays.asList(TriggerDaoFactory.create().findAll());
		} catch (TriggerDaoException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public List<Action> getActions() {
		try {
			return Arrays.asList(ActionDaoFactory.create().findAll());
		} catch (ActionDaoException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public void addTrigger(Trigger t) {
		TriggerDao _dao = TriggerDaoFactory.create();
		try {
			_dao.insert(t);
		} catch (TriggerDaoException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeTrigger(Trigger t) {
		TriggerDao _dao = TriggerDaoFactory.create();
		try {
			_dao.delete(t.createPk());
		} catch (TriggerDaoException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateTrigger(Trigger t) {
		TriggerDao _dao = TriggerDaoFactory.create();
		try {
			_dao.update(t.createPk(), t);
		} catch (TriggerDaoException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void addAction(Action a) {
		ActionDao _dao = ActionDaoFactory.create();
		try {
			_dao.insert(a);
		} catch (ActionDaoException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeAction(Action a) {
		ActionDao _dao = ActionDaoFactory.create();
		try {
			_dao.delete(a.createPk());
		} catch (ActionDaoException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateAction(Action a) {
		ActionDao _dao = ActionDaoFactory.create();
		try {
			_dao.update(a.createPk(), a);
		} catch (ActionDaoException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Property> getProperty(Sensor s, PropertyType pt, int limit) {
		Object[] param = { s.getIdSensor(), pt.getIdPropertyType(), limit };
		try {
			return Arrays
					.asList(PropertyDaoFactory
							.create()
							.findByDynamicSelect(
									"SELECT * FROM Property WHERE Sensor_idSensor = ? AND PropertyType_idPropertyType = ? ORDER BY idProperty DESC LIMIT ?",
									param));
		} catch (PropertyDaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<PropertyType> getPropertyTypes() {
		try {
			return Arrays.asList(PropertyTypeDaoFactory.create().findAll());
		} catch (PropertyTypeDaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<EventType> getEventTypes() {
		try {
			return Arrays.asList(EventTypeDaoFactory.create().findAll());
		} catch (EventTypeDaoException e) {
			e.printStackTrace();
		}
		return null;

	}
}
