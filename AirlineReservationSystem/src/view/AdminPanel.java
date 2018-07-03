package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import data.AircraftDB;
import data.AirportDB;
import data.FlightDB;
import data.MealDB;
import model.Aircraft;
import model.Airport;
import model.Flight;
import model.Meal;

/**
 * This is a panel is for view, add, and update flight, meal, aircraft, and airports.
 * 
 * @author Jieun Lee (jieun212@uw.edu)
 * @version 12-05-2016
 */
public class AdminPanel extends JPanel implements ActionListener {

	public static final int TEXT_FILED_SIZE = 25;

	private static final long serialVersionUID = -8311802340241162267L;

	private JPanel myContentPanel;
	private JTable myFlightTable, myCraftTable, myMealTable, myAirportTable;
	private JScrollPane myFlightSPane, myCraftSPane, myMealSPane, myAirportSPane;
	private JComboBox<Object> myFlightDropdown, myCraftDropdown, myDepartureDropdown, myArrivalDropdown,
			myFlightEditDropdown; // , myMealDropdown;

	private JButton myFlightListButton, myFlightAddButton, myFlightEditButton, myCraftListButton, myCraftAddButton,
			myCraftEditButton, myMealListButton, myMealAddButton, myAirportListButton, myGobackButton,
			myAddFlightButton, myEditFlightButton, myAddCraftButton, myEditCraftButton, myAddMealButton,
			myAvailableCraftButton;

	private List<Flight> myFlightList;
	private List<Aircraft> myCraftList;
	private List<Meal> myMealList;
	private List<Airport> myAirportList;
	private Object[][] myFlightData, myCraftData, myMealData, myAirportData;
	private String[] myFlightColumn = { "Flight No", "Tail No", "From", "Departure_date", "Departure_time",
			"Departure_gate", "To", "Arrival_date", "Arrival_time", "Arrival_gate", "Base_price" };
	private String[] myCraftColumn = { "Tail No", "Craft No", "Capacity" };
	private String[] myMealColumn = { "Meal Code", "Meal Name" };
	private String[] myAirportColumn = { "Airport Code", "City", "Country" };
	private JTextField myAddDDateTxtf, myAddDTimeTxtf, myAddDGateTxtf, myAddADateTxtf, myAddATimeTxtf, myAddAGateTxtf,
			myAddPriceTxtf, myAddCraftTxtf, myAddCapacityTxtf, myAddMealCodeTxtf, myAddMealNameTxtf, myEditFlightTxtf,
			myEditCraftTxtf;

	/*
	 * Constructor
	 */
	public AdminPanel() {
		setLayout(new BorderLayout());
		// setBackground(MainFrame.ASR_DARKBLUE);
		setBackground(Color.WHITE);

		getFlightData(null);
		getCraftData(null, null, null, null);
		getMealData();
		getAirportData(null);

		createComponent();
		setVisible(true);
	}

