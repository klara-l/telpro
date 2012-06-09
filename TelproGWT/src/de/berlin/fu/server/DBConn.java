package de.berlin.fu.server;

import java.util.HashMap;


public class DBConn {

	public HashMap<String, Integer> getTemperatur(){
		HashMap<String, Integer> temp = new HashMap<String, Integer>();
		
		temp.put("11:45", 20);
		temp.put("11:46", 26);
		temp.put("11:47", 21);
		temp.put("11:48", 19);
		temp.put("11:49", 15);
		temp.put("11:50", 21);
		temp.put("11:51", 20);
		
		return temp;
		
	}
}
