package model;

/**
 * This class represents a Meal that contains meal code, and its name(explanation).
 * 
 * @author Jieun Lee (jieun212@uw.edu)
 * @version 11-22-2016
 */
public class Meal {
	
	/** A meal code */
	private String myMealCode;
	
	/** A meal name or explanation of a meal code */
	private String myMealName;
	
	/**
	 * Construcs a Meal with given code and its name.
	 * @param theMealCode
	 * @param theMealName
	 */
	public Meal(final String theMealCode, final String theMealName) {
		myMealCode = theMealCode;
		myMealName = theMealName;
	}

	/**
	 * @return the myMealCode
	 */
	public String getMealCode() {
		return myMealCode;
	}

	/**
	 * @return the myMealName
	 */
	public String getMealName() {
		return myMealName;
	}

	/**
	 * @param theMealCode the myMealCode to set
	 */
	public void setMealCode(String theMealCode) {
		myMealCode = theMealCode;
	}

	/**
	 * @param theMealName the myMealName to set
	 */
	public void setMealName(String theMealName) {
		myMealName = theMealName;
	}
	
	

}
