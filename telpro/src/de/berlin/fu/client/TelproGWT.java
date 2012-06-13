package de.berlin.fu.client;

import java.util.LinkedHashMap;
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

import de.berlin.fu.data.dto.Event;
import de.berlin.fu.shared.MyServer;
import de.berlin.fu.shared.MyServerAsync;

public class TelproGWT implements EntryPoint {

	private final MyServerAsync server = GWT.create(MyServer.class);

	public void onModuleLoad() {
		// Create a callback to be called when the visualization API
		// has been loaded.

		server.getEventList(new AsyncCallback<List<Event>>() {

			@Override
			public void onSuccess(List<Event> result) {
				System.out.println("got list: " + result);

			}

			@Override
			public void onFailure(Throwable caught) {
				System.out.println(caught);

			}
		});

		Runnable onLoadCallback = new Runnable() {
			public void run() {
				Panel panel = RootPanel.get();
				// create a line chart
				LinkedHashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
				
				temp.put("11:45", 20);
				temp.put("11:46", 26);
				temp.put("11:47", 21);
				temp.put("11:48", 19);
				temp.put("11:49", 15);
				temp.put("11:50", 21);
				temp.put("11:51", 20);

				 LineChart line = new LineChart(createTempTable(temp), createOptions());

				// Create a pie chart visualization.
				//PieChart pie = new PieChart(createTable(), createOptions());

				//pie.addSelectHandler(createSelectHandler(pie));
				//panel.add(pie);
				panel.add(line);
			}
		};

		// Load the visualization api, passing the onLoadCallback to be called
		// when loading is done.
		 VisualizationUtils.loadVisualizationApi(onLoadCallback, LineChart.PACKAGE);
	}

	private Options createOptions() {
		Options options = Options.create();
		options.setWidth(400);
		options.setHeight(240);
		options.setTitle("Temperature in a day");
		return options;
	}


	private AbstractDataTable createTempTable(LinkedHashMap<String, Integer> list) {
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "Time");
		data.addColumn(ColumnType.NUMBER, "Temperatur");
		data.addRows(list.size());
		int i = 0;
		for (String time : list.keySet()) {
			data.setValue(i, 0, time);
			data.setValue(i, 1, list.get(time));
			i++;
		}

		return data;
	}
}
