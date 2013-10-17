//	$Id$
//	$Source$

package net.loadbang.osc.comms;

import java.net.InetAddress;

/**	An OSC transmitter using IP. It has an outgoing host and port.

	@author Nick Rothwell, nick@cassiel.com / nick@loadbang.net
 */

public abstract class IPTransmitter extends Transmitter {
	private InetAddress itsAddress;
	private int itsPort;

	public IPTransmitter(InetAddress address, int port) {
		itsAddress = address;
		itsPort = port;
	}
	
	/**	Set the Internet address. For TCP, the connection will have to be explicitly
	 	closed and reopened for this change to take effect. */

	public void setAddress(InetAddress address) {
		itsAddress = address;
	}
	
	/**	Set the Internet port. For TCP, the connection will have to be explicitly
	 	closed and reopened for this change to take effect. */

	public void setPort(int port) {
		itsPort = port;
	}
	
	/**	Get the Internet address. */
	public InetAddress getAddress() {
		return itsAddress;
	}
	
	/**	Get the Internet port. */
	public int getPort() {
		return itsPort;
	}
}
