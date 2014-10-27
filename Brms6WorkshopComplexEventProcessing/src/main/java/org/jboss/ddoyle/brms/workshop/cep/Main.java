package org.jboss.ddoyle.brms.workshop.cep;

import org.jboss.ddoyle.brms.workshop.cep.commons.Util;
import org.jboss.ddoyle.brms.workshop.cep.input.EventListener;
import org.jboss.ddoyle.brms.workshop.cep.kie.session.SessionManager;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class of our JBoss BRMS High Available CEP engine.
 *  
 *  
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class Main {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	/**
	 * Weld CDI container.
	 */
	private static Weld weld;

	public static void main(String[] args) {
		LOGGER.info("Bootstrapping the system.");
		String clientId = System.getProperty("rhsummit2014.hornetq.client.id");
		LOGGER.info("Client ID: " + clientId);
		
		final long startTime = System.currentTimeMillis();

		// Bootstrap CDI
		weld = new Weld();
		WeldContainer weldContainer = weld.initialize();

		// Retrieve SessionManager from Weld CDI container so it's managed.
		SessionManager sessionManager = weldContainer.instance().select(SessionManager.class).get();
		
		Util.logRulesInSession(sessionManager.getKieSession());
		// Register the shutdown-hook and start the KieSession.
		//sessionManager.startSession();

		try {
			// TODO: Stupid wait to wait until session has properly started. Need to do this with some proper synchronization, but I'm lazy.
			Thread.sleep(1000);
		} catch (InterruptedException ie) {
			System.out.println("Thread got interupted." + ie);
			// Set the interrupt back on the thread.
			Thread.currentThread().interrupt();
		}
		sessionManager.startEventListeners();

		registerShutdownHook(sessionManager);

		final long totalBootTime = System.currentTimeMillis() - startTime;
		final long totalBootTimeInSeconds = totalBootTime / 1000;

		LOGGER.info("JBoss BRMS CEP Engine started sucessfully in " + totalBootTimeInSeconds + " seconds.");

	}

	public static void stopWeld(Weld weld) {
		weld.shutdown();
	}

	private static void registerShutdownHook(SessionManager sessionManager) {
		JVMShutdownHook jvmShutdownHook = new JVMShutdownHook(sessionManager);
		Runtime.getRuntime().addShutdownHook(jvmShutdownHook);
		LOGGER.info("JVM Shutdown Hook Registered.");
	}

	/**
	 * JVM Shutdown Hook which stops {@link Weld} and the {@link EventListener} when the system shuts down.
	 * 
	 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
	 */
	private static class JVMShutdownHook extends Thread {

		private static final Logger LOGGER = LoggerFactory.getLogger(JVMShutdownHook.class);

		private final SessionManager sessionManager;
		
		public JVMShutdownHook(SessionManager sessionManager) {
			this.sessionManager = sessionManager;
		}

		public void run() {
			LOGGER.info("Stopping the system.");

			/*
			 * TODO: We might want to wait until this listener has actually stopped before stopping the session (or we should make it the
			 * responsibility of the eventListener to only return after the it has actually stopped, which seems a better solution.
			 * Currently, the listener does not do this and returns immediately after setting the stop flag. Another idea could be to keep
			 * track of all the threads running the eventListeners and check whether they've all stopped before closing the KieSession).
			 */
			sessionManager.stopEventListeners();
			//sessionManager.stopSession();
			stopWeld(weld);

		}
	}

	/*
	 * private static KieSession getKieSessionViaMaven() { KieServices kieServices = KieServices.Factory.get(); KieContainer mavenKContainer
	 * = kieServices.newKieContainer(kieServices.newReleaseId("org.drools", "named-kiesession", "6.0.0-SNAPSHOT")); KieSession kSession =
	 * mavenKContainer.newKieSession("my-ksession-name"); }
	 */

}
