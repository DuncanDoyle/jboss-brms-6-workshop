package org.jboss.ddoyle.brms.workshop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.drools.builder.ResourceType;
import org.drools.core.command.impl.GenericCommand;
import org.drools.core.command.runtime.BatchExecutionCommandImpl;
import org.drools.core.command.runtime.rule.InsertObjectCommand;
import org.jboss.ddoyle.brms.workshop.model.Person;
import org.joda.time.LocalDate;
import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Simple example that shows the basic API of Drools and some of the more common rules {@link ResourceType ResourceTypes}. 
 *  
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	private static KieContainer kieContainer = null;
	
	/**
	 * Runs 4 Drools runs, one that shows a basics DRL, one with a DSL, and 2 that show a basic decision table.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		fireDrlRules();
		fireDslrRules();
		fireDecisionTableRules();
		fireDecisionTable2Rules();
	}
	
	/**
	 * Builds a <code>Drools</code> {@link KieContainer} from the resources found on the classpath.
	 * 
	 * @return a <code>Drools</code> {@link KieContainer}
	 */
	private static synchronized KieContainer getKieContainer() {
		if (kieContainer == null) {
			KieServices kieServices = KieServices.Factory.get();
			kieContainer = kieServices.getKieClasspathContainer();
		}
		
		return kieContainer;
	}
	
	
	
	private static void fireDrlRules() {
		Person duncan = new Person("Duncan", "Doyle", new LocalDate(1979, 1, 30));
		Person jason = new Person("Jason", "Doyle", new LocalDate(2010, 3, 11));
		
		StatelessKieSession ksession = getKieContainer().getKieBase("drlRules").newStatelessKieSession();
		logRules(ksession);
		
		List<GenericCommand<?>> commandList = new ArrayList<GenericCommand<?>>();
		commandList.add(new InsertObjectCommand(duncan));
		commandList.add(new InsertObjectCommand(jason));
		BatchExecutionCommand batchCommand = new BatchExecutionCommandImpl(commandList);
		
		ksession.execute(batchCommand);
	}
	
	
	private static void fireDslrRules() {
		
		Person duncan = new Person("Duncan", "Doyle", new LocalDate(1979, 1, 30));
		Person jason = new Person("Jason", "Doyle", new LocalDate(2010, 3, 11));
		
		StatelessKieSession ksession = getKieContainer().getKieBase("dslRules").newStatelessKieSession();
		
		List<GenericCommand<?>> commandList = new ArrayList<GenericCommand<?>>();
		commandList.add(new InsertObjectCommand(duncan));
		commandList.add(new InsertObjectCommand(jason));
		BatchExecutionCommand batchCommand = new BatchExecutionCommandImpl(commandList);
		
		ksession.execute(batchCommand);
	}
	
	private static void fireDecisionTableRules() {
		Person duncan = new Person("Duncan", "Doyle", new LocalDate(1979, 1, 30));
		Person jason = new Person("Jason", "Doyle", new LocalDate(2010, 3, 11));
		
		StatelessKieSession ksession = getKieContainer().getKieBase("spreadsheetRulesOne").newStatelessKieSession();
		
		List<GenericCommand<?>> commandList = new ArrayList<GenericCommand<?>>();
		commandList.add(new InsertObjectCommand(duncan));
		commandList.add(new InsertObjectCommand(jason));
		BatchExecutionCommand batchCommand = new BatchExecutionCommandImpl(commandList);
		
		ksession.execute(batchCommand);
	}
	
	private static void fireDecisionTable2Rules() {
		Person duncan = new Person("Duncan", "Doyle", new LocalDate(1979, 1, 30));
		Person jason = new Person("Jason", "Doyle", new LocalDate(2010, 3, 11));
		
		StatelessKieSession ksession = getKieContainer().getKieBase("spreadsheetRulesTwo").newStatelessKieSession();
		
		List<GenericCommand<?>> commandList = new ArrayList<GenericCommand<?>>();
		commandList.add(new InsertObjectCommand(duncan));
		commandList.add(new InsertObjectCommand(jason));
		BatchExecutionCommand batchCommand = new BatchExecutionCommandImpl(commandList);
		
		ksession.execute(batchCommand);
	}
	
	private static void logRules(KieSession kieSession) {
		logRules(kieSession.getKieBase().getKiePackages());
	}
	
	private static void logRules(StatelessKieSession kieSession) {
		logRules(kieSession.getKieBase().getKiePackages());
	}
	
	private static void logRules(Collection<KiePackage> kiePackages) {
		for(KiePackage nextPackage: kiePackages) {
			Collection<Rule> rules = nextPackage.getRules();
			for (Rule nextRule: rules) {
				LOGGER.info(("Rule: " + nextRule.getName()));
			}
		}
	}
}
