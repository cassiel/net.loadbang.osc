//	$Id$
//	$Source$

package net.loadbang.osc.exn;

/**	Exception for communication/network errors.

	@author Nick Rothwell, nick@cassiel.com / nick@loadbang.net
 */

public class DataException extends Exception {
	public DataException(String message, Exception cause) {
		super(message, cause);
	}
	
	public DataException(String message) {
		this(message, null);
	}
}
