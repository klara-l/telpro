package de.berlin.fu.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.LineChart;
import com.google.gwt.visualization.client.visualizations.LineChart.Options;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.util.SC;

import de.berlin.fu.data.dto.Property;
import de.berlin.fu.data.dto.PropertyType;
import de.berlin.fu.data.dto.Sensor;
import de.berlin.fu.shared.MyServer;
import de.berlin.fu.shared.MyServerAsync;

public class TelproGWT implements EntryPoint {

	private final MyServerAsync server = GWT.create(MyServer.class);
	private ListGrid sensorTable;
	
	HashMap<String, Sensor> sensors;
	
	HashMap<String, PropertyType> propTypes;
	
	LineChart temperature;
	LineChart humidity;
	
	public void onModuleLoad() {
		sensors = new HashMap<String, Sensor>();
		propTypes = new HashMap<String, PropertyType>();
		
		final Panel panel = RootPanel.get();
		
		getAllPropertyTypes();
		
		createSensorTable();
		updateSensorSelection();
		
		Button showDia = new Button("Show Diagrams");
		addClickhandlerToShowDia(showDia);
		
		panel.add(sensorTable);
		panel.add(showDia);
		
		//add Charts
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				temperature = new LineChart(createTable(null, "Temperature"), createChartOptions("Temperature"));
				humidity = new LineChart(createTable(null, "Humidity"), createChartOptions("Humidity"));
				panel.add(temperature);
				panel.add(humidity);
			}
		};
		// Load the visualization api, passing the onLoadCallback to be called
		// when loading is done.
		 VisualizationUtils.loadVisualizationApi(onLoadCallback, LineChart.PACKAGE);
		
	}



	private void createSensorTable(){
		sensorTable = new ListGrid();
		ListGridField idSensor = new ListGridField("idSensor", "Sensor ID");
		ListGridField location = new ListGridField("location", "Location");
		ListGridField sensorIp = new ListGridField("sensorIp", "Sensor IP");
		sensorTable.setFields(idSensor, location, sensorIp);
		//User can only select one sensor node
		sensorTable.setSelectionType(SelectionStyle.SINGLE);
	}

	/**
	 * Get the Sensor-ID from Database and add the ID's to the ListBox
	 */
	private void updateSensorSelection(){
		
		server.getSensors(new AsyncCallback<List<Sensor>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO add a failure message
				
			}

			@Override
			public void onSuccess(List<Sensor> result) {
				for(Sensor s: result){
					//save the Sensors, because we need the nodes later
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
	 * If the user click the Button, get the selected node and load the diagrams of this node
	 * @param showDia Button 
	 */
	private void addClickhandlerToShowDia(Button showDia) {
		showDia.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				String sensorID ="";
				try{
					sensorID = sensorTable.getSelectedRecord().getAttribute("idSensor");
					
					getPropertiesTemp(sensorID);
					
				} catch(NullPointerException ex){
					SC.say("Error", "Please select a sensor node!");
				}
			}
		});
		
	}
	
	private void getPropertiesTemp(String sensorID){
		Sensor sensor = sensors.get(sensorID);
		
		PropertyType prop = propTypes.get("temperature");
		
	}
	
	/**
	 * Save all PropertyTypes in a HashMap, because we need the types several times
	 */
	private void getAllPropertyTypes(){
		server.getPropertyTypes(new AsyncCallback<List<PropertyType>>() {
			
			@Override
			public void onSuccess(List<PropertyType> result) {
				for(PropertyType type: result){
					propTypes.put(type.getName(), type);
				}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO add failure message
				
			}
		});
	}
	private Options createChartOptions(String title) {
		Options options = Options.create();
		options.setWidth(400);
		options.setHeight(240);
		options.setTitle(title);
		return options;
	}
	
	private AbstractDataTable createTable(ArrayList<Property> list, String title) {
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "Time");
		data.addColumn(ColumnType.NUMBER, title);
		int i = 0;
		if(list!=null){
			data.addRows(list.size());
			for(Property p: list){
				data.setValue(i, 0, p.getTimestamp());
				data.setValue(i, 1, p.getValue());
				i++;
			}
		}
		

		return data;
	}
}
