package de.berlin.fu.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.berlin.fu.data.dto.Action;
import de.berlin.fu.data.dto.Event;
import de.berlin.fu.data.dto.Property;
import de.berlin.fu.data.dto.PropertyType;
import de.berlin.fu.data.dto.Sensor;
import de.berlin.fu.data.dto.Trigger;

@RemoteServiceRelativePath("data")
public interface MyServer extends RemoteService {

	public List<Event> getEventList();

	public List<Event> getNewEvents(int idEvent);

	public List<Sensor> getSensors();

	public List<Trigger> getTriggers();

	public List<Action> getActions();

	public void addTrigger(Trigger t);

	public boolean removeTrigger(Trigger t);

	public boolean updateTrigger(Trigger t);

	public void addAction(Action a);

	public boolean removeAction(Action a);

	public boolean updateAction(Action a);

	public List<Property> getProperty(Sensor s, PropertyType pt);

}