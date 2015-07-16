package com.avro.serialization.practice;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import com.avro.serialization.practice.XmlAvroSerializer.AccountEvent;
import com.google.common.base.Charsets;

import org.apache.avro.Schema;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.serialization.AbstractAvroEventSerializer;
import org.apache.flume.serialization.EventSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class XmlAvroSerializer extends AbstractAvroEventSerializer<AccountEvent> {

	private static final Logger logger = LoggerFactory
			.getLogger(SyslogAvroEventSerializer.class);
	
	String text = "{  \"name\" : \"AccountEvent\",  \"namespace\" : \"com.avro.serialization.practice\",  \"type\" : \"record\",  \"fields\" : [ {    \"name\" : \"category\",    \"type\" : \"int\",    \"default\" :\"0\"  }, {    \"name\" : \"accountTitle\",    \"type\" : \"string\",    \"default\" : \"\"  }, {    \"name\" : \"shortTitle\",    \"type\" : \"string\",    \"default\" : \"\"  }, {    \"name\" : \"mnemonic\",    \"type\" : \"string\",    \"default\" : \"\"  }, {    \"name\" : \"currency\",    \"type\" : \"string\",    \"default\" : \"\"  }  ]}";

	private static final File schemaFile = new File("src/test/resources/account_xml.avsc");
	private final OutputStream out;
	private final Schema schema;

	public XmlAvroSerializer(OutputStream out) throws IOException {
		this.out = out;
		//this.schema = new Schema.Parser().parse(schemaFile);
		this.schema = new Schema.Parser().parse(text);
	}

	@Override
	protected AccountEvent convert(Event event) {
		AccountEvent accountEvent = new AccountEvent();
		System.out.println("event " +event.toString());
		
		System.out.println("header " +event.getHeaders());
		System.out.println("body " +event.getBody());
		String msg = new String(event.getBody(), Charsets.UTF_8);
		System.out.println("msg " + msg);
		accountEvent.setCategory(1);
		accountEvent.setAccountTitle("ACT1");
		return accountEvent;
	}

	@Override
	protected OutputStream getOutputStream() {
		return out;
	}

	@Override
	protected Schema getSchema() {
		return schema;
	}

	public static class Builder implements EventSerializer.Builder {

		public EventSerializer build(Context context, OutputStream out) {
			XmlAvroSerializer writer = null;
			try {
				writer = new XmlAvroSerializer(out);
				writer.configure(context);
			} catch (IOException e) {
				logger.error("Unable to parse schema file. Exception follows.",	e);
			}
			return writer;
		}
	}
	
	public static class AccountEvent {
		private int category;
		private String accountTitle = "";
		private String shortTitle = "";
		private String mnemonic = "";
		private String currency = "";
		public int getCategory() {
			return category;
		}
		public void setCategory(int category) {
			this.category = category;
		}
		public String getAccountTitle() {
			return accountTitle;
		}
		public void setAccountTitle(String accountTitle) {
			this.accountTitle = accountTitle;
		}
		public String getShortTitle() {
			return shortTitle;
		}
		public void setShortTitle(String shortTitle) {
			this.shortTitle = shortTitle;
		}
		public String getMnemonic() {
			return mnemonic;
		}
		public void setMnemonic(String mnemonic) {
			this.mnemonic = mnemonic;
		}
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		@Override
		public String toString() {
			return "AccountEvent [category=" + category + ", accountTitle="
					+ accountTitle + ", shortTitle=" + shortTitle
					+ ", mnemonic=" + mnemonic + ", currency=" + currency + "]";
		}

		
	}

}
