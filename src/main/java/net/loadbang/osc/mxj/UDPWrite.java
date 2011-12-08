//	$Id$
//	$Source$

package net.loadbang.osc.mxj;

import net.loadbang.osc.UDPWriterProxy;

public class UDPWrite extends IPWrite {
	public UDPWrite() {
		super(new UDPWriterProxy());
	}
}
