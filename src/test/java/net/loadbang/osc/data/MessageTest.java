//	$Id$
//	$Source$

package net.loadbang.osc.data;

import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MessageTest {
	@Test
	public void testLength0() throws IOException {
		assertEquals("length0.hello", 12, new Message("/hello").toByteArray().length);
			//	Three words: two for "/hello", one for "," (the type tag string).
		
		assertEquals("length0.helloX", 16, new Message("/helloXX").toByteArray().length);
			//	Make sure an eight-character message trips to the next word.
	}
	
	@Test
	public void testLengthAtom() throws IOException {
		assertEquals("length1.int", 12, new Message("/A").addInteger(1).toByteArray().length);
		assertEquals("length1.long", 16, new Message("/A").addLong(1).toByteArray().length);
		assertEquals("length1.float", 12, new Message("/A").addFloat(1).toByteArray().length);
		assertEquals("length1.double", 16, new Message("/A").addDouble(1).toByteArray().length);

		assertEquals("length1.string1", 12, new Message("/A").addString("").toByteArray().length);
			//	"/A"; ",s"; <nulls>
		assertEquals("length1.string2", 16,
					new Message("/A").addString("abcd").toByteArray().length
				   );
			//	"/A"; ",s"; "abcd"; <nulls>
	}
	
	@Test
	public void testLengthTags() throws IOException {
		assertEquals("tags.ii", 16,
					new Message("/A").addInteger(1).addInteger(1).toByteArray().length
				   );
			//	"/A"; ",ii"; <int>; <int>
		assertEquals("tags.ddd", 36,
					new Message("/A").addDouble(1).addDouble(1).addDouble(1).toByteArray().length
			   	   );
		//	"/A"; ",ddd"; <nulls>; <double>; <double>; <double>
	}
	
	@Test
	public void testTypeArguments() {
		Message m = new Message("/A");
		m.addString("x").addInteger(49).addFloat(2.17f);
		
		assertEquals("type arguments", ",sif", m.assembleTypeTags());
	}
}
