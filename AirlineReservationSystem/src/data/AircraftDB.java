package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Aircraft;

/**
 * This class contains methods to access Aircraft table.
 * 
 * @author Jieun Lee (jieun212@uw.edu)
 * @version 11-22-2016
 */
public class AircraftDB {

	/** A connection */
	private static Connection myConnection;

	/** A list of */
	private static List<Aircraft> myAircraftList;

	/**
	 * Retrieves all Aircrafts from Aircraft table.
	 * 
	 * @return A list of Aircrafts
	 * @throws SQLException
	 */
	public static List<Aircraft> getAllAircrafts() throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		String query = "SELECT * " + "FROM Aircraft;";

		myAircraftList = new ArrayList<Aircraft>();

		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String tailNo = result.getString("Tail_no");
				String craftNo = result.getString("Craft_no");
				String capacity = result.getString("Capacity");

				Aircraft aircraft = new Aircraft(craftNo, capacity);
				aircraft.setTailNo(tailNo);
				myAircraftList.add(aircraft);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (statement != null) {
				statement.close();
			}
		}

		return myAircraftList;
	}

	/**
	 * Retrieve all Aircrafts whose craft number is the given craft number.
	 * 
	 * @param theCraftNo
	 * @return The list of Aircrafts whose craft number is the given craft
	 *         number.
	 * @throws SQLException
	 */
	public static List<Aircraft> getAircraftsOfCraftNo(final String theCraftNo) throws SQLException {

		List<Aircraft> filteredList = new ArrayList<Aircraft>();
		if (myAircraftList == null) {
			getAllAircrafts();
		}

		for (Aircraft a : myAircraftList) {
			if (a.getCraftNo().equalsIgnoreCase(theCraftNo)) {
				filteredList.add(a);
			}
		}

		return myAircraftList;
	}

	/**
	 * Retrieve all Aircrafts whose craft number is the given craft number.
	 * 
	 * @param theCapacity
	 * @return
	 * @throws SQLException
	 */
//	public static List<Aircraft> getAircraftsWithCapacity(final String theCapacity) throws SQLException {
//
//		List<Aircraft> filteredList = new ArrayList<Aircraft>();
//		if (myAircraftList == null) {
//			getAllAircrafts();
//		}
//
//		for (Aircraft a : myAircraftList) {
//			if (a.getCapacity().equalsIgnoreCase(theCapacity)) {
//				filteredList.add(a);
//			}
//		}
//
//		return myAircraftList;
//	}

	/**
	 * 
	 * Retrieve all Aircrafts whith given departure date/time and arrival date/time.
	 * 
	 * @param theDepDate
	 * @param theDepTime
	 * @param theArrDate
	 * @param theArrTime
	 * @return Retrieve all Aircrafts whith given departure date/time and arrival date/time
	 * @throws SQLException
	 */
	public static List<Aircraft> getAircraftOf(final String theDepDate, final String theDepTime,
			final String theArrDate, final String theArrTime) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		//TODO - need to check
		String query = "SELECT * " + "FROM Aircraft WHERE ;";

		List<Aircraft> filteredList = new ArrayList<Aircraft>();

		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String tailNo = result.getString("Tail_no");
				String craftNo = result.getString("Craft_no");
				String capacity = result.getString("Capacity");

				Aircraft aircraft = new Aircraft(craftNo, capacity);
				aircraft.setTailNo(tailNo);
				filteredList.add(aircraft);
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
	 * Retrives the Aircraft whose tail number is the given tail number or null
	 * if not found.
	 * 
	 * @param theTailNo
	 * @return The Aircraft whose tail number is the given tail number or null
	 *         if not found.
	 * @throws SQLException
	 */
	public static Aircraft getAircraft(final String theTailNo) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		String query = "SELECT * " + "FROM Aircraft WHERE Tail_no = " + theTailNo + ";";

		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String tailNo = result.getString("Tail_no");
				String craftNo = result.getString("Craft_no");
				String capacity = result.getString("Capacity");

				Aircraft aircraft = new Aircraft(craftNo, capacity);
				aircraft.setTailNo(tailNo);
				return aircraft;

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
	 * Add the given Aircraft to the Aircraft table.
	 * 
	 * @param theAircraft
	 * @return Returns a message with success or failure.
	 * @throws SQLException
	 */
	public static String addAircraft(Aircraft theAircraft) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		PreparedStatement preparedStatement = null;
		String query = "INSERT INTO Aircraft(Tail_no, Craft_no, Capacity) VALUES (?, ?, ?);";

		try {
			preparedStatement = myConnection.prepareStatement(query);
			preparedStatement.setString(1, theAircraft.getTailNo());
			preparedStatement.setString(2, theAircraft.getCraftNo());
			preparedStatement.setString(3, theAircraft.getCapacity());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error adding aircraft: " + e.getMessage();
		}
		return "Aircraft added Successfully";

	}

	/**
	 * Update an Aircraft 
	 * 
	 * @param theAircraft
	 * @param theColumnName
	 * @param theData
	 * @return Returns a message with success or failure.
	 */
	public static String updateAircraft(Aircraft theAircraft, String theColumnName, Object theData) {

		String tail_no = theAircraft.getTailNo();
		String sql = "UPDATE Aircraft SET `" + theColumnName + "` = ?  WHERE Tail_no = " + tail_no + ";";

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
			return "Error updating Aircraft: " + e.getMessage();
		}
		return "Updated Aircraft Successfully";
	}

}
