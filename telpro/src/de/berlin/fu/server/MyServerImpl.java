package de.berlin.fu.server;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.berlin.fu.data.dto.Action;
import de.berlin.fu.data.dto.Event;
import de.berlin.fu.data.dto.Property;
import de.berlin.fu.data.dto.PropertyType;
import de.berlin.fu.data.dto.Sensor;
import de.berlin.fu.data.dto.Trigger;
import de.berlin.fu.data.exceptions.EventDaoException;
import de.berlin.fu.data.factory.EventDaoFactory;
import de.berlin.fu.shared.FieldVerifier;
import de.berlin.fu.shared.MyServer;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MyServerImpl extends RemoteServiceServlet implements MyServer {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid.
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back
			// to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script
		// vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html
	 *            the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sensor> getSensors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Trigger> getTriggers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Action> getActions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addTrigger(Trigger t) {
		// TODO Auto-generated method stub

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
}
