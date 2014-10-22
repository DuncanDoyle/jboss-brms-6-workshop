package org.jboss.ddoyle.brms.workshop.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

public class Person {

	private final String name;

	private final String surname;

	private final LocalDate dateOfBirth;

	public Person(final String name, final String surname, final LocalDate dateOfBirth) {
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public int getAge() {
		LocalDate today = new LocalDate();
		Period period = new Period(this.dateOfBirth, today, PeriodType.years());
		return period.getYears();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("name", getName()).append("surname", getSurname())
				.append("date of birth", getDateOfBirth()).append("age", getAge()).build();
	}

	
	
}
