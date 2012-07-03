package de.berlin.fu.client;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
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
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
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

	private Sensor currentSensor = null;

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
	private static final int refreshInterval = 5;

	private Timer timer = null;

	private final Window waitingWindow = new Window();

	private final HLayout sensorInfoAndButton = new HLayout();

	private final Label sensorInfoID = new Label();
	private final Label sensorInfoLoc = new Label();
	private final Label sensorInfoIp = new Label();

	private Table eventTable;

	private int selectedType;

	// key is tab-number and value is the id from propertytype
	private final HashMap<Integer, Integer> tabs = new HashMap<Integer, Integer>();

	private boolean firstDBAcc;

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

		VLayout sensorInfoLayout = new VLayout();
		sensorInfoLayout.setMembersMargin(10);
		sensorInfoLayout.setLayoutMargin(10);
		sensorInfoLayout.setWidth(300);
		sensorInfoLayout.setHeight(150);

		sensorInfoID.setHeight(30);
		sensorInfoLoc.setHeight(30);
		sensorInfoIp.setHeight(30);

		sensorInfoLayout.addMember(sensorInfoID);
		sensorInfoLayout.addMember(sensorInfoLoc);
		sensorInfoLayout.addMember(sensorInfoIp);

		sensorInfoAndButton.setMembersMargin(10);
		sensorInfoAndButton.setLayoutMargin(10);
		sensorInfoAndButton.setHeight(150);
		sensorInfoAndButton.setShowEdges(true);
		sensorInfoAndButton.setEdgeSize(3);

		sensorInfoAndButton.addMember(sensorInfoLayout);

		Button editSensorInfo = new Button("Edit");
		// editSensorInfo.setIcon("icons/16/icon_add_files.png");

		// if user click the edit button
		editSensorInfo.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				editWindow();

			}
		});

		sensorInfoAndButton.addMember(editSensorInfo);

		boxWithLayer.addMember(sensorBox);
		boxWithLayer.addMember(sensorInfoAndButton);

		sensorInfoAndButton.hide();

		updateSensorSelection();

		panel.add(boxWithLayer);
	}

	private void editWindow() {
		final Window editWindow = new Window();
		editWindow.setTitle("Edit sensor information");

		editWindow.setWidth(300);
		editWindow.setHeight(200);
		editWindow.setCanDragResize(true);
		editWindow.setIsModal(true);
		editWindow.setShowModalMask(true);
		editWindow.centerInPage();

		HLayout twoButtonsLayout = new HLayout();
		twoButtonsLayout.setMembersMargin(10);
		twoButtonsLayout.setLayoutMargin(10);
		twoButtonsLayout.setHeight(60);

		VLayout sensorInfoLayout = new VLayout();
		sensorInfoLayout.setMembersMargin(10);
		sensorInfoLayout.setLayoutMargin(10);
		sensorInfoLayout.setDefaultLayoutAlign(Alignment.CENTER);

		Label sensorIDlabel = new Label("<strong> node: </strong>"
				+ currentSensor.getIdSensor());
		sensorIDlabel.setHeight(20);

		Label sensorIplabel = new Label("<strong> IP: </strong>"
				+ currentSensor.getIpString());
		sensorIplabel.setHeight(20);

		sensorInfoLayout.addMember(sensorIDlabel);
		sensorInfoLayout.addMember(sensorIplabel);

		// we need to put the textitem in a dynamicform, because a simple
		// textitem can't be put to a window
		DynamicForm sensorInfoForm = new DynamicForm();
		sensorInfoForm.setLayoutAlign(VerticalAlignment.BOTTOM);

		final TextItem sensorLocation = new TextItem();
		sensorLocation.setTitle("sensor location");
		sensorLocation.setValue(currentSensor.getLocation());

		Button submitEdit = new Button("Accept");
		Button cancel = new Button("Cancel");
		cancel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				editWindow.hide();

			}
		});

		submitEdit.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				sendNewSensorInfo(sensorLocation.getValueAsString());
				editWindow.hide();
			}
		});

		twoButtonsLayout.addMember(submitEdit);
		twoButtonsLayout.addMember(cancel);

		sensorInfoForm.setFields(sensorLocation);

		editWindow.addItem(sensorInfoForm);
		editWindow.addItem(sensorInfoLayout);
		editWindow.addItem(twoButtonsLayout);
		editWindow.show();
	}

	private void sendNewSensorInfo(String sensorLoc) {

		final String oldLoc = currentSensor.getLocation();

		currentSensor.setLocation(sensorLoc);
		server.updateSensor(currentSensor, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				currentSensor.setLocation(oldLoc);
				SC.say("Error", "Something goes wrong with database!");

			}

			@Override
			public void onSuccess(Void result) {
				sensorInfoLoc.setContents("<strong> location: </strong>"
						+ currentSensor.getLocation());

				SC.say("Success", "Update sensor information!");

			}
		});
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
					sensorInfoAndButton.hide();
					// hide the tab-menu with diagrams
					tabPanel.setVisible(false);

				} else {
					currentSensor = sensors.get(sensorBox
							.getValue(selectedItemIndex));

					if (firstClick) {

						drawTabMenu(currentSensor.getIdSensor());

						firstClick = false;
					}
					sensorInfoAndButton.show();
					waitingWindow.show();
					// hide the tabPanel, because we have no data yet
					tabPanel.setVisible(false);
					// the first timer react after 1 seconds and run only 1
					// round
					firstDBAccess(currentSensor);
					sensorInfoID.setContents("<strong> node: </strong>"
							+ currentSensor.getIdSensor());
					sensorInfoLoc.setContents("<strong> location: </strong>"
							+ currentSensor.getLocation());
					sensorInfoIp.setContents("<strong> IP: </strong>"
							+ currentSensor.getIpString());
					// update the properties periodically
					startTimer(currentSensor);

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
				tabPanel.setHeight("700");

				int i = 0;

				for (Integer type : propTypes.keySet()) {
					String propName = propTypes.get(type).getName();
					LineChart chart = createLineChart(propName);
					charts.put(type, chart);
					tabPanel.add(chart, propName);

					tabs.put(i, type);
					i++;

				}

				tabPanel.add(createEventTable(), "Events");
				// events get -1, because the propertyTypes are positive
				tabs.put(i, -1);

				tabPanel.selectTab(0);
				selectedType = tabs.get(0);

				tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {

					@Override
					public void onSelection(SelectionEvent<Integer> event) {
						selectedType = tabs.get(event.getSelectedItem());
						getData(currentSensor);

					}
				});
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
		data.addColumn(ColumnType.STRING, "Details");
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
				data.setValue(i, 2,
						eventType.getDescription() + " " + e.getValue());
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

	private void getData(Sensor s) {

		if (selectedType != -1) {
			// events isn't selected

			// TODO add here try catch, if we had no internet access
			final LineChart chart = charts.get(selectedType);
			final String propName = propTypes.get(selectedType).getName();

			server.getProperty(s, propTypes.get(selectedType), 30, 2,
					new AsyncCallback<List<Property>>() {

						@Override
						public void onSuccess(List<Property> result) {
							AbstractDataTable table = createChartTable(result,
									propName);

							chart.draw(table, createChartOptions(propName));

							System.out.println("refresh proptype: " + propName);

							if (firstDBAcc) {
								// hide the waiting window, if we get the data
								// and set tab-menu visible
								tabPanel.setVisible(true);
								waitingWindow.hide();

								firstDBAcc = false;
							}

						}

						@Override
						public void onFailure(Throwable caught) {
							SC.say("Error",
									"Ups.. you have no access to database. Please reload the page! Property");
						}
					});
		} else {
			// user selected the event-tab

			server.getEventList(s, new AsyncCallback<List<Event>>() {

				@Override
				public void onSuccess(List<Event> result) {
					AbstractDataTable table = createEventTable(result);
					eventTable.draw(table);

				}

				@Override
				public void onFailure(Throwable caught) {
					SC.say("Error",
							"Ups.. you have no access to database. Please reload the page! Event");

				}
			});
		}

	}

	/**
	 * Start a timer, which terminate after 2 seconds and make the first access
	 * to database. (We need the timer, because it takes a while to draw the
	 * charts)
	 * 
	 * @param sensorID
	 *            ID from sensor
	 */
	private void firstDBAccess(final Sensor s) {

		Timer firstTimer = new Timer() {

			@Override
			public void run() {
				firstDBAcc = true;
				getData(s);

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
	private void startTimer(final Sensor s) {
		if (timer != null) {
			// kill the old timer
			timer.cancel();
		}

		timer = new Timer() {
			@Override
			public void run() {

				System.out.println("--------------------------------------");
				getData(s);
			}
		};

		// timer.scheduleRepeating(refreshInterval * 1000);
		timer.schedule(refreshInterval * 1000);
	}

}
