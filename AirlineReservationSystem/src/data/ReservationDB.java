package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Reservation;

/**
 * This class contains methods to access Reservation table.
 * 
 * @author Jieun Lee (jieun212@uw.edu)
 * @version 11-22-2016
 */
public class ReservationDB {
	
	/** A connection */
	private static Connection myConnection;
	
	/** A list of Reservations */
	private static List<Reservation> myReservationList;
	
	
	/**
	 * Retrieves all reservations.
	 * 
	 * @return A list of Reservations.
	 * @throws SQLException
	 */
	public static List<Reservation> getAllReservation() throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		
		Statement statement = null;
		String query = "SELECT * " + "FROM Reservation;";
		
		myReservationList = new ArrayList<Reservation>();
		
		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String reservation_no = result.getString("Reservation_no");
				String passenger_no = result.getString("Passenger_no");
				double paid_price = result.getDouble("Paid_price");
				String paid_confirm = result.getString("Paid_confirm");
				String pspt_country = result.getString("PSPT_countrycode");

				Reservation reservation = new Reservation(passenger_no, paid_price, paid_confirm);
				reservation.setReservationNo(reservation_no);
				reservation.setPSPTcountry(pspt_country);
				
				myReservationList.add(reservation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
		
		return myReservationList;
	}
	
	
	/**
	 * Retrieves all reservations of given passenger
	 * 
	 * @param thePassengerNo
	 * @return List of Reservations
	 * @throws SQLException
	 */
	public static List<Reservation> getReservationsOfPax(final String thePassengerNo) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		
		List<Reservation> filteredList = new ArrayList<Reservation>();
		Statement statement = null;
		String query = "SELECT * " + "FROM Reservation "
				+ "WHERE Passenger_no = '" + thePassengerNo + "'";

		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String reservation_no = result.getString("Reservation_no");
				String passenger_no = result.getString("Passenger_no");
				double paid_price = result.getDouble("Paid_price");
				String paid_confirm = result.getString("Paid_confirm");
				String pspt_country = result.getString("PSPT_countrycode");

				Reservation reservation = new Reservation(passenger_no, paid_price, paid_confirm);
				reservation.setReservationNo(reservation_no);
				if (pspt_country!=null && pspt_country.length()>0) {
					reservation.setPSPTcountry(pspt_country);
				}
				
				filteredList.add(reservation);
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
	 * Retrieves a Reaservation with given reservation number
	 * 
	 * @param theReservationNo
	 * @return A reservation 
	 * @throws SQLException
	 */
	public static Reservation getReservationOf(final String theReservationNo) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		String query = "SELECT * " + "FROM Reservation "
				+ "WHERE Reservation_no = " + theReservationNo + ";";

		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String reservation_no = result.getString("Reservation_no");
				String passenger_no = result.getString("Passenger_no");
				double paid_price = result.getDouble("Paid_price");
				String paid_confirm = result.getString("Paid_confirm");
				String pspt_country = result.getString("PSPT_countrycode");

				Reservation reservation = new Reservation(passenger_no, paid_price, paid_confirm);
				reservation.setReservationNo(reservation_no);
				reservation.setPSPTcountry(pspt_country);
				
				return reservation;
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
	 * Add a Reservation into the Reservation table
	 * 
	 * @param theReservation
	 * @return Returns a message with success or failure.
	 * @throws SQLException
	 */
	public static String addReservation(Reservation theReservation) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		
		PreparedStatement preparedStatement = null;
		String query = "INSERT INTO Reservation(Passenger_no, Paid_price, Paid_confirm) VALUES (?, ?, ?);";

		try {
			preparedStatement = myConnection.prepareStatement(query);
			preparedStatement.setString(1, theReservation.getPassengerNo());
			preparedStatement.setDouble(2, theReservation.getPaidPrice());
			preparedStatement.setString(3, theReservation.getPaidConfirm());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error adding Reservation: " + e.getMessage();
		}
		return "Reservation added Successfully";
	}
	
	/**
	 * Update a reservation
	 * 
	 * @param theReservation
	 * @param theColumnName
	 * @param theData
	 * @return Returns a message with success or failure.
	 */
	public static boolean updateReservation(Reservation theReservation, String theColumnName, Object theData) {

		String reservation_no = theReservation.getReservationNo();
	
		String sql = "UPDATE Reservation SET `" + theColumnName + "` = ?  WHERE Reservation_no = " + reservation_no+ ";";

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = myConnection.prepareStatement(sql);
			if (theData instanceof String) { // if the data is String
				preparedStatement.setString(1, (String) theData);
				
			} else if (theData instanceof Double) {
				preparedStatement.setDouble(1, (Double) theData);
				
			} else {
				System.out.println(theData.getClass().getName() + "");
			}

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
