package de.berlin.fu.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;


public class SmartGWTTest implements EntryPoint {

	Label test;
	int i;
	@Override
	public void onModuleLoad() {
		test = new Label();
		test.setContents("hallo");
		i=0;

	}
	
	private void startTimer(final String sensorID) {
		Timer t = new Timer() {
			public void run() {
				
				
				
			}
		};

		// get all 5 sec new properties
		t.scheduleRepeating(5000);

	}

}
