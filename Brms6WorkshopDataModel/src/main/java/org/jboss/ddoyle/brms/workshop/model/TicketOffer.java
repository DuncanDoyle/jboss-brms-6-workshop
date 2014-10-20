package org.jboss.ddoyle.brms.workshop.model;

import java.math.BigDecimal;

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
		BigDecimal price = new BigDecimal ((originalTicketPrice.doubleValue() / 100) * afterDiscount);
		return price;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("ticket", getTicket()).append("discount", getDiscount()).append("price", getPrice()).build();
	}
	
}
