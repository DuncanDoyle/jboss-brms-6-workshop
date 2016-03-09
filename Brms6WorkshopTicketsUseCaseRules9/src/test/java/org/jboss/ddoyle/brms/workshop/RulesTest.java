package org.jboss.ddoyle.brms.workshop;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.jboss.ddoyle.brms.workshop.model.Event;
import org.jboss.ddoyle.brms.workshop.model.Event.EVENT_RATING;
import org.jboss.ddoyle.brms.workshop.model.Person;
import org.jboss.ddoyle.brms.workshop.model.Ticket;
import org.jboss.ddoyle.brms.workshop.model.TicketOffer;
import org.jboss.ddoyle.brms.workshop.model.Venue;
import org.joda.time.LocalDate;
import org.junit.Ignore;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class RulesTest {
	
	@Test()
	//TODO: Remove the Ignore tag so the test gets executed.
	@Ignore
	public void testDiscountForNonAdultsRule() {
		KieServices kieServices = KieServices.Factory.get();
		
		KieContainer kieContainer = kieServices.getKieClasspathContainer();
		
		KieSession kieSession = kieContainer.newKieSession();
		
		Person person = new Person("Jason", "Doyle", new LocalDate(2010, 3, 11));
		
		Ticket ticket = new Ticket(new Event("Cool Even", EVENT_RATING.C), new Venue("Ahoy", "Rotterdam"), new LocalDate(), Ticket.TICKET_CLASS.SILVER, new BigDecimal("19.95"));
		TicketOffer ticketOffer = new TicketOffer(ticket);
		
		kieSession.insert(person);
		kieSession.insert(ticket);
		kieSession.insert(ticketOffer);
		
		//This execution will fail (actually, it will throw an NPE in the execution of the RHS of one of the rules).
		//TODO: Implement the logic to only test the "25% discount for non-adults" rule.
		kieSession.fireAllRules();
		
		assertEquals(25.0, ticketOffer.getDiscount(), 0.01);
	}

}
