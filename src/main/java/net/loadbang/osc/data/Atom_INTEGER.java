//	$Id$
//	$Source$

package net.loadbang.osc.data;

import java.io.IOException;

import net.loadbang.osc.util.Formatter;

public class Atom_INTEGER extends Atom {
	private int itsValue;

	public Atom_INTEGER(int value) {
		super('i');
		itsValue = value;
	}
	
	public int getValue() {
		return itsValue;
	}

	/**	Render an integer in OSC format. */
	@Override public void render(Formatter formatter) throws IOException {
		formatter.emitInteger(itsValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Atom_INTEGER other = (Atom_INTEGER) obj;
		if (itsValue != other.itsValue)
			return false;
		return true;
	}
}
