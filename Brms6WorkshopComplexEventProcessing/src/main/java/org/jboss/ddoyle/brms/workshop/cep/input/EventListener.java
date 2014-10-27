package org.jboss.ddoyle.brms.workshop.cep.input;

import java.io.File;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.ddoyle.brms.workshop.cep.eventproducer.FactsLoader;
import org.jboss.ddoyle.brms.workshop.cep.kie.input.FactInserter;
import org.jboss.ddoyle.brms.workshop.cep.kie.input.PseudoClock;
import org.jboss.ddoyle.brms.workshop.cep.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demo listener implementation which generates the events itself.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
@ApplicationScoped
public class EventListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventListener.class);

	@Inject @PseudoClock
	private FactInserter factInserter;

	public EventListener() {
	}
	
	@PostConstruct
	public void initialize() {
	}

	public void start() {
		
		String eventsFileUrl = this.getClass().getClassLoader().getResource("events.csv").getFile();
		File eventsFile = new File(eventsFileUrl);
		List<Event> events = FactsLoader.loadEvents(eventsFile);
		
		for (Event nextEvent: events) {
			factInserter.insert(nextEvent);
		}
	}
	
	public void stop() {
		//Stopping.
	}
}
