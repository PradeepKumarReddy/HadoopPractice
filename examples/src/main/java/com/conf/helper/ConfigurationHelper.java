package com.conf.helper;

import org.apache.hadoop.conf.Configuration;

public class ConfigurationHelper {
	
	public static Configuration getConf() {
		Configuration configuration = new Configuration();
		/** Get the Property from core-site.xml from "usr/lib/hadoop/etc/hadoop" */
		configuration.set("fs.defaultFS", "hdfs://quickstart.cloudera:8020");
		return configuration;
	}

}
