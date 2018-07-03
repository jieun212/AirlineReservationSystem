package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Ticket;

/**
 * This class contains methods to access Ticket table.
 * 
 * @author Jieun Lee (jieun212@uw.edu)
 * @version 11-22-2016
 */
public class TicketDB {

	/** A connection */
	private static Connection myConnection;

	/** A list of Flight */
	private static List<Ticket> myTicketList;

	/**
	 * Retrieves all Tickets from the Ticket table
	 * 
	 * @return A List of Tickes
	 * @throws SQLException
	 */
	public static List<Ticket> getAllTickets() throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		String query = "SELECT * " + "FROM Ticket";

		myTicketList = new ArrayList<Ticket>();

		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String ticketNo = result.getString("Ticket_no");
				String reservationNo = result.getString("Reservation_no");
				String flightNo = result.getString("Flight_no");
				String seat = result.getString("Assigned_seat");
				String meal = result.getString("Meal_code");
				String bassinet = result.getString("Bassinet");
				String um = result.getString("UM");
				String wheelchair = result.getString("Wheelchair");
				String pet = result.getString("Pet");

				Ticket ticket = new Ticket(reservationNo, flightNo, seat, meal, bassinet, um, wheelchair, pet);
				ticket.setTicketNo(ticketNo);
				myTicketList.add(ticket);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (statement != null) {
				statement.close();
			}
		}

		return myTicketList;
	}

	/**
	 * Retrieves all Tickets under the given reservation number
	 * 
	 * @param theReservationNo
	 * @return A List of Tickes
	 * @throws SQLException
	 */
	public static List<Ticket> getTicketsOfReservation(final String theReservationNo) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		String query = "SELECT * " + "FROM Ticket WHERE Reservation_no = " + theReservationNo;

		List<Ticket> filteredList = new ArrayList<Ticket>();

		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String ticketNo = result.getString("Ticket_no");
				String reservationNo = result.getString("Reservation_no");
				String flightNo = result.getString("Flight_no");
				String seat = result.getString("Assigned_seat");
				String meal = result.getString("Meal_code");
				String bassinet = result.getString("Bassinet");
				String um = result.getString("UM");
				String wheelchair = result.getString("Wheelchair");
				String pet = result.getString("Pet");

				Ticket ticket = new Ticket(reservationNo, flightNo, seat, meal, bassinet, um, wheelchair, pet);
				ticket.setTicketNo(ticketNo);
				filteredList.add(ticket);
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
	 * Retrieves all tickets of the given passenger with given condition and
	 * order.
	 * 
	 * @param thePassengerNo
	 * @param theViewColumn
	 * @param theViewKey
	 * @param theOrderBy
	 * @return A list of tickets for the given passenger with given condition
	 *         and order.
	 * @throws SQLException
	 */
	public static List<Ticket> getTicketsOfPassenger(final String thePassengerNo, final String theViewColumn,
			String theViewKey, final String theOrderBy) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;

		String order = "t.Reservation_no";
		if (theOrderBy != null) {
			order = theOrderBy;
		}

		String query = "SELECT t.* FROM Passenger p INNER JOIN Reservation r ON p.Passenger_no = r.Passenger_no"
				+ "		INNER JOIN Ticket t ON r.Reservation_no = t.Reservation_no"
				+ "		INNER JOIN Meal m ON t.Meal_code = m.Meal_code"
				+ "		INNER JOIN Flight f ON t.Flight_no = f.Flight_no"
				+ "		INNER JOIN Aircraft c ON f.Tail_no = c.Tail_no"
				+ "		INNER JOIN Airport d ON f.Departure_airport = d.airport_code"
				+ "		INNER JOIN Airport a ON f.Arrival_airport = a.airport_code";
		if (theViewColumn != null && theViewColumn != null) {
			if (!theViewColumn.equalsIgnoreCase("f.Departure_date")) {
				theViewKey = "= '" + theViewKey + "'";
			}
			query += " WHERE p.Passenger_no = '" + thePassengerNo + "'	AND " + theViewColumn + theViewKey
					+ "	ORDER BY " + order;
		} else {
			query += " WHERE p.Passenger_no = '" + thePassengerNo + "'" + " ORDER BY " + order;
		}

		List<Ticket> filteredList = new ArrayList<Ticket>();

		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String ticketNo = result.getString("Ticket_no");
				String reservationNo = result.getString("Reservation_no");
				String flightNo = result.getString("Flight_no");
				String seat = result.getString("Assigned_seat");
				String meal = result.getString("Meal_code");
				String bassinet = result.getString("Bassinet");
				String um = result.getString("UM");
				String wheelchair = result.getString("Wheelchair");
				String pet = result.getString("Pet");

				Ticket ticket = new Ticket(reservationNo, flightNo, seat, meal, bassinet, um, wheelchair, pet);
				ticket.setTicketNo(ticketNo);
				filteredList.add(ticket);
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
	 * Retrieves tickets that are met the given arguments.
	 * 
	 * @param theFlightNo
	 * @param theOrder
	 * @param theView
	 * @return A List of Tickes
	 * @throws SQLException
	 */
	public static List<Ticket> getTicketsOfFlight(final String theFlightNo, final String theViewColumn,
			String theViewKey, final String theOrder) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		String query = "SELECT * FROM Ticket t JOIN Reservation r ON t.Reservation_no = r.Reservation_no "
				+ " WHERE t.Flight_no = " + theFlightNo;

		if (theViewColumn != null && theViewKey != null) {
			query += " AND " + theViewColumn + theViewKey + " ORDER BY " + theOrder;
		} else {
			query += " ORDER BY " + theOrder;
		}

		List<Ticket> filteredList = new ArrayList<Ticket>();
		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String ticketNo = result.getString("Ticket_no");
				String reservationNo = result.getString("Reservation_no");
				String flightNo = result.getString("Flight_no");
				String seat = result.getString("Assigned_seat");
				String meal = result.getString("Meal_code");
				String bassinet = result.getString("Bassinet");
				String um = result.getString("UM");
				String wheelchair = result.getString("Wheelchair");
				String pet = result.getString("Pet");

				Ticket ticket = new Ticket(reservationNo, flightNo, seat, meal, bassinet, um, wheelchair, pet);
				ticket.setTicketNo(ticketNo);
				filteredList.add(ticket);
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
	 * Retrieves a Ticket which has given ticket number
	 * 
	 * @param theTicketNo
	 * @return A Ticke
	 * @throws SQLException
	 */
	public static Ticket getTicket(final String theTicketNo) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		String query = "SELECT * " + "FROM Ticket WHERE Ticket_no = " + theTicketNo;

		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			if (result.next()) {
				String ticketNo = result.getString("Ticket_no");
				String reservationNo = result.getString("Reservation_no");
				String flightNo = result.getString("Flight_no");
				String seat = result.getString("Assigned_seat");
				String meal = result.getString("Meal_code");
				String bassinet = result.getString("Bassinet");
				String um = result.getString("UM");
				String wheelchair = result.getString("Wheelchair");
				String pet = result.getString("Pet");

				Ticket ticket = new Ticket(reservationNo, flightNo, seat, meal, bassinet, um, wheelchair, pet);
				ticket.setTicketNo(ticketNo);
				return ticket;
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
	 * Add a Ticket into Ticket table
	 * 
	 * @param theTicket
	 * @return Returns a message with success or failure.
	 * @throws SQLException
	 */
	public static String addTicket(Ticket theTicket) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		PreparedStatement preparedStatement = null;
		String query = "INSERT INTO Ticket(Reservation_no, Flight_no, Assigned_seat, Meal_code, Bassinet, UM, Wheelchair, Pet) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			preparedStatement = myConnection.prepareStatement(query);
			preparedStatement.setString(1, theTicket.getReservationNo());
			preparedStatement.setString(2, theTicket.getFlightNo());
			preparedStatement.setString(3, theTicket.getSeatNo());
			preparedStatement.setString(4, theTicket.getMealCode());
			preparedStatement.setString(5, theTicket.getBassinet());
			preparedStatement.setString(6, theTicket.getUM());
			preparedStatement.setString(7, theTicket.getWHCR());
			preparedStatement.setString(8, theTicket.getPet());

			System.out.println(theTicket.getReservationNo() + ", " + theTicket.getFlightNo());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error adding Ticket: " + e.getMessage();
		}
		return "Ticket added Successfully";

	}

	/**
	 * Update a Ticket data
	 * 
	 * @param theTicket
	 * @param theColumnName
	 * @param theData
	 * @return Returns a message with success or failure.
	 */
	public static String updateTicket(Ticket theTicket, String theColumnName, Object theData) {

		String tiket_no = theTicket.getTicketNo();
		String sql = "UPDATE Ticket SET `" + theColumnName + "` = ?  WHERE Ticket_no = " + tiket_no;

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = myConnection.prepareStatement(sql);
			if (theData instanceof String) { // if the data is String
				preparedStatement.setString(1, (String) theData);
			} else {
				System.out.println(theData.getClass().getName() + "");
			}
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			return "Ticket updating flight: " + e.getMessage();
		}
		return "Updated Ticket Successfully";
	}

	/**
	 * Retrieves assigned seats of given flight
	 * 
	 * @param theFlightNo
	 * @return A list of assigned seats of given flight
	 * @throws SQLException
	 */
	public static List<String> getAssignedSeatsOf(final String theFlightNo) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		String query = "SELECT Assigned_seat " + "FROM Ticket WHERE Flight_no = " + theFlightNo;
		List<String> filteredList = new ArrayList<String>();

		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String seat = result.getString("Assigned_seat");
				filteredList.add(seat);
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

}
