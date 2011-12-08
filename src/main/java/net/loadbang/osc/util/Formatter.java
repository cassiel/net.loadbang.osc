//	$Id$
//	$Source$

package net.loadbang.osc.util;

/**	A utility class which can format objects into an output stream for transmission.
 	The Formatter object generally deals with alignment issues, but doesn't actually know
 	much about OSC messages (such as type tags).
	
	@author Nick Rothwell, nick@cassiel.com / nick@loadbang.net
*/

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Formatter {
	/**	The underlying output stream. */
	private ByteArrayOutputStream itsStream;
	
	/**	A data stream which will format ints, floats etc. for us. */
	private DataOutputStream itsDataStream;
	
	/**	Create a buffer to wrap an output stream. */
	public Formatter(ByteArrayOutputStream stream) {
		itsStream = stream;
		itsDataStream = new DataOutputStream(stream);
	}
	
	/**	Word-align the byte output stream. */
	private void align() throws IOException {
		int pad = 4 - (itsStream.size() % 4);
    
		if (pad != 4) {
			for (int i = 0; i < pad; i++) {
				itsStream.write(0);
			}
		}
	}
	
	/**	Emit a string (which might also be the name, the #bundle header, or the
	 	type tags). */

	public void emitString(String s) throws IOException {
		itsDataStream.writeBytes(s);
		itsDataStream.writeByte(0);
		align();
	}
	
	/**	Emit a 32-bit integer. */
	public void emitInteger(int i) throws IOException {
		itsDataStream.writeInt(i);
	}

	/**	Emit a 64-bit long. */
	public void emitLong(long l) throws IOException {
		itsDataStream.writeLong(l);
	}

	/**	Emit a 32-bit float. */
	public void emitFloat(float f) throws IOException {
		itsDataStream.writeFloat(f);
	}

	/**	Emit a 64-bit double. */
	public void emitDouble(double d) throws IOException {
		itsDataStream.writeDouble(d);
	}
	
	/**	Emit a byte array (assumed to be int-aligned). */
	public void emitBytes(byte[] bytes) throws IOException {
		itsStream.write(bytes);
	}
}
