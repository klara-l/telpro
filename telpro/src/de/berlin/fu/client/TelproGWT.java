package de.berlin.fu.client;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.LineChart;
import com.google.gwt.visualization.client.visualizations.LineChart.Options;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import de.berlin.fu.data.dto.Event;
import de.berlin.fu.data.dto.EventType;
import de.berlin.fu.data.dto.Property;
import de.berlin.fu.data.dto.PropertyType;
import de.berlin.fu.data.dto.Sensor;
import de.berlin.fu.shared.MyServer;
import de.berlin.fu.shared.MyServerAsync;

public class TelproGWT implements EntryPoint {

	private final MyServerAsync server = GWT.create(MyServer.class);
	private ListGrid sensorTable;

	private HashMap<String, Sensor> sensors;

	private HashMap<String, PropertyType> propTypes;
	private HashMap<Integer, EventType> eventTypes;

	private LineChart temperature;
	private LineChart humidity;
	private LineChart tilt;
	private LineChart roll;

	private Panel panel;

	private boolean firstClick = true;

	private Label textSelectednode;

	public void onModuleLoad() {
		sensors = new HashMap<String, Sensor>();
		propTypes = new HashMap<String, PropertyType>();
		eventTypes = new HashMap<Integer, EventType>();
		
		panel = RootPanel.get();

		getAllPropertyTypes();
		getAllEventTypes();

		createSensorTable();
		updateSensorSelection();	
		
	}
	


