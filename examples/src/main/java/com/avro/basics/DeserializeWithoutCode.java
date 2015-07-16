package com.avro.basics;

import java.io.File;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;

public class DeserializeWithoutCode {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Schema schema = new Schema.Parser().parse(new File("account.avsc"));
		GenericRecord emp = new GenericData.Record(schema);
		File file = new File("account2.avro");
		DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>(
				schema);
		DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(
				file, datumReader);

		while (dataFileReader.hasNext()) {
			emp = dataFileReader.next();
			System.out.println(emp);
		}
	}

}
