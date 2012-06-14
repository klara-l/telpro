package de.berlin.fu.server;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.berlin.fu.data.dao.TriggerDao;
import de.berlin.fu.data.dto.Action;
import de.berlin.fu.data.dto.Event;
import de.berlin.fu.data.dto.Property;
import de.berlin.fu.data.dto.PropertyType;
import de.berlin.fu.data.dto.Sensor;
import de.berlin.fu.data.dto.Trigger;
import de.berlin.fu.data.exceptions.ActionDaoException;
import de.berlin.fu.data.exceptions.EventDaoException;
import de.berlin.fu.data.exceptions.PropertyTypeDaoException;
import de.berlin.fu.data.exceptions.SensorDaoException;
import de.berlin.fu.data.exceptions.TriggerDaoException;
import de.berlin.fu.data.factory.ActionDaoFactory;
import de.berlin.fu.data.factory.EventDaoFactory;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean removeTrigger(Trigger t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateTrigger(Trigger t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addAction(Action a) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean removeAction(Action a) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAction(Action a) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Property> getProperty(Sensor s, PropertyType pt) {
		// TODO Auto-generated method stub
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
}
