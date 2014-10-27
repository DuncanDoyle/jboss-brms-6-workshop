package org.jboss.ddoyle.brms.workshop.cep.kie.session;

/**
 * Provides functionality to compare 2 (or more) 'streams' of working memory events.
 * The idea is that the events don't have to be in the exact same order, but we provide a sort of 'sliding window'
 * in which we allow events to be in a different order. 
 * 
 * So, basically, what we want to do is to create 2-way ack (does that make sense)?
 * If a line occurs in both lists, we remove both.
 * 
 * We however need to make sure that we keep track of lines that are not matched .....
 * 
 * THIS IS A BIG TODO and currently not scheduled for this PoC.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class WorkingMemoryEventComparator {

}
