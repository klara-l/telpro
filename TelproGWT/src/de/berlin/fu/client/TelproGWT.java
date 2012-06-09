package de.berlin.fu.client;

import java.util.HashMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.LineChart;
import com.google.gwt.visualization.client.visualizations.LineChart.Options;

import de.berlin.fu.server.DBConn;


public class TelproGWT implements EntryPoint {
	  public void onModuleLoad() {
	    // Create a callback to be called when the visualization API
	    // has been loaded.
	    Runnable onLoadCallback = new Runnable() {
	      public void run() {
	        Panel panel = RootPanel.get();
	        //create a line chart
	        DBConn conn = new DBConn();
	        
	        LineChart line = new LineChart(createTempTable(conn.getTemperatur()), createOptions());
	 
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
	    options.setTitle("Temperatur in a day");
	    return options;
	  }

	  private SelectHandler createSelectHandler(final LineChart chart) {
	    return new SelectHandler() {
	      @Override
	      public void onSelect(SelectEvent event) {
	        String message = "";
	        
	        // May be multiple selections.
	        JsArray<Selection> selections = chart.getSelections();

	        for (int i = 0; i < selections.length(); i++) {
	          // add a new line for each selection
	          message += i == 0 ? "" : "\n";
	          
	          Selection selection = selections.get(i);

	          if (selection.isCell()) {
	            // isCell() returns true if a cell has been selected.
	            
	            // getRow() returns the row number of the selected cell.
	            int row = selection.getRow();
	            // getColumn() returns the column number of the selected cell.
	            int column = selection.getColumn();
	            message += "cell " + row + ":" + column + " selected";
	          } else if (selection.isRow()) {
	            // isRow() returns true if an entire row has been selected.
	            
	            // getRow() returns the row number of the selected row.
	            int row = selection.getRow();
	            message += "row " + row + " selected";
	          } else {
	            // unreachable
	            message += "Pie chart selections should be either row selections or cell selections.";
	            message += "  Other visualizations support column selections as well.";
	          }
	        }
	        
	        Window.alert(message);
	      }
	    };
	  }

	  private AbstractDataTable createTable() {
	    DataTable data = DataTable.create();
	    data.addColumn(ColumnType.STRING, "Task");
	    data.addColumn(ColumnType.NUMBER, "Hours per Day");
	    data.addRows(2);
	    data.setValue(0, 0, "Work");
	    data.setValue(0, 1, 14);
	    data.setValue(1, 0, "Sleep");
	    data.setValue(1, 1, 10);
	    return data;
	  }
	  
	  private AbstractDataTable createTempTable(HashMap<String,Integer> list){
		  DataTable data = DataTable.create();
		  data.addColumn(ColumnType.STRING, "Time");
		  data.addColumn(ColumnType.NUMBER, "Temperatur" );
		  data.addRows(list.size());
		  int i = 0;
		  for(String time: list.keySet()){
			  data.setValue(i, 0, time);
			  data.setValue(i, 1, list.get(time));
			  i++;
		  }
		  
		  return data;
	  }
}

