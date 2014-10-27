package org.jboss.ddoyle.brms.workshop.cep.model;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Tag of a Bag. Uniquely identifies the bag within the system.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class BagTag implements Serializable {

	/**
	 * SerialVersionUID. 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String id;
	
	public BagTag() {
		this(UUID.randomUUID().toString());
	}
	
	public BagTag(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id).toString();
	}
	
}

