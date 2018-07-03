package model;

/**
 * This class represents a Reservation that contains reservation number,
 * passenger number, paid price, confirm of the pay, and passport issued country
 * if necessary.
 * 
 * @author Jieun Lee (jieun212@uw.edu)
 * @version 11-22-2016
 */
public class Reservation {
	
	public static String[] SET_PAID_CONFIRM = {"CONF", "NOT CONF"};

	/** A reservation number */
	private String myReservationNo;
	
	/** A passenger number */
	private String myPassengerNo;
	
	/** A paid price */
	private double myPaidPrice;
	
	/** A price paid confirmation */
	private String myPaidConfirm;
	
	/** A passport issued country */
	private String myPSPTcountry;
	
	
	/**
	 * Constructs a reservation for the given passenger.
	 * 
	 * @param thePassengerNo
	 */
	public Reservation(final String thePassengerNo, final double thePrice, final String theConfirm) {
		myPassengerNo = thePassengerNo;
		setPaidPrice(thePrice);
		setPaidConfirm(theConfirm);
	}


	/**
	 * @return the myReservationNo
	 */
	public String getReservationNo() {
		return myReservationNo;
	}


	/**
	 * @return the myPassengerNo
	 */
	public String getPassengerNo() {
		return myPassengerNo;
	}


	/**
	 * @return the myPaidPrice
	 */
	public double getPaidPrice() {
		return myPaidPrice;
	}


	/**
	 * @return the myPaidConfirm
	 */
	public String getPaidConfirm() {
		return myPaidConfirm;
	}


	/**
	 * @return the myPSPTcountry
	 */
	public String getPSPTcountry() {
		return myPSPTcountry;
	}


	/**
	 * @param theReservationNo the myReservationNo to set
	 */
	public void setReservationNo(final String theReservationNo) {
		this.myReservationNo = theReservationNo;
	}


	/**
	 * @param thePassengerNo the myPassengerNo to set
	 */
	public void setPassengerNo(final String thePassengerNo) {
		this.myPassengerNo = thePassengerNo;
	}


	/**
	 * @param thePaidPrice the myPaidPrice to set
	 */
	public void setPaidPrice(final double thePaidPrice) {
		if (thePaidPrice < 0) {
			throw new IllegalArgumentException("The price cannot be negative");
		}
		this.myPaidPrice = thePaidPrice;
	}


	/**
	 * @param thePaidConfirm the myPaidConfirm to set
	 */
	public void setPaidConfirm(final String thePaidConfirm) {
		
		for (String s : SET_PAID_CONFIRM) {
			if (s.equalsIgnoreCase(thePaidConfirm)) {
				this.myPaidConfirm = s;
				break;
			}
		}
		
		// if the input is not in the paid confirmation set
//		throw new IllegalArgumentException("The input should be \"CONF\" or \"NOT CONF\"");
	}


	/**
	 * @param thePSPTcountry the myPSPTcountry to set
	 */
	public void setPSPTcountry(final String thePSPTcountry) {
		this.myPSPTcountry = thePSPTcountry;
	}
	
	
	
	
}
