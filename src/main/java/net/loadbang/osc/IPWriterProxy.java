//	$Id$
//	$Source$

package net.loadbang.osc;

import java.net.InetAddress;
import java.net.UnknownHostException;

import net.loadbang.osc.comms.IPTransmitter;
import net.loadbang.osc.data.Bundle;
import net.loadbang.osc.data.Message;
import net.loadbang.osc.exn.CommsException;
import net.loadbang.osc.exn.DataException;
import net.loadbang.osc.exn.SetupException;

import com.cycling74.max.Atom;

/**	Proxy superclass object for the MXJ OSC writers (so that we can unit test). */

abstract public class IPWriterProxy {
	/**	Outgoing Internet address, or null if not set. */
	private InetAddress itsAddress00 = null;
	
	/**	Outgoing Internet port, or null if not set. */
	private Integer itsPort00 = null;
	
	/**	The IP layer (socket) for sending OSC packets; null until address and
	 	port have been set. This can be either a UDP transmitter or a TCP
	 	transmitter. */

    private IPTransmitter itsTransmitter00 = null;
    
    /** Intermediate OSC bundle, flushed out on bang. */
    private Bundle itsPendingBundle00 = null;
    
    /**	Flag to specify whether to bundle messages. Default is OFF. */
    private boolean itsBundleMessages = false;

	/**	Set up the transmitter, given that address and port (and transport) have
	 	been set. The existing transmitter can have its address and port changed,
	 	and for UDP this takes immediate effect. */

	private void setupTransmitter()
		throws CommsException
	{
		if (itsTransmitter00 == null) {
			itsTransmitter00 = newTransmitter(itsAddress00, itsPort00);
		} else {
			itsTransmitter00.setAddress(itsAddress00);
			itsTransmitter00.setPort(itsPort00);
		}
	}
	
	/**	Create a transmitter (UDP or TCP). */
	
    abstract protected IPTransmitter newTransmitter(InetAddress address, int port) throws CommsException;

	/** Change the destination host.

		@param host The name or IP address of the host
	 	@throws UnknownHostException 
	 	@throws CommsException 
	 */

	public void setHost(String host) throws CommsException, SetupException {
		try {
			itsAddress00 = InetAddress.getByName(host);

			if (itsPort00 != null) {
				setupTransmitter();
			}
		} catch (java.net.UnknownHostException exn) {
			throw new CommsException("unknown host: " + host, exn);
		}
	}
	
	/** Change the destination port.

		@param port The port to send to
	 	@throws CommsException 
	 */

	public void setPort(int port) throws CommsException {
		itsPort00 = port;
    		
		if (itsAddress00 != null) {
			setupTransmitter();
		}
	}
    
	/**	Set the bundle status. It's not clear what to do if some messages are currently
		bundled; at the moment we flush them.

		@throws CommsException 
		@throws SetupException 
	 */

	public synchronized void setBundle(boolean bundle) throws SetupException, CommsException {
		if (!bundle) {
			flush();
		}

		itsBundleMessages = bundle;
	}
    
    /**	Generic messages are packed and sent to the transmitter (if the
     	host and port have been set up).

	 	@param message the message string
	 	@param args the arguments
	 	@throws SetupException if there is a configuration problem
	 	@throws CommsException if there is a communication problem
     	@throws DataException if there is a data encoding problem
	 */

	synchronized public void transmit(String message, Atom args[])
		throws SetupException, CommsException, DataException
	{
		if (itsTransmitter00 == null) {
			throw new SetupException("host/port not set up");
		} else {
			Message m = new Message(message);
	        
			for (int i = 0; i < args.length; i++) {
				Atom a = args[i];
	        
				if (a.isFloat()) {
					m.addFloat(a.getFloat());
				} else if (a.isInt()) {
					m.addInteger(a.getInt());
				} else if (a.isString()) {
					m.addString(a.getString());
				} else {
					throw new DataException("cannot encode argument: " + a);
				}
			}
			
			if (itsBundleMessages) {
				if (itsPendingBundle00 == null) {
					itsPendingBundle00 = new Bundle();
				}
				
				itsPendingBundle00.add(m);
			} else {
				itsTransmitter00.transmit(m);
			}
		}
	}
    
	/**	Output the pending packet. 

		@throws SetupException
	 	@throws CommsException
	 */

	public synchronized void flush() throws SetupException, CommsException {
		if (itsTransmitter00 == null) {
			throw new SetupException("host/port not set up");
		} else {
			if (itsPendingBundle00 != null) {
				//itsPendingPacket00.dump();
				itsTransmitter00.transmit(itsPendingBundle00);
	
				itsPendingBundle00 = null;
			}
		}
	}
	
	/**	Open the connection. (Only makes sense for TCP.) 
	
		@throws CommsException
	 */

	public void open() throws CommsException {
		if (itsTransmitter00 != null) {
			itsTransmitter00.open();
		}
	}

	/**	Close the connection. For UDP, the connection is presumably closed permanently. 
	
		@throws CommsException
	 */

	public void close() throws CommsException {
		if (itsTransmitter00 != null) {
			itsTransmitter00.close();
		}
	}
}
