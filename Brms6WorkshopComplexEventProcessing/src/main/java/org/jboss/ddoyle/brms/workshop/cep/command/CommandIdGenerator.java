package org.jboss.ddoyle.brms.workshop.cep.command;


import org.kie.api.runtime.rule.Match;

/**
 * Generator component which generates unique IDs for {@link Command Commands} based on the rule {@link Match}. Utilizes the
 * {@link CommandBuilder} to generate the unique ID.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class CommandIdGenerator {

	public static String generateId(Match match) {
		CommandIdBuilder builder = new CommandIdBuilder();
		builder.addRulePackageName(match.getRule().getPackageName());
		builder.addRuleId(match.getRule().getId());
		builder.addRuleName(match.getRule().getName());
		builder.addFacts(match.getObjects());
		return builder.build();
	}

}
