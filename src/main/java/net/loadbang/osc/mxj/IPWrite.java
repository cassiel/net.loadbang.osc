//	$Id$
//	$Source$

package net.loadbang.osc.mxj;

import java.net.InetAddress;

import net.loadbang.osc.IPWriterProxy;
import net.loadbang.osc.exn.CommsException;
import net.loadbang.osc.exn.DataException;
import net.loadbang.osc.exn.SetupException;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

/** Abstract IP-based OSC sender object for MXJ. This sends data generically from a
 	message plus Atom array. We have sub-classes for TCP and UDP, although dynamically
 	changing them would be a bit messy.

	@author Nick Rothwell, nick@cassiel.com / nick@loadbang.net
 */

abstract public class IPWrite extends MaxObject {
	private IPWriterProxy itsIPWriterProxy;
	/**	Outgoing Internet address, or null if not set. */
	private InetAddress itsAddress00 = null;
	
	/**	Outgoing Internet port, or null if not set. */
	private Integer itsPort00 = null;
	
    /**	Flag to specify whether to bundle messages. Default is OFF. */
    private boolean itsBundleMessages = false;

    protected IPWrite(IPWriterProxy writerProxy) {
        declareAttribute("host", "getHost", "setHost");
        declareAttribute("port", "getPort", "setPort");
        declareAttribute("bundle", "getBundle", "setBundle");
        
        itsIPWriterProxy = writerProxy;
        
        declareIO(1, 0);		//	One inlet, plus info outlet only.
	    setInletAssist(0, "message to send, 'host', 'port', 'bundle <0/1>' or bang");
    }
    
	/** Change the destination host.

		@param host The name or IP address of the host.
	 */

	public void setHost(String host) {
		try {
			itsIPWriterProxy.setHost(host);
		} catch (CommsException exn) {
			error("sethost: " + exn.getMessage());
		} catch (SetupException exn) {
			error("sethost: " + exn.getMessage());
		}
	}
	
	/**	Return the current host. */
	public String getHost() {
		if (itsAddress00 == null) {
			error("host not set");
			return "none";
		} else {
			return itsAddress00.toString();
				//	I want to implement a getCanonicalHostName() as well, but
				//	that should probably be deferred as it will access DNS.
		}
	}

	/**	Return the current port. */
	public int getPort() {
		if (itsPort00 == null) {
			error("port not set");
			return 0;
		} else {
			return itsPort00;
		}
	}

	/** Change the destination port.

		@param port The port to send to.
	 */

	public void setPort(int port) {
		try {
			itsPort00 = port;
			itsIPWriterProxy.setPort(port);
		} catch (CommsException exn) {
			error("setPort: " + exn.getMessage());
		}
	}
    
	/**	Set the bundle status. It's not clear what to do if some messages are currently
		bundled; at the moment we flush them.
	 */

	public void setBundle(boolean bundle) {
		itsBundleMessages = bundle;
		try {
			itsIPWriterProxy.setBundle(bundle);
		} catch (SetupException exn) {
			error("setBundle: setup: " + exn.getMessage());
		} catch (CommsException exn) {
			error("setBundle: comms: " + exn.getMessage());
		}
	}
    
	/**	Get the bundle status. */
	public boolean getBundle() {
		return itsBundleMessages;
	}
    
	/**	Close the transmitter when this instance is freed. */
	@Override protected void notifyDeleted() {
		try { itsIPWriterProxy.close(); } catch (CommsException e) { }
	}
    
    /**	Generic messages are packed and sent to the transmitter (if the
     	host and port have been set up).
     */
    
	@Override protected void anything(String message, Atom args[]) {
		try {
			itsIPWriterProxy.transmit(message, args);
		} catch (SetupException exn) {
			error("setup: " + exn.getMessage());
		} catch (CommsException exn) {
			error("comms: " + exn.getMessage());
		} catch (DataException exn) {
			error("data: " + exn.getMessage());
		}
	}

	/**	Open the connection. (Only makes sense for TCP.) */
	public void open() {
		try {
			itsIPWriterProxy.open();
		} catch (CommsException e) {
			error("comms: " + e.getMessage());
		}
	}

	/**	Close the connection. For UDP, the connection is presumably closed permanently. */
	public void close() {
		try {
			itsIPWriterProxy.close();
		} catch (CommsException e) {
			error("comms: " + e.getMessage());
		}
	}

	/**	Output the pending packet. */
	@Override protected void bang() {
		try {
			itsIPWriterProxy.flush();
		} catch (SetupException exn) {
			error("setup: " + exn.getMessage());
		} catch (CommsException exn) {
			error("comms: " + exn.getMessage());
		}
	}
}
