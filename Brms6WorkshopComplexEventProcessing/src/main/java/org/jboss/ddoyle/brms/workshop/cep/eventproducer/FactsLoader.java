package org.jboss.ddoyle.brms.workshop.cep.eventproducer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jboss.ddoyle.brms.workshop.cep.model.BagScannedEvent;
import org.jboss.ddoyle.brms.workshop.cep.model.BagTag;
import org.jboss.ddoyle.brms.workshop.cep.model.Event;
import org.jboss.ddoyle.brms.workshop.cep.model.Fact;
import org.jboss.ddoyle.brms.workshop.cep.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loads {@link Fact objects from the given (CSV) file. 
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class FactsLoader {

	private static final Logger LOGGER = LoggerFactory.getLogger(FactsLoader.class);

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd:HHmmssSSS");
	
	public static List<Event> loadEvents(File eventsFile) {

		List<Event> eventList = new ArrayList<Event>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(eventsFile));
			String nextLine;
			while ((nextLine = br.readLine()) != null) {
				if (!nextLine.startsWith("#")) {
					Event bagScanEvent = readEvent(nextLine);
					if (bagScanEvent != null) {
						eventList.add(bagScanEvent);
					}
				}
			}
		} catch (IOException ioe) {
			throw new RuntimeException("Got an IO exception while reading events.", ioe);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ioe) {
					// Swallowing exception, not much we can do here.
					LOGGER.error("Unable to close reader.", ioe);
				}
			}
		}
		return eventList;
	}

	/**
	 * Layout of a StockTick line has to be {uuid}, {timestamp}, {symbol}, {price}.
	 * 
	 * @param line
	 *            the line to parse.
	 * @return the {@link Fact}
	 */
	private static Event readEvent(String line) {
		String[] eventData = line.split(",");
		if (eventData.length != 4) {
			LOGGER.error("Unable to parse string: " + line);
		}
		Event event = null;
		try {
			BagTag tag = new BagTag(eventData[1].trim());
			event = new BagScannedEvent(eventData[0], tag, Location.valueOf(eventData[2].trim()), DATE_FORMAT.parse(eventData[3].trim()));
			
		} catch (NumberFormatException nfe) {
			LOGGER.error("Error parsing line: " + line, nfe);
		} catch (ParseException pe) {
			LOGGER.error("Error parsing line: " + line, pe);
		}
		return event;

	}

}
