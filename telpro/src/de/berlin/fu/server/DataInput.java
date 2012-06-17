package de.berlin.fu.server;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.berlin.fu.data.dao.PropertyDao;
import de.berlin.fu.data.dao.SensorDao;
import de.berlin.fu.data.dto.Property;
import de.berlin.fu.data.dto.Sensor;
import de.berlin.fu.data.exceptions.PropertyDaoException;
import de.berlin.fu.data.exceptions.SensorDaoException;
import de.berlin.fu.data.factory.PropertyDaoFactory;
import de.berlin.fu.data.factory.SensorDaoFactory;

public class DataInput extends HttpServlet {

	public static int ipToInt(String addr) {
		String[] addrArray = addr.split("\\.");

		int num = 0;

		for (int i = 0; i < addrArray.length; i++) {
			int power = 3 - i;
			num += ((Integer.parseInt(addrArray[i]) % 256 * Math
					.pow(256, power)));
		}
		return num;
	}

	public static String intToIp(int i) {
		return ((i >> 24) & 0xFF) + "." + ((i >> 16) & 0xFF) + "."
				+ ((i >> 8) & 0xFF) + "." + (i & 0xFF);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1104114233075007233L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String deviceID = (String) request.getParameter("deviceID");
		float temp = Float.parseFloat((String) request.getParameter("temp"));
		float humid = Float.parseFloat((String) request.getParameter("humid"));
		float tilt = Float.parseFloat((String) request.getParameter("tilt"));
		float roll = Float.parseFloat((String) request.getParameter("roll"));
		int ip = ipToInt(request.getRemoteAddr());

		Sensor s = new Sensor();
		s.setIp(ip);
		SensorDao sdao = SensorDaoFactory.create();
		PropertyDao pdao = PropertyDaoFactory.create();
		try {
			// updating ip
			s = sdao.findWhereIdSensorEquals(deviceID)[0];
			s.setIp(ip);
			sdao.update(s.createPk(), s);
			// adding the fields
			Property prop = new Property();
			prop.setTimestamp(new Date());
			prop.setSensorIdsensor(deviceID);
			// temp
			prop.setPropertytypeIdpropertytype(1);
			prop.setValue(temp);
			pdao.insert(prop);
			// humid
			prop.setPropertytypeIdpropertytype(2);
			prop.setValue(humid);
			pdao.insert(prop);
			// tilt
			prop.setPropertytypeIdpropertytype(3);
			prop.setValue(tilt);
			pdao.insert(prop);
			// roll
			prop.setPropertytypeIdpropertytype(4);
			prop.setValue(roll);
			pdao.insert(prop);

		} catch (SensorDaoException e) {
			e.printStackTrace();
		} catch (PropertyDaoException e) {
			e.printStackTrace();
		}

		response.setStatus(200);
	}
}
