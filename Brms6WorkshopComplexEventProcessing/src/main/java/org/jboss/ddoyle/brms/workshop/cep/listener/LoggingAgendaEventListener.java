package org.jboss.ddoyle.brms.workshop.cep.listener;
import java.util.List;

import org.jboss.ddoyle.brms.workshop.cep.model.Event;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.AgendaGroupPoppedEvent;
import org.kie.api.event.rule.AgendaGroupPushedEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleFlowGroupDeactivatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class LoggingAgendaEventListener implements AgendaEventListener {

	private Logger LOGGER = LoggerFactory.getLogger(LoggingAgendaEventListener.class);
	
	@Override
	public void afterMatchFired(AfterMatchFiredEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void agendaGroupPopped(AgendaGroupPoppedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void agendaGroupPushed(AgendaGroupPushedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeMatchFired(BeforeMatchFiredEvent beforeMatchFiredEvent) {
		StringBuilder logLineBuilder = new StringBuilder("Before Match Fired.");
		logLineBuilder.append("Rule: ").append(beforeMatchFiredEvent.getMatch().getRule().getName());
		
		List<Object> matchedObjects = beforeMatchFiredEvent.getMatch().getObjects();
		for (Object nextMatchedObject: matchedObjects) {
			if (nextMatchedObject instanceof Event) {
				logLineBuilder.append("Event: ").append(((Event) nextMatchedObject).toString()).append(".");
			}
		}
		LOGGER.info(logLineBuilder.toString());
		
	}

	@Override
	public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void matchCancelled(MatchCancelledEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void matchCreated(MatchCreatedEvent matchCreatedEvent) {
		StringBuilder logLineBuilder = new StringBuilder("Match Created.");
		logLineBuilder.append("Rule: ").append(matchCreatedEvent.getMatch().getRule().getName());
		List<Object> matchedObjects = matchCreatedEvent.getMatch().getObjects();
		for (Object nextMatchedObject: matchedObjects) {
			if (nextMatchedObject instanceof Event) {
				logLineBuilder.append("Event: ").append(((Event) nextMatchedObject).toString());//.append(". Timestamp: ").append(((SimpleEvent) nextMatchedObject).getTimestamp());
			}
		}
		LOGGER.info(logLineBuilder.toString());
	}

}
