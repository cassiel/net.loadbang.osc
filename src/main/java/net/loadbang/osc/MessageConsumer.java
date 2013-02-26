//	$Id$
//	$Source$

package net.loadbang.osc;

import java.net.InetSocketAddress;
import java.util.Date;

import net.loadbang.osc.data.Message;

/**	A consumer of OSC elements (messages; our machinery deals with bundles automatically). */

public interface MessageConsumer {
	/** Consume a message (which may be part of a bundle).

		@param InetSocketAddress the source of the message
 		@param timestamp00 the timestamp as a Date; we pass null for "now"
 		@param message the message
	 */

	void consumeMessage(InetSocketAddress source, Date timestamp00, Message message);

	/** Consume a message (which may be part of a bundle). The
	 	{@link net.loadbang.osc.comms.Receiver} implementation provides
	 	this form in terms of the one which takes a socket address.

	 	@param timestamp00 the timestamp as a Date; we pass null for "now"
	 	@param message the message
	 */

	void consumeMessage(Date timestamp00, Message message);
}
