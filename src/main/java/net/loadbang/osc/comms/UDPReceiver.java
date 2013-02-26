//	$Id$
//	$Source$

package net.loadbang.osc.comms;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

import net.loadbang.osc.exn.CommsException;
import net.loadbang.osc.exn.DataException;
import net.loadbang.osc.exn.SetupException;
import net.loadbang.osc.util.Manifest;

abstract public class UDPReceiver extends IPReceiver {
    private DatagramSocket itsOscSocket00 = null;

    public UDPReceiver(int port) {
		super(port);
	}
    
    /** UDP receiver with unspecified port. When we open, the port is
     	lifted from the socket. */

    public UDPReceiver() {
    	this(0);
    }

    @Override
    public void open() throws CommsException {
    	try {
    		if (getPort() == 0) {
        		itsOscSocket00 = new DatagramSocket();
    			setPort(itsOscSocket00.getLocalPort());
    		} else {
    			itsOscSocket00 = new DatagramSocket(getPort());
    		}
    	} catch (SocketException e) {
    		throw new CommsException("open", e);
    	}
    }

    @Override
	public void close() throws CommsException {
		if (itsOscSocket00 != null) {
		    itsOscSocket00.close();
		    itsOscSocket00 = null;
		}
	}
	
	@Override
	public void take() throws SetupException, DataException, CommsException {
		if (itsOscSocket00 == null) {
			throw new SetupException("socket not open");
		} else {
			byte[] datagram  = new byte[Manifest.OSC_MAX_LEN];
			
			DatagramPacket dp = new DatagramPacket(datagram, datagram.length);
	
			// block until a datagram is received
			try {
				itsOscSocket00.receive(dp);
			} catch (IOException e) {
				throw new CommsException("I/O", e);
			}
	
			//clientAddress = dp.getAddress();
			//clientPort = dp.getPort();

			byte[] buff = new byte[dp.getLength()];
			ByteBuffer.wrap(datagram).get(buff);
			receivePacket((InetSocketAddress) dp.getSocketAddress(), buff);
		}
	}
}
