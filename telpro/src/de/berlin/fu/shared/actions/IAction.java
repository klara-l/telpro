package de.berlin.fu.shared.actions;

import de.berlin.fu.data.dto.Event;
import de.berlin.fu.data.dto.EventType;

public interface IAction {

	public void execute(Event e, EventType et);

	/**
	 * @return true, if this action should be executed on a client
	 */
	public boolean isClientSide();

	/**
	 * @return true, if this action should be executed on a server
	 */
	public boolean isServerSide();

}
