package org.jboss.ddoyle.brms.workshop.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class EventPass {
	
	private final int number;
	private final Person person;
	
	public EventPass(final int number, final Person person) {
		this.number = number;
		this.person = person;
	}

	public int getNumber() {
		return number;
	}

	public Person getPerson() {
		return person;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("number", getNumber()).append("person", getPerson()).build();
	}
}
