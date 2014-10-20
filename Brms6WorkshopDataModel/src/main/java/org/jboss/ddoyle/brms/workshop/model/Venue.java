package org.jboss.ddoyle.brms.workshop.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * Represents a Venue
 * 
 * @author ddoyle
 * 
 */
public class Venue {

	private final String name;

	private final String city;

	public Venue(final String name, final String city) {
		this.name = name;
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public String getCity() {
		return city;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("name", getName()).append("city", getCity()).build();
	}

}
