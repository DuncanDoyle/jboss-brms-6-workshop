package org.jboss.ddoyle.brms.workshop.cep.kie.session;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.drools.core.common.DroolsObjectInputStream;
import org.drools.core.common.DroolsObjectOutputStream;
import org.kie.api.KieBase;
import org.kie.api.marshalling.Marshaller;
import org.kie.api.marshalling.ObjectMarshallingStrategy;
import org.kie.api.marshalling.ObjectMarshallingStrategyAcceptor;
import org.kie.api.runtime.KieSession;
import org.kie.internal.marshalling.MarshallerFactory;

/**
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class KieSessionWrapper implements Serializable {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	private KieBase kieBase;

	private KieSession kieSession;

	public KieSessionWrapper(KieSession kieSession) {
		this.setKieBase(kieSession.getKieBase());
		this.setKieSession(kieSession);
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		//out.defaultWriteObject();

		// First write the KnowlegdeBase.
		DroolsObjectOutputStream droolsOut = new DroolsObjectOutputStream(out);
		droolsOut.writeObject(getKieBase());

		// Next, write the session.
		Marshaller marshaller = createSerializableMarshaller(getKieBase());
		marshaller.marshall(droolsOut, getKieSession());
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		//in.defaultReadObject();

		DroolsObjectInputStream droolsIn = new DroolsObjectInputStream(in);
		this.setKieBase((KieBase) droolsIn.readObject());

		Marshaller marshaller = createSerializableMarshaller(getKieBase());
		this.setKieSession(marshaller.unmarshall(droolsIn));

		/*
		 * TODO: Set the global variables when we read the KieSession.
		 * This is now done in the SessionProducer, so we might want to make that a static-util method.
		 */
		//kSession.setGlobal(GLOBAL_IDENTIFIER_CONTEXT, context);
		//kSession.setGlobal(GLOBAL_IDENTIFIER_COMMAND_FACTORY, commandFactory);
	}

	private Marshaller createSerializableMarshaller(KieBase kBase) {
		ObjectMarshallingStrategyAcceptor acceptor = MarshallerFactory.newClassFilterAcceptor(new String[] { "*.*" });
		ObjectMarshallingStrategy strategy = MarshallerFactory.newSerializeMarshallingStrategy(acceptor);
		Marshaller marshaller = MarshallerFactory.newMarshaller(kBase, new ObjectMarshallingStrategy[] { strategy });
		return marshaller;
	}

	public KieBase getKieBase() {
		return kieBase;
	}

	private void setKieBase(KieBase kieBase) {
		this.kieBase = kieBase;
	}

	public KieSession getKieSession() {
		return kieSession;
	}

	private void setKieSession(KieSession kieSession) {
		this.kieSession = kieSession;
	}

}
