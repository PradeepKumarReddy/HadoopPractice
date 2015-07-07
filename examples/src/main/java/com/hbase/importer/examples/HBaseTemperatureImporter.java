package com.hbase.importer.examples;

import java.io.IOException;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.ncdc.common.NcdcRecordParser;
import com.ncdc.common.RowKeyConverter;

/**
 * 
 * 
 * export HBASE_CLASSPATH='/home/cloudera/workspace/examples/target/examples-0.0.1-SNAPSHOT.jar'
 * 
 * hbase com.hbase.importer.examples.HBaseTemperatureImporter /user/hbase/data/ncdc/all
 * 
 * hbase com.hbase.importer.examples.HBaseTemperatureImporter /home/cloudera/practice/hadoop-book-master/input/ncdc/metadata/stations-fixed-width.txt
 * 
 * 
 * Uses HBase's {@link TableOutputFormat} to load temperature data into a HBase table.
 * 
 * Could not find job application_1432832205693_0001.
 * Job job_1432832205693_0001 could not be found: {"RemoteException":{"exception":"NotFoundException","message":"java.lang.Exception: job, job_1432832205693_0001, is not found","javaClassName":"org.apache.hadoop.yarn.webapp.NotFoundException"}} (error 404)
 * 
 * 
 */
public class HBaseTemperatureImporter extends Configured implements Tool {
  
  static class HBaseTemperatureMapper<K> extends Mapper<LongWritable, Text, K, Put> {
    private NcdcRecordParser parser = new NcdcRecordParser();

    @Override
    public void map(LongWritable key, Text value, Context context) throws
        IOException, InterruptedException {
      parser.parse(value.toString());
      if (parser.isValidTemperature()) {
        byte[] rowKey = RowKeyConverter.makeObservationRowKey(parser.getStationId(),
            parser.getObservationDate().getTime());
        Put p = new Put(rowKey);
        p.add(HBaseTemperatureQuery.DATA_COLUMNFAMILY,
            HBaseTemperatureQuery.AIRTEMP_QUALIFIER,
            Bytes.toBytes(parser.getAirTemperature()));
        context.write(null, p);
      }
    }
  }

  public int run(String[] args) throws Exception {
    if (args.length != 1) {
      System.err.println("Usage: HBaseTemperatureImporter <input>");
      return -1;
    }
    Job job = new Job(getConf(), getClass().getSimpleName());
    job.setJarByClass(getClass());
    FileInputFormat.addInputPath(job, new Path(args[0]));
    job.getConfiguration().set(TableOutputFormat.OUTPUT_TABLE, "observations");
    job.setMapperClass(HBaseTemperatureMapper.class);
    job.setNumReduceTasks(0);
    job.setOutputFormatClass(TableOutputFormat.class);
    return job.waitForCompletion(true) ? 0 : 1;
  }

  public static void main(String[] args) throws Exception {
    int exitCode = ToolRunner.run(HBaseConfiguration.create(),
        new HBaseTemperatureImporter(), args);
    System.exit(exitCode);
  }
}