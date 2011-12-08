//	$Id$
//	$Source$

package net.loadbang.osc;

import java.net.InetAddress;

import net.loadbang.osc.comms.IPTransmitter;
import net.loadbang.osc.comms.TCPTransmitter;
import net.loadbang.osc.exn.CommsException;

public class TCPWriterProxy extends IPWriterProxy {
	@Override
	protected IPTransmitter newTransmitter(InetAddress address, int port) throws CommsException {
		return new TCPTransmitter(address, port);
	}
}
