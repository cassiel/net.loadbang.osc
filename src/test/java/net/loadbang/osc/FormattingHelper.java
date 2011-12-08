//	$Id$
//	$Source$

package net.loadbang.osc;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;

public class FormattingHelper {
	public static void compare(ByteArrayOutputStream os, Object... chars) {
		byte[] data = os.toByteArray();
		
		assertEquals("data length", data.length, chars.length);
		
		for (int i = 0; i < data.length; i++) {
			Object obj = chars[i];
			
			if (obj instanceof Integer) {
				assertEquals("bytes: data[" + i + "]", data[i], ((Integer) obj).byteValue());
			} else {
				char c = (Character) obj;
				assertEquals("chars: data[" + i + "]", (char) data[i], c);
			}
		}
	}

	public static byte[] makeByteArray(Object... chars) {
		byte[] result = new byte[chars.length];
		
		for (int i = 0; i < result.length; i++) {
			Object obj = chars[i];

			if (obj instanceof Integer) {
				result[i] = ((Integer) obj).byteValue();
			}  else {
				result[i] = (byte) ((Character) obj).charValue();
			}
		}

		return result;
	}
}
