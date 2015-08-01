package com.xml.transformation;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

public class XmlMapperTest {

	MapDriver<LongWritable, Text, Text, NullWritable> mapDriver;

	String inputXml = "<Data><employee><id>1</id><name>PRADEEP</name><gender>M</gender></employee><employee><id>2</id><name>HEMA</name><gender>F</gender></employee><employee><id>3</id><name>RAM</name><gender>M</gender></employee><employee><id>4</id><name>SIVA</name><gender>M</gender></employee></Data>";

	@Before
	public void setup() {
		XmlMapper mapper = new XmlMapper();
		mapDriver = MapDriver.newMapDriver(mapper);
	}

	@Test
	public void testMap() throws IOException {
		mapDriver.addInput(new LongWritable(1L), new Text(inputXml));
		List<Pair<Text, NullWritable>> result = mapDriver.run();

		for (Pair<Text, NullWritable> pair : result) {
			System.out.println(pair.getFirst());
		}
	}

}
