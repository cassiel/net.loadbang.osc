//	$Id$
//	$Source$

package net.loadbang.osc.data;

import java.io.IOException;

import net.loadbang.osc.util.Formatter;

public class Atom_FLOAT extends Atom {
	private float itsValue;

	public Atom_FLOAT(float value) {
		super('f');
		itsValue = value;
	}

	public float getValue() {
		return itsValue;
	}

	/**	Render a 32-bit float in OSC format. */
	@Override public void render(Formatter formatter) throws IOException {
		formatter.emitFloat(itsValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Atom_FLOAT other = (Atom_FLOAT) obj;
		if (Float.floatToIntBits(itsValue) != Float
				.floatToIntBits(other.itsValue))
			return false;
		return true;
	}
}
