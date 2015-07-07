package com.mapred.practice.examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.MapDriver;
import org.apache.hadoop.mrunit.MapReduceDriver;
import org.apache.hadoop.mrunit.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WordCountMapperTest {

	MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;
	ReduceDriver<Text, IntWritable, Text, IntWritable> reduceDriver;
	MapReduceDriver mapReduceDriver;
	
	@Before
	public void setup() {
		WordCountMapper mapper = new WordCountMapper();
		WordCountReducer reducer = new WordCountReducer();
		mapDriver = MapDriver.newMapDriver(mapper);
		reduceDriver = ReduceDriver.newReduceDriver(reducer);
		mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
	}
	
	@Test
	public void testMap() throws IOException {
		mapDriver.withInput(new LongWritable(), new Text("test mrunit first time. but .."));
		mapDriver.withOutput(new Text("test"), new IntWritable(1));
		List<Pair<Text, IntWritable>> result = mapDriver.run();
		//mapDriver.runTest();
		assertEquals(6, result.size());
	}
	
	@Test
	public void testReducer() throws IOException {
		List<IntWritable> ls = new ArrayList<IntWritable>();
		ls.add(new IntWritable(1));
		ls.add(new IntWritable(1));
		ls.add(new IntWritable(1));
		ls.add(new IntWritable(1));
		
		Pair<Text, List<IntWritable>> input = new Pair<Text, List<IntWritable>>(new Text("test"), ls );

		reduceDriver.withInput(input);
		reduceDriver.withOutput(new Text("test"), new IntWritable(4));
		List<Pair<Text, IntWritable>> result = reduceDriver.run();
		
		assertEquals(4, result.get(0).getSecond().get());
	}
	
	@Test
	public void testMapReduce() throws IOException {
		mapReduceDriver.withInput(new LongWritable(), new Text("test mrunit first time. but .."));
		
		List<Pair<Text, IntWritable>> result = mapReduceDriver.run();
		assertEquals(4, result.get(0).getSecond().get());
		
	}
	
}