	/*
	 * Create Components
	 */
	private void createComponent() {
		// create all buttons and add action listener
		List<JButton> buttonList = new ArrayList<JButton>();

		myFlightListButton = new JButton("View Flights");
		buttonList.add(myFlightListButton);
		myFlightAddButton = new JButton("Add Flight");
		buttonList.add(myFlightAddButton);
		myFlightEditButton = new JButton("Edit Flight");
		buttonList.add(myFlightEditButton);
		myCraftListButton = new JButton("View Aircrafts");
		buttonList.add(myCraftListButton);
		myCraftAddButton = new JButton("Add Aircraft");
		buttonList.add(myCraftAddButton);
		myCraftEditButton = new JButton("Edit Aircraft");
		buttonList.add(myCraftEditButton);
		myMealListButton = new JButton("View Meals");
		buttonList.add(myMealListButton);
		myMealAddButton = new JButton("Add Meal");
		buttonList.add(myMealAddButton);
		myAirportListButton = new JButton("View Airports");
		buttonList.add(myAirportListButton);
		myGobackButton = new JButton("Back to Admin");
		buttonList.add(myGobackButton);
		myAddFlightButton = new JButton("Add Flight");
		buttonList.add(myAddFlightButton);
		myEditFlightButton = new JButton("Edit Flight");
		buttonList.add(myEditFlightButton);
		myAddCraftButton = new JButton("Add Aircraft");
		buttonList.add(myAddCraftButton);
		myEditCraftButton = new JButton("EditAircraft");
		buttonList.add(myEditCraftButton);
		myAddMealButton = new JButton("Add Meal");
		buttonList.add(myAddMealButton);
		myAvailableCraftButton = new JButton("Select Aircraft");
		buttonList.add(myAvailableCraftButton);

		for (JButton b : buttonList) {
			b.addActionListener(this);
		}

		final JPanel buttonPanel = new JPanel(new GridLayout(4, 6));
		buttonPanel.setBackground(Color.WHITE);
		
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(new JLabel("FLIGHT"));
		buttonPanel.add(myFlightListButton);
		buttonPanel.add(myFlightAddButton);
		buttonPanel.add(myFlightEditButton);
		buttonPanel.add(new JLabel(""));

		buttonPanel.add(new JLabel(""));
		buttonPanel.add(new JLabel("AIRCRAFT"));
		buttonPanel.add(myCraftListButton);
		buttonPanel.add(myCraftAddButton);
		buttonPanel.add(myCraftEditButton);
		buttonPanel.add(new JLabel(""));

		buttonPanel.add(new JLabel(""));
		buttonPanel.add(new JLabel("MEAL"));
		buttonPanel.add(myMealListButton);
		buttonPanel.add(myMealAddButton);
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(new JLabel(""));

		buttonPanel.add(new JLabel(""));
		buttonPanel.add(new JLabel("AIRPORT"));
		buttonPanel.add(myAirportListButton);



		// create content panle and add the panel into this
		myContentPanel = new JPanel(new BorderLayout());
		myContentPanel.setBackground(Color.WHITE);
		myContentPanel.add(createImagePanel(), BorderLayout.CENTER);

		add(buttonPanel, BorderLayout.NORTH);
		add(myContentPanel, BorderLayout.CENTER);
	}

	private JPanel createImagePanel() {
		final JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.WHITE);
		// image label
		final JLabel imageLabel = new JLabel();
		imageLabel.setBackground(Color.WHITE);
		imageLabel.setIcon(
				new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/adminmap.png"))));
		panel.add(imageLabel, BorderLayout.SOUTH);
		return panel;
	}

	private JPanel createListPanel(String theTableName) {
		final JPanel listPanel = new JPanel(new BorderLayout());
		listPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		final JPanel panel = new JPanel();
		panel.setBackground(MainFrame.ASR_LIGHTBLUE);

		final JLabel titleLabel = new JLabel();
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		if (theTableName.equalsIgnoreCase("flight")) {
			titleLabel.setText("Flight List");
			getFlightData(null);
			myFlightTable = new JTable(myFlightData, myFlightColumn);
			myFlightTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			myFlightSPane = new JScrollPane(myFlightTable);
			Dimension d = myFlightTable.getPreferredSize();
			myFlightSPane.setPreferredSize(new Dimension(d.width + 10, myFlightTable.getRowHeight() * (30)));
			panel.add(myFlightSPane);

		} else if (theTableName.equalsIgnoreCase("aircraft")) {
			titleLabel.setText("Aircraft List");
			getCraftData(null, null, null, null);
			myCraftTable = new JTable(myCraftData, myCraftColumn);
			myCraftSPane = new JScrollPane(myCraftTable);
			panel.add(myCraftSPane);

		} else if (theTableName.equalsIgnoreCase("meal")) {
			titleLabel.setText("Meal List");
			getMealData();
			myMealTable = new JTable(myMealData, myMealColumn);
			myMealSPane = new JScrollPane(myMealTable);
			panel.add(myMealSPane);

		} else { // airport
			titleLabel.setText("Airport List");
			getAirportData(null);
			myAirportTable = new JTable(myAirportData, myAirportColumn);
			myAirportSPane = new JScrollPane(myAirportTable);
			Dimension d = myAirportTable.getPreferredSize();
			myAirportSPane.setPreferredSize(new Dimension(d.width * 3, myAirportTable.getRowHeight() * (30)));
			panel.add(myAirportSPane);
		}
		final JPanel titlePanel = new JPanel();
		titlePanel.setBackground(MainFrame.ASR_DARKBLUE);
		titlePanel.add(titleLabel);

		final JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		buttonPanel.add(myGobackButton);

		listPanel.add(titlePanel, BorderLayout.NORTH);
		listPanel.add(panel, BorderLayout.CENTER);
		listPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		return listPanel;
	}

