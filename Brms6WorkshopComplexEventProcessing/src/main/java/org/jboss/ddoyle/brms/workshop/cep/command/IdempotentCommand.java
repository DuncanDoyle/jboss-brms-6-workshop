package org.jboss.ddoyle.brms.workshop.cep.command;


/**
 * Marker interface for Commands which are idempotent. This allows us to specify {@link Command Commands} for which we don't need the
 * idempotency layer in our BRMS CEP system. I.e. the {@link Command} command can be executed multiple times because it is intrinsically
 * idempotent.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public interface IdempotentCommand {

}
