//	$Id$
//	$Source$

package net.loadbang.osc.comms;

import net.loadbang.osc.exn.CommsException;
import net.loadbang.osc.exn.DataException;
import net.loadbang.osc.exn.SetupException;

/**	A receiver of OSC packets. Note that there's no explicit threading here; we assume that
	will be done elsewhere. We just support a blocking read operation.
	
	@author Nick Rothwell, nick@cassiel.com / nick@loadbang.net
 */

abstract public class IPReceiver extends Receiver {
	private int itsPort;

	public IPReceiver(int port) {
		itsPort = port;
	}

	/**	Open the port. This has no effect for UDP connections. */
	abstract public void open()  throws CommsException;
	
	/**	Close the port. */
	abstract public void close() throws CommsException;
	
	/**	Poll for a single packet (encoding a Message or a Bundle; a Bundle will result in
		several calls to the MessageConsumer). */

	abstract public void take() throws SetupException, DataException, CommsException;

	/**	Set the Internet port. For TCP, the connection will have to be explicitly
	 	closed and reopened for this change to take effect. */

	public void setPort(int port) {
		itsPort = port;
	}
	
	/**	Get the Internet port. */
	public int getPort() {
		return itsPort;
	}
}
