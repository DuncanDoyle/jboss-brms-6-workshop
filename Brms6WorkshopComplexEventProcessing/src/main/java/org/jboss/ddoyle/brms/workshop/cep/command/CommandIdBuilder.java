package org.jboss.ddoyle.brms.workshop.cep.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.drools.core.reteoo.InitialFactImpl;
import org.jboss.ddoyle.brms.workshop.cep.model.Fact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Builder component which provides functionality to build an ID based on the <code>rulePackageName</code>, <code>ruleId</code>,
 * <code>ruleName</code> and <code>facts</code>.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class CommandIdBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommandIdBuilder.class);

	private String rulePackageName;

	private String ruleId;

	private String ruleName;

	private List<Object> facts = new ArrayList<Object>();

	public CommandIdBuilder addRulePackageName(String rulePackageName) {
		this.rulePackageName = rulePackageName;
		return this;
	}

	public CommandIdBuilder addRuleId(String ruleId) {
		this.ruleId = ruleId;
		return this;
	}

	public CommandIdBuilder addRuleName(String ruleName) {
		this.ruleName = ruleName;
		return this;
	}

	public CommandIdBuilder addFacts(List<Object> facts) {
		this.facts = Collections.unmodifiableList(facts);
		return this;
	}

	/**
	 * The simple algorithm is just to create a string which concats all the hashcodes of facts.
	 */
	public String build() {
		StringBuilder idBuilder = new StringBuilder();
		idBuilder.append(rulePackageName).append(":").append(ruleId).append(":").append(ruleName).append("-");

		// We first need to sort the list based on the hashcode of the objects.
		/*
		 * TODO: So, we assume that Facts with the same hashcode are equal .... which might not be completely safe .... But if we want to
		 * create the Command ID based on the UUID, we assume that all facts have a uuid .... Using a hashcode might be a more flexible
		 * approach.
		 */
		SortedSet<String> factIds = new TreeSet<String>();
		for (Object nextObject : facts) {
			boolean added = false;
			boolean discarded = false;
			if (nextObject instanceof Fact) {
				added = factIds.add(((Fact) nextObject).getId());
			} else if (nextObject instanceof InitialFactImpl) {
				// this seems to be a class of drools which is always listed as a fact for every rule, so we discard it.
				discarded = true;
			} else {
				// This is not one of our Fact objects, so we're going to take the hash as the id.
				// TODO: Don't we have a problem when the object is not one of our facts? The hash of these objects might be different
				// across JVMs

				LOGGER.warn("BRMS Rule Match triggered by a fact which is not one of our Fact object.: '"
						+ nextObject.getClass().getCanonicalName()
						+ "'. This fact will be ignored in this ID generation. This situation should not happen in the RH Summit 2015 BRMS CEP HA system and indicates an incorrectly defined rule or Fact object.");
			}

			// Not sure whether the facts list can contain the same fact twice. If that's the case, we might need to use a 'Sorted Bag'
			// structure.
			if (added == false && discarded == false) {
				LOGGER.warn("We have facts with the same hashcode!!!");
			}
		}
		for (String nextId : factIds) {
			idBuilder.append("[" + nextId + "]");
		}
		return idBuilder.toString();
	}

	/*
	 * public class HashCodeComparator implements Comparator<Object> {
	 * 
	 * @Override public int compare(Object o1, Object o2) { int o1Hash = o1.hashCode(); int o2Hash = o2.hashCode(); return o1Hash - o2Hash;
	 * } }
	 */

}
