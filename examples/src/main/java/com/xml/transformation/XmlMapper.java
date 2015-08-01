package com.xml.transformation;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
	
	String SCHEMA = "{\"type\":\"record\",\"name\":\"Employee\",\"namespace\":\"com.xml.transformation\",\"fields\":[ {\"type\":\"int\",\"name\":\"id\"},{\"name\":\"name\",\"type\":\"string\"},{\"type\":\"string\",\"name\":\"gender\"}]}";

	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		try {

			InputStream is = new ByteArrayInputStream(value.toString().getBytes());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);

			doc.getDocumentElement().normalize();

			
			// creating avro object with out avro code
			Schema schema = new Schema.Parser().parse(SCHEMA);
			GenericRecord employee = new GenericData.Record(schema);

			
			NodeList nList = doc.getElementsByTagName("employee");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					String id = eElement.getElementsByTagName("id").item(0)
							.getTextContent();
					String name = eElement.getElementsByTagName("name").item(0)
							.getTextContent();
					String gender = eElement.getElementsByTagName("gender")
							.item(0).getTextContent();
					
					employee.put("id", id);
					employee.put("name", name);
					employee.put("gender", gender);
					
					System.out.println(employee.toString());
					context.write(new Text(id + "," + name + "," + gender),	NullWritable.get());

				}
			}
		} catch (Exception e) {
			System.out.println("Exception");
		}
	}

}
