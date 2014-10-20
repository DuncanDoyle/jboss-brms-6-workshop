package org.jboss.ddoyle.brms.workshop;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.ddoyle.brms.workshop.model.Event;
import org.jboss.ddoyle.brms.workshop.model.Event.EVENT_RATING;
import org.jboss.ddoyle.brms.workshop.model.EventPass;
import org.jboss.ddoyle.brms.workshop.model.Person;
import org.jboss.ddoyle.brms.workshop.model.Ticket;
import org.jboss.ddoyle.brms.workshop.model.Ticket.TICKET_CLASS;
import org.jboss.ddoyle.brms.workshop.model.TicketOffer;
import org.jboss.ddoyle.brms.workshop.model.TicketOffers;
import org.jboss.ddoyle.brms.workshop.model.Venue;
import org.joda.time.LocalDate;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;

public class RulesEngine {

	private KieContainer kieContainer;
	
	public void run(Person person, boolean hasEventPass) {
		KieServices kieServices = KieServices.Factory.get();
		kieContainer = kieServices.getKieClasspathContainer();
		runTicketRulesDemo(person, hasEventPass);
	}
		
	private List<Ticket> getAvailableTickets() {
		List<Ticket> tickets= new ArrayList<>();
		Venue ahoyRotterdam = new Venue("Ahoy", "Rotterdam");
		Venue kuipRotterdam = new Venue("Kuip",  "Rotterdam");
		Venue amsterdamArena = new Venue("Arena", "Amsterdam");
		
		Event pearlJam = new Event("Pearl Jam", EVENT_RATING.C);
		Event rhcp = new Event("Red Hat Chili Peppers", EVENT_RATING.C);
		Event feyenoordHeerenveen = new Event("Feyenoord - Heerenveen", EVENT_RATING.A);
		Event kamasutraBeurs = new Event("Kamasutra Beurs", EVENT_RATING.D);
		
		
		Ticket ticketPjAhoyBronze = new Ticket(pearlJam, ahoyRotterdam, new LocalDate(2014,11, 11), TICKET_CLASS.BRONZE, new BigDecimal(24.95));
		Ticket ticketPjAhoySilver = new Ticket(pearlJam, ahoyRotterdam, new LocalDate(2014,11, 11), TICKET_CLASS.SILVER, new BigDecimal(44.95));
		Ticket ticketPjAhoyGold = new Ticket(pearlJam, ahoyRotterdam, new LocalDate(2014,11, 11), TICKET_CLASS.GOLD, new BigDecimal(74.95));
		
		tickets.add(ticketPjAhoyBronze);
		tickets.add(ticketPjAhoySilver);
		tickets.add(ticketPjAhoyGold);
		
		Ticket ticketRhcpKuipBronze = new Ticket(rhcp, kuipRotterdam, new LocalDate(2015, 1, 30), TICKET_CLASS.BRONZE, new BigDecimal(19.95));
		Ticket ticketRhcpKuipSilver = new Ticket(rhcp, kuipRotterdam, new LocalDate(2015, 1, 30), TICKET_CLASS.SILVER, new BigDecimal(39.95));
		Ticket ticketRhcpKuipGold = new Ticket(rhcp, kuipRotterdam, new LocalDate(2015, 1, 30), TICKET_CLASS.GOLD, new BigDecimal(89.95));
		
		tickets.add(ticketRhcpKuipBronze);
		tickets.add(ticketRhcpKuipSilver);
		tickets.add(ticketRhcpKuipGold);
		
		Ticket ticketFhKuipBronze = new Ticket(feyenoordHeerenveen, kuipRotterdam, new LocalDate(2014, 12, 5), TICKET_CLASS.BRONZE, new BigDecimal(10.95));
		Ticket ticketFhKuipSilver = new Ticket(feyenoordHeerenveen, kuipRotterdam, new LocalDate(2014, 12, 5), TICKET_CLASS.SILVER, new BigDecimal(10.95));
		
		tickets.add(ticketFhKuipBronze);
		tickets.add(ticketFhKuipSilver);
		
		Ticket ksAhoyBronze = new Ticket(kamasutraBeurs, amsterdamArena, new LocalDate(2015, 2, 14), TICKET_CLASS.BRONZE, new BigDecimal(29.69));
		Ticket ksAhoySilver = new Ticket(kamasutraBeurs, amsterdamArena, new LocalDate(2015, 2, 14), TICKET_CLASS.SILVER, new BigDecimal(39.69));
		Ticket ksAhoyGold = new Ticket(kamasutraBeurs, amsterdamArena, new LocalDate(2015, 2, 14), TICKET_CLASS.SILVER, new BigDecimal(39.69));
		
		tickets.add(ksAhoyBronze);
		tickets.add(ksAhoySilver);
		tickets.add(ksAhoyGold);
		
		return(tickets);
		
	}
	
	private void runTicketRulesDemo(Person person, boolean hasEventPass ) {
		EventPass pass = null;
		if (hasEventPass) {
			//This is just a demo, so we dynamically create a pass with a randon number. Normall you would look it up.
			pass = new EventPass((int) Math.random(), person);
		}
		TicketOffers ticketOffers = getTicketOffers(person, pass);
		//output the available tickets.
		printTicketOffers(ticketOffers);
		
	}
	
	protected Map<String, Object> getGlobals() {
		return new HashMap<String, Object>();
	}
	
	private void printTicketOffers(TicketOffers ticketOffers) {
		for (TicketOffer nextOffer: ticketOffers.getTickets()) {
			System.out.println("TicketOffer: " + nextOffer + "\n");
		}
	}
	
	private TicketOffers getTicketOffers(final Person person) {
		return getTicketOffers(person, null);
	}
	
	private TicketOffers getTicketOffers(final Person person, final EventPass eventPass) {
		List<Ticket> tickets = getAvailableTickets(); 
		TicketOffers ticketOffers = new TicketOffers();
		
		KieSession ksession = kieContainer.newKieSession();
		
		ksession = decorateSession(ksession);
		
		//Insert global. This is an empty list,but will be filled with available ticket offers by the rules engine.
		ksession.setGlobal("ticketOffers", ticketOffers);
		Map<String, Object> globals = getGlobals();
		for (Map.Entry<String, Object> nextGlobal: globals.entrySet()) {
			ksession.setGlobal(nextGlobal.getKey(), nextGlobal.getValue());
		}
		
		//Insert the facts.
		ksession.insert(person);
		if (eventPass != null) {
			ksession.insert(eventPass);
		}
		for (Ticket nextTicket: tickets) {
			ksession.insert(nextTicket);
		}
		
		//Fire all the rules.
		ksession.fireAllRules(new AgendaFilter() {
			@Override
			public boolean accept(Match match) {
				if (match.getRule().getName().toLowerCase().endsWith("test")) {
					return false;
				} else {
					return true;
				}
			}
		});
		
		//Dispose the stateful knowledge session to clear references.
		ksession.dispose();
		
		return ticketOffers;
	}
	
	private KieSession decorateSession(final KieSession kieSession) {
		LoggingAgendaEventListener loggingListener = new LoggingAgendaEventListener();
		kieSession.addEventListener(loggingListener);
		return kieSession;
	}
	
	
}
