package com.avro.serialization.practice;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.commons.io.FileUtils;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.serialization.EventSerializer;
import org.apache.flume.serialization.EventSerializerFactory;
import org.apache.flume.source.SyslogUtils;
import org.junit.Assert;
import org.junit.Test;

public class TestXmlAvroEventSerializer {
	File testFile = new File("src/test/resources/AccountEvents.avro");
	File schemaFile = new File("src/test/resources/account_xml.avsc");

	private static List<Event> generateAccountEvents() {
		List<Event> list = Lists.newArrayList();
		Event e;
		
		// generate one that we supposedly parsed with SyslogTcpSource
		e = EventBuilder.withBody("<Account id=\"93584187422\" xmlns=\"http://tpox-benchmark.com/custacc\"><Category>6</Category><AccountTitle>CHF</AccountTitle><ShortTitle>null CHF</ShortTitle><Mnemonic>nullCHF</Mnemonic><Currency>CHF</Currency>", Charsets.UTF_8);
		//e.getHeaders().put(SyslogUtils.SYSLOG_FACILITY, "1");
		//e.getHeaders().put(SyslogUtils.SYSLOG_SEVERITY, "2");
		list.add(e);
		// generate another supposedly parsed with SyslogTcpSource with 2-digit
		// date
		e = EventBuilder.withBody("<Account id=\"93584187422\" xmlns=\"http://tpox-benchmark.com/custacc\"><Category>7</Category><AccountTitle>CHF7</AccountTitle><ShortTitle>null CHF</ShortTitle><Mnemonic>nullCHF</Mnemonic><Currency>CHF</Currency>", Charsets.UTF_8);
		//e.getHeaders().put(SyslogUtils.SYSLOG_FACILITY, "1");
		//e.getHeaders().put(SyslogUtils.SYSLOG_SEVERITY, "3");
		list.add(e);
		return list;
	}

	@Test
	public void test() throws FileNotFoundException, IOException {
		// Schema schema = new Schema.Parser().parse(schemaFile);
		// create the file, write some data
		OutputStream out = new FileOutputStream(testFile);
		String builderName = XmlAvroSerializer.Builder.class.getName();
		Context ctx = new Context();
		ctx.put("syncInterval", "4096");
		ctx.put("compressionCodec", "snappy");
		EventSerializer serializer = EventSerializerFactory.getInstance(
				builderName, ctx, out);
		serializer.afterCreate(); // must call this when a file is newly created
		List<Event> events = generateAccountEvents();
		for (Event e : events) {
			serializer.write(e);
		}
		serializer.flush();
		serializer.beforeClose();
		out.flush();
		out.close();
		// now try to read the file back
		DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>();
		DataFileReader<GenericRecord> fileReader = new DataFileReader<GenericRecord>(
				testFile, reader);
		GenericRecord record = new GenericData.Record(fileReader.getSchema());
		int numEvents = 0;
		while (fileReader.hasNext()) {
			fileReader.next(record);
			int category = (Integer) record.get("category");
			String accountTitle = record.get("accountTitle").toString();
			String shortTitle = record.get("shortTitle").toString();
			String hostname = record.get("mnemonic").toString();
			String message = record.get("currency").toString();
			//Assert.assertEquals("Facility should be 1", 1, category);
			numEvents++;
		}
		fileReader.close();
		//Assert.assertEquals("Should have found a total of 3 events", 3,	numEvents);
		FileUtils.forceDelete(testFile);
	}
}
