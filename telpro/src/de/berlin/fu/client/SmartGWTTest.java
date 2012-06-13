package de.berlin.fu.client;

import com.google.gwt.core.client.EntryPoint;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;


public class SmartGWTTest implements EntryPoint {

	@Override
	public void onModuleLoad() {
		IButton button = new IButton("Hello");
		button.addClickHandler(new ClickHandler()
		{
		public void onClick(ClickEvent event)
		{
			SC.say("Hello World from SmartGWT");
		  }
		});
		button.draw();

	}

}
