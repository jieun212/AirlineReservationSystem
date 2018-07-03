package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import data.AirportDB;
import data.FlightDB;
import data.PassengerDB;
import data.ReservationDB;
import data.TicketDB;
import model.Airport;
import model.Flight;
import model.Passenger;
import model.Reservation;
import model.Ticket;

/**
 * This is a panel that contains all Reservation of the given passenger.
 * 
 * @author Jieun Lee (jieun212@uw.edu)
 * @version 12-05-2016
 */
public class ViewReservationPanel extends JPanel implements ActionListener {


	/** A serial number. */
	private static final long serialVersionUID = 9113771204176692172L;
	
	/** A content panel. */
	private JPanel myContentPanel;
	
	/** Buttons. */
	private JButton myViewAllBtn, myViewConfirmedBtn, myGetReservationsBtn;
	
	/** A reservation Table. */
	private JTable myReservationTable;
	
	/** A scroll pnel for Reservatio table. */
	private JScrollPane myScrollPane;
	
	/** A dropdown combobox for year */
	private JComboBox<Object> myYearDropdown;
	
	/** A list of tickets. */
	private List<Ticket> myTicketList;
	
	/** Ticket data for Reservation table*/
	private Object[][] myTicketData;
	
	/** A Passenger Number. */
	private String myPaxNo;
	
	/** Column names for Reservation table. */
	private String[] myColumnName = { "Reserv No", "TKT No", "Flight No", "From", "To", "Departure", "Paid Price",
			"Confirmation" };
	

	/**
	 * Constructs ViewReservationPanel with given passenger number.
	 * 
	 * @param thePassengerNo
	 */
	public ViewReservationPanel(final String thePassengerNo) {
		myPaxNo = thePassengerNo;
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		setComponents();
		createYearComboBox();
		createComponent();
		setVisible(true);
	}
	
	/**
	 * Set buttons.
	 */
	private void setComponents() {
		myViewAllBtn = new JButton("View All");
		myViewAllBtn.addActionListener(this);
		myViewConfirmedBtn = new JButton("View Comfirmed");
		myViewConfirmedBtn.addActionListener(this);
		myGetReservationsBtn = new JButton("Search");
		myGetReservationsBtn.addActionListener(this);
	}
	
	/**
	 * Create a combo box for year.
	 */
	private void createYearComboBox() {
		Date theDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		Integer[] years = new Integer[11];
		int startyr = Integer.parseInt(formatter.format(theDate)) - 9; 
		years[0] = startyr;
		for (int i = 1; i < 11; i++) {
			years[i] = years[0] + i;
		}
		myYearDropdown = new JComboBox<Object>(years);
		myYearDropdown.setSelectedIndex(9);
	}
	
