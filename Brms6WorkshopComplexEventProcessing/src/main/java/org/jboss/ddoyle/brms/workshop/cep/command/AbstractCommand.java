package org.jboss.ddoyle.brms.workshop.cep.command;

/**
 * Base implementation class for all {@link Command} implementations.
 * <p/>
 * Provides the command id.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public abstract class AbstractCommand implements Command {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private final String id;
	
	public AbstractCommand(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}
	

}
