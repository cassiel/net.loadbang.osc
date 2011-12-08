//	$Id$
//	$Source$

package net.loadbang.osc;

import java.net.InetAddress;

import net.loadbang.osc.comms.IPTransmitter;
import net.loadbang.osc.comms.UDPTransmitter;
import net.loadbang.osc.exn.CommsException;

public class UDPWriterProxy extends IPWriterProxy {
	@Override
	protected IPTransmitter newTransmitter(InetAddress address, int port) throws CommsException {
		return new UDPTransmitter(address, port);
	}
}
