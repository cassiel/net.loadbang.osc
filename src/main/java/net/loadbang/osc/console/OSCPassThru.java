//	$Id$
//	$Source$

package net.loadbang.osc.console;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import net.loadbang.osc.comms.IPTransmitter;
import net.loadbang.osc.comms.UDPReceiver;
import net.loadbang.osc.comms.UDPTransmitter;
import net.loadbang.osc.data.Message;
import net.loadbang.osc.exn.CommsException;
import net.loadbang.osc.exn.SetupException;

/**	Testing: a console program which just passes OSC messages (on two threads,
	for bidirectional comms).
	
 	@author Nick Rothwell, nick@loadbang.net / nick@cassiel.com
 */

public class OSCPassThru implements Runnable {
	private UDPReceiver itsReceiver00;
	
	public OSCPassThru(UDPReceiver r00) throws CommsException {
		itsReceiver00 = r00;
		if (r00 != null) { r00.open(); }
	}

	public OSCPassThru() throws CommsException {
		this(null);
	}
	
	static class PassThru extends UDPReceiver {
		private IPTransmitter itsTransmitter;

		public PassThru(int from, int to) throws CommsException, SetupException {
			super(from);
			try {
				itsTransmitter = new UDPTransmitter(InetAddress.getByName("localhost"), to);
			} catch (UnknownHostException e) {
				throw new SetupException("unknown host", e);
			}
		}

		public void consumeMessage(Date timestamp00, Message message) {
			try {
				itsTransmitter.transmit(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void run() {
		while (true) {
			try {
				itsReceiver00.take();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws CommsException, SetupException {
		new Thread(new OSCPassThru(new PassThru(5555, 6555))).start();
		new Thread(new OSCPassThru(new PassThru(7555, 8555))).start();
		new Thread(new OSCPassThru(new PassThru(8080, 7080))).start();
	}
}
