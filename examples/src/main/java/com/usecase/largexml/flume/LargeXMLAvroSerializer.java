package com.usecase.largexml.flume;

import java.io.OutputStream;

import org.apache.avro.Schema;
import org.apache.flume.Event;
import org.apache.flume.serialization.AbstractAvroEventSerializer;
import org.apache.flume.serialization.EventSerializer;
import org.apache.flume.Context;

public class LargeXMLAvroSerializer extends AbstractAvroEventSerializer<Event> {

	//private static final Schema SCHEMA = new Schema.Parser().parse(
	//		\"{ \\"type\\":\\"record\\", \\"name\\": \\"Event\\", \\"fields\\": [\" +
	//		\" {\\"name\\": \\"headers\\", \\"type\\": { \\"type\\": \\"map\\", \\"values\\": \\"string\\" } }, \" +
	//		\" {\\"name\\": \\"body\\", \\"type\\": \\"bytes\\" } ] }\");
	
	
	String text  = "{ " +
  "\"name\" : \"_Date\", " +
  "\"namespace\" : \"my.generated.avro\", " +
  "\"type\" : \"record\", " +
  "\"doc\" : \"A date stored as a timestamp, in ms UTC.\", " +
 " \"fields\" : [ { " +
  "  \"name\" : \"timestamp\", " +
  " \"type\" : \"long\" " +
 // "  \"default\" : \"0\" " +
  "} ] " +
" }{ " +
  "\"name\" : \"AccountGValueDateMValueDate\", " +
  " \"namespace\" : \"my.generated.avro\", " +
  "\"type\" : \"record\", " +
  "\"fields\" : [ { " +
  "  \"name\" : \"valueDate\", " +
  "  \"type\" : \"my.generated.avro._Date\" " +
  "}, { " +
  "  \"name\" : \"creditMovement\", " +
  "  \"type\" : \"float\", " +
  "  \"default\" : \"0.0\" " +
  "}, { " +
   " \"name\" : \"valueDatedBal\", " +
   " \"type\" : \"int\", " +
   " \"default\" : \"0\" " +
  "} ] " +
"}{ " +
 " \"name\" : \"AccountGInputter\", " +
  "\"namespace\" : \"my.generated.avro\", " +
  "\"type\" : \"record\", " +
  "\"fields\" : [ { " +
  "  \"name\" : \"inputter\",  " +
   " \"type\" : \"string\", " +
    "\"default\" : \"\" " +
  "} ] " +
"}{ " +
 " \"name\" : \"AccountBalance\", " +
 " \"namespace\" : \"my.generated.avro\", " +
  "\"type\" : \"record\",  " +
  "\"fields\" : [ { " +
   " \"name\" : \"onlineActualBal\", " +
   " \"type\" : \"int\", " +
   " \"default\" : \"0\" " +
  "}, { " +
   " \"name\" : \"onlineClearedBal\", " +
   " \"type\" : \"int\", " +
   " \"default\" : \"0\" " +
  "}, { " +
   " \"name\" : \"workingBalance\", " +
   " \"type\" : \"int\", " +
    "\"default\" : \"0\" " +
  "} ] " +
"}{ " +
 " \"name\" : \"AccountHoldingsPosition\", " +
  "\"namespace\" : \"my.generated.avro\", " +
  "\"type\" : \"record\", " +
  "\"fields\" : [ { " +
  "  \"name\" : \"symbol\", " +
  "  \"type\" : \"string\", " +
  "  \"default\" : \"\" " +
  "}, { " +
  "  \"name\" : \"name\", " +
  "  \"type\" : \"string\", " +
   " \"default\" : \"\" " +
  "}, { " +
   " \"name\" : \"type\", " +
   " \"type\" : \"string\", " +
   " \"default\" : \"\" " +
  "}, { " +
   " \"name\" : \"quantity\", " +
   " \"type\" : \"float\", " +
   " \"default\" : \"0.0\" " +
  "} ] " +
"}{ " +
 " \"name\" : \"AccountGValueDate\", " +
  "\"namespace\" : \"my.generated.avro\", " +
  "\"type\" : \"record\", " +
  "\"fields\" : [ { " +
  "  \"name\" : \"mValueDate\"" +
 // "  \"type\" : [ \"null\", { " +
  "    \"type\" : \"array\", " +
   "   \"items\" : \"my.generated.avro.AccountGValueDateMValueDate\" " +
   " } ] " +
 //  " \"default\" : \"null\" " +
 " } ] " +
"}{ " +
 " \"name\" : \"AccountHoldings\", " +
  "\"namespace\" : \"my.generated.avro\", " +
  "\"type\" : \"record\", " +
 " \"fields\" : [ { " +
  "  \"name\" : \"position\", " +
   " \"type\" : [ \"null\", { " +
    "  \"type\" : \"array\", " +
     " \"items\" : \"my.generated.avro.AccountHoldingsPosition\" " +
   " } ], " +
   " \"default\" : \"null\" " +
  "} ] " +
"}{ " +
 " \"name\" : \"Account\", " +
  "\"namespace\" : \"my.generated.avro\", " +
  "\"type\" : \"record\", " +
  "\"fields\" : [ { " +
   " \"name\" : \"category\", " +
   " \"type\" : \"int\", " +
   " \"default\" : \"0\" " +
  "}, { " +
  "  \"name\" : \"accountTitle\", " +
   " \"type\" : \"string\", " +
   " \"default\" : \"\" " +
  "}, { " +
  "  \"name\" : \"shortTitle\", " +
  "  \"type\" : \"string\", " +
  "  \"default\" : \"\" " +
  "}, { " +
  "  \"name\" : \"mnemonic\", " +
   " \"type\" : \"string\", " +
   " \"default\" : \"\" " +
  "}, { " +
  "  \"name\" : \"currency\", " +
  "  \"type\" : \"string\", " +
  "  \"default\" : \"\" " +
  "}, { " +
   " \"name\" : \"currencyMarket\", " +
   " \"type\" : \"int\", " +
   " \"default\" : \"0\" " +
  "}, { " +
  "  \"name\" : \"openingDate\", " +
  "  \"type\" : \"my.generated.avro._Date\" " +
  "}, { " +
   " \"name\" : \"accountOfficer\", " +
   " \"type\" : \"string\", " +
   " \"default\" : \"\" " +
  "}, { " +
  "  \"name\" : \"lastUpdate\", " +
  "  \"type\" : \"my.generated.avro._Date\" " +
  "}, { " +
  "  \"name\" : \"balance\", " +
   " \"type\" : \"my.generated.avro.AccountBalance\" " +
  "}, { " +
  "  \"name\" : \"passbook\", " +
   " \"type\" : \"string\", " +
   " \"default\" : \"\" " +
  "}, { " +
  "  \"name\" : \"gValueDate\", " +
  "  \"type\" : \"my.generated.avro.AccountGValueDate\" " +
  "}, { " +
  "  \"name\" : \"chargeCcy\", " +
  "  \"type\" : \"string\", " +
  "  \"default\" : \"\" " +
  "}, { " +
  "  \"name\" : \"interestCcy\", " +
  "  \"type\" : \"string\", " +
  "  \"default\" : \"\" " +
  "}, { " +
  "  \"name\" : \"allowNetting\", " +
  "  \"type\" : \"string\", " +
  "  \"default\" : \"\" " +
  "}, { " +
  "  \"name\" : \"gInputter\", " +
  "  \"type\" : \"my.generated.avro.AccountGInputter\" " +
  "}, { " +
  "  \"name\" : \"holdings\", " +
   " \"type\" : \"my.generated.avro.AccountHoldings\" " + 
  "}, { " +
  "  \"name\" : \"id\", " +
  "  \"type\" : [ \"null\", \"long\" ], " +
  "  \"default\" : \"null\" " +
  "} ] " +
"}";
	
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
