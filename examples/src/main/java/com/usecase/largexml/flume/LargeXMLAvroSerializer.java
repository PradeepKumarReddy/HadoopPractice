package com.usecase.largexml.flume;

import java.io.OutputStream;

import org.apache.avro.Schema;
import org.apache.flume.Event;
import org.apache.flume.serialization.AbstractAvroEventSerializer;
import org.apache.flume.serialization.EventSerializer;
import org.apache.flume.Context;

public class LargeXMLAvroSerializer extends AbstractAvroEventSerializer<Event> {

	//private static final Schema SCHEMA = new Schema.Parser().parse(
	//		\\"{ \\\"type\\\":\\\"record\\\", \\\"name\\\": \\\"Event\\\", \\\"fields\\\": [\\" +
	//		\\" {\\\"name\\\": \\\"headers\\\", \\\"type\\\": { \\\"type\\\": \\\"map\\\", \\\"values\\\": \\\"string\\\" } }, \\" +
	//		\\" {\\\"name\\\": \\\"body\\\", \\\"type\\\": \\\"bytes\\\" } ] }\\");
	
	String text = 		  " { " +
			  "\"name\" : \"Account\",  " +
			  "\"namespace\" : \"my.generated.avro\",  " +
			  "\"type\" : \"record\",  " + 
			  "\"fields\" : [ {  " +
			  "  \"name\" : \"category\",  " +
			  "  \"type\" : \"int\",  " +
			  "  \"default\" : \"0\"  " +
			  "}, {  " + 
			  "  \"name\" : \"accountTitle\", " +
			  "  \"type\" : \"string\", " +
			  "  \"default\" : \"\" " +
			  "}, { " +
			  "  \"name\" : \"shortTitle\", " +
			  "  \"type\" : \"string\", " +
			  "  \"default\" : \"\" " +
			  "}, { " +
			  "  \"name\" : \"mnemonic\", " +
			  "  \"type\" : \"string\", " +
			  "  \"default\" : \"\" " +
			  "}, { " +
			  "  \"name\" : \"currency\", " +
			  "  \"type\" : \"string\", " +
			  "  \"default\" : \"\" " +
			  "}, { " +
			  "  \"name\" : \"currencyMarket\", " +
			  "  \"type\" : \"int\", " +
			  "  \"default\" : \"0\" " +
			  "}, { " +
			  "  \"name\" : \"openingDate\", " +
			  "  \"type\" : \"string\" " +
			  "}, { " +
			  "  \"name\" : \"accountOfficer\", " +
			  "  \"type\" : \"string\", " +
			  "  \"default\" : \"\" " +
			  "}, { " +
			  "  \"name\" : \"lastUpdate\", " +
			  "  \"type\" : \"string\" " +
			  "}, { " +
			  "  \"name\" : \"balance\", " +
			  "  \"type\" : { " +
			  "  \"name\" : \"AccountBalance\", " +
			  "\"namespace\" : \"my.generated.avro\", " +
			  "\"type\" : \"record\", " +
			  "\"fields\" : [ { " +
			  "  \"name\" : \"onlineActualBal\", " +
			  "  \"type\" : \"int\", " +
			  "  \"default\" : \"0\" " +
			  "}, { " +
			  "  \"name\" : \"onlineClearedBal\", " +
			  "  \"type\" : \"int\", " +
			  "  \"default\" : \"0\" " +
			  "}, { " +
			  "  \"name\" : \"workingBalance\", " +
			  "  \"type\" : \"int\", " +
			  "  \"default\" : \"0\" " +
			  "} ] " +
			" } " +
			 " }, { " +
			 "   \"name\" : \"passbook\", " +
			 "   \"type\" : \"string\", " +
			 "   \"default\" : \"\" " +
			 " }, { " +
			 "   \"name\" : \"gValueDate\", " +
			 "   \"type\" : \"string\" " +
			 " }, { " +
			 "   \"name\" : \"chargeCcy\", " +
			 "   \"type\" : \"string\", " +
			 "   \"default\" : \"\" " +
			 " }, { " +
			 "   \"name\" : \"interestCcy\", " +
			 "   \"type\" : \"string\", " +
			 "   \"default\" : \"\" " +
			 " }, { " +
			 "   \"name\" : \"allowNetting\", " +
			 "   \"type\" : \"string\", " +
			 "   \"default\" : \"\" " +
			 " }, { " +
			 "   \"name\" : \"gInputter\", " +
			 "   \"type\" : \"string\" " +
			 " }, { " +
			 "   \"name\" : \"holdings\", " +
			 "   \"type\" : \"string\" " +
			 " }, { " +
			 "   \"name\" : \"id\", " +
			 "   \"type\" : [ \"null\", \"long\" ], " +
			 "   \"default\" : \"null\" " +
			 "   } ] " +
			 " } " ;
	
	private Schema SCHEMA = new Schema.Parser().parse(text);
		
	private final OutputStream out;

	private LargeXMLAvroSerializer(OutputStream out) {
	
		this.out = out;
	}

	@Override
	protected Schema getSchema() {
		return SCHEMA;
	}

	@Override
	protected OutputStream getOutputStream() {
		return out;
	}

	/**
	 * A no-op for this simple, special-case implementation
	 * 
	 * @param event
	 * @return
	 */
	@Override
	protected Event convert(Event event) {
		System.out.println("event " +event.toString());
		
		System.out.println("header " +event.getHeaders());
		System.out.println("body " +event.getBody());
		return event;
	}

	public static class Builder implements EventSerializer.Builder {
		
		public EventSerializer build(Context context, OutputStream out) {
			LargeXMLAvroSerializer writer = new LargeXMLAvroSerializer(
					out);
			writer.configure(context);
			return writer;
		}
	}

}
