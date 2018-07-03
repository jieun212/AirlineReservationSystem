package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import component.AutoCompleteTextField;
import component.DateLabelFormatter;
import data.AircraftDB;
import data.AirportDB;
import data.FlightDB;
import data.MealDB;
import data.ReservationDB;
import data.TicketDB;
import model.Airport;
import model.Flight;
import model.Meal;
import model.Reservation;
import model.Ticket;

/**
 * This is a panel is for searching flights.
 * It allows to search flights with trip type, make a reservation, pay for reservation,
 * assigned seat, and request meal and other services.
 * 
 * @author Jieun Lee (jieun212@uw.edu)
 * @version 12-05-2016
 */
public class SearchPanel extends JPanel implements ActionListener {
	
	/** A default passenger no. */
	public static final String DEFAULT_PAX_NO = "1";
	
	/** A serial number. */
	private static final long serialVersionUID = -3279816271922871553L;

	/** Panels. */
	private JPanel myContentPanel, mySearchPanel, myFromToPane, myFromPanel, myConnectingPanel, myToPanel,
			myRadiobuttonPanel, myEmptyPanel, mySearchBtnPanel, myFromCityPanel, myFromDatePanel, myToCityPanel,
			myReturnDatePanel, myConnectingCityPanel, myConnectingDatePanel, fromCityDatePanel, toCityDatePanel;
	
	/** Buttons. */
	private JButton mySearchButton, myFirstSelectButton, mySecondSelectButton, myBookButton, myPayButton,
			myNextTicketButton, myCompleteButton, myGobackButton;
	
	/** Column names for table. */
	private String[] myColumnNames = { "Flight No", "From", "Departure Date", "Departure Time", "To", "Arrival Date",
			"Arrival Time", "Price" };
	
	/** Text fields for searching flights. */
	private AutoCompleteTextField myFromCityTxtf, myToCityTxtf, myConnectingCityTxtf;
	
	/** Date picker for searhing flights. */
	private JDatePickerImpl myFromDatePicker, myReturnDatePicker, myConnectingDatePicker;
	
	/** Trip type buttons. */
	private JRadioButton myRoundRbtn, myOnewayRbtn, myMultiRbtn;
	
	/** Trip tables. */
	private JTable myFirstTable, mySecondTable;
	
	/** Scroll panels for tables. */
	private JScrollPane myFirstScrollPane, mySecondScrollPane;
	
	/** Dropdown combo boxes. */
	private JComboBox<Object> myCountryDropdown, mySeatDropdown, myMealDropdown, myWHCRDropdown, myUMDropdown, myPetDropdown, myBassinetDropdown;

	/** Lists of searched flights */
	private List<Flight> myFirstList, mySecondList;
	
	/** Flight data. */
	private Object[][] myFirstData, mySecondData, myCombinedData;
	
	/** Data for seat, meal, and other optional services. */
	private Object[] mySeats, myMeals, myWHCRs, myUMs, myPets, myBsnts;
	
	/** Numbers. */
	private String myFirstFlightNo, mySecondFlightNo, myReservationNo, myPassengerNo;
	
	/** Flags. */
	private boolean myHasSecondFlight, isInternational;
	
	/** Payment. */
	private double myPayment;
	

	/**
	 * Constructs SearchPanel.
	 */
	public SearchPanel() {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		
		myPassengerNo = DEFAULT_PAX_NO;
		isInternational = false;
		addActiontoButton();
		createAutoCompTxtf();
		createDatePicker();
		createComponent();
		setVisible(true);
		setPreferredSize(getPreferredSize());
	}
	
	/**
	 * Set buttons
	 */
	private void addActiontoButton() {
		myFirstSelectButton  = new JButton("SELECT");
		mySecondSelectButton = new JButton("SELECT");
		myBookButton = new JButton("BOOK");
		myPayButton = new JButton("MAKE PAY"); // go to 1st ticket
		myNextTicketButton = new JButton("NEXT"); // go to 2nd ticket
		myCompleteButton = new JButton("COMPLETE");
		myGobackButton  = new JButton("BACK TO SEARCH");
		
		myFirstSelectButton.addActionListener(this);
		mySecondSelectButton.addActionListener(this);
		myBookButton.addActionListener(this);
		myPayButton.addActionListener(this);
		myNextTicketButton.addActionListener(this);
		myCompleteButton.addActionListener(this);
		myGobackButton.addActionListener(this);

	}

	/**
	 * Create components
	 */
	private void createComponent() {
		myContentPanel = new JPanel(new BorderLayout());
		myContentPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		myContentPanel.add(createSearchPanel(), BorderLayout.CENTER);
		myContentPanel.add(createImageLabel(), BorderLayout.SOUTH);
		add(myContentPanel);
	}
	
