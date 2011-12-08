//	$Id$
//	$Source$

package net.loadbang.osc.data;

import java.io.ByteArrayOutputStream;

import net.loadbang.osc.FormattingHelper;
import net.loadbang.osc.util.Formatter;

import org.junit.Test;

public class BundleTest {
	@Test
	public void bundlesASingleInteger() throws Exception {
		Bundle b = new Bundle();				//	Timestamp == now().
		
		b.add(new Message("/I").addInteger(37));
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Formatter f = new Formatter(os);

		b.render(f);
		
		FormattingHelper.compare(os,
								 '#', 'b', 'u', 'n', 'd', 'l', 'e', 0,
								 0, 0, 0, 0, 0, 0, 0, 1,			//	Time-tag: now()
								 0, 0, 0, 12,						//	First element size (bytes)
								 '/', 'I', 0, 0,					//	Message
								 ',', 'i', 0, 0,					//	Type tags: integer
								 0, 0, 0, 37						//	Value
								);
	}
	
	@Test
	public void bundlesTwoMessages() throws Exception {
		Bundle b = new Bundle();				//	Timestamp == now().
		
		b.add(new Message("/mess1").addInteger(37).addString("foo"))
		 .add(new Message("/mess2").addString("X").addString("YYYY").addString("ZZZ"));
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Formatter f = new Formatter(os);

		b.render(f);
		
		FormattingHelper.compare(os,
								 '#', 'b', 'u', 'n', 'd', 'l', 'e', 0,
								 0, 0, 0, 0, 0, 0, 0, 1,			//	Time-tag: now()

								 0, 0, 0, 20,						//	First element size (bytes)
								 '/', 'm', 'e', 's', 's', '1', 0, 0,	//	Message
								 ',', 'i', 's', 0,					//	Type tags: integer * string
								 0, 0, 0, 37,						//	Value
								 'f', 'o', 'o', 0,

								 0, 0, 0, 32,						//	Second element size (bytes)
								 '/', 'm', 'e', 's', 's', '2', 0, 0,	//	Message
								 ',', 's', 's', 's', 0, 0, 0, 0,	//	Type tags: string * string * string
								 'X', 0, 0, 0,						//	"X"
								 'Y', 'Y', 'Y', 'Y', 0, 0, 0, 0,	//	"YYYY"
								 'Z', 'Z', 'Z', 0					//	"ZZZ"
								);
	}

	@Test
	public void handlesRecursiveBundles() throws Exception {
		Bundle b = new Bundle();				//	Timestamp == now().
		
		b.add(new Bundle().add(new Message("/mess1")));
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Formatter f = new Formatter(os);

		b.render(f);
		
		FormattingHelper.compare(os,
								 '#', 'b', 'u', 'n', 'd', 'l', 'e', 0,
								 0, 0, 0, 0, 0, 0, 0, 1,			//	Time-tag: now()

								 0, 0, 0, 32,						//	First element size (bytes)
								 
								 '#', 'b', 'u', 'n', 'd', 'l', 'e', 0,
								 0, 0, 0, 0, 0, 0, 0, 1,			//	Time-tag: now()
								 0, 0, 0, 12,
								 '/', 'm', 'e', 's', 's', '1', 0, 0,	//	Message
								 ',', 0, 0, 0							//	Type tag string.
								);
	}
}
