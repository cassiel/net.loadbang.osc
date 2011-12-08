//	$Id$
//	$Source$

package net.loadbang.osc.util;

/**	Some manifest constants.

	@author Nick Rothwell, nick@cassiel.com / nick@loadbang.net
 */

public interface Manifest {
	/**	Header token for bundles. */
	static public final String BUNDLE = "#bundle";

	/**	Special timestamp meaning "now". */
	static public final long TIMESTAMP_NOW = 1L;

	public static final int OSC_MAX_LEN = 1024;
}
