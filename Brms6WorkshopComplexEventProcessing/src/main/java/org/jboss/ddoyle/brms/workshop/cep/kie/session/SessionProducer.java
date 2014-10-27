package org.jboss.ddoyle.brms.workshop.cep.kie.session;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.drools.core.FactHandle;
import org.jboss.ddoyle.brms.workshop.cep.channel.CommandDispatchChannel;
import org.jboss.ddoyle.brms.workshop.cep.command.SimpleCommandFactory;
import org.jboss.ddoyle.brms.workshop.cep.listener.HaWorkingMemoryEventListener;
import org.jboss.ddoyle.brms.workshop.cep.model.Fact;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CDI producer which produces the {@link KieSession} for this application.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
@ApplicationScoped
public class SessionProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(SessionProducer.class);

	private KieServices kieServices;

	private KieContainer kieContainer;

	private KieSession kieSession;

	private static final String SERIALIZED_KIE_SESSION_FILE_PROPERTY_NAME = "org.jboss.ddoyle.brms.kie.session.file";

	private static final String LOAD_KIE_SESSION_FROM_FILE_PROPERTY_NAME = "org.jboss.ddoyle.brms.kie.session.loadfromfile";

	private static final String GLOBAL_IDENTIFIER_COMMAND_FACTORY = "commandFactory";
	
	public SessionProducer() {
	}

	/**
	 * Depending on the mode that was used to start the app, this producer will create a new {@link KieSessio}, or load a previously
	 * persisted {@link KieSession} from disk.
	 * 
	 * @param context
	 * @param haWmEventListener
	 * @param commandDispatcherChannel
	 * @param commandFactory
	 */
	@Inject
	public SessionProducer(HaWorkingMemoryEventListener haWmEventListener,
			CommandDispatchChannel commandDispatcherChannel, SimpleCommandFactory commandFactory) {

		kieServices = KieServices.Factory.get();
		
		if (Boolean.parseBoolean(System.getProperty(LOAD_KIE_SESSION_FROM_FILE_PROPERTY_NAME))) {
			/*
			String kieSessionFile = System.getProperty(SERIALIZED_KIE_SESSION_FILE_PROPERTY_NAME);
			if (kieSessionFile == null || "".equals(kieSessionFile)) {
				String errorMessage = "The BRSM CEP HA system has been configured to load a serialized KieSession from a file, but no file has been specified. Either specify a file using the '"
						+ SERIALIZED_KIE_SESSION_FILE_PROPERTY_NAME
						+ "' system property or set '"
						+ LOAD_KIE_SESSION_FROM_FILE_PROPERTY_NAME + "' to 'false' to load a new KieSession fromr the KieContainer.";
				LOGGER.error(errorMessage);
				throw new IllegalStateException(errorMessage);
			}
			// We don't have or need a KieContainer when loading a serialized session.
			kieContainer = null;

			KieSessionLoader loader = new FileKieSessionLoader(new File(kieSessionFile));
			kieSession = loader.load();
			// Add event listeners.
			kieSession.addEventListener(haWmEventListener);
			// Set globals
			kieSession.setGlobal(GLOBAL_IDENTIFIER_COMMAND_FACTORY, commandFactory);
			// Register channels.
			kieSession.registerChannel("dossierAnalyseFacts", commandDispatcherChannel);

			// Some debug stuff:
			EntryPoint ep = kieSession.getEntryPoint("RHSummitStream");
			Collection<FactHandle> factHandles = ep.getFactHandles();
			LOGGER.info("FactHandles in loaded EntryPoint: " + factHandles.size());
			Iterator<FactHandle> factHandleIterator = factHandles.iterator();
			while (factHandleIterator.hasNext()) {
				FactHandle nextHandle = factHandleIterator.next();
				Fact nextFact = (Fact) ep.getObject(nextHandle);
				LOGGER.info("Fact: " + nextFact.getId()); // + ", " + nextFact.getTimestamp());
			}
			kieSession.fireAllRules();
		*/
		} else {

			LOGGER.debug("Bootstrapping JBoss BRMS KIE services.");
			kieContainer = kieServices.getKieClasspathContainer();
			kieSession = kieContainer.newKieSession();
			LOGGER.debug("KieSession: " + kieSession);
			// Add event listeners.
			kieSession.addEventListener(haWmEventListener);
			// Set globals
			kieSession.setGlobal(GLOBAL_IDENTIFIER_COMMAND_FACTORY, commandFactory);
			// Register channels.
			kieSession.registerChannel(CommandDispatchChannel.CHANNEL_ID, commandDispatcherChannel);
		}
	}

	public KieSession getKieSession() {
		return kieSession;
	}

}
