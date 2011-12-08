//	$Id$
//	$Source$

package net.loadbang.osc.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

import net.loadbang.osc.FormattingHelper;

public class FormatterTest {
	@Test
	public void padsString() throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Formatter f = new Formatter(os);
		
		f.emitString("Hello");
		FormattingHelper.compare(os, 'H', 'e', 'l', 'l', 'o', 0, 0, 0);
	}
	
	@Test
	public void formatsInteger() throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Formatter f = new Formatter(os);
		
		f.emitInteger(300);
		FormattingHelper.compare(os, 0, 0, 1, 44);
	}
}