	private void getAllEventTypes() {
		server.getEventTypes(new AsyncCallback<List<EventType>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(List<EventType> result) {
				for(EventType etype: result){
					eventTypes.put(etype.getIdEventType(), etype);
				}
				
			}
		});
		
	}



	private void drawCharts(){
		//add Charts
				Runnable onLoadCallback = new Runnable() {
					public void run() {
						HLayout tempAndHum = new HLayout();
						tempAndHum.setHeight(400); 
						tempAndHum.setWidth(1000);
						tempAndHum.setMembersMargin(20);  
						tempAndHum.setLayoutMargin(10);
						
						temperature = new LineChart(createTable(null, "Temperature"), createChartOptions("Temperature"));
						humidity = new LineChart(createTable(null, "Humidity"), createChartOptions("Humidity"));
						tempAndHum.addMember(temperature);
						tempAndHum.addMember(humidity);
						
						HLayout tiltAndRoll = new HLayout();
						tiltAndRoll.setHeight(400); 
						tiltAndRoll.setWidth(1000);
						tiltAndRoll.setMembersMargin(20);  
						tiltAndRoll.setLayoutMargin(10);
						
						tilt = new LineChart(createTable(null, "Tilt"), createChartOptions("Tilt"));
						roll = new LineChart(createTable(null, "Roll"), createChartOptions("Roll"));
						
						tiltAndRoll.addMember(tilt);
						tiltAndRoll.addMember(roll);
						
						panel.add(tempAndHum);
						panel.add(tiltAndRoll);
					}
				};
				
				// Load the visualization api, passing the onLoadCallback to be called
				// when loading is done.
		 VisualizationUtils.loadVisualizationApi(onLoadCallback, LineChart.PACKAGE);
	}

	private void createSensorTable() {
		HLayout sensorTableLayout = new HLayout();
		sensorTableLayout.setHeight(200);
		sensorTableLayout.setWidth(600);
		sensorTableLayout.setMembersMargin(20);
		sensorTableLayout.setLayoutMargin(10);

		VLayout tableWithButton = new VLayout();
		tableWithButton.setShowEdges(true);
		tableWithButton.setEdgeSize(3);
		tableWithButton.setWidth(600);
		tableWithButton.setMembersMargin(20);
		tableWithButton.setLayoutMargin(10);

		sensorTable = new ListGrid();
		ListGridField idSensor = new ListGridField("idSensor", "Sensor ID");
		ListGridField location = new ListGridField("location", "Location");
		ListGridField sensorIp = new ListGridField("sensorIp", "Sensor IP");
		sensorTable.setFields(idSensor, location, sensorIp);
		// User can only select one sensor node
		sensorTable.setSelectionType(SelectionStyle.SINGLE);

		textSelectednode = new Label();
		textSelectednode.setContents("<h4>Please select a sensor node! </h4>");
		

		Button showDia = new Button("Show Diagrams");
		addClickhandlerToShowDia(showDia);

		sensorTableLayout.addMember(sensorTable);
		sensorTableLayout.addMember(textSelectednode);

		tableWithButton.addMember(sensorTableLayout);
		tableWithButton.addMember(showDia);

		tableWithButton.setDefaultLayoutAlign(Alignment.CENTER);

		panel.add(tableWithButton);

	}

	/**
	 * Get the Sensor-ID from Database and add the ID's to the ListBox
	 */
	private void updateSensorSelection() {

		server.getSensors(new AsyncCallback<List<Sensor>>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Error",
						"Ups.. you have no access to database. Please reload the page!");

			}

			@Override
			public void onSuccess(List<Sensor> result) {
				for (Sensor s : result) {
					// save the Sensors, because we need the nodes later
					sensors.put(s.getIdSensor(), s);

					ListGridRecord sensorInfo = new ListGridRecord();
					sensorInfo.setAttribute("idSensor", s.getIdSensor());
					sensorInfo.setAttribute("location", s.getLocation());
					sensorInfo.setAttribute("sensorIp", s.getIp());
					sensorTable.addData(sensorInfo);
				}

			}

		});

	}

	/**
	 * If the user click the Button, he get the selected node and load the
	 * diagrams of this node
	 * 
	 * @param showDia
	 *            Button
	 */
	private void addClickhandlerToShowDia(Button showDia) {
		showDia.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String sensorID = "";
				try {
					sensorID = sensorTable.getSelectedRecord().getAttribute(
							"idSensor");

					if (firstClick) {
						drawCharts();
						firstClick = false;
					}
					textSelectednode
							.setContents("<h4>Diagrams from node: </h4>"
									+ sensorID);
					// failure here... think, the diagrams are not ready
//					getProperties(sensorID, "temperature");
//					getProperties(sensorID, "humidity");
					startTimer(sensorID);
					
					showEvents(sensorID);
					
					//showEvents();
				} catch (NullPointerException ex) {
					SC.say("Error", "Please select a sensor node!");
				}
			}
		});

	}
	

	private void getProperties(String sensorID, final String type) {
		Sensor sensor = sensors.get(sensorID);

		PropertyType proType = propTypes.get(type);

		server.getProperty(sensor, proType,
				new AsyncCallback<List<Property>>() {

					@Override
					public void onSuccess(List<Property> result) {
						AbstractDataTable table = createTable(result, type);
						if (type.equals("temperature")) {
							temperature.draw(table);
						} else if (type.equals("humidity")) {
							humidity.draw(table);
						} else if (type.equals("tilt")) {
							tilt.draw(table);
						} else {
							roll.draw(table);
						}

					}

					@Override
					public void onFailure(Throwable caught) {
						SC.say("Error",
								"Ups.. you have no access to database. Please reload the page!");
					}
				});

	}

	/**
	 * Save all PropertyTypes in a HashMap, because we need the types several
	 * times
	 */
	private void getAllPropertyTypes() {
		server.getPropertyTypes(new AsyncCallback<List<PropertyType>>() {

			@Override
			public void onSuccess(List<PropertyType> result) {
				for (PropertyType type : result) {
					propTypes.put(type.getName(), type);
				}

			}

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Error",
						"Ups.. you have no access to database. Please reload the page!");

			}
		});
	}

	private Options createChartOptions(String title) {
		Options options = Options.create();
		options.setWidth(600);
		options.setHeight(240);
		options.setTitle(title);
		return options;
	}

	private AbstractDataTable createTable(List<Property> list, String title) {
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.DATE, "Time");
		data.addColumn(ColumnType.NUMBER, title);
		int i = 0;
		if (list != null) {
			data.addRows(list.size());
			for (Property p : list) {
				data.setValue(i, 0, p.getTimestamp());
				data.setValue(i, 1, p.getValue());
				i++;
			}
		}
		

		return data;
	}

	private void startTimer(final String sensorID) {
		Timer t = new Timer() {
			public void run() {

				getProperties(sensorID, "temperature");
				getProperties(sensorID, "humidity");
				getProperties(sensorID, "tilt");
				getProperties(sensorID, "roll");
			}
		};

		// get all 5 sec new properties
		t.scheduleRepeating(5000);

	}
	
	private void showEvents(String sensorID){
		VLayout eventTableLayout = new VLayout();
		eventTableLayout.setShowEdges(true);
		eventTableLayout.setEdgeSize(3);
		eventTableLayout.setHeight(300);
		eventTableLayout.setWidth(600);
		eventTableLayout.setMembersMargin(10);
		eventTableLayout.setLayoutMargin(10);
		
		
		Label eventLabelHeader = new Label();
		eventLabelHeader.setContents("<h3>Events from sensor node: "+sensorID +"</h3>");
		eventTableLayout.addMember(eventLabelHeader);
		
		final ListGrid eventTable = new ListGrid();
		ListGridField timestamp = new ListGridField("timestamp", "Timestamp");
		ListGridField eventtype = new ListGridField("eventtype", "Event type");
		ListGridField eventdecription = new ListGridField("eventdecription", "Description type");
		ListGridField sensorId = new ListGridField("sensorId", "Sensor ID");
		eventTable.setFields(timestamp, eventtype, eventdecription, sensorId);
		
		
		server.getEventList(new AsyncCallback<List<Event>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(List<Event> result) {
				for(Event e: result){
					ListGridRecord eventInfo = new ListGridRecord();
					eventInfo.setAttribute("timestamp", e.getTimestamp().toString());
					EventType eventType = eventTypes.get(e.getEventtypeIdeventtype());
					eventInfo.setAttribute("eventtype", eventType.getName());
					eventInfo.setAttribute("eventdecription", eventType.getDescription());
					eventInfo.setAttribute("sensorId", e.getSensorIdsensor());
					eventTable.addData(eventInfo);
				}
				
			}
		});
		
		eventTableLayout.addMember(eventTable);
		panel.add(eventTableLayout);
	}
}
