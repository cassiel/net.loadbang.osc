//	$Id$
//	$Source$

package net.loadbang.osc.comms;

import net.loadbang.osc.MessageConsumer;
import net.loadbang.osc.PacketProcessor;
import net.loadbang.osc.exn.DataException;

/**	Abstract receiver class for OSC messages. */

public abstract class Receiver implements MessageConsumer {
	private PacketProcessor itsPacketProcessor;
	
	protected Receiver() {
		itsPacketProcessor = new PacketProcessor(this);
	}

	protected void receivePacket(byte[] packet) throws DataException {
		itsPacketProcessor.consumePacket(packet);
	}
}
