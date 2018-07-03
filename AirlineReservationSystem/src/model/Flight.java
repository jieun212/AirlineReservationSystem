package model;

import java.sql.Date;
import java.sql.Time;

/**
 * This class represents a Flight that contains flight number, tail number of
 * aircraft, base price, and airport, date, time of departure and arrival.
 * 
 * @author Jieun Lee (jieun212@uw.edu)
 * @version 11-22-2016
 */
public class Flight {
	
	
	/** A flight number */
	private String myFlightNo;
	
	/** A tail number */
	private String myTailNo;
	
	/** A departure airport code */
	private String myDepartureAirport;
	
	/** A departure date */
	private Date myDepartureDate;
	
	/** A departure time */
	private Time myDepartureTime;
	
	/** A departure gate */
	private String myDepartureGate;
	
	/** An arrival airport code */
	private String myArrivalAirport;
	
	/** An arrival date */
	private Date myArrivalDate;
	
	/** An arrival time */
	private Time myArrivalTime;
	
	/** An arrival gate */
	private String myArrivalGate;
	
	/** A base price */
	private double myBasePrice;
	
	
	/**
	 * Construcst a flight with full information with aircraft tail number,
	 * departure and arrival information, and base price.
	 * 
	 * @param theTailNo
	 * @param theDepartureAirport
	 * @param theDepartureDate
	 * @param theDepartureTime
	 * @param theDepartureGate
	 * @param theArrivalAirport
	 * @param theArrivalDate
	 * @param theArrivalTime
	 * @param theArrivalGate
	 * @param theBasePrice
	 */
	public Flight(final String theTailNo, final String theDepartureAirport, final Date theDepartureDate,
			final Time theDepartureTime, final String theDepartureGate, final String theArrivalAirport,
			final Date theArrivalDate, final Time theArrivalTime, final String theArrivalGate,
			final double theBasePrice) {

		this(theTailNo, theDepartureAirport, theDepartureDate, theDepartureTime, 
				theArrivalAirport, theArrivalDate, theArrivalTime, 
				theBasePrice);
		myDepartureGate = theDepartureGate;
		myArrivalGate = theArrivalGate;
	}

	/**
	 *  Construcst a flight with gate information.
	 * 
	 * @param theTailNo
	 * @param theDepartureAirport
	 * @param theDepartureDate
	 * @param theDepartureTime
	 * @param theArrivalAirport
	 * @param theArrivalDate
	 * @param theArrivalTime
	 * @param theBasePrice
	 */
	public Flight(final String theTailNo, final String theDepartureAirport, final Date theDepartureDate,
			final Time theDepartureTime, final String theArrivalAirport,
			final Date theArrivalDate, final Time theArrivalTime, 
			final double theBasePrice) {
		
		// set values
		setTailNo(theTailNo);
		setDepartureAirport(theDepartureAirport);
		setDepartureDate(theDepartureDate);
		setDepartureTime(theDepartureTime);
		setArrivalAirport(theArrivalAirport);
		setArrivalDate(theArrivalDate);
		setArrivalTime(theArrivalTime);
		setBasePrice(theBasePrice);
	}
	

	/* Getter */

	/**
	 * @return the myFlightNo
	 */
	public String getFlightNo() {
		return myFlightNo;
	}


	/**
	 * @return the myTailNo
	 */
	public String getTailNo() {
		return myTailNo;
	}


	/**
	 * @return the myDepartureAirport
	 */
	public String getDepartureAirport() {
		return myDepartureAirport;
	}


	/**
	 * @return the myDepartureDate
	 */
	public Date getDepartureDate() {
		return myDepartureDate;
	}


	/**
	 * @return the myDepartureTime
	 */
	public Time getDepartureTime() {
		return myDepartureTime;
	}


	/**
	 * @return the myDepartureGate
	 */
	public String getDepartureGate() {
		return myDepartureGate;
	}


	/**
	 * @return the myArrivalAirport
	 */
	public String getArrivalAirport() {
		return myArrivalAirport;
	}


	/**
	 * @return the myArrivalDate
	 */
	public Date getArrivalDate() {
		return myArrivalDate;
	}


	/**
	 * @return the myArrivalTime
	 */
	public Time getArrivalTime() {
		return myArrivalTime;
	}


	/**
	 * @return the myArrivalGate
	 */
	public String getArrivalGate() {
		return myArrivalGate;
	}


	/**
	 * @return the myBasePrice
	 */
	public double getBasePrice() {
		return myBasePrice;
	}

	
	
	
	
	
	/* Setter */

	/**
	 * @param theFlightNo the myFlightNo to set
	 */
	public void setFlightNo(final String theFlightNo) {
		this.myFlightNo = theFlightNo;
	}


	/**
	 * @param theTailNo the myTailNo to set
	 */
	public void setTailNo(final String theTailNo) {
		if (theTailNo == null) {
			throw new IllegalArgumentException("The tail number cannot be null");
		}
		this.myTailNo = theTailNo;
	}


	/**
	 * @param theDepartureAirport the myDepartureAirport to set
	 */
	public void setDepartureAirport(final String theDepartureAirport) {
		if (theDepartureAirport.equalsIgnoreCase(myArrivalAirport)) {
			throw new IllegalArgumentException("The departure airport cannot be same as the arrival airport");
		}
		this.myDepartureAirport = theDepartureAirport;
	}


	/**
	 * @param theDepartureDate the myDepartureDate to set
	 */
	public void setDepartureDate(final Date theDepartureDate) {
//		if (theDepartureDate.isBefore(LocalDate.now())) {
//			throw new IllegalArgumentException("The departure date cannot be set before today");
//		}

		this.myDepartureDate = theDepartureDate;
	}


	/**
	 * @param theDepartureTime the myDepartureTime to set
	 */
	public void setDepartureTime(final Time theDepartureTime) {
//		if (theDepartureTime.isBefore(LocalTime.now())) {
//			throw new IllegalArgumentException("The departure time cannot be set before now");
//		}

		this.myDepartureTime = theDepartureTime;
	}


	/**
	 * @param theDepartureGate the myDepartureGate to set
	 */
	public void setDepartureGate(final String theDepartureGate) {
		this.myDepartureGate = theDepartureGate;
	}


	/**
	 * @param theArrivalAirport the myArrivalAirport to set
	 */
	public void setArrivalAirport(final String theArrivalAirport) {
		if (theArrivalAirport.equalsIgnoreCase(myDepartureAirport)) {
			throw new IllegalArgumentException("The arrival airport cannot be same as the departure airport");
		}
		this.myArrivalAirport = theArrivalAirport;
	}


	/**
	 * @param theArrivalDate the myArrivalDate to set
	 */
	public void setArrivalDate(final Date theArrivalDate) {
		if (theArrivalDate.before(myDepartureDate)) {
			throw new IllegalArgumentException("The arrival date cannot be before the departure date");	
		}
		this.myArrivalDate = theArrivalDate;
	}


	/**
	 * @param theArrivalTime the myArrivalTime to set
	 */
	public void setArrivalTime(final Time theArrivalTime) {
		this.myArrivalTime = theArrivalTime;
	}


	/**
	 * @param theArrivalGate the myArrivalGate to set
	 */
	public void setArrivalGate(final String theArrivalGate) {
		this.myArrivalGate = theArrivalGate;
	}


	/**
	 * @param theBasePrice the myBasePrice to set
	 */
	public void setBasePrice(final double theBasePrice) {
		if (theBasePrice < 0) {
			throw new IllegalArgumentException("The price cannot be negative");
		}
		this.myBasePrice = theBasePrice;
	}
	
}
