package org.jboss.ddoyle.brms.workshop.cep.command;

import javax.enterprise.context.ApplicationScoped;

import org.drools.core.spi.KnowledgeHelper;
import org.kie.api.runtime.rule.Match;

/**
 * Factory implementation which creates {@link Command} objects.
 * <p/>
 * This factory can be used in rules to create the various {@link Command} implementations. It will take care of computing the unique id of
 * the {@link Command}, which will be used x-engine to identify the same commands. The injected {@link CommandIdGenerator} is responsible
 * for generating the ids from the Drools {@link Match}.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
@ApplicationScoped
public class SimpleCommandFactory implements CommandFactory {

	public SystemOutCommand getSystemOutCommand(String message, KnowledgeHelper kHelper) {
		return new SystemOutCommand(CommandIdGenerator.generateId(kHelper.getMatch()), message);
	}

}