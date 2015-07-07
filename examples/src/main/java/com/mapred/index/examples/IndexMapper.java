package com.mapred.index.examples;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.util.StringUtils;

public class IndexMapper extends Mapper<LongWritable, Text, Text, Text> {
	private Text documentId;
	private Text word = new Text();

	@Override
	protected void setup(Context context) {
		String filename = ((FileSplit) context.getInputSplit()).getPath()
				.getName();
		documentId = new Text(filename);

	}

	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		for (String token : StringUtils.split(value.toString())) {
			word.set(token);
			context.write(word, documentId);
		}

	}

}
