//	$Id$
//	$Source$

package net.loadbang.osc;

import java.net.InetSocketAddress;
import net.loadbang.osc.exn.DataException;

/**	A consumer of packets of bytes. */

public interface PacketConsumer {
	void consumePacket(InetSocketAddress source, byte[] packet) throws DataException;
}
