package com.mapred.practice.examples;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class WordCountMapper extends MapReduceBase implements
		Mapper<LongWritable, Text, Text, IntWritable> {

	private final static IntWritable one = new IntWritable(1);
	Text text = new Text();

	public void map(LongWritable inputKey, Text inputValue,
			OutputCollector<Text, IntWritable> output, Reporter report)
			throws IOException {
		String line = inputValue.toString();
		StringTokenizer stringTokenizer = new StringTokenizer(line);
		while (stringTokenizer.hasMoreTokens()) {
			text.set(stringTokenizer.nextToken());
			output.collect(text, one);
		}
	}

}
