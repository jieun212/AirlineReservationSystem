package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import data.FlightDB;
import data.PassengerDB;
import data.ReservationDB;
import data.TicketDB;
import model.Flight;
import model.Passenger;
import model.Reservation;
import model.Ticket;

/**
 * This is a panel that contains Flights. It allows to show all tickets,
 * including passenger name, ticketnumber, assigned seat, meal, other service
 * request, and citizenship for the selected flight.
 * 
 * @author Jieun Lee (jieun212@uw.edu)
 * @version 12-05-2016
 */
public class ViewFlightPanel extends JPanel implements ActionListener {

	/** A serial number. */
	private static final long serialVersionUID = -6785250266671501269L;
	
	/** A content panel. */
	private JPanel myContentPanel;
	
	/** Buttons. */
	private JButton mySearchFlightButton, myViewAllTktButton, myOrderMealButton, myOrderSeatButton, myViewWHCRButton,
			myViewBSSNButton, myViewUMButton, myViewPetButton, myGobackButton;
	
	/** A flight dropdown combobox. */
	private JComboBox<Object> myFlightDropdown;
	
	/** A ticket table. */
	private JTable myTicketTable;
	
	/** A scroll pane for ticket table. */
	private JScrollPane myTicketSPane;

	/** A list of flights. */
	private List<Flight> myFlightList;
	
	/** A list of tickets. */
	private List<Ticket> myTicketList;
	
	/** Data for tables. */
	private Object[][] myFlightData, myTicketData;
	
	/** Column names for flight. */
	private String[] myFlightColumn = { "Flight No", "Tail No", "From", "Departure Date", "Time", "Gate", "To",
			"Arrival Date", "Time", "Gate", "Base Price" };
	
	/** Column names for ticket. */
	private String[] myTicketColumn = { "Ticket No", "Reservation No", "First Name", "Middle Name", "Last Name", "Seat",
			"Meal", "WHCR", "UM", "BSSN", "PET", "Country" };
	
	
	/** A Flight no */
	private String myFlightNo;

	
	/**
	 * Constructs a ViewFlightPanel.
	 */
	public ViewFlightPanel() {
		setLayout(new BorderLayout());
		createComponent();
		setVisible(true);
		setPreferredSize(getPreferredSize());
	}

	/**
	 * Create componenets.
	 */
	private void createComponent() {

		mySearchFlightButton = new JButton("Search");
		mySearchFlightButton.addActionListener(this);

		// create dropdown
		myFlightDropdown = new JComboBox<Object>();

		myContentPanel = new JPanel(new BorderLayout());
		myContentPanel.add(createImagePanel(), BorderLayout.CENTER);

		add(createSearchFlightPanel(), BorderLayout.NORTH);
		add(myContentPanel, BorderLayout.CENTER);
		setPreferredSize(getPreferredSize());
	}

