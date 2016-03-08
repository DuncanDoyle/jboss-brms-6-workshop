package org.jboss.ddoyle.brms.workshop.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		Venue venue = (Venue) obj;
		//@formatter:off
		return new EqualsBuilder().appendSuper(super.equals(venue))
				.append(getCity(), venue.getCity())
				.append(getName(), venue.getName()).isEquals();
				
		//@formatter:on
	}
	
	@Override
	public int hashCode() {
		//@formatter:off
		return new HashCodeBuilder(17, 67)
				.append(getCity())
				.append(getName()).toHashCode();
		//@formatter:on
	}

}
