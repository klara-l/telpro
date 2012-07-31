package de.berlin.fu.server;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.berlin.fu.data.dao.ActionDao;
import de.berlin.fu.data.dao.EventTypeDao;
import de.berlin.fu.data.dao.EventtypeHasActionDao;
import de.berlin.fu.data.dao.SensorDao;
import de.berlin.fu.data.dao.TriggerDao;
import de.berlin.fu.data.dto.Action;
import de.berlin.fu.data.dto.Event;
import de.berlin.fu.data.dto.EventType;
import de.berlin.fu.data.dto.EventtypeHasAction;
import de.berlin.fu.data.dto.Property;
import de.berlin.fu.data.dto.PropertyType;
import de.berlin.fu.data.dto.Sensor;
import de.berlin.fu.data.dto.Trigger;
import de.berlin.fu.data.exceptions.ActionDaoException;
import de.berlin.fu.data.exceptions.EventDaoException;
import de.berlin.fu.data.exceptions.EventTypeDaoException;
import de.berlin.fu.data.exceptions.EventtypeHasActionDaoException;
import de.berlin.fu.data.exceptions.PropertyDaoException;
import de.berlin.fu.data.exceptions.PropertyTypeDaoException;
import de.berlin.fu.data.exceptions.SensorDaoException;
import de.berlin.fu.data.exceptions.TriggerDaoException;
import de.berlin.fu.data.factory.ActionDaoFactory;
import de.berlin.fu.data.factory.EventDaoFactory;
import de.berlin.fu.data.factory.EventTypeDaoFactory;
import de.berlin.fu.data.factory.EventtypeHasActionDaoFactory;
import de.berlin.fu.data.factory.PropertyDaoFactory;
import de.berlin.fu.data.factory.PropertyTypeDaoFactory;
import de.berlin.fu.data.factory.SensorDaoFactory;
import de.berlin.fu.data.factory.TriggerDaoFactory;
import de.berlin.fu.shared.MyServer;

/**
 * The server side implementation of the RPC service.
 */
public class MyServerImpl extends RemoteServiceServlet implements MyServer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1893722591009147486L;

	@Override
	public List<Event> getEventList(Sensor s) {
		try {
			return Arrays.asList(EventDaoFactory.create().findBySensor(
					s.getIdSensor()));
		} catch (EventDaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Event> getNewEvents(Sensor s, int idEvent) {
		Object[] param = { s.getIdSensor(), idEvent };
		try {
			return Arrays.asList(EventDaoFactory.create().findByDynamicWhere(
					"Sensor_idSensor = ? AND idEvent > ?", param));
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
	public void updateSensor(Sensor s) {
		SensorDao _dao = SensorDaoFactory.create();
		try {
			_dao.update(s.createPk(), s);
		} catch (SensorDaoException e) {
			e.printStackTrace();
		}

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

	//
	@Override
	public List<Property> getProperty(Sensor s, PropertyType pt, int limit,
			int stretch) {
		Object[] param = { s.getIdSensor(), pt.getIdPropertyType(), stretch,
				stretch * limit };
		try {
			return Arrays
					.asList(PropertyDaoFactory
							.create()
							.findByDynamicSelect(
									"SELECT * FROM Property WHERE Sensor_idSensor = ? AND PropertyType_idPropertyType = ? AND idProperty % ? = 0 AND idProperty > (SELECT max(idProperty - 4 * ?) FROM Property)",
									param));
		} catch (PropertyDaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Property> getProperty(Sensor s, PropertyType pt, int limit) {
		Object[] param = { s.getIdSensor(), pt.getIdPropertyType(), limit };
		try {
			return Arrays
					.asList(PropertyDaoFactory
							.create()
							.findByDynamicSelect(
									"SELECT * FROM Property WHERE Sensor_idSensor = ? AND PropertyType_idPropertyType = ? AND idProperty > ((SELECT max(idProperty - 4 * ?) FROM Property ))",
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

	@Override
	public List<Property> getPropertyUpdate(Sensor s, PropertyType pt,
			int propertyID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Action> getActionsForEvent(Event e) {
		List<Action> result = null;
		try {
			EventTypeDao etdao = EventTypeDaoFactory.create();
			EventtypeHasActionDao eadao = EventtypeHasActionDaoFactory.create();
			// getting the event type
			ActionDao adao = ActionDaoFactory.create();
			etdao.findWhereIdEventTypeEquals(e.getEventtypeIdeventtype());
			EventtypeHasAction[] actionKeys = eadao.findByEventType(e
					.getEventtypeIdeventtype());
			result = new LinkedList<Action>();
			for (int i = 0; i < actionKeys.length; i++) {
				Action[] actions = adao.findWhereIdActionEquals(actionKeys[i]
						.getActionIdaction());
				if (actions[0].isClientSide())
					result.add(actions[0]);
			}
		} catch (EventTypeDaoException e1) {
			e1.printStackTrace();
		} catch (EventtypeHasActionDaoException e2) {
			e2.printStackTrace();
		} catch (ActionDaoException e3) {
			e3.printStackTrace();
		}
		return result;
	}
}
