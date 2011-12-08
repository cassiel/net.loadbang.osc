//	$Id$
//	$Source$

package net.loadbang.osc.data;

import java.io.IOException;

import net.loadbang.osc.util.Formatter;

public class Atom_LONG extends Atom {
	private long itsValue;

	public Atom_LONG(long value) {
		super('h');
		itsValue = value;
	}

	public long getValue() {
		return itsValue;
	}

	/**	Render a 64-bit long in OSC format. */
	@Override public void render(Formatter formatter) throws IOException {
		formatter.emitLong(itsValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Atom_LONG other = (Atom_LONG) obj;
		if (itsValue != other.itsValue)
			return false;
		return true;
	}
}
