package com.nayanzin.invertedindex;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class InvertedIndexMapReduceTest {

    private MapReduceDriver<LongWritable, Text, Text, Text, Text, Text> driver;

    @Before
    public void setUp() {
        driver = new MapReduceDriver<>(new InvertedIndexMapper(), new InvertedIndexReducer());
    }

    @Test
    public void mapReduceTest() throws IOException {
        driver.withInput(new LongWritable(), new Text("www.kohls.com,clothes,shoes,beauty,toys"));
        driver.withInput(new LongWritable(), new Text("www.macys.com,shoes,clothes,toys,jeans,sweaters"));

        final List<Pair<Text, Text>> result = driver.run();

        final Pair<Text, Text> clothes = new Pair<>(new Text("clothes"), new Text("www.kohls.com,www.macys.com"));
        final Pair<Text, Text> shoes = new Pair<>(new Text("shoes"), new Text("www.kohls.com,www.macys.com"));
        final Pair<Text, Text> beauty = new Pair<>(new Text("beauty"), new Text("www.kohls.com"));
        final Pair<Text, Text> toys = new Pair<>(new Text("toys"), new Text("www.kohls.com,www.macys.com"));
        final Pair<Text, Text> jeans = new Pair<>(new Text("jeans"), new Text("www.macys.com"));
        final Pair<Text, Text> sweaters = new Pair<>(new Text("sweaters"), new Text("www.macys.com"));

        assertThat(result)
                .isNotNull()
                .hasSize(6)
                .containsExactlyInAnyOrder(clothes, shoes, beauty, toys, jeans, sweaters);
    }
}