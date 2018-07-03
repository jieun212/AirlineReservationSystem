package view;

/**
 * Runs Airline Reservation System by instantiating and starting the MainFrame.
 * 
 * @author Jieun Lee (jieun212@uw.edu)
 * @version 11-23-2016
 */
public class ARSMain {

	private ARSMain() {
		throw new IllegalStateException();
	}
	
	public static void main (final String[] theArgs) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override

            public void run() {
                new MainFrame();
            }
        });
	}
}
