package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 * Run Airline Reservation System program
 * 
 * @author Jieun Lee (jieun212@uw.ede)
 *
 */
public class MainFrame extends JFrame {

	/*
	 * colors
	 */
	public static Color ASR_DARKBLUE = new Color(96, 129, 171);
	public static Color ASR_BLUE = new Color(157, 175, 196);
	public static Color ASR_LIGHTBLUE = new Color(202, 215, 227);

	public static Font ASR_BOLD_FONT = new Font("Lucida Grande", Font.BOLD, 16);

	public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm");
	public static DateTimeFormatter LOCAL_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	/**
	 * A serial number 
	 */
	private static final long serialVersionUID = 5879666289605001764L;

	/**
	 * A ToolKit.
	 */
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();

	/**
	 * The Dimension of the screen.
	 */
	private static final Dimension SCREEN_SIZE = KIT.getScreenSize();

	public MainFrame() {
		super("AIRLINE RESERVATION SYSTEM (Jieun Lee)");

		createComponents();

		pack();
		setBackground(Color.WHITE);
		setSize(1300, 720);
		setLocation(SCREEN_SIZE.width / 2 - getWidth() / 2, (int) (SCREEN_SIZE.getHeight() / 4 - getHeight() / 4));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void createComponents() {

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBackground(ASR_DARKBLUE);
		tabbedPane.setForeground(Color.WHITE);

		JComponent search = makeTextPanel("Search Flights");
		tabbedPane.addTab("Search Flights", search);

		JComponent viewReservation = makeTextPanel("View My Reservations");
		tabbedPane.addTab("View My Reservations", viewReservation);

		JComponent viewTickets = makeTextPanel("View Tickets");
		tabbedPane.addTab("View Tickets", viewTickets);

		JComponent admin = makeTextPanel("Admin");
		tabbedPane.addTab("Admin", admin);

		tabbedPane.setSize(800, 500);

		tabbedPane.setUI((new BasicTabbedPaneUI() {
			@Override
			protected void installDefaults() {
				super.installDefaults();
				highlight = ASR_BLUE;
				lightHighlight = ASR_LIGHTBLUE;
				shadow = ASR_BLUE;
				darkShadow = ASR_DARKBLUE;
				focus = ASR_DARKBLUE;
			}
		}));

		// add all panels to the frame
		add(createWestPanel(), BorderLayout.WEST);
		add(tabbedPane, BorderLayout.CENTER);

	}

	private JComponent makeTextPanel(String type) {

		if (type.equalsIgnoreCase("Search Flights")) {
			return new SearchPanel();

		} else if (type.equalsIgnoreCase("View My Reservations")) {
			return new ViewReservationPanel(SearchPanel.DEFAULT_PAX_NO);

		} else if (type.equalsIgnoreCase("View Tickets")) {
			return new ViewFlightPanel();

		} else {
			return new AdminPanel();
		}

	}

	private JPanel createWestPanel() {

		final JPanel westPanel = new JPanel(new BorderLayout());

		// logo panel
		final JPanel westLogoPanel = new JPanel(new BorderLayout());
		westLogoPanel.setBackground(Color.WHITE);
		final JLabel logoLabel = new JLabel();
		logoLabel.setBackground(Color.WHITE);
		logoLabel.setIcon(
				new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/logo.png"))));
		westLogoPanel.add(logoLabel);

		// login panel

		final JPanel westLoginWPanel = new JPanel();
		final JPanel westLoginEPanel = new JPanel();
		final JPanel westLoginCPanel = new JPanel(new BorderLayout());
		westLoginWPanel.setBackground(Color.WHITE);
		westLoginEPanel.setBackground(Color.WHITE);
		westLoginCPanel.setBackground(Color.WHITE);

		// radio buttons
		final JPanel westLoginRbuttonPanel = new JPanel();
		westLoginRbuttonPanel.setBackground(Color.WHITE);
		final JRadioButton memberRbtn = new JRadioButton("member");
		final JRadioButton employeeRbtn = new JRadioButton("employee");
		final JRadioButton adminRbtn = new JRadioButton("admin");

		final ButtonGroup loginRbtnGroup = new ButtonGroup();
		loginRbtnGroup.add(memberRbtn);
		loginRbtnGroup.add(employeeRbtn);
		loginRbtnGroup.add(adminRbtn);

		memberRbtn.setSelected(true);
		westLoginRbuttonPanel.add(memberRbtn);
		westLoginRbuttonPanel.add(employeeRbtn);
		westLoginRbuttonPanel.add(adminRbtn);

		// txt fields
		final JPanel westLoginTxtfPanel = new JPanel(new BorderLayout());

		final JPanel idPanel = new JPanel();
		idPanel.setBackground(ASR_LIGHTBLUE);
		final JPanel pwPanel = new JPanel();
		pwPanel.setBackground(ASR_LIGHTBLUE);
		final JLabel idLabel = new JLabel("ID");
		final JLabel pwLabel = new JLabel("PW");
		final JTextField idTxtf = new JTextField(10);
		final JTextField pwTxtf = new JTextField(10);

		// set disable because this is not the requirement of this project
		idTxtf.setEnabled(false);
		pwTxtf.setEnabled(false);
		idPanel.add(idLabel);
		idPanel.add(idTxtf);
		pwPanel.add(pwLabel);
		pwPanel.add(pwTxtf);
		final JPanel txtfPanel = new JPanel(new BorderLayout());
		txtfPanel.add(idPanel, BorderLayout.NORTH);
		txtfPanel.add(pwPanel, BorderLayout.CENTER);

		final JPanel btnfPanel = new JPanel();
		btnfPanel.setBackground(Color.WHITE);
		final JLabel loginLabel = new JLabel(); // login button
		logoLabel.setBackground(Color.WHITE);
		loginLabel.setIcon(
				new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/login.png"))));

		final JLabel registerLabel = new JLabel(); // register button
		registerLabel.setBackground(Color.WHITE);
		registerLabel.setIcon(
				new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/register.jpg"))));

		btnfPanel.add(registerLabel);
		btnfPanel.add(loginLabel);

		westLoginTxtfPanel.add(westLoginRbuttonPanel, BorderLayout.NORTH);
		westLoginTxtfPanel.add(txtfPanel, BorderLayout.CENTER);
		westLoginTxtfPanel.add(btnfPanel, BorderLayout.SOUTH);

		westLoginCPanel.add(westLoginTxtfPanel, BorderLayout.NORTH);

		final JPanel westLoginPanel = new JPanel(new BorderLayout());
		westLoginPanel.setBackground(Color.WHITE);
		westLoginPanel.add(westLoginWPanel, BorderLayout.WEST);
		westLoginPanel.add(westLoginCPanel, BorderLayout.CENTER);
		westLoginPanel.add(westLoginEPanel, BorderLayout.EAST);

		// copyright panel
		final JPanel westCopyPanel = new JPanel();
		westCopyPanel.setBackground(Color.WHITE);
		final JLabel copyrightLabel = new JLabel(); // register button
		registerLabel.setBackground(Color.WHITE);
		copyrightLabel.setIcon(
				new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/copyright.png"))));

		westCopyPanel.add(copyrightLabel);

		westPanel.add(westLogoPanel, BorderLayout.NORTH);
		westPanel.add(westLoginPanel, BorderLayout.CENTER);
		westPanel.add(westCopyPanel, BorderLayout.SOUTH);

		return westPanel;
	}

}
