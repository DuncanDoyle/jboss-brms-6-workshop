package org.jboss.ddoyle.brms.workshop.cep.kie.session;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.jboss.ddoyle.brms.workshop.cep.input.EventListener;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages the BRMS CEP KieSession and exposes functionality as an MBean.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
@ApplicationScoped
public class SessionManager implements SessionManagerMBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(SessionManager.class);

	private static final String MBEAN_NAME = "org.jboss.ddoyle.brms.cep:type=SessionManager";

	private boolean sessionRunning = false;

	/*
	 * TODO: Currently, the Session we're managing gets injected. So, the SessionProducer is responsible for either creating a new session
	 * or loading an existing one from a file. However, the SessionManager is responsible for saving/persisting the session. Shouldn't the
	 * SessionManager therefore not be responsible for creating a new session or loading one from a file?
	 * 
	 * One thing to think of is if we want to do something like reloading a session (i.e. disposing the existing one and loading a new one
	 * from a file), we need to be able to 'inject' that new session into all the components that require it.
	 * 
	 * Wouldn't it therefore not be a better idea to not inject the session directly, but inject it via a container (or wrapper) so we can
	 * more easily change the session that all components access? Basically abstracting most of BRMS from our own components .....
	 * 
	 * On reload, we, for example, would also need to retrieve a new clock (when using the PseudoClock) and the new EntryPoints .....
	 * 
	 * Thinking of it, we actually migth be able to pull this off via custom CDI scopes and starting a new scope when we reload the session
	 * .... hmmmm, actually, that idea sounds rather nice ..... if it's possible that is .....
	 */

	private KieSession kieSession;

	// TODO: Should the EventListener be managed by this SessionManager ....?
	@Inject
	private EventListener eventListener;

	@Inject
	private SessionProducer sessionProducer;
	
	private static MBeanServer mbs;
	
	private ExecutorService executorService;
	
	private static final int numberOfThreads = 10;

	@PostConstruct
	private void postConstruct() {
		executorService = Executors.newFixedThreadPool(numberOfThreads);
		kieSession = sessionProducer.getKieSession();
		LOGGER.info("Registering SessionManager MBean.");
		try {
			MBeanServer mbs = getMBeanServer();
			ObjectName mbName = new ObjectName(MBEAN_NAME);
			if (!mbs.isRegistered(mbName)) {
				LOGGER.info("Registering SessionManagerMBean with platform MBeanServer.");
				mbs.registerMBean(this, mbName);
			}
		} catch (Exception e) {
			System.err.println("Unable to register SessionManager into the platform MBeanServer");
			e.printStackTrace();
		}
	}
	
	public KieSession getKieSession() {
		return kieSession;
	}
	
	
	// TODO Note that we currently only have one listener, so we only have to stop 1.
	/*
	 * TODO: For safety reasons, we've synchronized the entire method. Also, we should make sure that we lock all interaction with the
	 * SessionManager (actually with the session), and the EventListeners so that the session is not accidently restarted, or new facts
	 * being inserted while we persist the session.
	 */
	public synchronized void saveKieSession(String fileName) {
		File file = new File(fileName);
		// TODO: Might want to do some additional checks here whether we can actually create the file ...
		stopEventListeners();
		// We need a proper way to make sure that there are no facts being inserted anymore ....
		haltSession();
		// Persist the session ...
		try {
		KieSessionLoader loader = new FileKieSessionLoader(file);
		loader.save(kieSession);
		} catch (Throwable t) {
			LOGGER.error("Huh??? Throwable???: " + t);
		}

		// TODO: Start the KieSession fireUntilHallt in a new Thread
		startSession();
		// Start the EventListener start the EventListener in a new Thread.
		startEventListeners();
	}

	@Override
	public void startSession() {
		startKieSession(kieSession);
	}

	@Override
	public synchronized void haltSession() {
		if (sessionRunning) {
			kieSession.halt();
			sessionRunning = false;
		} else {
			LOGGER.warn("There is no running KieSession, nothing to halt.");
		}

	}

	private synchronized void startKieSession(KieSession kSession) {
		if (!sessionRunning) {
			LOGGER.info("Starting KieSession engine.");
			//Thread sessionThread = new Thread(new KieSessionRunnable(kSession));
			executorService.submit(new KieSessionRunnable(kSession));
			//sessionThread.start();
			sessionRunning = true;
		} else {
			LOGGER.warn("KieSession already running. Not starting it twice.");
		}
	}

	@Override
	public void stopSession() {
		LOGGER.info("Halting the KieSession.");
		haltSession();
		kieSession.dispose();
	}

	public void startEventListeners() {
		//Thread eventTopicListenerThread = new Thread(new EventListenerRunnable(eventListener));
		LOGGER.info("Starting EventListener.");
		executorService.submit(new EventListenerRunnable(eventListener));
		//eventTopicListenerThread.start();
	}

	public void stopEventListeners() {
		LOGGER.info("Stopping EventListener.");
		eventListener.stop();
	}

	/**
	 * Runs the CEP {@link KieSession#fireUntilHalt()} method which causes the {@link KieSession} to fire it's activations until the
	 * {@link KieSession} is halted.
	 * 
	 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
	 */
	public static class KieSessionRunnable implements Runnable {

		private static final Logger LOGGER = LoggerFactory.getLogger(KieSessionRunnable.class);

		private KieSession session;

		public KieSessionRunnable(KieSession session) {
			this.session = session;
		}

		public void run() {
			LOGGER.info("Session FireUntilHalt");
			// Start the engine
			try {
				session.fireUntilHalt();
			} catch (Throwable t) {
				LOGGER.error("Throwable!!!!", t);
			}
			LOGGER.info("Session FireUntilHalt stopped.");
		}
	}

	/**
	 * Runs the {@link EventListener} which listens for new events ({@link Facts} to be inserted into the BRMS CEP {@link KieSession}.
	 * 
	 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
	 */
	public static class EventListenerRunnable implements Runnable {
		private static final Logger LOGGER = LoggerFactory.getLogger(EventListenerRunnable.class);

		private EventListener eventListener;

		public EventListenerRunnable(EventListener eventListener) {
			this.eventListener = eventListener;
		}

		@Override
		public void run() {
			LOGGER.info("Starting Event Listener work.");
			eventListener.start();
		}
	}

	private static synchronized MBeanServer getMBeanServer() {
		if (mbs == null) {
			mbs = ManagementFactory.getPlatformMBeanServer();
		}
		return mbs;
	}

	public static ObjectName createObjectName(String name) {
		try {
			return new ObjectName(name);
		} catch (Exception e) {
			System.err.println("This is a bug. Error creating ObjectName for MBean: " + name);
			System.err.println("Please contact the development team and provide the following stack trace: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

}
