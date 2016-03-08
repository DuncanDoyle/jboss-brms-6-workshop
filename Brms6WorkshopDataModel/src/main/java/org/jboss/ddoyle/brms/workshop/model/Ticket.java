package org.jboss.ddoyle.brms.workshop.model;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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

	public Ticket(final Event event, final Venue venue, final LocalDate date, final TICKET_CLASS ticketClass,
			final BigDecimal price) {
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
		return new ToStringBuilder(this).append("event", getEvent()).append("venue", getVenue())
				.append("date", getDate()).append("ticketClass", getTicketClass()).append("price", getPrice()).build();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		Ticket ticket = (Ticket) obj;
		//@formatter:off
		return new EqualsBuilder().appendSuper(super.equals(ticket))
				.append(getEvent(), ticket.getEvent())
				.append(getVenue(), ticket.getVenue())
				.append(getDate(), ticket.getDate())
				.append(getTicketClass(), ticket.getTicketClass())
				.append(getPrice(), ticket.getPrice()).isEquals();
		//@formatter:on
	}
	
	@Override
	public int hashCode() {
		//@formatter:off
		return new HashCodeBuilder(7, 43).
				        append(getEvent()).
				        append(getVenue()).
				        append(getDate()).
				        append(getTicketClass()).
				        append(getPrice()).toHashCode();
		//@formatter:on
	}

}
