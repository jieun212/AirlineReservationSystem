package model;

/**
 * This class represents an Aircraft that contains tail number, craft type, and capacity.
 * 
 * @author Jieun Lee (jieun212@uw.edu)
 * @version 11-22-2016
 */
public class Aircraft {

	/** A tail number */
	private String myTailNo;
	
	/** A craft number */
	private String myCraftNo;
	
	/** A capacity */
	private String myCapacity;
	
	/**
	 * Constructs an Aircraft with given tail number, craft number, ant its capacity.
	 * 
	 * @param theTailNo
	 * @param theCraftNo
	 * @param theCapacity
	 */
	public Aircraft(final String theCraftNo, final String theCapacity) {
		setCraftNo(theCraftNo);
		setCapacity(theCapacity);
	}

	/**
	 * @return the myTailNo
	 */
	public String getTailNo() {
		return myTailNo;
	}

	/**
	 * @return the myCraftNo
	 */
	public String getCraftNo() {
		return myCraftNo;
	}

	/**
	 * @return the myCapacity
	 */
	public String getCapacity() {
		return myCapacity;
	}

	/**
	 * @param theTailNo the myTailNo to set
	 */
	public void setTailNo(final String theTailNo) {
		this.myTailNo = theTailNo;
	}

	/**
	 * @param theCraftNo the myCraftNo to set
	 */
	public void setCraftNo(final String theCraftNo) {
		this.myCraftNo = theCraftNo;
	}

	/**
	 * @param theCapacity the myCapacity to set
	 */
	public void setCapacity(final String theCapacity) {
		if (Integer.valueOf(theCapacity) < 0) {
			throw new IllegalArgumentException("The capaticy cannot be negative ");
		}
		this.myCapacity = theCapacity;
	}
	
	
}
