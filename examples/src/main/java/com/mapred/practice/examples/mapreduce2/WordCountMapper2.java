package com.mapred.practice.examples.mapreduce2;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper2 extends
		Mapper<LongWritable, Text, Text, IntWritable> {
	private final static IntWritable one = new IntWritable(1);
	Text text = new Text();
	public void map(LongWritable key, Text value, Context context) throws IOException,
			InterruptedException {
		String line = value.toString();
		StringTokenizer stringTokenizer = new StringTokenizer(line);
		while (stringTokenizer.hasMoreTokens()) {
			text.set(stringTokenizer.nextToken());
			context.write(text, one);
		}
		
	}

}
