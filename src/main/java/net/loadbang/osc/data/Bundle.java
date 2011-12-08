//	$Id$
//	$Source$

package net.loadbang.osc.data;

/**	An OSC bundle, comprising messages and nested bundles.
	
	@author Nick Rothwell, nick@cassiel.com / nick@loadbang.net
*/

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import net.loadbang.osc.util.Formatter;
import net.loadbang.osc.util.Manifest;
import net.loadbang.osc.util.Timing;
import net.loadbang.util.Pair;

public class Bundle extends Element {
	/**	The timestamp of this bundle. */
	private long itsTimestamp;
	
	/**	The elements of this bundle. */
	private Vector<Element> itsElements;
	
	/**	Create a new bundle with a specific timestamp. */
	public Bundle(Date date00) {
		if (date00 == null) {
			itsTimestamp = Manifest.TIMESTAMP_NOW;
		} else {
			itsTimestamp = Timing.makeTimestamp(date00);
		}

		itsElements = new Vector<Element>();
	}
	
	/**	Create a new bundle with a timestamp of "now". */
	public Bundle() {
		itsTimestamp = Manifest.TIMESTAMP_NOW;
		itsElements = new Vector<Element>();
	}
	
	public void setTimestamp(long stamp) {
		itsTimestamp = stamp;
	}
	
	/**	Add an element to this bundle.
	
		@param element the element (bundle or message) to add
	 	@return the bundle
	 */

	public Bundle add(Element element) {
		itsElements.add(element);
		return this;
	}
	
	/**	Render this bundle into a stream. */
	@Override void render(Formatter formatter) throws IOException {
		//	Put out the header information.
		formatter.emitString(Manifest.BUNDLE);
		formatter.emitLong(itsTimestamp);
		
		//	Put out the nested elements. The elements are completely rendered
		//	into byte arrays because each one is preceded by its length.
		
		for (Enumeration<Element> e = itsElements.elements();
			 e.hasMoreElements();
			) {
			Element elem = e.nextElement();
			byte[] elemBytes = elem.toByteArray();
			
			formatter.emitInteger(elemBytes.length);
			formatter.emitBytes(elemBytes);
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
		final Bundle other = (Bundle) obj;
		if (itsElements == null) {
			if (other.itsElements != null)
				return false;
		} else if (!itsElements.equals(other.itsElements))
			return false;
		if (itsTimestamp != other.itsTimestamp)
			return false;
		return true;
	}

	@Override
	public void getMessages(Date enclosingDate00, List<Pair<Date, Message>> dest) {
		Date d = Timing.unTimestamp00(itsTimestamp);

		for (Element e: itsElements) {
			e.getMessages(d, dest);
		}
	}
}
