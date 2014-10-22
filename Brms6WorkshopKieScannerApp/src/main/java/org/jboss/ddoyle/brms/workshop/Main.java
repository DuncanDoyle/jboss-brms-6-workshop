package org.jboss.ddoyle.brms.workshop;

import org.jboss.ddoyle.brms.workshop.model.Person;
import org.joda.time.LocalDate;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Main {

	public static void main(String[] args) {
		KieServices kieServices = KieServices.Factory.get();
		ReleaseId id = kieServices.newReleaseId("org.jboss.ddoyle.brms.workshop", "Brms6WorkshopKieScannerRules", "1.0.0-SNAPSHOT");
		KieContainer kieContainer = kieServices.newKieContainer(id);

		KieScanner scanner = kieServices.newKieScanner(kieContainer);
		scanner.start(5000);

		//Just keep looping to demonstrate that the KieContainer is reloaded when we change the rules.
		while (true) {
			KieSession ksession = kieContainer.newKieSession();
			Person person = new Person("Duncan", "Doyle", new LocalDate(1979,1,30));
			ksession.insert(person);
			ksession.fireAllRules();
			ksession.dispose();
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// reset the interrupt.
				Thread.currentThread().interrupt();
			}
		}

	}
}
