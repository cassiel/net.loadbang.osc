//	$Id$
//	$Source$

package net.loadbang.osc;

import net.loadbang.osc.exn.DataException;

/**	A consumer of packets of bytes. */

public interface PacketConsumer {
	void consumePacket(byte[] packet) throws DataException;
}
