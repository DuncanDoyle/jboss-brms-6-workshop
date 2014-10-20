package org.jboss.ddoyle.brms.workshop.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents an Event, i.e. a concert, etc.
 *  
 * @author ddoyle
 */
public class Event {
	
	private final String name;
	
	public static enum EVENT_RATING {
		A, B, C, D
	}
	
	private final EVENT_RATING rating;
	
	
	public Event(final String name, final EVENT_RATING rating) {
		this.name = name;
		this.rating = rating;
	}


	public String getName() {
		return name;
	}


	public EVENT_RATING getRating() {
		return rating;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("name", getName()).append("rating", getRating()).build();
	}
	
	

}
