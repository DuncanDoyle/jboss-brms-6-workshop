package org.jboss.ddoyle.brms.workshop;


public class DiscountService {

	public static enum DISCOUNT_CODE {
		EVENTPASS, NON_ADULT
	}

	public Integer getDiscount(DISCOUNT_CODE id) {
		int discount = 0;
		if (DISCOUNT_CODE.EVENTPASS.equals(id)) {
			discount = 9;
		} else if (DISCOUNT_CODE.NON_ADULT.equals(id)) {
			discount = 24;
		}
		return discount;
	}

}
