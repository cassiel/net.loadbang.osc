//	$Id$
//	$Source$

package net.loadbang.osc.util;

import java.util.Date;

/**	Some time-handling tools.

	@author Nick Rothwell, nick@cassiel.com / nick@loadbang.net
 */

public class Timing {
	/**	Seconds between 1900 and 1970 (UNIX epoch), allowing for 17 leap years. */
	static private final long SECONDS_FROM_1900_to_1970 = 2208988800L;

	/**	Conversion value (approximate) to build fractional timestamp field. */
	static private final long TWO_TO_THE_32_OVER_1000 = (1L << 32) / 1000;

	/**	Turn a Java date into an OSC/NTP timestamp value.

		I think Ramakrishnan's timestamp calculations are misconceived: the
		lower 32 bits of the value are the decimal part of a fixpoint number
		of seconds, and not a picosecond counter. I'm going back to Matt Wright's
		C code as a reference.
	 */

	static public long makeTimestamp(Date date) {
		long millisecs = date.getTime();

		long secsSince1970 = millisecs / 1000;
		long timestampSecs = secsSince1970 + SECONDS_FROM_1900_to_1970;
			//	That'll fit in 32 bits, but only if unsigned (bit 31 is 1).
		
		//	Millisecond part:
		int fractionMsec = (int) (millisecs % 1000);
		
		//	Millisecond part as bit pattern, 32-bit unsigned:
		long fraction = fractionMsec * TWO_TO_THE_32_OVER_1000;
		
		//	Final result. I think this all works out in longs using unsigned left-shift,
		//	without resorting to long->int narrowing.
		return (timestampSecs << 32) | fraction;
	}
	
	static public Date unTimestamp00(long stamp) {
		if (stamp == Manifest.TIMESTAMP_NOW) {
			return null;
		} else {
			long fraction = stamp & 0x00000000FFFFFFFF;
			long timestampSecs = stamp >> 32;

			int fractionMSec = (int) (fraction / TWO_TO_THE_32_OVER_1000);
			long secsSince1970 = timestampSecs - SECONDS_FROM_1900_to_1970;
			
			return new Date(secsSince1970 * 1000 + fractionMSec);
		}
	}
}
