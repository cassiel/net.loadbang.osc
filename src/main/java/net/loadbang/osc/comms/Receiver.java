//	$Id$
//	$Source$

package net.loadbang.osc.comms;

import java.net.InetSocketAddress;
import java.util.Date;

import net.loadbang.osc.MessageConsumer;
import net.loadbang.osc.PacketProcessor;
import net.loadbang.osc.data.Message;
import net.loadbang.osc.exn.DataException;

/**	Abstract receiver class for OSC messages. */

public abstract class Receiver implements MessageConsumer {
	private PacketProcessor itsPacketProcessor;
	
	protected Receiver() {
		itsPacketProcessor = new PacketProcessor(this);
	}

	protected void receivePacket(InetSocketAddress source, byte[] packet) throws DataException {
		itsPacketProcessor.consumePacket(source, packet);
	}
	
	/*	Consume this message, given the source. By default, call the same method
	 	with the source omitted.

	 	@see net.loadbang.osc.MessageConsumer#consumeMessage(java.net.InetSocketAddress, java.util.Date, net.loadbang.osc.data.Message)
	 */

	public void consumeMessage(InetSocketAddress source, Date timestamp00, Message message) {
		consumeMessage(timestamp00, message);
	}
}
