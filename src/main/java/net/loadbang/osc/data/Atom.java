//	$Id$
//	$Source$

package net.loadbang.osc.data;

import java.io.IOException;

import net.loadbang.osc.util.Formatter;

/**	Abstract class for values which can be embedded in a message. ("Atom" is probably
 	misleading if we support OSC arrays).

	@author Nick Rothwell, nick@cassiel.com / nick@loadbang.net
 */

abstract public class Atom {
	private Character itsTypeTag;
	
	/**	Create an atom with a character for its OSC type tag. */
	public Atom(Character typeTag) {
		itsTypeTag = typeTag;
	}
	
	/**	Retrieve the type tag. */
	public Character getTypeTag() {
		return itsTypeTag;
	}
	
	/**	Render this atom into a formatter. */
	abstract public void render(Formatter formatter) throws IOException;
	
	@Override
	abstract public boolean equals(Object other);
}
