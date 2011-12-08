//	$Id$
//	$Source$

package net.loadbang.osc.comms;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import net.loadbang.osc.exn.SetupException;
import net.loadbang.osc.exn.CommsException;

/**	Transmitter of OSC elements via TCP. We currently pay no attention to any data coming
 	the other way (and will eventually cause a blockage if there is any), although we
 	do set up the input stream.

	@author Nick Rothwell, nick@cassiel.com / nick@loadbang.net
*/

public class TCPTransmitter extends IPTransmitter {
	private Socket itsSocket00 = null;
	private InputStream itsInputStream00;
	private OutputStream itsOutputStream00;
	private DataOutputStream itsDataOutputStream00;

	public TCPTransmitter(InetAddress address, int port) throws CommsException {
		super(address, port);
	}
	
	@Override public void close() throws CommsException {
		if (itsSocket00 != null) {
			try {
				itsSocket00.close();
				itsInputStream00.close();
				itsOutputStream00.close();
			} catch (IOException exn) {
				throw new CommsException("TCPTransmitter::close()", exn);
			} finally {
				itsSocket00 = null;
			}
		}
	}
	
	@Override public void open() throws CommsException {
		if (itsSocket00 != null) {
			throw new CommsException("connection already open");
		} else {
			try {
				itsSocket00 = new Socket(getAddress(), getPort());
				itsInputStream00 = itsSocket00.getInputStream();
				itsOutputStream00 = itsSocket00.getOutputStream();
				itsDataOutputStream00 = new DataOutputStream(itsOutputStream00);
			} catch (IOException exn) {
				throw new CommsException("TCPTransmitter::open()", exn);
			}
		}
	}
	
	@Override protected void transmitBytes(byte[] data)
		throws SetupException, CommsException
	{
		if (itsSocket00 == null) {
			throw new SetupException("connection not open");
		} else {
			try {
				itsDataOutputStream00.writeInt(data.length);
				itsOutputStream00.write(data);
				itsOutputStream00.flush();
			} catch (IOException exn) {
				throw new CommsException("TCPTransmitter::transmitBytes()", exn);
			}
		}
	}
}
