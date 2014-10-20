package org.jboss.ddoyle.brms.workshop;

import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.joda.time.LocalDate;

import org.jboss.ddoyle.brms.workshop.model.Person;

public class Main extends RulesEngine{

	public static void main(String[] args) {
		Main main = new Main();
		main.run(new Person("Jason", "Doyle", new LocalDate(2011, 3, 11)), true);
	}
	
	@Override
	protected void addResources(KnowledgeBuilder kbuilder) {
		kbuilder.add(ResourceFactory.newClassPathResource("tickets-ruleflow.bpmn2"), ResourceType.BPMN2);
	}
	
	
}