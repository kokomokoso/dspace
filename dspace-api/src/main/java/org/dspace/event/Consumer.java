/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.event;

import org.dspace.core.Context;

/**
 * Interface for content event consumers. Note that the consumer cannot tell
 * if it is invoked synchronously or asynchronously; the consumer interface
 * and sequence of calls is the same for both. Asynchronous consumers may see
 * more consume() calls between the start and end of the event stream, if they
 * are invoked asynchronously, once in a long time period, rather than
 * synchronously after every Context.commit().
 */
public interface Consumer {
    /**
     * Allocate any resources required to operate. This may include
     * initializing any pooled JMS resources. Called ONCE when created by the
     * dispatcher pool. This should be used to set up expensive resources that
     * will remain for the lifetime of the consumer.
     *
     * @throws Exception if error
     */
    public void initialize() throws Exception;

    /**
     * Consume an event.  Events may be filtered by a dispatcher, hiding them
     * from the consumer.  This behavior is based on the dispatcher/consumer
     * configuration.  Should include logic to initialize any resources
     * required for a batch of events.
     *
     * <p>This method <em>must not</em> commit the context.  Committing causes
     * re-dispatch of the event queue, which can result in infinite recursion
     * leading to memory exhaustion as seen in
     * {@link https://github.com/DSpace/DSpace/pull/8756}.
     *
     * @param ctx   the current DSpace session
     * @param event the content event
     * @throws Exception if error
     */
    public void consume(Context ctx, Event event) throws Exception;

    /**
     * Signal that there are no more events queued in this event stream and
     * event processing for the preceding consume calls should be finished up.
     *
     * @param ctx the execution context object
     * @throws Exception if error
     */
    public void end(Context ctx) throws Exception;

    /**
     * Finish - free any allocated resources. Called when consumer (via it's
     * parent dispatcher) is going to be destroyed by the dispatcher pool.
     *
     * @param ctx the execution context object
     * @throws Exception if error
     */
    public void finish(Context ctx) throws Exception;

}
