//	$Id$
//	$Source$

package net.loadbang.osc.mxj;

import net.loadbang.osc.TCPWriterProxy;

public class TCPWrite extends IPWrite {
	public TCPWrite() {
		super(new TCPWriterProxy());
	}
}
