//	$Id$
//	$Source$

package net.loadbang.osc.data;

import java.io.IOException;

import net.loadbang.osc.util.Formatter;

public class Atom_DOUBLE extends Atom {
	private double itsValue;

	public Atom_DOUBLE(double value) {
		super('d');
		itsValue = value;
	}

	public double getValue() {
		return itsValue;
	}

	/**	Render a 64-bit double in OSC format. */
	@Override public void render(Formatter formatter) throws IOException {
		formatter.emitDouble(itsValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Atom_DOUBLE other = (Atom_DOUBLE) obj;
		if (Double.doubleToLongBits(itsValue) != Double
				.doubleToLongBits(other.itsValue))
			return false;
		return true;
	}
}
