package org.jboss.ddoyle.brms.workshop.cep.command;


/**
 * Command which prints its message to the <code>System.out</code> when executed.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class SystemOutCommand extends AbstractCommand {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	private final String message;
	
	public SystemOutCommand(String id, String message) {
		super(id);
		this.message = message;
	}
	
	/**
	 * Prints the {@link #message} via <code>System.out</code>.
	 * 
	 * @return <code>null</code>
	 */
	@Override
	public Object execute() {
		System.out.println("Command-ID: " + getId() + "\nMessage: " + message);
		return null;
	}
	

}
