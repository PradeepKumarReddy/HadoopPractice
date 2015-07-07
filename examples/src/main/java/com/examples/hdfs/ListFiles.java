package com.examples.hdfs;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

import com.conf.helper.ConfigurationHelper;

public class ListFiles {

	public static void main(String[] arg) throws IOException,
			URISyntaxException {
		/*FileSystem fileSystem = FileSystem.get(new URI("/user/hdfs/"),
				ConfigurationHelper.getConf());*/
		
		FileSystem fileSystem = FileSystem.get(ConfigurationHelper.getConf());

		RemoteIterator<LocatedFileStatus> itr = null;
		try {
			itr = fileSystem.listFiles(new Path("/user/hdfs/"), true);
			
			while (itr.hasNext()) {
				System.out.println(itr.next().getPath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

	}

}
