package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Passenger;


/**
 * This class contains methods to access Flight table.
 * 
 * @author Jieun Lee (jieun212@uw.edu)
 * @version 11-22-2016
 */
public class PassengerDB {

	/** A connection */
	private static Connection myConnection;
	
	/** A list of Flight*/
	private static List<Passenger> myPassengerList;

	/**
	 * Retrieves all Flights from Flight table.
	 * 
	 * @return A list of Flights
	 * @throws SQLException
	 */
	public static List<Passenger> getAllPassengers() throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		
		Statement statement = null;
		String query = "SELECT * " + "FROM Passenger;";
		
		myPassengerList = new ArrayList<Passenger>();
		
		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String passengerNo = result.getString("Passenger_no");
				String firstName = result.getString("Fname");
				String middleName = result.getString("Mname");
				String lastName = result.getString("Lname");
				String phone = result.getString("phone");
				String email = result.getString("email");
				String street = result.getString("street");
				String city = result.getString("city");
				String state = result.getString("state");
				String zipcode = result.getString("zipcode");
				String country = result.getString("country");

				Passenger passenger = new Passenger(firstName, lastName, country);
				
				if (middleName != null) {
					passenger.setMiddleName(middleName);
				}
				if (phone != null) {
					passenger.setPhone(phone);
				}
				if (email != null) {
					passenger.setEmail(email);
				}
				if (street != null) {
					passenger.setStreet(street);
				}
				if (city != null) {
					passenger.setCity(city);
				}
				if  (state != null) {
					passenger.setState(state);
				}
				if (zipcode != null) {
					passenger.setZipcode(zipcode);
				}
				passenger.setPassengerNo(passengerNo);
				myPassengerList.add(passenger);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
		
		return myPassengerList;
	}
	
	/**
	 * Retrieves all passengers who have the given first and last name.
	 * 
	 * @param theFirstName
	 * @param theLastName
	 * @return A Passenger
	 * @throws SQLException
	 */
	public static List<Passenger> getPassengersOf(final String theFirstName, final String theLastName) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		
		List<Passenger> filteredList = new ArrayList<Passenger>();
		
		Statement statement = null;
		String query = "SELECT * " + "FROM Passenger "
				+ "WHERE Fname LIKE '%" + theFirstName + "%' AND Lname LIKE '%" + theLastName + "%'";
		
		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String passengerNo = result.getString("Passenger_no");
				String firstName = result.getString("Fname");
				String middleName = result.getString("Mname");
				String lastName = result.getString("Lname");
				String phone = result.getString("phone");
				String email = result.getString("email");
				String street = result.getString("street");
				String city = result.getString("city");
				String state = result.getString("state");
				String zipcode = result.getString("zipcode");
				String country = result.getString("country");

				Passenger passenger = new Passenger(firstName, lastName, country);
				
				if (middleName != null) {
					passenger.setMiddleName(middleName);
				}
				if (phone != null) {
					passenger.setPhone(phone);
				}
				if (email != null) {
					passenger.setEmail(email);
				}
				if (street != null) {
					passenger.setStreet(street);
				}
				if (city != null) {
					passenger.setCity(city);
				}
				if  (state != null) {
					passenger.setState(state);
				}
				if (zipcode != null) {
					passenger.setZipcode(zipcode);
				}
				passenger.setPassengerNo(passengerNo);
				filteredList.add(passenger);
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
	 * Retrieves a Passenger whose passenger number is given
	 * 
	 * @param thePassengerNo
	 * @return A passenger whose passenger number is given
	 * @throws SQLException
	 */
	
	
	/**
	 * Get a passenger who has the given passenger number.
	 * 
	 * @param thePassengerNo
	 * @return A Passenger
	 * @throws SQLException
	 */
	public static Passenger getPassenger(final String thePassengerNo) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		
		
		Statement statement = null;
		String query = "SELECT * " + "FROM Passenger WHERE Passenger_no = " + thePassengerNo + ";";

		try {
			
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			if (result.next()) {
				String passengerNo = result.getString("Passenger_no");
				String firstName = result.getString("Fname");
				String middleName = result.getString("Mname");
				String lastName = result.getString("Lname");
				String phone = result.getString("phone");
				String email = result.getString("email");
				String street = result.getString("street");
				String city = result.getString("city");
				String state = result.getString("state");
				String zipcode = result.getString("zipcode");
				String country = result.getString("country");

				Passenger passenger = new Passenger(firstName, lastName, country);
				
				if (middleName != null) {
					passenger.setMiddleName(middleName);
				}
				if (phone != null) {
					passenger.setPhone(phone);
				}
				if (email != null) {
					passenger.setEmail(email);
				}
				if (street != null) {
					passenger.setStreet(street);
				}
				if (city != null) {
					passenger.setCity(city);
				}
				if  (state != null) {
					passenger.setState(state);
				}
				if (zipcode != null) {
					passenger.setZipcode(zipcode);
				}
				passenger.setPassengerNo(passengerNo);
				return passenger;
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
		return null;
	}
	
	
	/**
	 * Add a Passenger.
	 * 
	 * @param thePassenger
	 * @return Returns a message with success or failure.
	 * @throws SQLException
	 */
	public static String addPassenger(Passenger thePassenger) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		
		PreparedStatement preparedStatement = null;
		String query = "INSERT INTO Passenger(Fname, Mname, Lname, phone, email, street, city, state, zipcode, country) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

		try {
			preparedStatement = myConnection.prepareStatement(query);
			preparedStatement.setString(1, thePassenger.getFirstName());
			preparedStatement.setString(2, thePassenger.getMiddleName());
			preparedStatement.setString(3, thePassenger.getLastName());
			preparedStatement.setString(4, thePassenger.getPhone());
			preparedStatement.setString(5, thePassenger.getEmail());
			preparedStatement.setString(6, thePassenger.getStreet());
			preparedStatement.setString(7, thePassenger.getCity());
			preparedStatement.setString(8, thePassenger.getState());
			preparedStatement.setString(9, thePassenger.getZipcode());
			preparedStatement.setString(10, thePassenger.getCountry());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error adding Passenger: " + e.getMessage();
		}
		return "Passenger added Successfully";
		
	}
	
}
