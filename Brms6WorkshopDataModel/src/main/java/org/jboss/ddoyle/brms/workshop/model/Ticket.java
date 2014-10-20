package org.jboss.ddoyle.brms.workshop.model;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.LocalDate;

public class Ticket {

	public static enum TICKET_CLASS {
		GOLD, SILVER, BRONZE
	}

	private final Event event;

	private final Venue venue;

	private final LocalDate date;

	private final TICKET_CLASS ticketClass;

	private final BigDecimal price;

	public Ticket(final Event event, final Venue venue, final LocalDate date, final TICKET_CLASS ticketClass, final BigDecimal price) {
		this.event = event;
		this.venue = venue;
		this.date = date;
		this.ticketClass = ticketClass;
		this.price = price;
	}

	public Event getEvent() {
		return event;
	}

	public Venue getVenue() {
		return venue;
	}

	public LocalDate getDate() {
		return date;
	}

	public TICKET_CLASS getTicketClass() {
		return ticketClass;
	}

	public BigDecimal getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("event", getEvent()).append("venue", getVenue()).append("date", getDate())
				.append("ticketClass", getTicketClass()).append("price", getPrice()).build();
	}

}
