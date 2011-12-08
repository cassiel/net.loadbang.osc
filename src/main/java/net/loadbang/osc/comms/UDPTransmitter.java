//	$Id$
//	$Source$

package net.loadbang.osc.comms;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.io.IOException;
import net.loadbang.osc.exn.CommsException;
import net.loadbang.osc.exn.SetupException;

/**	Transmitter of OSC elements via UDP. The transmitter retains the Internet
 	address and port, although the underlying datagram socket is stateless
 	in this regard.

	@author Nick Rothwell, nick@cassiel.com / nick@loadbang.net
 */

public class UDPTransmitter extends IPTransmitter {
	private DatagramSocket itsSocket00;
	
	public UDPTransmitter(InetAddress address, int port) throws CommsException {
		super(address, port);

		try {
			//	Open immediately. (No reason why not.)
			itsSocket00 = new DatagramSocket();
		} catch (SocketException exn) {
			throw new CommsException("UDPTransmitter.ctor", exn);
		}
	}
	
	/**	Transmit the bytes of an OSC element over a datagram socket. */
	@Override protected void transmitBytes(byte[] data)
		throws SetupException, CommsException
	{
		if (itsSocket00 == null) {
			throw new SetupException("UDP: connection closed?");
		} else {
			DatagramPacket packet =
				new DatagramPacket(data, data.length, getAddress(), getPort());
	        
			try {
				itsSocket00.send(packet);
			} catch (IOException exn) {
				throw new CommsException("UDPTransmitter.transmitBytes()", exn);
			}
		}
	}
	
	/**	Open the port. (No action needed.) */
	@Override public void open()
		throws CommsException
	{
		if (itsSocket00 != null) {
			throw new CommsException("UDP: connection already open?");
		} else {
			try {
				itsSocket00 = new DatagramSocket();
			} catch (SocketException exn) {
				throw new CommsException("UDPTransmitter.open()", exn);
			}
		}
	}

	/**	Close the underlying datagram socket. */
	@Override public void close() {
		if (itsSocket00 != null) {
			itsSocket00.close();
			itsSocket00 = null;
		}
	}
}
