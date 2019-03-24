package ifb.db3d.der6.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.text.MaskFormatter;

public class DateUtil {
	public static DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	public static DateFormat formatPG = new SimpleDateFormat("yyyy-MM-dd");
	
	public static MaskFormatter getMaskFormatter()
	{
		MaskFormatter mf = null;
		try {
			mf = new MaskFormatter("##/##/####");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return mf;
	}
	
	public static String formatDate(Date date)
	{
		return format.format(date);
	}
	
	public static Date parseDate(String string)
	{
		Date date = null;
		try {
			date = format.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date parseDateDB(String string)
	{
		Date date = null;
		try {
			date = formatPG.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
