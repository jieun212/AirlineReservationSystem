package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Meal;

/**
 * This class contains methods to access Meal table.
 * 
 * @author Jieun Lee (jieun212@uw.edu)
 * @version 11-22-2016
 */
public class MealDB {

	/** A connection */
	private static Connection myConnection;

	/** A list of Meals */
	private static List<Meal> myMealList;

	/**
	 * Retrieves all Meals from Meal table.
	 * 
	 * @return A list of Meals
	 * @throws SQLException
	 */
	public static List<Meal> getAllMeals() throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		String query = "SELECT * " + "FROM Meal;";

		myMealList = new ArrayList<Meal>();

		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String code = result.getString("Meal_code");
				String name = result.getString("Meal_name");

				Meal meal = new Meal(code, name);
				myMealList.add(meal);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (statement != null) {
				statement.close();
			}
		}

		return myMealList;
	}

	/**
	 * Retrieve all Meals which contains the given keyword.
	 * 
	 * @param theKeyword
	 * @return The list of Meals which contains the given name
	 * @throws SQLException
	 */
	public static List<Meal> getMeals(final String theKeyword) throws SQLException {

		List<Meal> filteredList = new ArrayList<Meal>();
		if (myMealList == null) {
			getAllMeals();
		}
		for (Meal m : myMealList) {
			if (m.getMealName().toLowerCase().contains(theKeyword.toLowerCase())) {
				filteredList.add(m);
			}
		}

		return myMealList;
	}

	/**
	 * Retrives the Meal whose code is the given or null if not found.
	 * 
	 * @param theCode
	 * @return The Meal whose code is the given or null if not found.
	 * @throws SQLException
	 */
	public static Meal getMeal(final String theCode) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		String query = "SELECT * " + "FROM Meal WHERE Meal_code = " + theCode + ";";

		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String code = result.getString("Meal_code");
				String name = result.getString("Meal_name");

				return new Meal(code, name);
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
	 * Add the given Meal to the Meal table.
	 * 
	 * @param theMeal
	 * @return Returns a message with success or failure.
	 * @throws SQLException
	 */
	public static boolean addMeal(Meal theMeal) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		PreparedStatement preparedStatement = null;
		String query = "INSERT INTO Meal(Meal_code, Meal_name) VALUES (?, ?)";

		try {
			preparedStatement = myConnection.prepareStatement(query);
			preparedStatement.setString(1, theMeal.getMealCode());
			preparedStatement.setString(2, theMeal.getMealName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}


	/**
	 * Check if there exists a given meal in the Meal table.
	 * 
	 * @param theMealCode
	 * @return True if the meal is already in Meal table
	 * @throws SQLException
	 */
	public static boolean hasMealCode(final String theMealCode) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}

		Statement statement = null;
		String query = "SELECT COUNT(Meal_code) AS Meal_count FROM Meal WHERE Meal_code = '" + theMealCode
				+ "' GROUP BY Meal_code";

		try {
			statement = myConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String count_str = result.getString("Meal_count");
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

}
