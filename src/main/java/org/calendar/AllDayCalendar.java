package org.calendar;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class AllDayCalendar {
	
	public static java.util.Calendar calendar = java.util.Calendar.getInstance();
	public static net.fortuna.ical4j.model.Calendar cal = new net.fortuna.ical4j.model.Calendar();
	private static boolean offset;
	
	public AllDayCalendar(boolean pOffset) {

		offset = pOffset;
		//Initialize Calendar and all required properties
		cal = new Calendar();
		cal.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
		cal.getProperties().add(CalScale.GREGORIAN);
		cal.getProperties().add(Version.VERSION_2_0);
	}
	
	public static void create(Assignment hw) throws IOException, ValidationException {
		
		//Send data to a helper method to parse and add dates
		createAllDay(hw);

		// initialize as an all-day event..
		String eventName = hw.getClassName() + ": " + hw.getAssignmentName();
		VEvent event = new VEvent(new Date(calendar.getTime()), eventName);

		// Generate a UID for the event..
		//UidGenerator ug = new UidGenerator("1");
		//event.getProperties().add(ug.generateUid());

		UidGenerator ug = new UidGenerator("uidGen");
		Uid uid = ug.generateUid();
		event.getProperties().add(uid);

		//Add the event to the calendar
		cal.getComponents().add(event);
		System.out.println(cal);

	}
	
	public static void createAllDay(Assignment hw) throws IOException, ValidationException {
		
		//Take string inputs from assignment class and parse them
		String date = hw.getDueDate();
		String[] dates = date.split("/");
		
		//Used substring to get rid of comma after the date
		
		int DayOfMonth = Integer.parseInt(dates[1]);
		
		int year = Integer.parseInt(dates[2]);
	
		//Checks and sets the month appropriately
		if(dates[0].equalsIgnoreCase("01")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.JANUARY);
		} else if(dates[0].equalsIgnoreCase("02")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.FEBRUARY);
		} else if(dates[0].equalsIgnoreCase("03")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.MARCH);
		} else if(dates[0].equalsIgnoreCase("04")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.APRIL);
		} else if(dates[0].equalsIgnoreCase("05")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.MAY);
		} else if(dates[0].equalsIgnoreCase("06")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.JUNE);
		} else if(dates[0].equalsIgnoreCase("07")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.JULY);
		} else if(dates[0].equalsIgnoreCase("08")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.AUGUST);
		} else if(dates[0].equalsIgnoreCase("09")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.SEPTEMBER);
		} else if(dates[0].equalsIgnoreCase("10")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.OCTOBER);
		} else if(dates[0].equalsIgnoreCase("11")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.NOVEMBER);
		} else if(dates[0].equalsIgnoreCase("12")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.DECEMBER);
		}
		int numOffset;
		
		if(offset) {
			numOffset = 0;
		}
		else {
			numOffset = 1;
		}
	
		//sets date and year based on parsed values above
		calendar.set(java.util.Calendar.DAY_OF_MONTH, DayOfMonth - numOffset);
		calendar.set(java.util.Calendar.YEAR, year);
		
	}
	
	
	public static void outputAllDay(String filename) throws IOException, ValidationException {
		//outputs the data
		String home = System.getProperty("user.home");
		
		//creates the .ics file
		File f = new File(home + File.separator + "Desktop" + File.separator + filename + ".ics");
		
		FileOutputStream fout = new FileOutputStream(f);

		CalendarOutputter outputter = new CalendarOutputter();
		outputter.output(cal, fout);
		
		System.out.println("\nSuccess! Please check your Desktop for " + filename + ".ics" );
	}
	
	

}
