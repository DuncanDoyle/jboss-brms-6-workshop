package org.jboss.ddoyle.brms.workshop.model;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * @author ddoyle
 *
 */
public class TicketOffer {

	private final Ticket ticket;
	private double discount;

	public TicketOffer(final Ticket ticket) {
		this(ticket, 0);
	}

	public TicketOffer(final Ticket ticket, final double discount) {
		this.ticket = ticket;
		this.discount = discount;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public void addDiscount(double discount) {
		this.discount = this.discount + discount;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public BigDecimal getPrice() {
		BigDecimal originalTicketPrice = getTicket().getPrice();
		double afterDiscount = 100 - getDiscount();
		BigDecimal price = new BigDecimal((originalTicketPrice.doubleValue() / 100) * afterDiscount);
		return price;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("ticket", getTicket()).append("discount", getDiscount()).append("price", getPrice())
				.build();
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
		TicketOffer ticketOffer = (TicketOffer) obj;
		//@formatter:off
		return new EqualsBuilder().appendSuper(super.equals(ticket))
				.append(getDiscount(), ticketOffer.getDiscount())
				.append(getPrice(), ticketOffer.getPrice())
				.append(getTicket(), ticketOffer.getTicket()).isEquals();
		//@formatter:on
	}

	@Override
	public int hashCode() {
		//@formatter:off
		return new HashCodeBuilder(17, 37).
				        append(getDiscount()).
				        append(getPrice()).
				        append(getTicket()).
				        toHashCode();
		//@formatter:on
	}

}