	/**
	 * Create a panel for image.
	 * 
	 * @return A panel for image.
	 */
	private JPanel createImagePanel() {
		final JPanel panel = new JPanel(new BorderLayout());
		final JLabel imageLabel = new JLabel();
		imageLabel.setBackground(Color.WHITE);
		imageLabel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/viewflight.jpg"))));
		panel.add(imageLabel, BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * Create a search panel
	 * 
	 * @return A search panel.
	 */
	private JPanel createSearchFlightPanel() {
		final JPanel panel = new JPanel(new GridLayout(3, 0));
		panel.setBackground(MainFrame.ASR_LIGHTBLUE);

		final JPanel searchPanel = new JPanel();
		searchPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		searchPanel.add(new JLabel("Select Scheduled Flight:"));
		searchPanel.add(createFlightDropdown());
		searchPanel.add(mySearchFlightButton);

		panel.add(new JLabel());
		panel.add(searchPanel);


		return panel;
	}

	/**
	 * Create a flight dropdown combobox.
	 * 
	 * @return A flight dropdown combobox.
	 */
	private JComboBox<Object> createFlightDropdown() {
		getFlightData();
		Object[] flights = new Object[myFlightList.size()];
		for (int i = 0; i < myFlightList.size(); i++) {
			flights[i] = myFlightList.get(i).getFlightNo() + ",    " + myFlightList.get(i).getDepartureAirport() + "-"
					+ myFlightList.get(i).getArrivalAirport() + "     " + myFlightList.get(i).getDepartureDate() + ", "
					+ myFlightList.get(i).getDepartureTime();
		}
		if (flights != null) {
			myFlightDropdown = new JComboBox<Object>(flights);
			if (flights.length < 1) {
				myFlightDropdown.insertItemAt("No scheduled flight, Add flight schedule!", 0);
			}
			myFlightDropdown.setSelectedIndex(0);
		}
		return myFlightDropdown;
	}

	/**
	 * Create a result panel after searching.
	 * 
	 * @param theFlightNo
	 * @param theViewColumn
	 * @param theViewKey
	 * @param theOrder
	 * @return A result panel
	 */
	private JPanel createResultPanel(final String theFlightNo, final String theViewColumn, String theViewKey,
			final String theOrder) {
		final JPanel panel = new JPanel(new BorderLayout());

		getTicketData(theFlightNo, theViewColumn, theViewKey, theOrder);
		myTicketTable = new JTable(myTicketData, myTicketColumn);
		myTicketSPane = new JScrollPane(myTicketTable);

		panel.add(createResultButtonPanel(), BorderLayout.NORTH);
		panel.add(myTicketSPane, BorderLayout.CENTER);

		return panel;
	}

	private JPanel createResultButtonPanel() {
		final JPanel buttonPanel = new JPanel(new BorderLayout());
		final JPanel button1stPanel = new JPanel();
		final JPanel button2ndPanel = new JPanel();

		myViewAllTktButton = new JButton("View All");
		myViewAllTktButton.addActionListener(this);

		myOrderMealButton = new JButton("Order by Meal");
		myOrderMealButton.addActionListener(this);

		myOrderSeatButton = new JButton("Order by Seat");
		myOrderSeatButton.addActionListener(this);

		myViewWHCRButton = new JButton("View Wheelchair PAX");
		myViewWHCRButton.addActionListener(this);

		myViewBSSNButton = new JButton("View Bassinet PAX");
		myViewBSSNButton.addActionListener(this);

		myViewUMButton = new JButton("View UM PAX");
		myViewUMButton.addActionListener(this);

		myViewPetButton = new JButton("View Pet");
		myViewPetButton.addActionListener(this);

		myGobackButton = new JButton("Close");
		myGobackButton.addActionListener(this);

		button1stPanel.add(myViewAllTktButton);
		button1stPanel.add(myOrderMealButton);
		button1stPanel.add(myOrderSeatButton);
		button2ndPanel.add(myViewWHCRButton);
		button2ndPanel.add(myViewBSSNButton);
		button2ndPanel.add(myViewUMButton);
		button2ndPanel.add(myViewPetButton);
		button1stPanel.add(myGobackButton);

		buttonPanel.add(button1stPanel, BorderLayout.NORTH);
		buttonPanel.add(button2ndPanel, BorderLayout.SOUTH);

		return buttonPanel;
	}

	
	
	
	/*
	 * Perform
	 */
	
	/**
	 * Listen the buttons.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mySearchFlightButton) {
			String flightNo = myFlightDropdown.getSelectedItem().toString();
			flightNo = flightNo.substring(0, flightNo.indexOf(','));
			myFlightNo = flightNo;
			myContentPanel.removeAll();
			myContentPanel.add(createResultPanel(flightNo, null, null, "Ticket_no"), BorderLayout.CENTER);
			myContentPanel.revalidate();
			this.repaint();
			
		} else if (e.getSource() == myViewAllTktButton) {
			myContentPanel.removeAll();
			myContentPanel.removeAll();
			myContentPanel.add(createResultPanel(myFlightNo, null, null, "Ticket_no"), BorderLayout.CENTER);
			myContentPanel.revalidate();
			this.repaint();
	
		} else if (e.getSource() == myOrderMealButton) {
			myContentPanel.removeAll();
			myContentPanel.add(createResultPanel(myFlightNo, null, null, "Meal_code"), BorderLayout.CENTER);
			myContentPanel.revalidate();
			this.repaint();

		} else if (e.getSource() == myOrderSeatButton) {
			myContentPanel.removeAll();
			myContentPanel.add(createResultPanel(myFlightNo, null, null, "Assigned_seat"), BorderLayout.CENTER);
			myContentPanel.revalidate();
			this.repaint();

		} else if (e.getSource() == myViewWHCRButton) {
			myContentPanel.removeAll();
			myContentPanel.add(createResultPanel(myFlightNo, "Wheelchair", " != 'N/A'", "Ticket_no"),
					BorderLayout.CENTER);
			myContentPanel.revalidate();
			this.repaint();

		} else if (e.getSource() == myViewPetButton) {
			myContentPanel.removeAll();
			myContentPanel.add(createResultPanel(myFlightNo, "Pet", " != 'N/A'", "Ticket_no"), BorderLayout.CENTER);
			myContentPanel.revalidate();
			this.repaint();

		} else if (e.getSource() == myViewBSSNButton) {
			myContentPanel.removeAll();
			myContentPanel.add(createResultPanel(myFlightNo, "Bassinet", " != 'N/A'", "Ticket_no"), BorderLayout.CENTER);
			myContentPanel.revalidate();
			this.repaint();

		} else if (e.getSource() == myViewUMButton) {
			myContentPanel.removeAll();
			myContentPanel.add(createResultPanel(myFlightNo, "UM"," != 'N/A'", "Ticket_no"), BorderLayout.CENTER);
			myContentPanel.revalidate();
			this.repaint();

		} else if (e.getSource() == myGobackButton) {
			myFlightDropdown.setSelectedIndex(0);
			myContentPanel.removeAll();
			myContentPanel.add(createImagePanel(), BorderLayout.CENTER);
			myContentPanel.revalidate();
			this.repaint();
		}
	}

	
	
	
	/*
	 * Get data
	 */

	/**
	 * Get ticket data
	 * 
	 * @param theFlightNo
	 * @param theViewColumn
	 * @param theViewKey
	 * @param theOrder
	 */
	private void getTicketData(final String theFlightNo, final String theViewColumn, String theViewKey,
			final String theOrder) {

		try {
			if (theFlightNo != null) {
				myTicketList = TicketDB.getTicketsOfFlight(theFlightNo, theViewColumn, theViewKey, theOrder);
			} else {
				myTicketList = TicketDB.getAllTickets();
			}
			if (myTicketList != null) {
				myTicketData = new Object[myTicketList.size()][myTicketColumn.length];
				for (int i = 0; i < myTicketList.size(); i++) {
					Reservation reservation = ReservationDB.getReservationOf(myTicketList.get(i).getReservationNo());
					String paxNo = reservation.getPassengerNo();
					Passenger pax = PassengerDB.getPassenger(paxNo);

					myTicketData[i][0] = myTicketList.get(i).getTicketNo();
					myTicketData[i][1] = myTicketList.get(i).getReservationNo();
					myTicketData[i][2] = pax.getFirstName();
					myTicketData[i][3] = pax.getMiddleName();
					myTicketData[i][4] = pax.getLastName();
					myTicketData[i][5] = myTicketList.get(i).getSeatNo();
					myTicketData[i][6] = myTicketList.get(i).getMealCode();
					myTicketData[i][7] = myTicketList.get(i).getWHCR();
					myTicketData[i][8] = myTicketList.get(i).getUM();
					myTicketData[i][9] = myTicketList.get(i).getBassinet();
					myTicketData[i][10] = myTicketList.get(i).getPet();
					myTicketData[i][11] = reservation.getPSPTcountry();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get flight data.
	 */
	private void getFlightData() {

		try {
			myFlightList = FlightDB.getAllFlights();
			if (myFlightList != null) {
				myFlightData = new Object[myFlightList.size()][myFlightColumn.length];
				for (int i = 0; i < myFlightList.size(); i++) {
					myFlightData[i][0] = myFlightList.get(i).getFlightNo();
					myFlightData[i][1] = myFlightList.get(i).getTailNo();
					myFlightData[i][2] = myFlightList.get(i).getDepartureAirport();
					myFlightData[i][3] = myFlightList.get(i).getDepartureDate();
					myFlightData[i][4] = myFlightList.get(i).getDepartureTime();
					myFlightData[i][5] = myFlightList.get(i).getDepartureGate();
					myFlightData[i][6] = myFlightList.get(i).getArrivalAirport();
					myFlightData[i][7] = myFlightList.get(i).getArrivalDate();
					myFlightData[i][8] = myFlightList.get(i).getArrivalTime();
					myFlightData[i][9] = myFlightList.get(i).getArrivalGate();
					myFlightData[i][10] = myFlightList.get(i).getBasePrice();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