	private JPanel createImagePanel() {
		final JPanel panel = new JPanel(new BorderLayout());

		// image label
		final JLabel imageLabel = new JLabel();
		imageLabel.setBackground(Color.WHITE);
		imageLabel.setIcon(
				new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/viewreservation.jpeg"))));
		panel.add(imageLabel, BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * Create components.
	 */
	private void createComponent() {
		final JPanel paxPanel = new JPanel();
		paxPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		
		final JLabel paxLabel = new JLabel();
		paxLabel.setFont(MainFrame.ASR_BOLD_FONT);

		Passenger pax = null;
		try {
			pax = PassengerDB.getPassenger(myPaxNo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (pax.getMiddleName() == null || pax.getMiddleName().length() < 1) {
			paxLabel.setText("Passenger No. " + pax.getPassengerNo() + ",  " + pax.getFirstName() + " " + pax.getLastName());
		} else {
			paxLabel.setText("Passenger No. " + pax.getPassengerNo() + ",  " + pax.getFirstName() + " " + pax.getMiddleName()
					+ " " + pax.getLastName());
		}
		paxLabel.setPreferredSize(new Dimension(paxLabel.getPreferredSize().width, paxLabel.getPreferredSize().height * 2));	
		paxPanel.add(paxLabel);

		myContentPanel = new JPanel(new BorderLayout());
		myContentPanel.setBackground(Color.WHITE);
		myContentPanel.add(createButtonPanel(), BorderLayout.NORTH);
		myContentPanel.add(createImagePanel(), BorderLayout.CENTER);

		add(paxPanel, BorderLayout.NORTH);
		add(myContentPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Create button panel.
	 * 
	 * @return A button panel.
	 */
	private JPanel createButtonPanel() {
		final JPanel buttonPanel = new JPanel(new GridLayout(0,6));
		buttonPanel.setBackground(MainFrame.ASR_DARKBLUE);
		final JLabel label = new JLabel(" View reservations in ");
		label.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		label.setForeground(Color.WHITE);
		buttonPanel.add(label);
		buttonPanel.add(myYearDropdown);
		buttonPanel.add(myGetReservationsBtn);
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(myViewAllBtn);
		buttonPanel.add(myViewConfirmedBtn);
		
		return buttonPanel;
	}
	
	/**
	 * Create JScrollPane for Reservation table.
	 * 
	 * @param thePassengerNo
	 * @param theViewColumn
	 * @param theViewKey
	 * @param theOrderBy
	 * @return A Reservatio table scroll panel.
	 */
	private JScrollPane createTable(String thePassengerNo, final String theViewColumn, final String theViewKey, final String theOrderBy) {

		getData(thePassengerNo, theViewColumn, theViewKey, theOrderBy);
		myReservationTable = new JTable(myTicketData, myColumnName);
		myReservationTable.setRowHeight(22);
		myScrollPane = new JScrollPane(myReservationTable);
		myScrollPane.setSize(new Dimension(myReservationTable.getSize().width * 2 , (myReservationTable.getPreferredSize().height) * 3/2));

		return myScrollPane;
	}
	
	
	/**
	 * Listen a button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == myViewAllBtn) {
			myContentPanel.removeAll();
			myContentPanel.add(createButtonPanel(), BorderLayout.NORTH);
			myContentPanel.add(createTable(myPaxNo, null, null, "t.Reservation_no"), BorderLayout.CENTER);
			myContentPanel.revalidate();
			this.repaint();
		} else if (e.getSource() == myViewConfirmedBtn) {
			myContentPanel.removeAll();
			myContentPanel.add(createButtonPanel(), BorderLayout.NORTH);
			myContentPanel.add(createTable(myPaxNo,"r.paid_confirm", "CONF", "t.Reservation_no"), BorderLayout.CENTER);
			myContentPanel.revalidate();
			this.repaint();

		} else if (e.getSource() == myGetReservationsBtn) {
			String year_str = myYearDropdown.getSelectedItem().toString();
			int year = Integer.parseInt(year_str);
			String yearSql = " BETWEEN '" + year + "-01-01' AND '" + year + "-12-31'";
			myContentPanel.removeAll();
			myContentPanel.add(createButtonPanel(), BorderLayout.NORTH);
			myContentPanel.add(createTable(myPaxNo, "f.Departure_date", yearSql, "t.Reservation_no"), BorderLayout.CENTER);
			myContentPanel.revalidate();
			this.repaint();
		} 
	}
	
	
	/**
	 * Get data for Reservation table.
	 * 
	 * @param thePassengerNo
	 * @param theViewColumn
	 * @param theViewKey
	 * @param theOrderBy
	 */
	private void getData(String thePassengerNo, final String theViewColumn, final String theViewKey, final String theOrderBy) {
		try {
			myTicketList = TicketDB.getTicketsOfPassenger(thePassengerNo, theViewColumn, theViewKey, theOrderBy);
			myTicketData = new Object[myTicketList.size()][myColumnName.length];
			for (int i = 0; i < myTicketList.size(); i++) {
				Ticket ticket = myTicketList.get(i);
				Reservation reservation = ReservationDB.getReservationOf(ticket.getReservationNo());
				Flight flight = FlightDB.getFlightOf(ticket.getFlightNo());
				Airport departure = AirportDB.getAirport(flight.getDepartureAirport());
				Airport arrival = AirportDB.getAirport(flight.getArrivalAirport());
				myTicketData[i][0] = ticket.getReservationNo();
				myTicketData[i][1] = ticket.getTicketNo();
				myTicketData[i][2] = ticket.getFlightNo();
				myTicketData[i][3] = flight.getDepartureAirport() + "-" + departure.getCity() + ", " + departure.getCountry();
				myTicketData[i][4] = flight.getArrivalAirport() + "-" + arrival.getCity() + ", " + arrival.getCountry();
				myTicketData[i][5] = flight.getDepartureDate();
				myTicketData[i][6] = reservation.getPaidPrice();
				myTicketData[i][7] = reservation.getPaidConfirm();
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
