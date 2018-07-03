package model;

/**
 * This class represents a Passenger that contains passenger number, name, phone
 * number, email, and address.
 * 
 * @author Jieun Lee (jieun212@uw.edu)
 * @version 11-22-2016
 */
public class Passenger {
	
	/** A passenger number*/
	private String myPassengerNo;
	
	/** A first name */
	private String myFirstName;
	
	/** A middle name */
	private String myMiddleName;
	
	/** A last name */
	private String myLastName;
	
	/** A phone number */
	private String myPhone;
	
	/** A email address */
	private String myEmail;
	
	/** A street */
	private String myStreet;
	
	/** A city */
	private String myCity;
	
	/** A state */
	private String myState;
	
	/** A zipcode */
	private String myZipcode;
	
	/** A country */
	private String myCountry;
	
	public Passenger(final String theFirstName, final String theLastName, final String theMiddleName,
			final String thePhone, final String theEmail, final String theStree, final String theCity,
			final String theState, final String theZipcode, final String theCountry) {
		this(theFirstName, theLastName, theMiddleName,theCountry);
		setPhone(thePhone);
		setEmail(theEmail);
		setStreet(theStree);
		setCity(theCity);
		setState(theState);
		setZipcode(theZipcode);
		setCountry(theCountry);
	}
	
	
	
	/**
	 * Constructs a Passenger with givne first, last and middle name, and the citizenship.
	 * @param theFirstName
	 * @param theLastName
	 * @param theMiddleName
	 * @param theCountry
	 */
	public Passenger(final String theFirstName, final String theLastName, final String theMiddleName,
			final String theCountry) {
		this(theFirstName, theLastName, theCountry);
		setMiddleName(theMiddleName);
		
		myPhone = null;
		myEmail = null;
		myStreet = null;
		myCity = null;
		myState = null;
		myZipcode = null;
	}
	
	/**
	 * Constructs a Passenger with givne first and last name, and the citizenship.
	 * @param theFirstName
	 * @param theLastName
	 * @param theCountry
	 */
	public Passenger(final String theFirstName, final String theLastName, final String theCountry) {
		setFirstName(theFirstName);
		setLastName(theLastName);
		setCountry(theCountry);
		
		myMiddleName = null;
		myPhone = null;
		myEmail = null;
		myStreet = null;
		myCity = null;
		myState = null;
		myZipcode = null;
	}
	

	/* Getter */
	
	/**
	 * @return the myPassengerNo
	 */
	public String getPassengerNo() {
		return myPassengerNo;
	}

	/**
	 * @return the myFirstName
	 */
	public String getFirstName() {
		return myFirstName;
	}

	/**
	 * @return the myMiddleName
	 */
	public String getMiddleName() {
		return myMiddleName;
	}

	/**
	 * @return the myLastName
	 */
	public String getLastName() {
		return myLastName;
	}

	/**
	 * @return the myPhone
	 */
	public String getPhone() {
		return myPhone;
	}

	/**
	 * @return the myEmail
	 */
	public String getEmail() {
		return myEmail;
	}

	/**
	 * @return the myStreet
	 */
	public String getStreet() {
		return myStreet;
	}

	/**
	 * @return the myCity
	 */
	public String getCity() {
		return myCity;
	}

	/**
	 * @return the myState
	 */
	public String getState() {
		return myState;
	}

	/**
	 * @return the myZipcode
	 */
	public String getZipcode() {
		return myZipcode;
	}

	/**
	 * @return the myCountry
	 */
	public String getCountry() {
		return myCountry;
	}

	
	/* Setter */
	
	/**
	 * @param thePassengerNo the myPassengerNo to set
	 */
	public void setPassengerNo(String thePassengerNo) {
		this.myPassengerNo = thePassengerNo;
	}

	/**
	 * @param theFirstName the myFirstName to set
	 */
	public void setFirstName(String theFirstName) {
		if (theFirstName == null || theFirstName.length() < 1) {
			throw new IllegalArgumentException("The first name cannot be null or empty.");
		}
		this.myFirstName = theFirstName;
	}

	/**
	 * @param theMiddleName the myMiddleName to set
	 */
	public void setMiddleName(String theMiddleName) {
		this.myMiddleName = theMiddleName;
	}

	/**
	 * @param theLastName the myLastName to set
	 */
	public void setLastName(String theLastName) {
		if (theLastName == null || theLastName.length() < 1) {
			throw new IllegalArgumentException("The last name cannot be null or empty.");
		}
		this.myLastName = theLastName;
	}

	/**
	 * @param thePhone the myPhone to set
	 */
	public void setPhone(String thePhone) {
		this.myPhone = thePhone;
	}

	/**
	 * @param theEmail the myEmail to set
	 */
	public void setEmail(String theEmail) {
		this.myEmail = theEmail;
	}

	/**
	 * @param theStreet the myStreet to set
	 */
	public void setStreet(String theStreet) {
		this.myStreet = theStreet;
	}

	/**
	 * @param theCity the myCity to set
	 */
	public void setCity(String theCity) {
		this.myCity = theCity;
	}

	/**
	 * @param theState the myState to set
	 */
	public void setState(String theState) {
		this.myState = theState;
	}

	/**
	 * @param theZipcode the myZipcode to set
	 */
	public void setZipcode(String theZipcode) {
		if (theZipcode.length() < 1 || theZipcode.length() > 10) {
			throw new IllegalArgumentException("The length of zipcode should be between 0 and 10");
		} 
		
		this.myZipcode = theZipcode;
		
	}

	/**
	 * @param theCountry the myCountry to set
	 */
	public void setCountry(String theCountry) {
		if (theCountry == null) {
			throw new IllegalArgumentException("The last name cannot be null.");
		} 
		this.myCountry = theCountry;
	}
	
	
	

}