	private JPanel createAddPanel(String theTableName) {
		final JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(MainFrame.ASR_DARKBLUE);
		
		final JLabel titleLabel = new JLabel();
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		
		final JPanel inputPanel;
		final JPanel addPanel;

		final JPanel buttonPanel = new JPanel();
		buttonPanel.add(myGobackButton);

		
		if (theTableName.equalsIgnoreCase("flight")) {
			inputPanel = new JPanel();
			addPanel = new JPanel(new GridLayout(24, 0));

			titleLabel.setText("Add Flight");
			myAddDDateTxtf = new JTextField(TEXT_FILED_SIZE);
			myAddDTimeTxtf = new JTextField(TEXT_FILED_SIZE);
			myAddDGateTxtf = new JTextField(TEXT_FILED_SIZE);
			myAddADateTxtf = new JTextField(TEXT_FILED_SIZE);
			myAddATimeTxtf = new JTextField(TEXT_FILED_SIZE);
			myAddAGateTxtf = new JTextField(TEXT_FILED_SIZE);
			myAddPriceTxtf = new JTextField(TEXT_FILED_SIZE);

			// add exept flight no(auto increment)
			createAirportDropdown("departure");
			addPanel.add(new JLabel(" " + myFlightColumn[1]));
			addPanel.add(createAircraftDropdown());
			addPanel.add(new JLabel(" " + myFlightColumn[2]));
			addPanel.add(myDepartureDropdown);
			addPanel.add(new JLabel(" " + myFlightColumn[3] + " (Format yyyy-mm-dd)"));
			addPanel.add(myAddDDateTxtf);
			addPanel.add(new JLabel(" " + myFlightColumn[4] + " (Format hh:mm:ss)"));
			addPanel.add(myAddDTimeTxtf);

			addPanel.add(new JLabel(" " + myFlightColumn[6]));
			createAirportDropdown("arrival");
			addPanel.add(myArrivalDropdown);
			addPanel.add(new JLabel(" " + myFlightColumn[7] + " (Format yyyy-mm-dd)"));
			addPanel.add(myAddADateTxtf);
			addPanel.add(new JLabel(" " + myFlightColumn[8] + " (Format hh:mm:ss)"));
			addPanel.add(myAddATimeTxtf);
			addPanel.add(new JLabel(" " + myFlightColumn[10]));
			addPanel.add(myAddPriceTxtf);

			buttonPanel.add(myAddFlightButton);

		} else if (theTableName.equalsIgnoreCase("aircraft")) {
			inputPanel = new JPanel();
			addPanel = new JPanel(new GridLayout(4, 0));

			titleLabel.setText("Add Aircraft");
			myAddCraftTxtf = new JTextField(TEXT_FILED_SIZE);
			myAddCapacityTxtf = new JTextField(TEXT_FILED_SIZE);
			addPanel.add(new JLabel("" + myCraftColumn[1]));
			addPanel.add(myAddCraftTxtf);
			addPanel.add(new JLabel("" + myCraftColumn[2]));
			addPanel.add(myAddCapacityTxtf);

			buttonPanel.add(myAddCraftButton);

		} else {
			inputPanel = new JPanel();
			addPanel = new JPanel(new GridLayout(4, 0));

			titleLabel.setText("Add Meal");
			myAddMealCodeTxtf = new JTextField(TEXT_FILED_SIZE);
			myAddMealNameTxtf = new JTextField(TEXT_FILED_SIZE);
			addPanel.add(new JLabel(myMealColumn[0]));
			addPanel.add(myAddMealCodeTxtf);
			addPanel.add(new JLabel(myMealColumn[1]));
			addPanel.add(myAddMealNameTxtf);

			buttonPanel.add(myAddMealButton);
		}
		addPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		buttonPanel.setBackground(MainFrame.ASR_LIGHTBLUE);


		inputPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		inputPanel.add(addPanel, BorderLayout.NORTH);

		final JPanel titlePanel = new JPanel();
		titlePanel.setBackground(MainFrame.ASR_DARKBLUE);
		titlePanel.add(titleLabel);
		
		panel.add(titlePanel, BorderLayout.NORTH);
		panel.add(inputPanel, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		return panel;
	}

	private JPanel createEditPanel(String theTableName) {
		final JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(MainFrame.ASR_DARKBLUE);
		
		final JLabel titleLabel = new JLabel("");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		// button panel
		final JPanel buttonPanel = new JPanel();

		myGobackButton = new JButton("Back to Admin");
		myGobackButton.addActionListener(this);

		final JPanel addPanel;
		if (theTableName.equalsIgnoreCase("flight")) {
			addPanel = new JPanel(new GridLayout(6, 0));
			titleLabel.setText("Edit Flight");

			Object[] flights = new Object[5];
			flights[0] = myFlightColumn[4];
			flights[1] = myFlightColumn[5];
			flights[2] = myFlightColumn[8];
			flights[3] = myFlightColumn[9];
			flights[4] = myFlightColumn[10];

			if (flights != null) {
				myFlightEditDropdown = new JComboBox<Object>(flights);
				if (flights.length < 1) {
					myFlightEditDropdown.insertItemAt("No flights to be updated!", 0);
				}
				myFlightEditDropdown.setSelectedIndex(0);
			}

			myEditFlightTxtf = new JTextField(TEXT_FILED_SIZE);
			addPanel.add(new JLabel("Select a Flight"));
			addPanel.add(createFlightDropdown()); // add flight dropdown
			addPanel.add(new JLabel(""));
			addPanel.add(new JLabel("Update"));
			addPanel.add(myFlightEditDropdown); // add flight edit dropdown
			addPanel.add(myEditFlightTxtf);

			myEditFlightButton = new JButton("Edit Flight");
			myEditFlightButton.addActionListener(this);
			buttonPanel.add(myEditFlightButton);

		} else {
			titleLabel.setText("Edit Aircraft");

			addPanel = new JPanel(new GridLayout(5, 0));
			myEditCraftTxtf = new JTextField(TEXT_FILED_SIZE);
			getCraftData(null, null, null, null);
			addPanel.add(new JLabel("Select a Aircraft"));
			addPanel.add(createAircraftDropdown()); // add aircraft dropdown
			addPanel.add(new JLabel(""));
			addPanel.add(new JLabel("Capacity")); // only capacity can be changed
			addPanel.add(myEditCraftTxtf);

			myEditCraftButton = new JButton("Edit Aircraft");
			myEditCraftButton.addActionListener(this);
			buttonPanel.add(myEditCraftButton);
		}
		addPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		buttonPanel.setBackground(MainFrame.ASR_LIGHTBLUE);

		final JPanel inputPanel = new JPanel();
		inputPanel.setBackground(MainFrame.ASR_LIGHTBLUE);
		inputPanel.add(addPanel, BorderLayout.NORTH);

		final JPanel titlePanel = new JPanel();
		titlePanel.setBackground(MainFrame.ASR_DARKBLUE);
		titlePanel.add(titleLabel);
		
		panel.add(titlePanel, BorderLayout.NORTH);
		panel.add(inputPanel, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		return panel;
	}

	private JPanel createAvailableAircraftPanel() {

		getCraftData(myAddDDateTxtf.getText(), myAddDTimeTxtf.getText(), myAddADateTxtf.getText(),
				myAddATimeTxtf.getText());

		final JPanel panel = new JPanel(new BorderLayout());
		final JLabel titleLabel = new JLabel("Choose available aircraft");
		panel.add(titleLabel, BorderLayout.NORTH);
		panel.add(createAircraftDropdown(), BorderLayout.CENTER);
		panel.add(myGobackButton, BorderLayout.SOUTH);

		return panel;
	}

	private JComboBox<Object> createAircraftDropdown() {

		Object[] crafts = new Object[myCraftList.size()];
		for (int i = 0; i < myCraftList.size(); i++) {
			crafts[i] = myCraftList.get(i).getTailNo() + ", Craft No:" + myCraftList.get(i).getCraftNo()
					+ ", Capacity: " + myCraftList.get(i).getCapacity();
		}
		if (crafts != null) {
			myCraftDropdown = new JComboBox<Object>(crafts);
			if (crafts.length < 1) {
				myCraftDropdown.insertItemAt("No aircraft available!", 0);
			}
			myCraftDropdown.setSelectedIndex(0);
		}
		return myCraftDropdown;
	}

	private void createAirportDropdown(String theAirport) {

		JComboBox<Object> airportDropdown = null;
		Object[] airports = new Object[myAirportList.size()];
		for (int i = 0; i < myAirportList.size(); i++) {
			airports[i] = myAirportList.get(i).getAirport() + ", " + myAirportList.get(i).getCity() + ", "
					+ myAirportList.get(i).getCountry();
		}
		if (airports != null) {
			airportDropdown = new JComboBox<Object>(airports);
			if (airports.length < 1) {
				airportDropdown.insertItemAt("No airport available!", 0);
			}
			airportDropdown.setSelectedIndex(0);
		}

		if (theAirport.equalsIgnoreCase("departure")) {
			myDepartureDropdown = airportDropdown;
		} else {
			myArrivalDropdown = airportDropdown;
		}
	}

	private JComboBox<Object> createFlightDropdown() {
		getFlightData(null);
		Object[] flights = new Object[myFlightList.size()];
		for (int i = 0; i < myFlightList.size(); i++) {
			flights[i] = myFlightList.get(i).getFlightNo() + ", From: " + myFlightList.get(i).getDepartureAirport()
					+ ", To: " + myFlightList.get(i).getArrivalAirport() + ", Departure: "
					+ myFlightList.get(i).getDepartureDate() + " " + myFlightList.get(i).getDepartureTime();
		}
		if (flights != null) {
			myFlightDropdown = new JComboBox<Object>(flights);
			if (flights.length < 1) {
				myFlightDropdown.insertItemAt("No flights to be updated!", 0);
			}
			myFlightDropdown.setSelectedIndex(0);
		}
		return myFlightDropdown;
	}

	/*
	 * Perform action
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == myFlightListButton) {
			myContentPanel.removeAll();
			myContentPanel.add(createListPanel("flight"));
			myContentPanel.revalidate();
			this.repaint();

		} else if (e.getSource() == myFlightAddButton) {
			myContentPanel.removeAll();
			myContentPanel.add(createAddPanel("flight"));
			myContentPanel.revalidate();
			this.repaint();

		} else if (e.getSource() == myFlightEditButton) {
			myContentPanel.removeAll();
			myContentPanel.add(createEditPanel("flight"));
			myContentPanel.revalidate();
			this.repaint();

		} else if (e.getSource() == myCraftListButton) {
			myContentPanel.removeAll();
			myContentPanel.add(createListPanel("aircraft"));
			myContentPanel.revalidate();
			this.repaint();

		} else if (e.getSource() == myCraftAddButton) {
			myContentPanel.removeAll();
			myContentPanel.add(createAddPanel("aircraft"));
			myContentPanel.revalidate();
			this.repaint();

		} else if (e.getSource() == myCraftEditButton) {
			myContentPanel.removeAll();
			myContentPanel.add(createEditPanel("aircraft"));
			myContentPanel.revalidate();
			this.repaint();

		} else if (e.getSource() == myMealListButton) {
			myContentPanel.removeAll();
			myContentPanel.add(createListPanel("meal"));
			myContentPanel.revalidate();
			this.repaint();

		} else if (e.getSource() == myMealAddButton) {
			myContentPanel.removeAll();
			myContentPanel.add(createAddPanel("meal"));
			myContentPanel.revalidate();
			this.repaint();

		} else if (e.getSource() == myAirportListButton) {
			myContentPanel.removeAll();
			myContentPanel.add(createListPanel("airport"));
			myContentPanel.revalidate();
			this.repaint();

		} else if (e.getSource() == myGobackButton) {
			myContentPanel.removeAll();
			myContentPanel.add(createImagePanel());
			myContentPanel.revalidate();
			this.repaint();

		} else if (e.getSource() == myAvailableCraftButton) {
			myContentPanel.removeAll();
			myContentPanel.add(createAvailableAircraftPanel());
			myContentPanel.revalidate();
			this.repaint();

		} else if (e.getSource() == myAddFlightButton) {
			performAddFlight();

		} else if (e.getSource() == myEditFlightButton) {
			performEditFlight();

		} else if (e.getSource() == myAddCraftButton) {
			performAddCraft();

		} else if (e.getSource() == myEditCraftButton) {
			performEditCraft();

		} else if (e.getSource() == myAddMealButton) {
			performAddMeal();
		}
	}

	private void performAddMeal() {

		// get added data
		String meal_code = myAddMealCodeTxtf.getText();
		if (meal_code == null || meal_code.length() < 1) {
			JOptionPane.showMessageDialog(null, "Enter a meal code");
			return;
		} else
			try {
				if (MealDB.hasMealCode(meal_code)) {
					JOptionPane.showMessageDialog(null, "The input Meal code exists in Meal DB!");
					return;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		String meal_name = myAddMealNameTxtf.getText();
		if (meal_name == null || meal_name.length() < 1) {
			JOptionPane.showMessageDialog(null, "Enter the name of the meal code");
			return;
		}

		// create aircraft, if added success return message
		Meal meal = new Meal(meal_code, meal_name);

		try {
			String message = "Adding meal is failed. Try again";
			if (!MealDB.addMeal(meal)) {
				JOptionPane.showMessageDialog(null, message);
				return;
			} else {
				message = "Meal is added successfully.";
				JOptionPane.showMessageDialog(null, message);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// clear text fields
		myAddMealCodeTxtf.setText("");
		myAddMealNameTxtf.setText("");
	}

	private void performEditCraft() {
		// get text fileds
		System.out.println(myCraftDropdown.getSelectedIndex());
		String selected = myCraftDropdown.getSelectedItem().toString();
		if (selected == null) {
			JOptionPane.showMessageDialog(null, "Select an aircraft to be updated");
			return;
		}
		String tail_no = selected.substring(0, selected.indexOf(','));

		try {
			Aircraft aircraft = AircraftDB.getAircraft(tail_no);

			String data = myEditCraftTxtf.getText();
			if (data == null || Integer.valueOf(data) < 1) {
				JOptionPane.showMessageDialog(null, "Enter a capacity (cannot be negative)");
				return;
			}

			String message = "Updating Aircraft No. " + tail_no + " is failed";
			if (AircraftDB.updateAircraft(aircraft, "Capacity", data).startsWith("Updated Aircraft Successfully")) {
				message = "Flight No. " + tail_no + " is updated successfully";
			}
			JOptionPane.showMessageDialog(null, message);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// clear text fields and dropdown list.
		myCraftDropdown.setSelectedIndex(0);
		myEditCraftTxtf.setText("");
	}

	private void performAddCraft() {

		// get added data
		String craft_no = myAddCraftTxtf.getText();
		if (craft_no == null) {
			JOptionPane.showMessageDialog(null, "Enter an aircraft number");
			return;
		}

		String capacity = myAddCapacityTxtf.getText();
		if (capacity == null || Integer.valueOf(capacity) < 1) {
			JOptionPane.showMessageDialog(null, "Enter a capacity (cannot be negative)");
			return;
		}

		// create aircraft, if added success return message
		Aircraft aircraft = new Aircraft(craft_no, capacity);

		try {
			String message = "Adding an aricraft is failed. Try again";
			if (AircraftDB.addAircraft(aircraft).startsWith("Aircraft added Successfully")) {
				message = "Aircraft is added successfully.";
			}
			JOptionPane.showMessageDialog(null, message);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// clear text fields
		myAddCraftTxtf.setText("");
		myAddCapacityTxtf.setText("");
	}

	private void performEditFlight() {

		// get text fileds
		String selected = myFlightDropdown.getSelectedItem().toString();
		if (selected == null) {
			JOptionPane.showMessageDialog(null, "Select a flight to be updated");
			return;
		}
		String flight_no = selected.substring(0, selected.indexOf(','));

		try {
			Flight flight = FlightDB.getFlightOf(flight_no);
			String column = myFlightEditDropdown.getSelectedItem().toString();
			if (column == null) {
				JOptionPane.showMessageDialog(null, "Select an item to be updated");
			}

			String data = myEditFlightTxtf.getText();

			String message = "Updating flight No. " + flight_no + " is failed";
			if (FlightDB.updateFlight(flight, column, data).startsWith("Updated Flight Successfully")) {
				message = "Flight No. " + flight_no + " is updated successfully";
			}
			JOptionPane.showMessageDialog(null, message);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// clear text fields and dropdown list.
		myFlightDropdown.setSelectedIndex(0);
		myFlightEditDropdown.setSelectedIndex(0);
		myEditFlightTxtf.setText("");

	}

	private void performAddFlight() {

		// get added data
		String tail_no = myCraftDropdown.getSelectedItem().toString();
		tail_no = tail_no.substring(0, tail_no.indexOf(','));
		if (tail_no == null) {
			JOptionPane.showMessageDialog(null, "Select an aircraft tail number");
			return;
		}

		String dep_airport = myDepartureDropdown.getSelectedItem().toString();
		dep_airport = dep_airport.substring(0, dep_airport.indexOf(','));
		if (dep_airport == null) {
			JOptionPane.showMessageDialog(null, "Select a departure airport");
			return;
		}

		String dep_gate = myAddDGateTxtf.getText();

		String arr_airport = myArrivalDropdown.getSelectedItem().toString();
		arr_airport = arr_airport.substring(0, arr_airport.indexOf(','));
		if (arr_airport == null) {
			JOptionPane.showMessageDialog(null, "Select an arrival airport");
			return;
		}

		String arr_gate = myAddAGateTxtf.getText();

		String base_price_str = myAddPriceTxtf.getText();
		double price = 0.0;
		if (base_price_str.length() != 0) {
			try {
				price = Double.parseDouble(base_price_str);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Enter price as decimal");
				myAddPriceTxtf.setText("");
				myAddPriceTxtf.setFocusable(true);
				return;
			}
		}

		try {

			String dep_date_str = myAddDDateTxtf.getText();
			if (dep_date_str.length() != 10) {
				JOptionPane.showMessageDialog(null, "The date format: yyyy-mm-dd");
				return;
			}
			java.util.Date dep_date = MainFrame.DATE_FORMAT.parse(dep_date_str);
			java.sql.Date sqlDep_date = new Date(dep_date.getTime());

			String dep_time_str = myAddDTimeTxtf.getText();
			if (dep_time_str.length() != 8) {
				JOptionPane.showMessageDialog(null, "The time format: hh:mm:ss");
				return;
			}
			long dep_time = MainFrame.TIME_FORMAT.parse(dep_time_str).getTime();
			Time sqlDep_time = new Time(dep_time);

			String arr_date_str = myAddADateTxtf.getText();
			if (arr_date_str.length() != 10) {
				JOptionPane.showMessageDialog(null, "The date format: yyyy-mm-dd");
				return;
			}
			java.util.Date arr_date = MainFrame.DATE_FORMAT.parse(arr_date_str);
			java.sql.Date sqlArr_date = new Date(arr_date.getTime());

			String arr_time_str = myAddATimeTxtf.getText();
			if (arr_time_str.length() != 8) {
				JOptionPane.showMessageDialog(null, "The time format: hh:mm:ss");
				return;
			}
			long arr_time = MainFrame.TIME_FORMAT.parse(arr_time_str).getTime();
			Time sqlArr_time = new Time(arr_time);

			if (FlightDB.hasFlight(dep_airport, sqlDep_date, sqlDep_time, arr_airport)) {
				JOptionPane.showMessageDialog(null,
						"There exists a flight which contains same departure airport, same departure date/time, and same destination. ");
				return;
			}

			// create flight, if added success return message
			Flight flight = new Flight(tail_no, dep_airport, sqlDep_date, sqlDep_time, dep_gate, arr_airport,
					sqlArr_date, sqlArr_time, arr_gate, price);

			String message = "Flight added failed. Try again";
			if (FlightDB.addFlight(flight).startsWith("Flight added Successfully")) {
				message = "Flight added successfully.";
			}
			JOptionPane.showMessageDialog(null, message);

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (HeadlessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// repaint with first page

		// clear text fields
		myCraftDropdown.setSelectedIndex(0);
		myDepartureDropdown.setSelectedIndex(0);
		myAddDDateTxtf.setText("");
		myAddDTimeTxtf.setText("");
		myAddDGateTxtf.setText("");
		myArrivalDropdown.setSelectedIndex(0);
		myAddADateTxtf.setText("");
		myAddATimeTxtf.setText("");
		myAddAGateTxtf.setText("");
		myAddPriceTxtf.setText("");
	}

	/* Get Data */

	private void getFlightData(final Date theDepartureFrom) {

		try {
			if (theDepartureFrom != null) {
				myFlightList = FlightDB.getFlightsFrom(theDepartureFrom);
			} else {
				myFlightList = FlightDB.getAllFlights();
			}

			if (myFlightList != null) {
				// { "Flight No", "Tail No", "From", "Departure Date", "Time",
				// "Gate", "To",
				// "Arrival Date", "Time", "Gate", "Base Price" };
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

	private void getCraftData(final String theDepDate, final String theDepTime, final String theArrDate,
			final String theArrTime) {

		try {
			if (theDepDate != null && theDepTime != null && theArrDate != null && theArrTime != null) {
				myCraftList = AircraftDB.getAircraftOf(theDepDate, theDepTime, theArrDate, theArrTime);
			} else {
				myCraftList = AircraftDB.getAllAircrafts();
			}

			if (myCraftList != null) {
				// { "Tail No", "Craft No", "Capacity" };
				myCraftData = new Object[myCraftList.size()][myCraftColumn.length];
				for (int i = 0; i < myCraftList.size(); i++) {
					myCraftData[i][0] = myCraftList.get(i).getTailNo();
					myCraftData[i][1] = myCraftList.get(i).getCraftNo();
					myCraftData[i][2] = myCraftList.get(i).getCapacity();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getMealData() {

		try {
			myMealList = MealDB.getAllMeals();

			if (myMealList != null) {
				// { "Meal Code", "Meal Name" };
				myMealData = new Object[myMealList.size()][myMealColumn.length];
				for (int i = 0; i < myMealList.size(); i++) {
					myMealData[i][0] = myMealList.get(i).getMealCode();
					myMealData[i][1] = myMealList.get(i).getMealName();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getAirportData(final String theCity) {

		try {
			if (theCity != null) {
				myAirportList = AirportDB.getAirportsInCity(theCity);
			} else {
				myAirportList = AirportDB.getAllAirports();
			}
			if (myAirportList != null) {
				// { "Airport Code", "City", "Country" };
				myAirportData = new Object[myAirportList.size()][myAirportColumn.length];
				for (int i = 0; i < myAirportList.size(); i++) {
					myAirportData[i][0] = myAirportList.get(i).getAirport();
					myAirportData[i][1] = myAirportList.get(i).getCity();
					myAirportData[i][2] = myAirportList.get(i).getCountry();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
