package org.jboss.ddoyle.brms.workshop.cep.model;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Event fired when a bag is lost.
 *
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 *
 */
public class BagLostEvent extends AbstractFact implements Event {

	/**
	 * SerialVersionUID. 
	 */
	private static final long serialVersionUID = 1L;
	
	private final BagTag bagTag;
	
	private final Location location;
	
	private Date timestamp;
	
	public BagLostEvent(BagTag bagTag, Location location) {
		this(bagTag, location, new Date());
	}
	
	public BagLostEvent(BagTag bagTag, Location location, Date eventTimestamp) {
		this(UUID.randomUUID().toString(), bagTag,location, eventTimestamp);
	}
	
	public BagLostEvent(String id, BagTag bagTag, Location location, Date eventTimestamp) {
		super(id);
		this.bagTag = bagTag;
		this.location = location;
		this.timestamp = eventTimestamp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BagTag getBagTag() {
		return bagTag;
	}

	public Location getLocation() {
		return location;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public Date getTimestamp() {
		return timestamp;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("bagTag", bagTag).append("location", location).append("timestamp", timestamp).toString();
	}
	
}
