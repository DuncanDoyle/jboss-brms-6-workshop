package org.jboss.ddoyle.brms.workshop.cep.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleCommandDispatcher implements CommandDispatcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleCommandDispatcher.class);
	
	@Override
	public void dispatch(Command command) {
		LOGGER.info("Dispatching Command: " + command);
		command.execute();
		
	}

}
