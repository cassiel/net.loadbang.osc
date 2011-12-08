//	$Id$
//	$Source$

package net.loadbang.osc.data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import net.loadbang.osc.util.Formatter;
import net.loadbang.util.Pair;

/**	A generic OSC element, which may either be a message or a bundle. (This
 	roughly corresponds to the OSC specification.) An element can be rendered
 	as a sequence of bytes; the Formatter object takes care of the details of
 	representation and alignment.
 	
 	@author Nick Rothwell, nick@cassiel.com / nick@loadbang.net
 */

abstract public class Element {
	/**	Render this element into a stream. */
	abstract void render(Formatter formatter) throws IOException;
	
	/**	Deliver the element as a byte array (according to OSC layout syntax)
		by rendering it. */

	public byte[] toByteArray() throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		Formatter formatter = new Formatter(stream);
		
		render(formatter);
		return stream.toByteArray();
	}
	
	@Override
	abstract public boolean equals(Object other);

	/**	Get all messages in this element, with timestamp (for bundles). */
	abstract public void getMessages(Date enclosingDate00, List<Pair<Date, Message>> dest);
}
