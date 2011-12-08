//	$Id$
//	$Source$

package net.loadbang.osc.data;

import java.io.IOException;

import net.loadbang.osc.util.Formatter;

public class Atom_STRING extends Atom {
	private String itsValue;

	public Atom_STRING(String value) {
		super('s');
		itsValue = value;
	}

	public String getValue() {
		return itsValue;
	}

	/**	Render a string in OSC format. */
	@Override public void render(Formatter formatter) throws IOException {
		formatter.emitString(itsValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Atom_STRING other = (Atom_STRING) obj;
		if (itsValue == null) {
			if (other.itsValue != null)
				return false;
		} else if (!itsValue.equals(other.itsValue))
			return false;
		return true;
	}
}