	/**
	 * Create image label
	 * @return Image label
	 */
	private JLabel createImageLabel() {
		final JLabel imageLabel = new JLabel();
		imageLabel.setBackground(Color.WHITE);
		imageLabel.setIcon(
				new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/back.jpg"))));
		return imageLabel;
	}
	
	/**
	 * Create serch panel.
	 * @return A search panel.
	 */
	private JPanel createSearchPanel() {
		mySearchPanel = new JPanel();
		mySearchPanel.setBackground(MainFrame.ASR_LIGHTBLUE);

		myEmptyPanel = new JPanel();
		myEmptyPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		
		// search button
		mySearchBtnPanel = new JPanel();
		mySearchBtnPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		mySearchButton = new JButton("Search");
		mySearchButton.addActionListener(this);
		mySearchBtnPanel.add(mySearchButton);
		
		createRegularSearchPanel();
		
		// trip type radio buttons
		myRadiobuttonPanel = new JPanel(new GridLayout(3,0));
		myRadiobuttonPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final ButtonGroup rbtnGroup = new ButtonGroup();
		myRoundRbtn = new JRadioButton("round");
		myOnewayRbtn = new JRadioButton("one-way");
		myMultiRbtn = new JRadioButton("multi-city");
		rbtnGroup.add(myRoundRbtn);
		rbtnGroup.add(myOnewayRbtn);
		rbtnGroup.add(myMultiRbtn);
		myRoundRbtn.setSelected(true);
		myHasSecondFlight = true;
		
		myRoundRbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createRegularSearchPanel();
				performTripType();

				myReturnDatePicker.getJFormattedTextField().setEnabled(true);
				myHasSecondFlight = true;
			}
		});
		myOnewayRbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createRegularSearchPanel();
				performTripType();
				myReturnDatePicker.getJFormattedTextField().setEnabled(false);
				myHasSecondFlight = false;
			}
		});
		myMultiRbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createMultiSearchPanel();
				performTripType();
				myReturnDatePicker.getJFormattedTextField().setEnabled(false);
				myHasSecondFlight = true;

			}
		});

		
		myRadiobuttonPanel.add(myRoundRbtn);
		myRadiobuttonPanel.add(myOnewayRbtn);
		myRadiobuttonPanel.add(myMultiRbtn);

		mySearchPanel.add(myRadiobuttonPanel);
		mySearchPanel.add(myEmptyPanel);
		mySearchPanel.add(myFromToPane);
		mySearchPanel.add(mySearchBtnPanel);

		return mySearchPanel;
	}

	/**
	 * Create default search panel.
	 */
	private void createRegularSearchPanel() {
		myFromToPane = new JPanel(new BorderLayout());
		
		createFromPanel();
		createToPanel();
		
		myFromToPane.add(myFromPanel, BorderLayout.WEST);
		myFromToPane.add(createArrowSymbol(), BorderLayout.CENTER);
		myFromToPane.add(myToPanel, BorderLayout.EAST);
	}
	
	/**
	 * Create multi-city search panel.
	 */
	private void createMultiSearchPanel() {
		myFromToPane = new JPanel(new BorderLayout());
		
		createFromPanel();
		createToPanel();
		createConnectingPanel();
		
		myFromToPane.add(myFromPanel, BorderLayout.WEST);
		myFromToPane.add(myConnectingPanel, BorderLayout.CENTER);
		myFromToPane.add(myToPanel, BorderLayout.EAST);
	}
	
	/**
	 * Create From panel.
	 */
	private void createFromPanel() {
		// from / date (west)
		myFromPanel = new JPanel(new BorderLayout());
		myFromPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		
		final JLabel fromLabel = new JLabel("FROM");
		fromLabel.setFont(new Font("Abadi MT Condensed Extra Bold", Font.BOLD, 14));
		
		myFromCityPanel = new JPanel(new BorderLayout());
		myFromCityPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final JLabel fromCityLabel = new JLabel("CITY: ");
		fromCityLabel.setFont(new Font("Abadi MT Condensed", Font.BOLD, 12));
		fromCityLabel.setForeground(MainFrame.ASR_DARKBLUE);

		myFromCityPanel.add(fromCityLabel, BorderLayout.WEST);
		myFromCityPanel.add(myFromCityTxtf, BorderLayout.CENTER);
		
		myFromDatePanel = new JPanel(new BorderLayout());
		myFromDatePanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final JLabel fromDateLabel = new JLabel("DATE:");
		fromDateLabel.setFont(new Font("Abadi MT Condensed", Font.BOLD, 12));
		fromDateLabel.setForeground(MainFrame.ASR_DARKBLUE);
		
		myFromDatePanel.add(fromDateLabel, BorderLayout.WEST);
		myFromDatePanel.add(myFromDatePicker, BorderLayout.CENTER);
		
		fromCityDatePanel = new JPanel(new BorderLayout());
		fromCityDatePanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		fromCityDatePanel.add(myFromCityPanel, BorderLayout.NORTH);
		fromCityDatePanel.add(myFromDatePanel, BorderLayout.SOUTH);
		
		myFromPanel.add(fromLabel, BorderLayout.NORTH);
		myFromPanel.add(fromCityDatePanel, BorderLayout.CENTER);
	}
	
	/**
	 * Create To panel
	 */
	private void createToPanel() {
		myToPanel = new JPanel(new BorderLayout());
		myToPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final JLabel toLabel = new JLabel("TO");
		toLabel.setFont(new Font("Abadi MT Condensed Extra Bold", Font.BOLD,14));
		
		myToCityPanel = new JPanel(new BorderLayout());
		myToCityPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final JLabel toCityLabel = new JLabel("CITY: ");
		toCityLabel.setFont(new Font("Abadi MT Condensed", Font.BOLD, 12));
		toCityLabel.setForeground(MainFrame.ASR_DARKBLUE);

		myToCityPanel.add(toCityLabel, BorderLayout.WEST);
		myToCityPanel.add(myToCityTxtf, BorderLayout.CENTER);
		
		myReturnDatePanel = new JPanel(new BorderLayout());
		myReturnDatePanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final JLabel returnDateLabel = new JLabel("DATE:");
		returnDateLabel.setFont(new Font("Abadi MT Condensed", Font.BOLD, 12));
		returnDateLabel.setForeground(MainFrame.ASR_DARKBLUE);

		myReturnDatePanel.add(returnDateLabel, BorderLayout.WEST);
		myReturnDatePanel.add(myReturnDatePicker, BorderLayout.CENTER);
		
		toCityDatePanel = new JPanel(new BorderLayout());
		toCityDatePanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		toCityDatePanel.add(myToCityPanel, BorderLayout.NORTH);
		toCityDatePanel.add(myReturnDatePanel, BorderLayout.SOUTH);
		
		myToPanel.add(toLabel, BorderLayout.NORTH);
		myToPanel.add(toCityDatePanel, BorderLayout.CENTER);
	}
	
	/**
	 * Create Connecting panel
	 */
	private void createConnectingPanel() {
		myConnectingPanel = new JPanel(new BorderLayout());
		myConnectingPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final JPanel connectingPanel = new JPanel(new BorderLayout());
		connectingPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		
		final JLabel connectingLabel = new JLabel("CONNECTING");
		connectingLabel.setFont(new Font("Abadi MT Condensed Extra Bold", Font.BOLD, 14));
		
		myConnectingCityPanel = new JPanel(new BorderLayout());
		myConnectingCityPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final JLabel connectingCityLabel = new JLabel("CITY: ");
		connectingCityLabel.setFont(new Font("Abadi MT Condensed", Font.BOLD, 12));
		connectingCityLabel.setForeground(MainFrame.ASR_DARKBLUE);
		
		myConnectingCityPanel.add(connectingCityLabel, BorderLayout.WEST);
		myConnectingCityPanel.add(myConnectingCityTxtf, BorderLayout.CENTER);
		
		myConnectingDatePanel = new JPanel(new BorderLayout());
		myConnectingDatePanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final JLabel connectingDateLabel = new JLabel("DATE:");
		connectingDateLabel.setFont(new Font("Abadi MT Condensed", Font.BOLD, 12));
		connectingDateLabel.setForeground(MainFrame.ASR_DARKBLUE);

		myConnectingDatePanel.add(connectingDateLabel, BorderLayout.WEST);
		myConnectingDatePanel.add(myConnectingDatePicker, BorderLayout.CENTER);
		
		final JPanel connectingCityDatePanel = new JPanel(new BorderLayout());
		connectingCityDatePanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		connectingCityDatePanel.add(myConnectingCityPanel, BorderLayout.NORTH);
		connectingCityDatePanel.add(myConnectingDatePanel, BorderLayout.SOUTH);
		
		connectingPanel.add(connectingLabel, BorderLayout.NORTH);
		connectingPanel.add(connectingCityDatePanel, BorderLayout.CENTER);
		
		myConnectingPanel.add(createArrowSymbol(), BorderLayout.WEST);
		myConnectingPanel.add(connectingPanel, BorderLayout.CENTER);
		myConnectingPanel.add(createArrowSymbol(), BorderLayout.EAST);
	}
	
	/**
	 * Create arrow symbol panel
	 * 
	 * @return A symbol panel
	 */
	private JPanel createArrowSymbol() {
		final JPanel arrowPanel = new JPanel(new BorderLayout());
		arrowPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final JPanel arrowNPanel = new JPanel();
		arrowNPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final JPanel arrowSPanel = new JPanel();
		arrowSPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final JLabel imageLabel = new JLabel();
		imageLabel.setBackground(Color.WHITE);
		imageLabel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/small>>.png"))));
		
		arrowSPanel.add(imageLabel);
		arrowPanel.add(arrowNPanel, BorderLayout.CENTER);
		arrowPanel.add(arrowSPanel, BorderLayout.SOUTH);
		return arrowPanel;
	}

	/**
	 * Create Payment panel
	 * @return A payment panel
	 */
	private JPanel createPaymentPanel() {
		final JPanel panel = new JPanel(new BorderLayout());
		
		final JPanel titlePanel = new JPanel();
		titlePanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final JLabel payLabel = new JLabel("BOOK A TRIP ");
		payLabel.setFont(MainFrame.ASR_BOLD_FONT);
		titlePanel.add(payLabel);
		
		final JPanel statusPanel = new JPanel();
		statusPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final JLabel resNoLabel = new JLabel("Reservation No. " + myReservationNo + " Status: Not confirmed");
		statusPanel.add(resNoLabel);

	
		final JPanel tripPanel = new JPanel(new BorderLayout());
		tripPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		tripPanel.add(createMyTripData(), BorderLayout.CENTER);
		
		final JPanel payBtnPanel = new JPanel();
		payBtnPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		
		final JPanel groupPanel = new JPanel();
		groupPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final JPanel payPanel = new JPanel(new GridLayout(6, 2));
		payPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final JLabel fareLabel = new JLabel("Fare amount:");
		final JLabel priceLabel = new JLabel("$ " + myPayment);
		final JLabel ccnumLabel = new JLabel("Enter credit card number: (16 digits)");
		final JTextField ccnumberTxtf = new JTextField(16);
		final JLabel expdateLabel = new JLabel("Enger experation date: (ex: 12/17)");
		final JTextField expdateTxtf = new JTextField(5);
		final JLabel cvsLabel = new JLabel("Enter security code: (3 digit in back)");
		final JTextField cvsTxtf = new JTextField(3);
		payPanel.add(fareLabel);
		payPanel.add(priceLabel);
		payPanel.add(ccnumLabel);
		payPanel.add(ccnumberTxtf);
		payPanel.add(expdateLabel);
		payPanel.add(expdateTxtf);
		payPanel.add(cvsLabel);
		payPanel.add(cvsTxtf);
		if (isInternational) {
			final JLabel citizenLabel = new JLabel("Select Citizenship:");
			payPanel.add(citizenLabel);
			payPanel.add(createCountryDropdown());
		}
		groupPanel.add(payPanel);
		payBtnPanel.add(myPayButton);
		
		final JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		bottomPanel.add(groupPanel, BorderLayout.CENTER);
		bottomPanel.add(payBtnPanel, BorderLayout.SOUTH);
		
		panel.add(titlePanel, BorderLayout.NORTH);
		panel.add(tripPanel, BorderLayout.CENTER);
		panel.add(bottomPanel, BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * Create booking panel
	 * 
	 * @return A booking panel
	 */
	private JPanel createBookingPanel() {
		final JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(MainFrame.ASR_LIGHTBLUE);
		
		final JPanel bookLabelPanel = new JPanel();
		bookLabelPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final JLabel bookLabel = new JLabel("BOOK A TRIP");
		bookLabel.setFont(MainFrame.ASR_BOLD_FONT);
		bookLabelPanel.add(bookLabel);
		
		final JPanel tripPanel = new JPanel(new BorderLayout());
		tripPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		tripPanel.add(createMyTripData(), BorderLayout.CENTER);
		
		
		final JPanel bottomPanel = new JPanel(new BorderLayout());
		
		bottomPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final JLabel priceLabel = new JLabel("Total fare: $ " + myPayment);
		priceLabel.setFont(MainFrame.ASR_BOLD_FONT);
		priceLabel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final JPanel pricePanel = new JPanel();
		pricePanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		pricePanel.add(priceLabel);
		buttonPanel.add(myBookButton);
		bottomPanel.add(pricePanel, BorderLayout.NORTH);
		bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		panel.add(bookLabelPanel, BorderLayout.NORTH);
		panel.add(tripPanel, BorderLayout.CENTER);
		panel.add(bottomPanel, BorderLayout.SOUTH);

		return panel;
	}

	/**
	 * Create first ticket panel
	 * @return A first ticket panel
	 */
	private JPanel createFirstTicketPanel() {
		final JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(MainFrame.ASR_LIGHTBLUE);
		
		final JPanel tripPanel = new JPanel();
		tripPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final JLabel tripLabel = new JLabel("");
		String labelStr = "";
		if (myHasSecondFlight) {
			labelStr += "TRIP 1: ";
		} else {
			labelStr += "TRIP: ";
		}
		try {
			labelStr += " " + FlightDB.getFlightOf(myFirstFlightNo).getDepartureAirport() + " " +  " ▶ ▶ ▶ "
					+ " " + FlightDB.getFlightOf(myFirstFlightNo).getArrivalAirport();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tripLabel.setText(labelStr);
		tripLabel.setFont(MainFrame.ASR_BOLD_FONT);
		tripPanel.add(tripLabel);
		
		final JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		if (myHasSecondFlight) {
			buttonPanel.add(myNextTicketButton);
		} else {
			buttonPanel.add(myCompleteButton);
		}
		
		final JPanel dropdownPanel = new JPanel();
		dropdownPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		dropdownPanel.add(createTicketDropdown(myFirstFlightNo));
		
		panel.add(tripPanel, BorderLayout.NORTH);
		panel.add(dropdownPanel, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		return panel;
	}
	
	/**
	 * Create a panel for seat, meal, and other service requests for the given panel
	 * 
	 * @param flightNo
	 * @return A panel for seat, meal, and other service requests for the given panel
	 */
	private JPanel createTicketDropdown(final String flightNo) {
		final JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final JPanel selectionPanel = new JPanel(new GridLayout(6,2));
		selectionPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		
		int seatSize;
		try {
			seatSize = Integer.valueOf(AircraftDB.getAircraft(FlightDB.getFlightOf(flightNo).getTailNo()).getCapacity());
			String[] seats = new String[seatSize];
			for (int i = 0; i < seatSize; i++) {
				
				seats[i] = String.valueOf(i + 1);
			}
			List<String> assigned = TicketDB.getAssignedSeatsOf(flightNo);
			mySeats = new Object[seats.length-assigned.size()+1];
			int k = 0;
			boolean taken = false;
			for (int i = 0; i < seats.length; i++) {
				for (int j = 0; j < assigned.size(); j++) {
					String seat = assigned.get(j);
					if (seats[i].equalsIgnoreCase(seat)){
						taken = true;
						break;
					} else {
						taken = false;
					}
				}
				if (!taken) {
					mySeats[k] = seats[i];
					k++;
				}
			}
			assigned.clear();
			assigned = null;
			
			List<Meal> mealLsit= MealDB.getAllMeals();
			myMeals = new Object[mealLsit.size()];
			for (int i = 0; i < mealLsit.size(); i++) {
				myMeals[i] = mealLsit.get(i).getMealCode() + "-" +mealLsit.get(i).getMealName();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		myWHCRs = new Object[Ticket.SET_WHCR.length];
		for (int i = 0; i < Ticket.SET_WHCR.length; i++) {
			myWHCRs[i] = Ticket.SET_WHCR[i];
		}
		myUMs = new Object[Ticket.SET_UM.length];
		for (int i = 0; i < Ticket.SET_UM.length; i++) {
			myUMs[i] = Ticket.SET_UM[i];
		}
		myPets = new Object[Ticket.SET_PET.length];
		for (int i = 0; i < Ticket.SET_PET.length; i++) {
			myPets[i] = Ticket.SET_PET[i];
		}
		myBsnts = new Object[Ticket.SET_BASSINET.length];
		for (int i = 0; i < Ticket.SET_BASSINET.length; i++) {
			myBsnts[i] = Ticket.SET_BASSINET[i];
		}
		if (mySeats != null) {
			mySeatDropdown = new JComboBox<Object>(mySeats);
			if (mySeats.length < 1) {
				mySeatDropdown.insertItemAt("No more seat!", 0);
			}
			mySeatDropdown.setSelectedIndex(0);
			selectionPanel.add(new JLabel("Select seat"));
			selectionPanel.add(mySeatDropdown);
		}
		if (myMeals != null) {
			myMealDropdown = new JComboBox<Object>(myMeals);
			if (myMeals.length < 1) {
				myMealDropdown.insertItemAt("No more meal!", 0);
			}
			myMealDropdown.setSelectedIndex(3);
			selectionPanel.add(new JLabel("Select meal"));
			selectionPanel.add(myMealDropdown);
		}
		if (myWHCRs != null) {
			myWHCRDropdown = new JComboBox<Object>(myWHCRs);
			if (myWHCRs.length < 1) {
				myWHCRDropdown.insertItemAt("No WHCR option available!", 0);
			}
			myWHCRDropdown.setSelectedIndex(0);
			selectionPanel.add(new JLabel("Select Wheelchair service"));
			selectionPanel.add(myWHCRDropdown);
		}
		if (myUMs != null) {
			myUMDropdown = new JComboBox<Object>(myUMs);
			if (myUMs.length < 1) {
				myUMDropdown.insertItemAt("No UM option available!", 0);
			}
			myUMDropdown.setSelectedIndex(0);
			selectionPanel.add(new JLabel("Select Unaccompanied Minor service"));
			selectionPanel.add(myUMDropdown);
		}
		if (myPets != null) {
			myPetDropdown = new JComboBox<Object>(myPets);
			if (myPets.length < 1) {
				myPetDropdown.insertItemAt("No Pet option available!", 0);
			}
			myPetDropdown.setSelectedIndex(0);
			selectionPanel.add(new JLabel("Select Pet service"));
			selectionPanel.add(myPetDropdown);
		}
		if (myBsnts != null) {
			myBassinetDropdown = new JComboBox<Object>(myBsnts);
			if (myBsnts.length < 1) {
				myBassinetDropdown.insertItemAt("No option available!", 0);
			}
			myBassinetDropdown.setSelectedIndex(0);
			selectionPanel.add(new JLabel("Select Bassinet service"));
			selectionPanel.add(myBassinetDropdown);
		}
		panel.add(selectionPanel, BorderLayout.CENTER);
		return panel;
	}
	
	/**
	 * Create a second trip panel
	 * 
	 * @return A second trip panel
	 */
	private JPanel createSecondTicketPanel() {
		final JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(MainFrame.ASR_LIGHTBLUE);
		
		final JPanel tripPanel = new JPanel();
		tripPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final JLabel tripLabel = new JLabel("");
		String labelStr = "TRIP 2";
		try {
			labelStr += FlightDB.getFlightOf(mySecondFlightNo).getDepartureAirport() + " ▶▶▶ "
					+ FlightDB.getFlightOf(mySecondFlightNo).getArrivalAirport();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tripLabel.setText(labelStr);
		tripLabel.setFont(MainFrame.ASR_BOLD_FONT);
		tripPanel.add(tripLabel);
		
		final JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		buttonPanel.add(myCompleteButton);
		
		final JPanel dropdownPanel = new JPanel();
		dropdownPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		dropdownPanel.add(createTicketDropdown(mySecondFlightNo));

		panel.add(tripPanel, BorderLayout.NORTH);
		panel.add(dropdownPanel, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		return panel;
	}
	
	/**
	 * Create complete panel (the last page)
	 * 
	 * @return A complete panel
	 */
	private JPanel createConfirmationOptionPanel() {
		final JPanel panel = new JPanel(new GridLayout(3, 0));

		final JLabel titleLabel = new JLabel("COMPLETE YOUR RESERVATION");
		final JLabel resNoLabel = new JLabel("Reservation No. " + myReservationNo);
		resNoLabel.setFont(MainFrame.ASR_BOLD_FONT);
		final JLabel checkLabel = new JLabel("Check your reservation on \"View My Reservation\"");

		panel.add(titleLabel);
		panel.add(resNoLabel);
		panel.add(checkLabel);

		return panel;
	}
	
	/**
	 * Create country dropdown
	 * @return A country dropdown
	 */
	private JComboBox<Object> createCountryDropdown() {
		
		List<String> countryList;
		try {
			countryList = AirportDB.getCountries();
			if (countryList != null) {
				Object[] countryData = new Object[countryList.size()];
				for (int i = 0; i < countryList.size(); i++) {
					countryData[i] = countryList.get(i).toString();
				}
				myCountryDropdown = new JComboBox<Object>(countryData);
				if (countryData.length < 1) {
					myCountryDropdown.insertItemAt("No Pet option available!", 0);
				}
				myCountryDropdown.setSelectedIndex(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return myCountryDropdown;
	}

	/**
	 * Create text fidlds
	 */
	private void createAutoCompTxtf() {
		// Source from: http://www.java2s.com/Code/Java/Swing-Components/AutoCompleteTextField.htm
		myFromCityTxtf = new AutoCompleteTextField(10);
		myToCityTxtf = new AutoCompleteTextField(10);
		myConnectingCityTxtf = new AutoCompleteTextField(10);
		try {
			List<Airport> airports = AirportDB.getAllAirports();
			for (int i = 0; i < airports.size(); i++) {
				String s =  airports.get(i).getAirport() + "-" + airports.get(i).getCity() + ", " + airports.get(i).getCountry();
//				String s =  airports.get(i).getCity() + "-" + airports.get(i).getAirport();
				myFromCityTxtf.addPossibility(s);
				myToCityTxtf.addPossibility(s);
				myConnectingCityTxtf.addPossibility(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create date pickers
	 */
	private void createDatePicker() {
		// Source From: https://github.com/JDatePicker/JDatePicker
		// source from: http://stackoverflow.com/questions/26794698/how-do-i-implement-jdatepicker
		UtilDateModel[] model = new UtilDateModel[3];
		for (int i = 0; i < model.length; i++) {
			model[i] = new UtilDateModel();
		}
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl fromPanel = new JDatePanelImpl(model[0], p);
		fromPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		JDatePanelImpl returnPanel = new JDatePanelImpl(model[1], p);
		returnPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		JDatePanelImpl connectPanel = new JDatePanelImpl(model[2], p);
		connectPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		myFromDatePicker = new JDatePickerImpl(fromPanel, new DateLabelFormatter());
		myReturnDatePicker = new JDatePickerImpl(returnPanel, new DateLabelFormatter());
		myConnectingDatePicker = new JDatePickerImpl(connectPanel, new DateLabelFormatter());
		Dimension d =  myFromDatePicker.getPreferredSize();
		myFromDatePicker.setPreferredSize(new Dimension((d.width - 20), d.height));
		myReturnDatePicker.setPreferredSize(new Dimension((d.width - 20), d.height));
		myConnectingDatePicker.setPreferredSize(new Dimension((d.width - 20), d.height));
		myFromDatePicker.setBackground(MainFrame.ASR_LIGHTBLUE);
		myReturnDatePicker.setBackground(MainFrame.ASR_LIGHTBLUE);
		myConnectingDatePicker.setBackground(MainFrame.ASR_LIGHTBLUE);
	}
	
	/**
	 * Create Passenger's trip table
	 * @return A scroll panel for trip table
	 */
	private JScrollPane createMyTripData() {
		final String[] tripColumnName = {"Trip", "From", "To", "Departure Date", "Time", "Arrival Date", "Time"};
		
		try {
			if (myHasSecondFlight) {
				myCombinedData = new Object[2][tripColumnName.length];
			} else {
				myCombinedData = new Object[1][tripColumnName.length];
			}
			Flight firstFlight = FlightDB.getFlightOf(myFirstFlightNo);
			myCombinedData[0][0] = "Trip 1";
			myCombinedData[0][1] = firstFlight.getDepartureAirport();
			myCombinedData[0][2] = firstFlight.getArrivalAirport();
			myCombinedData[0][3] = firstFlight.getDepartureDate();
			myCombinedData[0][4] = firstFlight.getDepartureTime();
			myCombinedData[0][5] = firstFlight.getArrivalDate();
			myCombinedData[0][6] = firstFlight.getArrivalTime();
			
			if (myHasSecondFlight) {
				Flight secondFlight = FlightDB.getFlightOf(mySecondFlightNo);
				myCombinedData[1][0] = "Trip 2";
				myCombinedData[1][1] = secondFlight.getDepartureAirport();
				myCombinedData[1][2] = secondFlight.getArrivalAirport();
				myCombinedData[1][3] = secondFlight.getDepartureDate();
				myCombinedData[1][4] = secondFlight.getDepartureTime();
				myCombinedData[1][5] = secondFlight.getArrivalDate();
				myCombinedData[1][6] = secondFlight.getArrivalTime();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		final JTable tripTable = new JTable(myCombinedData, tripColumnName);
		final JScrollPane tripScrollPane = new JScrollPane(tripTable);
		Dimension d = tripTable.getPreferredSize();
		tripScrollPane.setPreferredSize(new Dimension(d.width + 10, tripTable.getRowHeight() * (30)));
		
		return tripScrollPane;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * Listen to the button //TIDI
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mySearchButton) {
			
			performSearchFlights();		
			
			// create first table
			final JPanel buttonPanel = new JPanel();
			buttonPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
			buttonPanel.add(myFirstSelectButton);
			buttonPanel.add(myGobackButton);
			myFirstTable = new JTable(myFirstData, myColumnNames);
			myFirstScrollPane = new JScrollPane(myFirstTable);
			myContentPanel.removeAll();
			myContentPanel.add(myFirstScrollPane, BorderLayout.CENTER);
			myContentPanel.add(buttonPanel, BorderLayout.SOUTH);
			myContentPanel.revalidate();
			this.repaint();
			
		} else if (e.getSource() == myFirstSelectButton) { // select first flight
			performSelectFirstFlight();
			
			
			if (myHasSecondFlight) { // repaint with second flight list
				final JPanel buttonPanel = new JPanel();
				buttonPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
				buttonPanel.add(mySecondSelectButton);
				buttonPanel.add(myGobackButton);
				
				myContentPanel.removeAll();
				myContentPanel.add(mySecondScrollPane, BorderLayout.CENTER);
				myContentPanel.add(buttonPanel, BorderLayout.SOUTH);
				myContentPanel.revalidate();
				this.repaint();
			} else {
				myContentPanel.removeAll();
				myContentPanel.add(createBookingPanel(), BorderLayout.CENTER);
				myContentPanel.revalidate();
				this.repaint();
			}
			
		} else if (e.getSource() == mySecondSelectButton) { // select Second flight
			performSelectSecondFlight();

			// repaint with book reservation
			myContentPanel.removeAll();
			myContentPanel.add(createBookingPanel(), BorderLayout.CENTER);
			myContentPanel.revalidate();
			this.repaint();
			
		} else if (e.getSource() == myBookButton) { // make a reservation
			performBookReservation();
			
			// repaint with payment 
			myContentPanel.removeAll();
			myContentPanel.add(createPaymentPanel(), BorderLayout.CENTER);
			myContentPanel.revalidate();
			this.repaint();
			
		} else if (e.getSource() == myPayButton) { // make a payment
			
			performPayReservation();
			
			// repaint with a panel that contains seat assignment, meal, other requests.
			myContentPanel.removeAll();
			myContentPanel.add(createFirstTicketPanel(), BorderLayout.CENTER);
			myContentPanel.revalidate();
			this.repaint();
			
		} else if (e.getSource() == myNextTicketButton) { // set seat, meal, other for first ticekt
			performFirstTicket();
			
			myContentPanel.removeAll();
			myContentPanel.add(createSecondTicketPanel(), BorderLayout.CENTER);
			myContentPanel.revalidate();
			this.repaint();

		} else if (e.getSource() == myCompleteButton) { // set for 2nd ticket
			if (myHasSecondFlight) {
				performSecondTicket();
			} else {
				performFirstTicket();
			}

		} else if (e.getSource() == myGobackButton) { // select back to search page
			getData(null,null,null,null,null,null);
			myContentPanel.removeAll();
			myContentPanel.add(createSearchPanel(), BorderLayout.CENTER);
			myContentPanel.add(createImageLabel(), BorderLayout.SOUTH);
			myContentPanel.revalidate();
			this.repaint();
		}
	}

	/**
	 * Perform whant 'mySecondSelectButton' is pressed
	 */
	private void performSecondTicket() {
		String seat = mySeatDropdown.getSelectedItem().toString();
		String meal = myMealDropdown.getSelectedItem().toString();
		meal = meal.substring(0, meal.indexOf('-'));
		String whcr = myWHCRDropdown.getSelectedItem().toString();
		String um = myUMDropdown.getSelectedItem().toString();
		String pet = myPetDropdown.getSelectedItem().toString();
		String bsn = myBassinetDropdown.getSelectedItem().toString();
		
		Ticket secondTicket = new Ticket(myReservationNo, mySecondFlightNo, seat, meal, bsn, um, whcr, pet);
		try {
			if (!TicketDB.addTicket(secondTicket).startsWith("Ticket added Successfully")) {
				JOptionPane.showMessageDialog(null, "Make a ticke is failed.");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int result = JOptionPane.showConfirmDialog(null, createConfirmationOptionPanel(), "You are done!", JOptionPane.OK_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			getData(null,null,null,null,null,null);
			myContentPanel.removeAll();
			myContentPanel.add(createSearchPanel(), BorderLayout.CENTER);
			myContentPanel.add(createImageLabel(), BorderLayout.SOUTH);
			myContentPanel.revalidate();
			this.repaint();
		}

	}
	
	/**
	 * Perform to make 1st ticket with seat, meal, and other request
	 */
	private void performFirstTicket() {
		// make 1st ticket
		String seat = mySeatDropdown.getSelectedItem().toString();
		String meal = myMealDropdown.getSelectedItem().toString();
		meal = meal.substring(0, meal.indexOf('-'));
		String whcr = myWHCRDropdown.getSelectedItem().toString();
		String um = myUMDropdown.getSelectedItem().toString();
		String pet = myPetDropdown.getSelectedItem().toString();
		String bsn = myBassinetDropdown.getSelectedItem().toString();

		Ticket firstTicket = new Ticket(myReservationNo, myFirstFlightNo, seat, meal, bsn, um, whcr, pet);
		try {
			
			if (!TicketDB.addTicket(firstTicket).startsWith("Ticket added Successfully")) {
				JOptionPane.showMessageDialog(null, "Make a ticke is failed.");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (!myHasSecondFlight) {

			int result = JOptionPane.showConfirmDialog(null, createConfirmationOptionPanel(), "You are done!", JOptionPane.OK_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				getData(null,null,null,null,null,null);
				myContentPanel.removeAll();
				myContentPanel.add(createSearchPanel(), BorderLayout.CENTER);
				myContentPanel.add(createImageLabel(), BorderLayout.SOUTH);
				myContentPanel.revalidate();
				this.repaint();
			}
		}

	}
	
	/**
	 * Perform when 'Pay' button in Payment panel is pressed.
	 */
	private void performPayReservation() {
		//  set reservation status to CONF
		
		try {
			Reservation reservation = ReservationDB.getReservationOf(myReservationNo);
			if (isInternational) {
				String citizen = myCountryDropdown.getSelectedItem().toString();
				reservation.setPSPTcountry(citizen);
				if (!ReservationDB.updateReservation(reservation, "PSPT_countrycode", citizen)) {
					JOptionPane.showMessageDialog(null, "Update a reservation is failed.");
					return;
				}
			}

			reservation.setPaidConfirm("CONF");
			// update reservation
			if (!ReservationDB.updateReservation(reservation, "Paid_confirm", "CONF")) {
				JOptionPane.showMessageDialog(null, "Update a reservation is failed.");
				return;
			}
			

			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Perform when 'Book' button in Book a Flight panel is pressed.
	 */
	private void performBookReservation() {

		try { // make reservation number with "not confirmed"
			Reservation reservation = new Reservation(myPassengerNo, myPayment, "NOT CONF");
			
			if (ReservationDB.addReservation(reservation).startsWith("Reservation added Successfully")) {
				reservation = ReservationDB.getAllReservation().get(ReservationDB.getAllReservation().size()-1);
				myReservationNo = reservation.getReservationNo();

			} else {
				JOptionPane.showMessageDialog(null, "Make a reservation is failed.");
				return;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Perform when user presses "SELECT" button for Second flight
	 */
	private void performSelectSecondFlight() {

		if (mySecondTable.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, "Select a flight");
			return;
		}
		mySecondFlightNo = (String) mySecondTable.getValueAt(mySecondTable.getSelectedRow(), 0);

		if (mySecondFlightNo != null) {
			try {
				if (!isInternational) {
					isInternational = FlightDB.isInternationalFlight(mySecondFlightNo);
				}
				myPayment += FlightDB.getSellingPriceOf(mySecondFlightNo);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Select a flight");
		}

	}
	
	
	/**
	 * Perform when user presses "SELECT" button for first flight
	 */
	private void performSelectFirstFlight() {

		if (myFirstTable.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, "Select a flight");
			return;
		}
		myFirstFlightNo = (String) myFirstTable.getValueAt(myFirstTable.getSelectedRow(), 0);
		
		if(myFirstFlightNo != null) {
			try {
				isInternational = FlightDB.isInternationalFlight(myFirstFlightNo);
				myPayment = FlightDB.getSellingPriceOf(myFirstFlightNo);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		} else {
			JOptionPane.showMessageDialog(null, "Select a flight");
			return;
		}
	}
	
	
	/**
	 * Perform when serch button is pressed.
	 * 
	 * @throws SQLException
	 */
	private void performSearchFlights() {
		// if search button is pressed
		String fromCity = myFromCityTxtf.getText();
		fromCity = fromCity.substring(0, fromCity.indexOf('-'));
		String fromDate_str = myFromDatePicker.getJFormattedTextField().getText();
		String toCity = myToCityTxtf.getText();
		toCity = toCity.substring(0, toCity.indexOf('-'));
		String returnDate_str = null;
		String connectingCity = null;
		String connectingDate_str = null;

		java.util.Date fromDate = null;
		try {
			fromDate = MainFrame.DATE_FORMAT.parse(fromDate_str);
			java.sql.Date sqlDep_date = new Date(fromDate.getTime());

			if (myMultiRbtn.isSelected()) { // if trip type is multi-city
				connectingCity = myConnectingCityTxtf.getText();
				connectingCity = connectingCity.substring(0, connectingCity.indexOf('-'));
				System.out.println(connectingCity);
//				connectingCity = connectingCity.substring(connectingCity.indexOf('-'), connectingCity.indexOf(connectingCity.length()-1));
				connectingDate_str = myConnectingDatePicker.getJFormattedTextField().getText();
				java.util.Date connectingDate = MainFrame.DATE_FORMAT.parse(connectingDate_str);
				java.sql.Date sqlConnection_date = new Date(connectingDate.getTime());
				
				// get data for multi-city flights
				getData(fromCity, sqlDep_date, connectingCity, sqlConnection_date, toCity, null);

				if ( myFirstList.size() <1 || mySecondList.size() < 1) {
					JOptionPane.showMessageDialog(null, "No shceduled flight on the given date");
					return;
				}
				// create second table
				mySecondTable = new JTable(mySecondData, myColumnNames);
				mySecondScrollPane = new JScrollPane(mySecondTable);
				
			} else if (myRoundRbtn.isSelected()) { // if trip type is round trip
				returnDate_str = myReturnDatePicker.getJFormattedTextField().getText();
				if (returnDate_str == null || returnDate_str.length() < 1) {
					JOptionPane.showMessageDialog(null, "Enter return date (yyyy-MM-dd)");
					return;
				}
				java.util.Date returnDate = MainFrame.DATE_FORMAT.parse(returnDate_str);
				java.sql.Date sqlReturn_date = new Date(returnDate.getTime());

				// get data for round trip
				getData(fromCity, sqlDep_date, null, null, toCity, sqlReturn_date);
				
				if ( myFirstList.size() <1 || mySecondList.size() < 1) {
					JOptionPane.showMessageDialog(null, "No shceduled flight on the given date");
					return;
				}

				// create second table
				mySecondTable = new JTable(mySecondData, myColumnNames);
				mySecondScrollPane = new JScrollPane(mySecondTable);
				
			} else if (myOnewayRbtn.isSelected()) { // if trip type is oneway trip
				getData(fromCity, sqlDep_date, null, null, toCity, null);
				if ( myFirstList.size() <1) {
					JOptionPane.showMessageDialog(null, "No shceduled flight on the given date");
					return;
				}
			}
		
		} catch (ParseException e) {
			e.printStackTrace();
		}

		
		// clear all text fields
		myFromCityTxtf.setText("");
		myFromDatePicker.getJFormattedTextField().setText("");
		myToCityTxtf.setText("");
		myReturnDatePicker.getJFormattedTextField().setText("");
		if (myMultiRbtn.isSelected()) {
			myConnectingCityTxtf.setText("");
			myConnectingDatePicker.getJFormattedTextField().setText("");
		}
	}
	
	
	/**
	 * Perform trip type radion button is selected.
	 */
	private void performTripType() {
		mySearchPanel.removeAll();
		mySearchPanel.add(myRadiobuttonPanel);
		mySearchPanel.add(myEmptyPanel);
		mySearchPanel.add(myFromToPane);
		mySearchPanel.add(mySearchBtnPanel);
		mySearchPanel.revalidate();
		this.repaint();
	}

	
	
	
	/*
	 * Get Data
	 */
	
	
	
	/**
	 * Get flight data
	 * 
	 * @param theFromCity
	 * @param theFromDate
	 * @param theConnectCity
	 * @param theConnectDate
	 * @param theToCity
	 * @param theReturnDate
	 */
	private void getData(String theFromCity, Date theFromDate, String theConnectCity, Date theConnectDate,
			String theToCity, Date theReturnDate){
		List<Flight> first = null;
		List<Flight> second = null;
		try {
			if (theConnectCity == null && theConnectDate == null && theReturnDate == null
					&& theFromCity!= null && theFromDate != null && theToCity != null) { // if oneway
			
				first = FlightDB.getFlights(theFromCity, theFromDate, theToCity);
				
			} else if (theConnectCity == null && theConnectDate == null && theReturnDate != null
					&& theFromCity!= null && theFromDate != null && theToCity != null) { // if round
				
	
				first = FlightDB.getFlights(theFromCity, theFromDate, theToCity);
				second = FlightDB.getFlights(theToCity, theReturnDate, theFromCity);

			} else if (theFromCity != null && theFromDate != null && theConnectCity != null
					&& theConnectDate != null && theToCity != null && theReturnDate == null) { // if multi city

				first = FlightDB.getFlights(theFromCity, theFromDate, theConnectCity);
				second = FlightDB.getFlights(theConnectCity, theConnectDate, theToCity);

			} else {
				first = FlightDB.getAllFlights();
			}

			if (first != null) {
				myFirstList = new ArrayList<Flight>();
				for (int i = 0; i < first.size(); i++) {
					String f_no = first.get(i).getFlightNo();
					int available_seat = FlightDB.getNumAvailableSeatOf(f_no);
					if(available_seat > 0) {
						myFirstList.add(first.get(i));
					}
				}
				myFirstData = new Object[myFirstList.size()][myColumnNames.length];
				for (int i = 0; i < myFirstList.size(); i++) {
					myFirstData[i][0] = myFirstList.get(i).getFlightNo();
					myFirstData[i][1] = myFirstList.get(i).getDepartureAirport();
					myFirstData[i][2] = myFirstList.get(i).getDepartureDate();
					myFirstData[i][3] = myFirstList.get(i).getDepartureTime();
					myFirstData[i][4] = myFirstList.get(i).getArrivalAirport();
					myFirstData[i][5] = myFirstList.get(i).getArrivalDate();
					myFirstData[i][6] = myFirstList.get(i).getArrivalTime();
					myFirstData[i][7] = FlightDB.getSellingPriceOf(myFirstList.get(i).getFlightNo());
				}
			}
			if (second != null) {
				mySecondList = new ArrayList<Flight>();
				for (int i = 0; i < second.size(); i++) {
					if(FlightDB.getNumAvailableSeatOf(second.get(i).getFlightNo()) > 0) {
						mySecondList.add(second.get(i));
					}
				}
				mySecondData = new Object[mySecondList.size()][myColumnNames.length];
				for (int i = 0; i < mySecondList.size(); i++) {
					mySecondData[i][0] = mySecondList.get(i).getFlightNo();
					mySecondData[i][1] = mySecondList.get(i).getDepartureAirport();
					mySecondData[i][2] = mySecondList.get(i).getDepartureDate();
					mySecondData[i][3] = mySecondList.get(i).getDepartureTime();
					mySecondData[i][4] = mySecondList.get(i).getArrivalAirport();
					mySecondData[i][5] = mySecondList.get(i).getArrivalDate();
					mySecondData[i][6] = mySecondList.get(i).getArrivalTime();
					mySecondData[i][7] = FlightDB.getSellingPriceOf(mySecondList.get(i).getFlightNo());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
