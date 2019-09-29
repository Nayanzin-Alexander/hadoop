package com.nayanzin.invertedindex;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.util.StringUtils;

import java.io.IOException;

class InvertedIndexMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

    private static final char SEPARATOR = ',';
    private static final int RETAILER_INDEX = 0;

    @Override
    public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
        final String[] record = StringUtils.split(value.toString(), SEPARATOR);
        final String retailer = record[RETAILER_INDEX];
        int bound = record.length;
        for (int i = 0; i < bound; i++) {
            if (RETAILER_INDEX != i) {
                output.collect(new Text(record[i]), new Text(retailer));
            }
        }
    }
}
