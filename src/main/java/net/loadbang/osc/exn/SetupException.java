//	$Id$
//	$Source$

package net.loadbang.osc.exn;

/**	Exception for general configuration errors.

	@author Nick Rothwell, nick@cassiel.com / nick@loadbang.net
 */

public class SetupException extends Exception {
	public SetupException(String message, Exception cause) {
		super(message, cause);
	}
	
	public SetupException(String message) {
		this(message, null);
	}
}
