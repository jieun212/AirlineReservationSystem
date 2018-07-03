package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Airport;

/**
 * This class contains methods to access Airport table.
 * 
 * @author Jieun Lee (jieun212@uw.edu)
 * @version 11-22-2016
 */
public class AirportDB {

	/** A connection */
	private static Connection myConnection;

	/** A list of Airport */
	private static List<Airport> myAirportList;

	/**
	 * Retrieves all Airport from Airport table.
	 * 
	 * @return A list of Airports.
	 * @throws SQLException
	 */
	public static List<Airport> getAllAirports() throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		String query = "SELECT * " + "FROM Airport;";

		myAirportList = new ArrayList<Airport>();

		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String code = result.getString("Airport_Code");
				String city = result.getString("city");
				String country = result.getString("country");

				Airport airport = new Airport(code, city, country);
				myAirportList.add(airport);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (statement != null) {
				statement.close();
			}
		}

		return myAirportList;
	}

	/**
	 * Retrieves all Airport with given excluding airport from Airport table.
	 * 
	 * @return A list of Airports that excludes given.
	 * @throws SQLException
	 */
	public static List<Airport> getAirportsExclude(final String theExculde) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		String query = "SELECT * " + "FROM Airport WHERE Airport_code != '" + theExculde + "'";

		myAirportList = new ArrayList<Airport>();

		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String code = result.getString("Airport_Code");
				String city = result.getString("city");
				String country = result.getString("country");

				Airport airport = new Airport(code, city, country);
				myAirportList.add(airport);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (statement != null) {
				statement.close();
			}
		}

		return myAirportList;
	}

	/**
	 * Retrieve all Aircrafts in the city.
	 * 
	 * @param theCity
	 * @return The list of Airports in the city.
	 * @throws SQLException
	 */
	public static List<Airport> getAirportsInCity(final String theCity) throws SQLException {

		List<Airport> filteredList = new ArrayList<Airport>();
		if (myAirportList == null) {
			getAllAirports();
		}

		for (Airport a : myAirportList) {
			if (a.getCity().equalsIgnoreCase(theCity)) {
				filteredList.add(a);
			}
		}

		return myAirportList;
	}

	/**
	 * Retrieve all Aircrafts in the country.
	 * 
	 * @param theCountry
	 * @return The list of Airports in the country.
	 * @throws SQLException
	 */
	public static List<Airport> getAirportsInCountry(final String theCountry) throws SQLException {

		List<Airport> filteredList = new ArrayList<Airport>();
		if (myAirportList == null) {
			getAllAirports();
		}

		for (Airport a : myAirportList) {
			if (a.getCountry().equalsIgnoreCase(theCountry)) {
				filteredList.add(a);
			}
		}

		return myAirportList;
	}

	/**
	 * Retrives the Airport whose code is the given or null if not found.
	 * 
	 * @param theCode
	 * @return The Airport whose code is the given or null if not found.
	 * @throws SQLException
	 */
	public static Airport getAirport(final String theCode) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		String query = "SELECT * " + "FROM Airport WHERE Airport_Code = '" + theCode + "'";

		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String code = result.getString("Airport_Code");
				String city = result.getString("city");
				String country = result.getString("country");

				return new Airport(code, city, country);
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
	
	public static List<String> getCountries() throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		String query = "SELECT country FROM Airport GROUP BY country";
		List<String> filteredList = new ArrayList<String>();
		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {

				String country = result.getString("country");
				filteredList.add(country);
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
