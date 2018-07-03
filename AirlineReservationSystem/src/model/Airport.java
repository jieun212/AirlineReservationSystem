package model;

/**
 * This class represents an Airport that contains airport code, city, and country.
 * 
 * @author Jieun Lee (jieun212@uw.edu)
 * @version 11-22-2016
 */
public class Airport {
	
	/** An airport code */
	private String myAirport;
	
	/** A city */
	private String myCity;
	
	/** A country */
	private String myCountry;
	
	
	/**
	 * Constructs an Airport with given airport code, city, and country.
	 * 
	 * @param theAirport
	 * @param theCity
	 * @param theCountry
	 */
	public Airport(final String theAirport, final String theCity, final String theCountry) {

		setAirport(theAirport);
		myCity = theCity;
		myCountry = theCountry;
	}


	/**
	 * @return the myAirport
	 */
	public String getAirport() {
		return myAirport;
	}


	/**
	 * @return the myCity
	 */
	public String getCity() {
		return myCity;
	}


	/**
	 * @return the myCountry
	 */
	public String getCountry() {
		return myCountry;
	}


	/**
	 * @param theAirport the myAirport to set
	 */
	public void setAirport(String theAirport) {
		if (theAirport.length() != 3) {
			throw new IllegalArgumentException("The airport code must be 3 characters.");
		}
		this.myAirport = theAirport;
	}


	/**
	 * @param theCity the myCity to set
	 */
	public void setCity(String theCity) {
		this.myCity = theCity;
	}


	/**
	 * @param theCountry the myCountry to set
	 */
	public void setCountry(String theCountry) {
		this.myCountry = theCountry;
	}
	
	

}
