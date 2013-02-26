package net.loadbang.osc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import net.loadbang.osc.data.Message;
import net.loadbang.osc.exn.DataException;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class PacketProcessorTest {
	private Mockery itsContext = new JUnit4Mockery();

	@Test
	public void canParseSimpleMessage() throws Exception {
		final MessageConsumer mc = itsContext.mock(MessageConsumer.class);

		itsContext.checking(new Expectations() {{
			one(mc).consumeMessage(null, null, new Message("/I").addInteger(37));
		}});

		PacketProcessor processor = new PacketProcessor(mc);
		
		processor.consumePacket(
			null,
			FormattingHelper.makeByteArray(
				'/', 'I', 0, 0,
				',', 'i', 0, 0,
				0, 0, 0, 37
			)
		);
	}
	
	@Test
	public void throwsCleanExceptionOnEOD() {
		final MessageConsumer mc = itsContext.mock(MessageConsumer.class);

		itsContext.checking(new Expectations() {{
		}});

		PacketProcessor processor = new PacketProcessor(mc);
		
		try {
			processor.consumePacket(
				null,
				FormattingHelper.makeByteArray(
					'/', 'I', 0, 0,
					',', 'i', 0, 0
				)
			);

			fail("exception expected");
		} catch (DataException exn) {
			assertEquals(exn.getMessage(), "buffer underflow", exn.getMessage());
		}
	}
	
	@Test
	public void detectsExtraCharacters() {
		final MessageConsumer mc = itsContext.mock(MessageConsumer.class);

		itsContext.checking(new Expectations() {{
		}});

		PacketProcessor processor = new PacketProcessor(mc);
		
		try {
			processor.consumePacket(
				null,
				FormattingHelper.makeByteArray(
					'/', 'I', 0, 0,
					',', 'i', 0, 0,
					0, 0, 0, 37,
					'x', 'x', 'x', 'x'
				)
			);

			fail("exception expected");
		} catch (DataException exn) {
			assertEquals(exn.getMessage(), "buffer not exhausted (12/16)", exn.getMessage());
		}
	}
	
	@Ignore
	public void canParseSimpleMessageWithNoTypeTags() throws Exception {
		final MessageConsumer mc = itsContext.mock(MessageConsumer.class);

		itsContext.checking(new Expectations() {{
			one(mc).consumeMessage(null, new Message("/I").addInteger(37));
		}});

		PacketProcessor processor = new PacketProcessor(mc);
		
		processor.consumePacket(
			null,
			FormattingHelper.makeByteArray(
				'/', 'I', 0, 0,
				0, 0, 0, 37
			)
		);
	}

	@Ignore
	public void canParseSimpleMessageWithEscapedString() throws Exception {
		final MessageConsumer mc = itsContext.mock(MessageConsumer.class);

		itsContext.checking(new Expectations() {{
			one(mc).consumeMessage(null, new Message("/st").addString(",X"));
		}});

		PacketProcessor processor = new PacketProcessor(mc);
		
		processor.consumePacket(
			null,
			FormattingHelper.makeByteArray(
				'/', 's', 't', 0,
				',', ',', 'X', 0
			)
		);
	}
	
	@Test
	public void canParseSimpleBundle() throws Exception {
		final MessageConsumer mc = itsContext.mock(MessageConsumer.class);

		itsContext.checking(new Expectations() {{
			one(mc).consumeMessage(null, null, new Message("/I").addInteger(37));
		}});

		PacketProcessor processor = new PacketProcessor(mc);
		
		processor.consumePacket(
			null,
			FormattingHelper.makeByteArray(
				'#', 'b', 'u', 'n', 'd', 'l', 'e', 0,
				 0, 0, 0, 0, 0, 0, 0, 1,			//	Time-tag: now()
				 0, 0, 0, 12,						//	First element size (bytes)
				 '/', 'I', 0, 0,					//	Message
				 ',', 'i', 0, 0,					//	Type tags: integer
				 0, 0, 0, 37						//	Value
			)
		);
	}

	@Test
	public void canParseTwoMessageBundle() throws Exception {
		final MessageConsumer mc = itsContext.mock(MessageConsumer.class);

		itsContext.checking(new Expectations() {{
			one(mc).consumeMessage(null, null,
								   new Message("/mess1").addInteger(37).addString("foo")
								  );
			one(mc).consumeMessage(null, null,
					   			   new Message("/mess2").addString("X").addString("YYYY").addString("ZZZ")
					  			  );
		}});

		PacketProcessor processor = new PacketProcessor(mc);
		
		processor.consumePacket(
			null,
			FormattingHelper.makeByteArray(
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
			)
		);
	}
	
	@Test
	public void canParseRecursiveBundle() throws Exception {
		final MessageConsumer mc = itsContext.mock(MessageConsumer.class);

		itsContext.checking(new Expectations() {{
			one(mc).consumeMessage(null, null, new Message("/mess1"));
		}});

		PacketProcessor processor = new PacketProcessor(mc);
		
		processor.consumePacket(
			null,
			FormattingHelper.makeByteArray(
				'#', 'b', 'u', 'n', 'd', 'l', 'e', 0,
				 0, 0, 0, 0, 0, 0, 0, 1,			//	Time-tag: now()

				 0, 0, 0, 32,						//	First element size (bytes)
				 
				 '#', 'b', 'u', 'n', 'd', 'l', 'e', 0,
				 0, 0, 0, 0, 0, 0, 0, 1,			//	Time-tag: now()
				 0, 0, 0, 12,
				 '/', 'm', 'e', 's', 's', '1', 0, 0,	//	Message
				 ',', 0, 0, 0							//	Type tag string.
			)
		);
	}
}
