package de.berlin.fu.client;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.LegendPosition;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.LineChart;
import com.google.gwt.visualization.client.visualizations.LineChart.Options;
import com.google.gwt.visualization.client.visualizations.Table;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.HStack;
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

	ListBox sensorBox = new ListBox();

	private final HashMap<String, Sensor> sensors = new HashMap<String, Sensor>();

	private final HashMap<Integer, PropertyType> propTypes = new HashMap<Integer, PropertyType>();
	private final HashMap<Integer, EventType> eventTypes = new HashMap<Integer, EventType>();

	private final HashMap<Integer, LineChart> charts = new HashMap<Integer, LineChart>();

	private Panel panel;

	private boolean firstClick = true;

	private final DateTimeFormat formatter = DateTimeFormat
			.getFormat("HH:mm:ss");

	private final TabPanel tabPanel = new TabPanel();

	/**
	 * Refresh interval from timer in seconds
	 */
	private final int refreshInterval = 5;

	private Timer timer = null;

	private final Window waitingWindow = new Window();

	private final VLayout sensorInfoLayout = new VLayout();

	private final Label sensorInfoID = new Label();
	private final Label sensorInfoLoc = new Label();
	private final Label sensorInfoIp = new Label();

	private Table eventTable;

	@Override
	public void onModuleLoad() {
		panel = RootPanel.get();
		getAllPropertyTypes();
		getAllEventTypes();

		createSensorSelection();

		createWaitingWindow();
	}

	/**
	 * Save all PropertyTypes in a HashMap, because we need the types several
	 * times.
	 */
	private void getAllPropertyTypes() {
		server.getPropertyTypes(new AsyncCallback<List<PropertyType>>() {

			@Override
			public void onSuccess(List<PropertyType> result) {
				for (PropertyType type : result) {
					propTypes.put(type.getIdPropertyType(), type);
				}

			}

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Error",
						"Ups.. you have no access to database. Please reload the page! Propertytype");

			}
		});
	}

	/**
	 * Save all EventTypes in a HashMap, because we need the types several
	 * times.
	 */
	private void getAllEventTypes() {
		server.getEventTypes(new AsyncCallback<List<EventType>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(List<EventType> result) {
				for (EventType etype : result) {
					eventTypes.put(etype.getIdEventType(), etype);
				}

			}
		});

	}

	/**
	 * Draw a Layout with the ListBox for the sensor selection.
	 */
	private void createSensorSelection() {
		HLayout boxWithLayer = new HLayout();
		boxWithLayer.setMembersMargin(20);
		boxWithLayer.setLayoutMargin(10);
		boxWithLayer.setWidth(600);

		addSensorHandler();

		sensorInfoLayout.setMembersMargin(10);
		sensorInfoLayout.setLayoutMargin(10);
		sensorInfoLayout.setWidth(400);
		sensorInfoLayout.setHeight(150);
		sensorInfoLayout.setShowEdges(true);
		sensorInfoLayout.setEdgeSize(3);

		sensorInfoID.setHeight(30);
		sensorInfoLoc.setHeight(30);
		sensorInfoIp.setHeight(30);

		sensorInfoLayout.addMember(sensorInfoID);
		sensorInfoLayout.addMember(sensorInfoLoc);
		sensorInfoLayout.addMember(sensorInfoIp);

		boxWithLayer.addMember(sensorBox);
		boxWithLayer.addMember(sensorInfoLayout);

		sensorInfoLayout.hide();

		updateSensorSelection();

		panel.add(boxWithLayer);

	}

	/**
	 * Add a addChangeHandler to sensor selection. If the user select a sensor
	 * node, the "onChange"-method is called.
	 */
	private void addSensorHandler() {
		sensorBox.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				int selectedItemIndex = sensorBox.getSelectedIndex();
				if (selectedItemIndex == 0) {
					SC.say("Error", "Please select a sensor node!");
					// hide the sensor information
					sensorInfoLayout.hide();
					// hide the tab-menu with diagrams
					tabPanel.setVisible(false);

				} else {
					String sensorID = sensorBox.getValue(selectedItemIndex);
					Sensor sensor = sensors.get(sensorID);

					if (firstClick) {

						drawTabMenu(sensorID);

						firstClick = false;
					}
					sensorInfoLayout.show();
					waitingWindow.show();
					// hide the tabPanel, because we have no data yet
					tabPanel.setVisible(false);
					// the first timer react after 1 seconds and run only 1
					// round
					firstDBAccess(sensorID);
					sensorInfoID.setContents("<strong> node: </strong>"
							+ sensorID);
					sensorInfoLoc.setContents("<strong> location: </strong>"
							+ sensor.getLocation());
					sensorInfoIp.setContents("<strong> IP: </strong>"
							+ sensor.getIpString());
					// update the properties periodically
					startTimer(sensorID);

				}

			}
		});
	}

	private void createWaitingWindow() {
		waitingWindow.setTitle("Waiting...");
		waitingWindow.setWidth(250);
		waitingWindow.setCanDragResize(true);
		waitingWindow.setShowCloseButton(false);
		waitingWindow.setIsModal(true);
		waitingWindow.setShowModalMask(true);
		waitingWindow.centerInPage();

		HStack layout = new HStack();
		layout.setMembersMargin(10);
		layout.setLayoutMargin(10);

		Img spinningWheel = new Img("loadingSmall.gif", 16, 16);

		layout.addMember(spinningWheel);

		Label text = new Label();
		text.setContents("Please wait a moment!");

		layout.addMember(text);

		waitingWindow.addItem(layout);
	}

	/**
	 * Get the Sensor-ID from Database and add the ID's to the ListBox.
	 */
	private void updateSensorSelection() {

		server.getSensors(new AsyncCallback<List<Sensor>>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Error",
						"Ups.. you have no access to database. Please reload the page! Sensor");

			}

			@Override
			public void onSuccess(List<Sensor> result) {
				sensorBox.addItem("Select a node...");

				for (Sensor s : result) {
					// save the Sensors, because we need the nodes later
					String sensorID = s.getIdSensor();
					sensors.put(sensorID, s);

					sensorBox.addItem(sensorID);

				}

			}

		});

	}

	/**
	 * Draw the tab menu. One tab and one chart for one PropertyType.
	 * 
	 * @param sensorID
	 *            ID from sensor
	 */
	private void drawTabMenu(String sensorID) {
		VisualizationUtils.loadVisualizationApi(new Runnable() {
			@Override
			public void run() {
				eventTable = new Table();
				final VerticalPanel vp = new VerticalPanel();
				vp.getElement().getStyle().setPropertyPx("margin", 15);
				panel.add(vp);
				vp.add(tabPanel);
				tabPanel.setWidth("1000");
				tabPanel.setHeight("1000");

				for (Integer type : propTypes.keySet()) {
					String propName = propTypes.get(type).getName();
					LineChart chart = createLineChart(propName);
					charts.put(type, chart);
					tabPanel.add(chart, propName);
				}

				tabPanel.add(createEventTable(), "Events");
				tabPanel.selectTab(0);
			}
		}, LineChart.PACKAGE, Table.PACKAGE);

		tabPanel.setVisible(false);

	}

	/**
	 * @param propName
	 *            the PropertyType name
	 * @return a generated LineChart
	 */
	private LineChart createLineChart(String propName) {
		LineChart chart = new LineChart(createChartTable(null, propName),
				createChartOptions(propName));
		return chart;

	}

	private Table createEventTable() {
		Panel hPanel = new HorizontalPanel();
		Panel flowPanel = new FlowPanel();
		hPanel.add(flowPanel);
		flowPanel.add(eventTable);

		eventTable.draw(createEventTable(null));
		return eventTable;
	}

	/**
	 * @param title
	 *            from chart (PropertyType)
	 * @return options for this charts
	 */
	private Options createChartOptions(String title) {
		Options options = Options.create();
		options.setWidth(900);
		options.setHeight(600);
		options.setTitle(title);
		options.setLegend(LegendPosition.NONE);
		return options;
	}

	/**
	 * Create a datatable for the charts. Google charts need this function to
	 * draw a chart.
	 * 
	 * @param list
	 *            of all Properties
	 * @param title
	 *            from chart
	 * @return a datatable for this chart
	 */
	private AbstractDataTable createChartTable(List<Property> list, String title) {

		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "Time");
		data.addColumn(ColumnType.NUMBER, title);
		int i = 0;
		if (list != null) {
			data.addRows(list.size());
			for (Property p : list) {
				Date time = p.getTimestamp();
				data.setValue(i, 0, formatter.format(time));
				data.setValue(i, 1, p.getValue());
				i++;
			}
		}

		return data;
	}

	/**
	 * Create datatable for event view.
	 * 
	 * @param list
	 *            of events per sensor
	 * @return a datatable for event view
	 */
	private AbstractDataTable createEventTable(List<Event> list) {
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "Timestamp");
		data.addColumn(ColumnType.STRING, "Eventtype");
		data.addColumn(ColumnType.STRING, "Eventdescription");
		data.addColumn(ColumnType.STRING, "SensorID");
		int i = 0;
		if (list != null) {
			data.addRows(list.size());
			for (Event e : list) {
				EventType eventType = eventTypes.get(e
						.getEventtypeIdeventtype());
				Date time = e.getTimestamp();
				data.setValue(i, 0, formatter.format(time));
				data.setValue(i, 1, eventType.getName());
				data.setValue(i, 2, eventType.getDescription());
				data.setValue(i, 3, e.getSensorIdsensor());
				i++;
			}
		}

		return data;
	}

	/**
	 * Get the last 30 entries per PropertyType from database and display the
	 * data in LineChart.
	 * 
	 * @param sensorID
	 *            ID from sensor
	 */
	private void getProperties(String sensorID) {
		Sensor sensor = sensors.get(sensorID);

		for (Integer type : propTypes.keySet()) {
			final String propName = propTypes.get(type).getName();
			final LineChart chart = charts.get(type);

			server.getProperty(sensor, propTypes.get(type), 30,
					new AsyncCallback<List<Property>>() {

						@Override
						public void onSuccess(List<Property> result) {
							AbstractDataTable table = createChartTable(result,
									propName);

							chart.draw(table, createChartOptions(propName));
						}

						@Override
						public void onFailure(Throwable caught) {
							SC.say("Error",
									"Ups.. you have no access to database. Please reload the page! Property");
						}
					});

		}

	}

	private void getEvents(String sensorID) {
		Sensor sensor = sensors.get(sensorID);

		server.getEventList(sensor, new AsyncCallback<List<Event>>() {

			@Override
			public void onSuccess(List<Event> result) {
				AbstractDataTable table = createEventTable(result);
				// eventTable.draw(table);

			}

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Error",
						"Ups.. you have no access to database. Please reload the page! Event");

			}
		});
	}

	/**
	 * Start a timer, which terminate after 2 seconds and make the first access
	 * to database. (We need the timer, because it takes a while to draw the
	 * charts)
	 * 
	 * @param sensorID
	 *            ID from sensor
	 */
	private void firstDBAccess(final String sensorID) {

		Timer firstTimer = new Timer() {

			@Override
			public void run() {
				getProperties(sensorID);
				getEvents(sensorID);
				tabPanel.setVisible(true);
				waitingWindow.hide();
			}
		};

		firstTimer.schedule(1000);
	}

	/**
	 * Start a timer, which refresh the data all {@value #refreshInterval}
	 * seconds
	 * 
	 * @param sensorID
	 *            ID from sensor
	 */
	private void startTimer(final String sensorID) {
		if (timer != null) {
			// kill the old timer
			timer.cancel();
		}

		timer = new Timer() {
			@Override
			public void run() {

				System.out.println("--------------------------------------");
				getProperties(sensorID);
				getEvents(sensorID);
			}
		};

		// timer.scheduleRepeating(refreshInterval * 1000);
		timer.schedule(refreshInterval * 1000);
	}

}
