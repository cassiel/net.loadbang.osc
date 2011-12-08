//	$Id$
//	$Source$

package net.loadbang.osc.exn;

/**	Exception for communication/network errors.

	@author Nick Rothwell, nick@cassiel.com / nick@loadbang.net
 */

public class CommsException extends Exception {
	public CommsException(String message, Exception cause) {
		super(message, cause);
	}
	
	public CommsException(String message) {
		this(message, null);
	}
}
