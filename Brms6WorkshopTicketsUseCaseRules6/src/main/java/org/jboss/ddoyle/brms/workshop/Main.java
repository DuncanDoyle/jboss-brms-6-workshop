package org.jboss.ddoyle.brms.workshop;

import org.jboss.ddoyle.brms.workshop.model.Person;
import org.joda.time.LocalDate;

public class Main extends RulesEngine{

	public static void main(String[] args) {
		Main main = new Main();
		main.run(new Person("Jason", "Doyle", new LocalDate(2011, 3, 11)), true);
	}
	
	
}