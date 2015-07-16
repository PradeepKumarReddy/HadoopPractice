package com.avro.basics;

import java.io.File;
import java.io.IOException;

import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;

public class AccountWithCode {
	public static void main(String[] a) throws IOException {

		Account act1 = new Account("ACT1", "CAT1", "TITLE1");
		Account act2 = Account.newBuilder().setName("ACT2").setCategory("CAT2").setAccountTitle("TITLE2").build();
		// Serialize emp1 and emp2 to disk
		File file = new File("account.avro");
		DatumWriter<Account> userDatumWriter = new SpecificDatumWriter<Account>(
				Account.class);
		DataFileWriter<Account> dataFileWriter = new DataFileWriter<Account>(
				userDatumWriter);

		dataFileWriter.create(act1.getSchema(), file);

		dataFileWriter.append(act1);
		dataFileWriter.append(act2);
		dataFileWriter.close();
	}
}
