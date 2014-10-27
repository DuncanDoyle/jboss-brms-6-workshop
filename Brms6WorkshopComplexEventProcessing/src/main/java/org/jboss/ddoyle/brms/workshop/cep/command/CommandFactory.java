package org.jboss.ddoyle.brms.workshop.cep.command;

import org.drools.core.spi.KnowledgeHelper;

/**
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public interface CommandFactory {

	public abstract SystemOutCommand getSystemOutCommand(String message, KnowledgeHelper kHelper);
	
}
