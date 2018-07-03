package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import model.Flight;

/**
 * This class contains methods to access Flight table.
 * 
 * @author Jieun Lee (jieun212@uw.edu)
 * @version 11-22-2016
 */
public class FlightDB {

	/** A connection */
	private static Connection myConnection;

	/** A list of Flight */
	private static List<Flight> myFlightList;

	
	/**
	 * Retrieves all Flights from Flight table.
	 * 
	 * @return A list of Flights
	 * @throws SQLException
	 */
	public static List<Flight> getAllFlights() throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		String query = "SELECT * " + "FROM Flight;";

		myFlightList = new ArrayList<Flight>();

		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String flightNo = result.getString("Flight_no");
				String tailNo = result.getString("Tail_no");
				String dep_airport = result.getString("Departure_airport");
				Date dep_date = result.getDate("Departure_date");
				Time dep_time = result.getTime("Departure_time");
				String dep_gate = result.getString("Departure_gate");
				String arr_airport = result.getString("Arrival_airport");
				Date arr_date = result.getDate("Arrival_date");
				Time arr_time = result.getTime("Arrival_time");
				String arr_gate = result.getString("Arrival_gate");
				double basePrice = result.getDouble("Base_price");

				Flight flight = new Flight(tailNo, dep_airport, dep_date, dep_time, dep_gate, arr_airport, arr_date,
						arr_time, arr_gate, basePrice);
				flight.setFlightNo(flightNo);

