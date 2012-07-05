package de.berlin.fu.shared.actions;

import de.berlin.fu.data.dto.Event;
import de.berlin.fu.data.dto.EventType;

public class SysoutAction implements IAction {

	@Override
	public void execute(Event e, EventType et) {
		System.out.println("Event: " + e + "\nEventType: " + et);
	}

	@Override
	public boolean isClientSide() {
		return true;
	}

	@Override
	public boolean isServerSide() {
		return true;
	}

}
