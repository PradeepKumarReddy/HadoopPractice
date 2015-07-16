package com.avro.basics;

import java.io.File;
import java.io.IOException;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;

public class DeserializeWithCode {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		File file = new File("account.avro");
		DatumReader<Account> userDatumReader = new SpecificDatumReader<Account>(Account.class);
		DataFileReader<Account> dataFileReader = new DataFileReader<Account>(file, userDatumReader);
		Account emp = null;
		while (dataFileReader.hasNext())
		{
		emp = dataFileReader.next();
		System.out.println(emp);
		}

	}

}
