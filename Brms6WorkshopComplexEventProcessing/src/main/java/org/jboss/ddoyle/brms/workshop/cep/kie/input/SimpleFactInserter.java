package org.jboss.ddoyle.brms.workshop.cep.kie.input;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.ddoyle.brms.workshop.cep.kie.session.SessionProducer;
import org.jboss.ddoyle.brms.workshop.cep.model.Fact;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.api.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Responsible for inserting facts into the engine's working memory.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
@ApplicationScoped
public class SimpleFactInserter implements FactInserter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleFactInserter.class);
	
	@Inject
	private SessionProducer sessionProducer;
	
	private KieSession kSession;
	
	public SimpleFactInserter() {
	}
	
	@PostConstruct
	private void postConstruct() {
		kSession = sessionProducer.getKieSession();
	}
	
	public FactHandle insert(final Fact fact) {
		return insert(DEFAULT_STREAM, fact);
	}
	
	public FactHandle insert(String stream, Fact fact) {
		LOGGER.info("Inserting fact with id: '" + fact.getId() + "' into stream: " + stream);
		EntryPoint ep = getkSession().getEntryPoint(stream);
		return ep.insert(fact);
	}

	public KieSession getkSession() {
		return kSession;
	}

	public void setkSession(KieSession kSession) {
		this.kSession = kSession;
	}

}
