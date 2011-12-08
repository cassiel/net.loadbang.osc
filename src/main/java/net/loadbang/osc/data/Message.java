//	$Id$
//	$Source$

package net.loadbang.osc.data;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import net.loadbang.osc.util.Formatter;
import net.loadbang.util.Pair;

/**	An OSC message (as opposed to a bundle).

	@author Nick Rothwell, nick@cassiel.com / nick@loadbang.net
*/

public class Message extends Element {
	/**	The  address (pattern) of this message. */
	private String itsAddress;

	/**	The values in the message. (These will not include (sub) messages or
	 	bundles; bundles themselves can be recursive, however.) */

	private Vector<Atom> itsArguments;

	/**	Create an empty message. */
	public Message(String address) {
		itsAddress = address;
		itsArguments = new Vector<Atom>();
	}
	
	/**	Create a message with an array of atoms. */
	public Message(String address, Atom[] atoms) {
		this(address);
		for (Atom a: atoms) { itsArguments.add(a); }
	}
	
	/**	Add an integer to the message. */
	public Message addInteger(int i) { itsArguments.add(new Atom_INTEGER(i)); return this; }
	
	/**	Add a float to the message. */
	public Message addFloat(float f) { itsArguments.add(new Atom_FLOAT(f)); return this; }
	
	/**	Add a long to the message. */
	public Message addLong(long l) { itsArguments.add(new Atom_LONG(l)); return this; }
	
	/**	Add a double to the message. */
	public Message addDouble(double d) { itsArguments.add(new Atom_DOUBLE(d)); return this; }
	
	/**	Add a string to the message. */
	public Message addString(String s) { itsArguments.add(new Atom_STRING(s)); return this; }
	
	/**	Assemble the type tags of this message as a string. */
	String assembleTypeTags() {
		int len = itsArguments.size();
		char data[] = new char[1 + len];	//	Allow for ",".
		
		data[0] = ',';

		for (int i = 0; i < len; i++) {
			data[1 + i] = itsArguments.get(i).getTypeTag();
		}
		
		return new String(data);
	}
	
	public String getAddress() {
		return itsAddress;
	}
	
	public int getNumArguments() {
		return itsArguments.size();
	}
	
	public Atom getArgument(int index) {
		return itsArguments.elementAt(index);
	}

	/**	Render this element into a stream. The syntax is the name (what OSC calls
	 	an "address pattern"), followed by the type tags, followed by the arguments. */

	@Override void render(Formatter formatter) throws IOException {
		//	Render the name (address pattern).
		formatter.emitString(itsAddress);
	
		//	Render the type tags. The initial "," and the individual tag characters
		//	constitute a string.
		
		formatter.emitString(assembleTypeTags());

		for (Enumeration<Atom> values = itsArguments.elements();
			 values.hasMoreElements();
			) {
			values.nextElement().render(formatter);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Message other = (Message) obj;
		if (itsAddress == null) {
			if (other.itsAddress != null)
				return false;
		} else if (!itsAddress.equals(other.itsAddress))
			return false;
		if (itsArguments == null) {
			if (other.itsArguments != null)
				return false;
		} else if (!itsArguments.equals(other.itsArguments))
			return false;
		return true;
	}

	@Override
	public void getMessages(Date enclosingDate00, List<Pair<Date, Message>> dest) {
		dest.add(new Pair<Date, Message>(enclosingDate00, this));
	}
}
