package com.nayanzin.invertedindex;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.StringUtils;

import java.io.IOException;

class InvertedIndexMapper extends Mapper<LongWritable, Text, Text, Text> {

    private static final char SEPARATOR = ',';
    private static final int RETAILER_INDEX = 0;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        final String[] record = StringUtils.split(value.toString(), SEPARATOR);
        final String retailer = record[RETAILER_INDEX];
        int bound = record.length;
        for (int i = 0; i < bound; i++) {
            if (RETAILER_INDEX != i) {
                context.write(new Text(record[i]), new Text(retailer));
            }
        }
    }
}
