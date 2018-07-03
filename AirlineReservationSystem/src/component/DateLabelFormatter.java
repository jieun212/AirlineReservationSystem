package component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField.AbstractFormatter;

/**
 * This class is a Formatter for Date label.
 * 
 * @author Unknown
 * {@link} http://stackoverflow.com/questions/26794698/how-do-i-implement-jdatepicker
 */
public class DateLabelFormatter extends AbstractFormatter {

    /**
	 * A serial number.
	 */
	private static final long serialVersionUID = -407459969380553612L;
	
	/** A date pattern */
	private String datePattern = "yyyy-MM-dd";
	
	/** A Date Format */
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }

}