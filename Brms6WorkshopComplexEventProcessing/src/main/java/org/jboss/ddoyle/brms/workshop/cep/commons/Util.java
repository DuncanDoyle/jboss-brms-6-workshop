package org.jboss.ddoyle.brms.workshop.cep.commons;

import java.util.Collection;

import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Common utility methods.
 *  
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class Util {
	
	/**
	 * Logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);
	
	/**
	 * Utility method that logs the names of the rules in a specific package.
	 * 
	 * @param session
	 */
	public static void logRulesInSession(KieSession session) {
		Collection<KiePackage> kPackages = session.getKieBase().getKiePackages();
		LOGGER.info("Listing rules in session.");
		for (KiePackage nextPackage : kPackages) {
			Collection<Rule> rules = nextPackage.getRules();
			for (Rule nextRule : rules) {
				System.out.println("Rule: " + nextRule.getPackageName() + ":" + nextRule.getName());
			}
		}
	}

}
