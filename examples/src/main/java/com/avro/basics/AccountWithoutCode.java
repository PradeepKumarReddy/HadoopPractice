package com.avro.basics;

import java.io.File;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;

public class AccountWithoutCode {

	public static void main(String[] a) throws IOException {

		Schema schema = new Schema.Parser().parse(new File("account.avsc"));
		GenericRecord act1 = new GenericData.Record(schema);
		act1.put("name", "act3");
		act1.put("category", "cat3");
		act1.put("accountTitle", "title3");
		
		File file = new File("account2.avro");
		DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(
				schema);
		DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(
				datumWriter);
		dataFileWriter.create(schema, file);
		dataFileWriter.append(act1);
		dataFileWriter.close();
	}

}
