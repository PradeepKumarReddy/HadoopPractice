package com.examples.hdfs;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import com.conf.helper.ConfigurationHelper;

public class ReadFile {

	public static void main(String[] arg) throws IOException,
			URISyntaxException {
		FileSystem fileSystem = FileSystem.get(new URI("/user/hdfs/test.txt"),
				ConfigurationHelper.getConf());

		InputStream in = null;
		try {
			in = fileSystem.open(new Path("/user/hdfs/test.txt"));
			IOUtils.copyBytes(in, System.out, 4026, false);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			in.close();
		}

	}
}
