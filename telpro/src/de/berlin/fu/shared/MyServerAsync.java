package de.berlin.fu.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.berlin.fu.data.dto.Action;
import de.berlin.fu.data.dto.Event;
import de.berlin.fu.data.dto.EventType;
import de.berlin.fu.data.dto.Property;
import de.berlin.fu.data.dto.PropertyType;
import de.berlin.fu.data.dto.Sensor;
import de.berlin.fu.data.dto.Trigger;

public interface MyServerAsync {

	void addAction(Action a, AsyncCallback<Void> callback);

	void addTrigger(Trigger t, AsyncCallback<Void> callback);

	void getActions(AsyncCallback<List<Action>> callback);

	void getProperty(Sensor s, PropertyType pt, int limit,
			AsyncCallback<List<Property>> callback);

	void getSensors(AsyncCallback<List<Sensor>> callback);

	void getTriggers(AsyncCallback<List<Trigger>> callback);

	void removeAction(Action a, AsyncCallback<Void> callback);

	void removeTrigger(Trigger t, AsyncCallback<Void> callback);

	void updateAction(Action a, AsyncCallback<Void> callback);

	void updateTrigger(Trigger t, AsyncCallback<Void> callback);

	void getPropertyTypes(AsyncCallback<List<PropertyType>> callback);

	void getEventTypes(AsyncCallback<List<EventType>> callback);

	void getPropertyUpdate(Sensor s, PropertyType pt, int propertyID,
			AsyncCallback<List<Property>> callback);

	void getEventList(Sensor s, AsyncCallback<List<Event>> callback);

	void getNewEvents(Sensor s, int idEvent, AsyncCallback<List<Event>> callback);

	void getProperty(Sensor s, PropertyType pt, int limit, int stretch,
			AsyncCallback<List<Property>> callback);

	void updateSensor(Sensor s, AsyncCallback<Void> callback);

}
