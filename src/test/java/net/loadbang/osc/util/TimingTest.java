package net.loadbang.osc.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;


public class TimingTest {
	@Test
	public void dealsWithNow() {
		assertNull(Timing.unTimestamp00(Manifest.TIMESTAMP_NOW));
	}

	@Ignore
	public void roundTrip() {
		Date d = new Date();
		assertEquals(d, Timing.unTimestamp00(Timing.makeTimestamp(d)));
	}
}
