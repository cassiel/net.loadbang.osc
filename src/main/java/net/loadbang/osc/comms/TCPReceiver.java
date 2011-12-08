//	$Id$
//	$Source$

package net.loadbang.osc.comms;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

import net.loadbang.osc.exn.CommsException;
import net.loadbang.osc.exn.DataException;
import net.loadbang.osc.exn.SetupException;

/**	OSC packet receiver which operates over TCP/IP. */

abstract public class TCPReceiver extends IPReceiver implements Runnable {
	private ServerSocket itsOscSocket00;

	//	Even though we don't support a threading model explicitly at this level, we need to
	//	thread in order to service the socket, and so we synchronise via a single buffer.
	//	Each entry is a complete packet (Message or Bundle), as bytes.
	
	private BlockingQueue<byte[]> itsNextMessage;
	
    public TCPReceiver(int port) {
		super(port);
		itsNextMessage = new SynchronousQueue<byte[]>();
	}

    @Override
    public void open() throws CommsException {
    	try {
    		itsOscSocket00 = new ServerSocket(getPort());
    		new Thread(this).start();
    	} catch (SocketException e) {
    		throw new CommsException("open", e);
    	} catch (IOException e) {
     		throw new CommsException("I/O", e);
    	}
    }

    @Override
	public void close() throws CommsException {
		if (itsOscSocket00 != null) {
		    try { itsOscSocket00.close(); } catch (IOException _) { }
		    itsOscSocket00 = null;
		}
	}
    
    @Override public void take() throws SetupException, DataException, CommsException {
    	if (itsOscSocket00 == null) {
			throw new SetupException("socket not open");
    	} else {
    		try {
				receivePacket(itsNextMessage.take());
			} catch (InterruptedException e) {
				throw new CommsException("interrupted", e);
			}
    	}
    }
	
	public void run() {
		try {
			while (itsOscSocket00 != null) {
				service(itsOscSocket00.accept());
				
			}
		} catch (IOException _) {
			
		}
	}

	/**	Launch a thread to service a newly-connected client. */

	private void service(Socket socket) throws IOException {
		InputStream is = socket.getInputStream();
		final DataInputStream ds = new DataInputStream(is);

		new Thread(new Runnable() {
			public void run() {
				int bytes;
				try {
					while (true) {				//	We continue until error.
						bytes = ds.readInt();
						byte b[] = new byte[bytes];
						ds.readFully(b);
						itsNextMessage.put(b);
					}
				} catch (IOException e) {
				} catch (InterruptedException e) {
				}
			}
		}).start();
	}
}