				myFlightList.add(flight);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (statement != null) {
				statement.close();
			}
		}

		return myFlightList;
	}

	/**
	 * Retrieves all Flights whose departure date is given date from Flight table.
	 * 
	 * @param theSearchingDate
	 * @return Flights whose departure date is given date from Flight table.
	 * @throws SQLException
	 */
	public static List<Flight> getFlightsFrom(final Date theSearchingDate) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		String query = "SELECT * " + "FROM Flight WHERE Departure_date = " + theSearchingDate;

		List<Flight> filteredList = new ArrayList<Flight>();

		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String flightNo = result.getString("Flight_no");
				String tailNo = result.getString("Tail_no");
				String dep_airport = result.getString("Departure_airport");
				Date dep_date = result.getDate("Departure_date");
				Time dep_time = result.getTime("Departure_time");
				String dep_gate = result.getString("Departure_gate");
				String arr_airport = result.getString("Arrival_airport");
				Date arr_date = result.getDate("Arrival_date");
				Time arr_time = result.getTime("Arrival_time");
				String arr_gate = result.getString("Arrival_gate");
				double basePrice = result.getDouble("Base_price");

				Flight flight = new Flight(tailNo, dep_airport, dep_date, dep_time, dep_gate, arr_airport, arr_date,
						arr_time, arr_gate, basePrice);
				flight.setFlightNo(flightNo);
				filteredList.add(flight);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (statement != null) {
				statement.close();
			}
		}

		return filteredList;
	}

	/**
	 * Retrieves Flight whose flight number is given from the Flight table
	 * 
	 * @param theFlightNo
	 * @return Flight whose flight number is given
	 * @throws SQLException
	 */
	public static Flight getFlightOf(final String theFlightNo) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		String query = "SELECT * " + "FROM Flight WHERE Flight_no = " + theFlightNo;

		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String flightNo = result.getString("Flight_no");
				String tailNo = result.getString("Tail_no");
				String dep_airport = result.getString("Departure_airport");
				Date dep_date = result.getDate("Departure_date");
				Time dep_time = result.getTime("Departure_time");
				String dep_gate = result.getString("Departure_gate");
				String arr_airport = result.getString("Arrival_airport");
				Date arr_date = result.getDate("Arrival_date");
				Time arr_time = result.getTime("Arrival_time");
				String arr_gate = result.getString("Arrival_gate");
				double basePrice = result.getDouble("Base_price");

				Flight flight = new Flight(tailNo, dep_airport, dep_date, dep_time, dep_gate, arr_airport, arr_date,
						arr_time, arr_gate, basePrice);
				flight.setFlightNo(flightNo);
				return flight;

			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
		return null;
	}

	/**
	 * Retrieves Flights with given departure and arrival information.
	 * 
	 * @param theDepartureAirport
	 * @param theDepartureDate
	 * @param theArrivalAirport
	 * @return Flights with given departure and arrival information.
	 * @throws SQLException
	 */
	public static List<Flight> getFlights(String theDepartureAirport, Date theDepartureDate, String theArrivalAirport)
			throws SQLException {

		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		theDepartureAirport = theDepartureAirport.toUpperCase();
		theArrivalAirport = theArrivalAirport.toUpperCase();

		List<Flight> filteredList = new ArrayList<Flight>();
		Statement statement = null;
		String query = "SELECT * FROM Flight" + " WHERE Departure_airport = '" + theDepartureAirport
				+ "' AND Arrival_airport = '" + theArrivalAirport + "' AND Departure_date = '" + theDepartureDate + "'";

		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String flightNo = result.getString("Flight_no");
				String tailNo = result.getString("Tail_no");
				String dep_airport = result.getString("Departure_airport");
				Date dep_date = result.getDate("Departure_date");
				Time dep_time = result.getTime("Departure_time");
				String dep_gate = result.getString("Departure_gate");
				String arr_airport = result.getString("Arrival_airport");
				Date arr_date = result.getDate("Arrival_date");
				Time arr_time = result.getTime("Arrival_time");
				String arr_gate = result.getString("Arrival_gate");
				double basePrice = result.getDouble("Base_price");

				Flight flight = new Flight(tailNo, dep_airport, dep_date, dep_time, dep_gate, arr_airport, arr_date,
						arr_time, arr_gate, basePrice);
				flight.setFlightNo(flightNo);
				filteredList.add(flight);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (statement != null) {
				statement.close();
			}
		}

		return filteredList;
	}

	
	/**
	 * Get selling price of the given flight number
	 * 
	 * @param theFlightNo
	 * @return Selling price
	 * @throws SQLException
	 */
	public static double getSellingPriceOf(final String theFlightNo) throws SQLException {

		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		String query = "SELECT" + " CASE WHEN DATE_SUB(NOW(), INTERVAL 14 DAY) " + " THEN ( CASE"
				+ " WHEN ROUND(COUNT(t.Ticket_no)/c.capacity *100, 0) > 75 THEN (f.base_price*2.0) "
				+ " WHEN ROUND(COUNT(t.Ticket_no)/c.capacity *100, 0) < 75 THEN (f.base_price * 0.5) END) END Selling_price"
				+ " FROM Flight f LEFT OUTER JOIN Ticket t ON t.Flight_no = f.Flight_no JOIN Aircraft c ON f.Tail_no= c.Tail_no"
				+ " WHERE f.Flight_no = " + theFlightNo + " GROUP BY f.Flight_no  ORDER BY (f.Flight_no + 0);";

		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) { // TODO delete unnecessary information when
									// creating GUI
				double sellingPrice = result.getDouble("Selling_price");
				return sellingPrice;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (statement != null) {
				statement.close();
			}
		}

		return 0;
	}

	/**
	 * Get a percentage of capacity of given flight number.
	 * 
	 * @param theFlightNo
	 * @return A percentage of capacity
	 * @throws SQLException
	 */
	public static int getPercentCapacityOf(String theFlightNo) throws SQLException {

		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		String query = "SELECT ROUND(COUNT(t.Ticket_no)/c.capacity*100, 0) AS Capacity,"
				+ " FROM Flight f LEFT OUTER JOIN Ticket t ON t.Flight_no = f.Flight_no JOIN Aircraft c ON f.Tail_no= c.Tail_no"
				+ " WHERE f.Flight_no = " + theFlightNo + " GROUP BY f.Flight_no" + " ORDER BY (f.Flight_no + 0);";
		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {

				int capacity = result.getInt("Capacity");
				return capacity;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
		return 0;
	}

	/**
	 * Get number of available seat of given flight
	 * 
	 * @param theFlightNo
	 * @return Number of available seat
	 * @throws SQLException
	 */
	public static int getNumAvailableSeatOf(String theFlightNo) throws SQLException {

		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		String query = "SELECT (c.capacity - COUNT(t.Ticket_no)) AS available_seat"
				+ " FROM Flight f LEFT OUTER JOIN Ticket t ON t.Flight_no = f.Flight_no JOIN Aircraft c ON f.Tail_no= c.Tail_no"
				+ " WHERE f.Flight_no = " + theFlightNo + " GROUP BY f.Flight_no";

		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {

				int left_seat = result.getInt("available_seat");
				return left_seat;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
		return 0;
	}

	/**
	 * True if there is a flight of given departure and arrival information.
	 * 
	 * @param theDepartureAirport
	 * @param theDepartureDate
	 * @param theDepartureTime
	 * @param theArrivalAirport
	 * @return True if there is a flight of given departure and arrival information.
	 * @throws SQLException
	 */
	public static boolean hasFlight(final String theDepartureAirport, final Date theDepartureDate,
			final Time theDepartureTime, final String theArrivalAirport) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		String query = "SELECT COUNT(Flight_no) AS Flight_count FROM Flight" + " WHERE Departure_airport = '"
				+ theDepartureAirport + "' AND Departure_date = '" + theDepartureDate + "' AND Departure_time = '"
				+ theDepartureTime + "' AND Arrival_airport = '" + theArrivalAirport + "' GROUP BY Flight_no";


		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String count_str = result.getString("Flight_count");
				int count = Integer.valueOf(count_str);
				if (count > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (statement != null) {
				statement.close();
			}
		}

		return false;
	}
	
	/**
	 * Returns true if the given flight is an international inbound.
	 * @param theFlightNo
	 * @return True if the given flight is an international inbound.
	 * @throws SQLException
	 */
	public static boolean isInternationalFlight(final String theFlightNo) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		String query = "SELECT COUNT(f.Flight_no) AS isInternational"
				+ " FROM Flight f LEFT JOIN Airport departure ON f.Departure_airport = departure.Airport_code"
				+ " JOIN Airport arrival ON f.Arrival_airport = arrival.Airport_code"
				+ " WHERE f.Flight_no = '" + theFlightNo
				+ "' AND departure.country != 'UNITED STATES' AND arrival.country = 'UNITED STATES'";
		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String count_str = result.getString("isInternational");
				int count = Integer.valueOf(count_str);
				if (count > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (statement != null) {
				statement.close();
			}
		}

		return false;
	}

	/**
	 * Add the given Flight to the Flight table.
	 * 
	 * @param theAircraft
	 * @return Returns a message with success or failure.
	 * @throws SQLException
	 */
	public static String addFlight(Flight theFlight) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		PreparedStatement preparedStatement = null;
		String query = "INSERT INTO Flight(Tail_no, Departure_airport, Departure_date, Departure_time, Arrival_airport, Arrival_date, Arrival_time, Base_price) "
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

		try {
			preparedStatement = myConnection.prepareStatement(query);
			preparedStatement.setString(1, theFlight.getTailNo());
			preparedStatement.setString(2, theFlight.getDepartureAirport());
			preparedStatement.setDate(3, theFlight.getDepartureDate());
			preparedStatement.setTime(4, theFlight.getDepartureTime());
			preparedStatement.setString(5, theFlight.getArrivalAirport());
			preparedStatement.setDate(6, theFlight.getArrivalDate());
			preparedStatement.setTime(7, theFlight.getArrivalTime());
			preparedStatement.setDouble(8, theFlight.getBasePrice());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error adding Flight: " + e.getMessage();
		}
		return "Flight added Successfully";

	}

	/**
	 * Modifies the data on a Flight - except flight_no
	 * 
	 * @param theFlight
	 * @param theColumnName
	 * @param theData
	 * @return Returns a message with success or failure.
	 */
	public static String updateFlight(Flight theFlight, String theColumnName, Object theData) {

		String flight_no = theFlight.getFlightNo();
		String sql = "UPDATE Flight SET `" + theColumnName + "` = ?  WHERE Flight_no = ?;";

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = myConnection.prepareStatement(sql);
			if (theData instanceof String) { // if the data is String
				preparedStatement.setString(1, (String) theData);

			} else if (theData instanceof Date) { // for Date type
				preparedStatement.setDate(1, (Date) theData);

			} else if (theData instanceof Time) {// for Time type
				preparedStatement.setTime(1, (Time) theData);

			} else {
				System.out.println(theData.getClass().getName() + "");
			}
			preparedStatement.setString(2, flight_no);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			return "Error updating flight: " + e.getMessage();
		}
		return "Updated Flight Successfully";
	}

}
