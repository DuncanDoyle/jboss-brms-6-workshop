package org.jboss.ddoyle.brms.workshop.cep.kie.input;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.ddoyle.brms.workshop.cep.model.Fact;
import org.kie.api.runtime.rule.FactHandle;

/**
 * Responsible for inserting {@link Fact Facts} into the JBoss BRMS CEP Session.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
@ApplicationScoped
public interface FactInserter {
	
	public static final String DEFAULT_STREAM = "RHSummitStream";
	
	public abstract FactHandle insert(Fact fact);
	
	public abstract FactHandle insert(String stream, Fact fact);
	

}
