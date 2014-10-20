package org.jboss.ddoyle.brms.workshop.model;

import java.util.ArrayList;
import java.util.List;

public class TicketOffers {
	
	private List<TicketOffer> ticketList = new ArrayList<>();

	public void addTicket(TicketOffer ticket) {
		ticketList.add(ticket);
	}
	
	public List<TicketOffer> getTickets() {
		return ticketList;
	}

}
