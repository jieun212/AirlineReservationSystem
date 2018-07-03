package model;

/**
 * This class represents a Ticket that contains ticket number, reservation
 * number, flight number, assigned seat, meal code, other special service,
 * including bassinet, unaccompanied minor, wheelchair, and pet.
 * 
 * @author Jieun Lee (jieun212@uw.edu)
 * @version 11-22-2016
 */
public class Ticket {
	
	/** A set of bassinet service request */
	public static String[] SET_BASSINET = {"N/A", "BSNT"};
	
	/** A set of unaccompaied minor service request */
	public static String[] SET_UM = {"N/A", "UM"};
	
	/** A set of wheelchair service request */
	public static String[] SET_WHCR = {"N/A", "WHCR"};
	
	/** A set of pet service request */
	public static String[] SET_PET = {"N/A", "PETC", "AVIH"};
	
	
	
	/** A ticket number */
	private String myTicketNo;
	
	/** A reservation number */
	private String myReservationNo;
	
	/** A flight number */
	private String myFlightNo;
	
	/** A seat number */
	private String mySeatNo;
	
	/** A meal code. */
	private String myMealCode;
	
	/** A bassinet service request. */
	private String myBassinet;
	
	/** An unaccompanied minor service request.  */
	private String myUM;
	
	/** A wheelchair service request. */
	private String myWHCR;
	
	/** A pet service request. */
	private String myPet;
	
	

	/**
	 * Full constructor.
	 * 
	 * @param theReservationNo
	 * @param theFlightNo
	 * @param theSeat
	 * @param theMeal
	 * @param theBassinet
	 * @param theUm
	 * @param theWheelchair
	 * @param thePet
	 */
	public Ticket(final String theReservationNo, final String theFlightNo, final String theSeat,
			final String theMeal, final String theBassinet, final String theUm,
			final String theWheelchair, final String thePet) {
		this(theReservationNo, theFlightNo, theSeat);
		setMeal(theMeal);
		setBassinet(theBassinet);
		setUM(theUm);
		setWHCR(theWheelchair);
		setPet(thePet);
	}
	

	/**
	 * Construcs a Ticket with givne reservation number, flight number, and assigned seat number.
	 * 
	 * @param theReservationNo
	 * @param theFlightNo
	 * @param theSeat
	 */
	public Ticket(final String theReservationNo, final String theFlightNo, final String theSeat) {
		setReservationNo(theReservationNo);
		setFlightNo(theFlightNo);
		setSeat(theSeat);
		myMealCode = "N/A";
		myBassinet = "N/A";
		myUM = "N/A";
		myWHCR = "N/A";
		myPet = "N/A";
	}

	/**
	 * @return the myTicketNo
	 */
	public String getTicketNo() {
		return myTicketNo;
	}

	/**
	 * @return the myReservationNo
	 */
	public String getReservationNo() {
		return myReservationNo;
	}

	/**
	 * @return the myFlightNo
	 */
	public String getFlightNo() {
		return myFlightNo;
	}

	/**
	 * @return the mySeatNo
	 */
	public String getSeatNo() {
		return mySeatNo;
	}

	/**
	 * @return the myMealCode
	 */
	public String getMealCode() {
		return myMealCode;
	}

	/**
	 * @return the myBassinet
	 */
	public String getBassinet() {
		return myBassinet;
	}

	/**
	 * @return the myUM
	 */
	public String getUM() {
		return myUM;
	}

	/**
	 * @return the myWHCR
	 */
	public String getWHCR() {
		return myWHCR;
	}

	/**
	 * @return the myPet
	 */
	public String getPet() {
		return myPet;
	}
	
	

	/* Setter */

	/**
	 * @param theTicketNo the myTicketNo to set
	 */
	public void setTicketNo(final String theTicketNo) {
		this.myTicketNo = theTicketNo;
	}

	/**
	 * @param theReservationNo the myReservationNo to set
	 */
	public void setReservationNo(final String theReservationNo) {
		if (theReservationNo == null) {
			throw new IllegalArgumentException("The reservation number cannot be null");
		}
		this.myReservationNo = theReservationNo;
	}

	/**
	 * @param theFlightNo the myFlightNo to set
	 */
	public void setFlightNo(final String theFlightNo) {
		if (theFlightNo == null) {
			throw new IllegalArgumentException("The flight number cannot be null");
		}
		this.myFlightNo = theFlightNo;
	}

	/**
	 * @param theSeatNo the mySeatNo to set
	 */
	public void setSeat(final String theSeatNo) {
		this.mySeatNo = theSeatNo;
	}

	/**
	 * @param theMealCode the myMealCode to set
	 */
	public void setMeal(final String theMealCode) {
		this.myMealCode = theMealCode;
	}

	/**
	 * @param theBassinet the myBassinet to set
	 */
	public void setBassinet(final String theBassinet) {
		for (String s : SET_BASSINET) {
			if (s.equalsIgnoreCase(theBassinet)) {
				this.myBassinet = s;
				break;
			}
		}
//		throw new IllegalArgumentException("The input should be \"N/A\" or \"BSNT\"");
	}

	/**
	 * @param theUM the myUM to set
	 */
	public void setUM(final String theUM) {
		for (String s : SET_UM) {
			if (s.equalsIgnoreCase(theUM)) {
				this.myUM = s;
				break;
			}
		}
//		throw new IllegalArgumentException("The input should be \"N/A\" or \"UM\"");
	}

	/**
	 * @param theWHCR the myWHCR to set
	 */
	public void setWHCR(final String theWHCR) {
		for (String s : SET_WHCR) {
			if (s.equalsIgnoreCase(theWHCR)) {
				this.myWHCR = s;
				break;
			}
		}
//		throw new IllegalArgumentException("The input should be \"N/A\" or \"WHCR\"");
	}

	/**
	 * @param thePet the myPet to set
	 */
	public void setPet(final String thePet) {
		for (String s : SET_PET) {
			if (s.equalsIgnoreCase(thePet)) {
				this.myPet = s;
				break;
			}
		}
//		throw new IllegalArgumentException("The input should be \"N/A\" or \"PETC\" or \"AVIH\"");
	}


}
