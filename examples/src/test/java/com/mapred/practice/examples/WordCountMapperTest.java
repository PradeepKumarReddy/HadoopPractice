package com.mapred.practice.examples;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.MapDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WordCountMapperTest {

	MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;
	
	@Before
	public void setup() {
		WordCountMapper mapper = new WordCountMapper();
		mapDriver = MapDriver.newMapDriver(mapper);
	}
	
	@Test
	public void testMap() throws IOException {
		mapDriver.withInput(new LongWritable(), new Text("test mrunit first time. but .."));
		mapDriver.withOutput(new Text("test"), new IntWritable(1));
		List<Pair<Text, IntWritable>> result = mapDriver.run();
		//mapDriver.runTest();
		assertEquals(6, result.size());
		
	}
}
