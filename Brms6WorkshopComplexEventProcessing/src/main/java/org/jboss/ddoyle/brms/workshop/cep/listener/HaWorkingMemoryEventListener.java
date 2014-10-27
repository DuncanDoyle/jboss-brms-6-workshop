package org.jboss.ddoyle.brms.workshop.cep.listener;

import javax.enterprise.context.ApplicationScoped;

import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Currently a simple {@link WorkingMemoryEventListener} that just logs the WorkingMemory events.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
@ApplicationScoped
public class HaWorkingMemoryEventListener implements RuleRuntimeEventListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(HaWorkingMemoryEventListener.class);

	@Override
	public void objectUpdated(ObjectUpdatedEvent event) {
		LOGGER.info("Object updated: " + event.getObject().hashCode());
	}

	@Override
	public void objectInserted(ObjectInsertedEvent event) {
		LOGGER.info("Object inserted: " + event.getObject().hashCode());
	}

	@Override
	public void objectDeleted(ObjectDeletedEvent event) {
		LOGGER.info("Object deleted: " + event.getOldObject());
	}

}
