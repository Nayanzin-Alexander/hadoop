package com.nayanzin.hadoop.salescountry;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.MapDriver;
import org.apache.hadoop.mrunit.MapReduceDriver;
import org.apache.hadoop.mrunit.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class SalesCountryMapReducerTest {

    private MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;
    private ReduceDriver<Text, IntWritable, Text, IntWritable> reduceDriver;
    private MapReduceDriver<LongWritable, Text, Text, IntWritable, Text, IntWritable> mapReduceDriver;

    @Before
    public void setUp() {
        SalesCountryMapper mapper = new SalesCountryMapper();
        SalesCountryReducer reducer = new SalesCountryReducer();
        mapDriver = MapDriver.newMapDriver(mapper);
        reduceDriver = ReduceDriver.newReduceDriver(reducer);
        mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
    }

    @Test
    public void testMapper() {
        mapDriver.withInput(new LongWritable(),
                new Text("1/2/09 6:17,Product1,1200,Mastercard,carolina,Basildon,England,United Kingdom,1/2/09 6:00,1/2/09 6:08,51.5,-1.1166667"));
        mapDriver.withOutput(new Text("United Kingdom"), new IntWritable(1));
        mapDriver.runTest();
    }

    @Test
    public void testReducer() {
        List<IntWritable> values = IntStream
                .range(0, 10)
                .mapToObj(i -> new IntWritable(1))
                .collect(toList());
        reduceDriver.withInput(new Text("Ukraine"), values);
        reduceDriver.withOutput(new Text("Ukraine"), new IntWritable(10));
        reduceDriver.runTest();
    }

    @Test
    public void testMapReducer() {
        mapReduceDriver.withInput(new LongWritable(),
                new Text("1/2/09 6:17,Product1,1200,Mastercard,carolina,Basildon,England,United Kingdom,1/2/09 6:00,1/2/09 6:08,51.5,-1.1166667"));
        mapReduceDriver.withOutput(new Text("United Kingdom"), new IntWritable(1));
        mapReduceDriver.runTest();
    }
}