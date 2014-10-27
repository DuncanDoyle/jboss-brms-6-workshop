package org.jboss.ddoyle.brms.workshop.cep.kie.session;

/**
 * <code>MBean</code> interface for BRMS Session Management.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public interface SessionManagerMBean {

	public abstract void saveKieSession(String fileName);
	
	public abstract void startSession();
	
	public abstract void haltSession();
	
	public abstract void stopSession();
	
	public abstract void startEventListeners();
	
	public abstract void stopEventListeners();
	
}
