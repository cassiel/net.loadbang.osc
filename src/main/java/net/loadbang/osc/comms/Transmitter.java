//	$Id$
//	$Source$

package net.loadbang.osc.comms;

import net.loadbang.osc.data.Element;
import java.io.IOException;
import net.loadbang.osc.exn.SetupException;
import net.loadbang.osc.exn.CommsException;

/**	An abstract transmitter for sending out OSC messages/bundles.
	
	@author Nick Rothwell, nick@cassiel.com / nick@loadbang.net
 */

abstract public class Transmitter {
	/**	Open the port. This has no effect for UDP connections. */
	abstract public void open()  throws CommsException;
	
	/**	Close the port. */
	abstract public void close() throws CommsException;
	
	/**	Transmit the bytes of an OSC element. */
	abstract protected void transmitBytes(byte[] data)
		throws SetupException, CommsException;
	
	/**	Transmit an OSC element. */
	public void transmit(Element element)
		throws SetupException, CommsException
	{
		try {
			transmitBytes(element.toByteArray());
		} catch (IOException exn) {
			exn.printStackTrace();
			assert false : "OSC serialisation";
		}
	}
}
