//	$Id$
//	$Source$

package net.loadbang.osc;

import java.net.InetSocketAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.loadbang.osc.data.Bundle;
import net.loadbang.osc.data.Element;
import net.loadbang.osc.data.Message;
import net.loadbang.osc.exn.DataException;
import net.loadbang.osc.util.Manifest;
import net.loadbang.osc.util.Timing;
import net.loadbang.util.Pair;

/**	A packet consumer which parses packets and passes them into a message consumer. */

public class PacketProcessor implements PacketConsumer {
	private MessageConsumer itsMessageConsumer;

	public PacketProcessor(MessageConsumer messageConsumer) {
		itsMessageConsumer = messageConsumer;
	}
	
	public void consumePacket(InetSocketAddress source, byte[] packet) throws DataException {
		try {
			ByteBuffer buff = ByteBuffer.wrap(packet);
			Element elem = parse(buff);
			
			List<Pair<Date, Message>> l = new ArrayList<Pair<Date, Message>>();
			
			elem.getMessages(null, l);
			
			for (Pair<Date, Message> msgs: l) {
				itsMessageConsumer.consumeMessage(source, msgs.getFst(), msgs.getSnd());
			}
		} catch (BufferUnderflowException exn) {
			throw new DataException("buffer underflow", exn);
		}
	}

	private Element parse(ByteBuffer buff) throws BufferUnderflowException, DataException {
		Element result;

		if (haveString(buff, Manifest.BUNDLE)) {
			Bundle b = new Bundle(Timing.unTimestamp00(buff.getLong()));
			
			while (haveData(buff)) {
				int msgSizeBytes = buff.getInt();
				
				byte[] cs = new byte[msgSizeBytes];
				buff.get(cs);
				b.add(parse(ByteBuffer.wrap(cs)));
			}
			
			result = b;
		} else {
			String name = consumeString(buff);

			if (haveChar(buff, ',')) {						//	Identified type tags.
				Message m = new Message(name);
				
				byte[] typeTags = consumeString(buff).getBytes();
				
				for (byte b: typeTags) {
					switch (b) {
						case 's':
							m.addString(consumeString(buff));
							break;
						
						case 'i':
							m.addInteger(buff.getInt());
							break;
	
						case 'h':
							m.addLong(buff.getLong());
							break;
	
						case 'f':
							m.addFloat(buff.getFloat());
							break;
	
						case 'd':
							m.addDouble(buff.getDouble());
							break;
							
						default:
							throw new DataException("unknown type tag: '" + b + "'");
					}
				}
			
				result = m;
			} else {
				throw new DataException("no type tags");	//	TODO
			}
		}

		if (haveData(buff)) {
			throw new DataException("buffer not exhausted ("
									+ buff.position() + "/" + buff.limit() + ")"
								   );
		}
		
		return result;
	}
	
	private boolean haveData(ByteBuffer buff) {
		return buff.remaining() > 0;
	}

	private boolean haveChar(ByteBuffer buff, char ch) {
		buff.mark();
		
		if (buff.get() == ch) {
			return true;
		} else {
			buff.reset();
			return false;
		}
	}
	
	private boolean haveString(ByteBuffer buff, String str) {
		buff.mark();
		
		if (str.equals(consumeString(buff))) {
			return true;
		} else {
			buff.reset();
			return false;
		}
	}

	private String consumeString(ByteBuffer buff) {
		StringBuffer str = new StringBuffer();

		byte b;

		while ((b = buff.get()) != 0) {
			str.append((char) b);
		}
		
		int p = buff.position();
		
		if (p % 4 != 0) {
			buff.position(p + 4 - p % 4);
		}
		
		return str.toString();
	}
}
