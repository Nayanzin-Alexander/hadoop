package com.nayanzin.invertedindex;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class InvertedIndexMapTest {

    private MapDriver<LongWritable, Text, Text, Text> mapDriver;

    @Before
    public void setUp() {
        InvertedIndexMapper mapper = new InvertedIndexMapper();
        mapDriver = new MapDriver<>(mapper);
    }

    @Test
    public void mapTestWithSingleKeyAndValue() throws IOException {
        mapDriver.withInput(new LongWritable(), new Text("www.kohls.com,clothes"));
        mapDriver.withOutput(new Text("clothes"), new Text("www.kohls.com"));
        mapDriver.runTest();
    }

    @Test
    public void mapTestWithSingleKeyAndValueWithAssertion() throws IOException {
        final LongWritable inputKey = new LongWritable(0);
        final Text inputValue = new Text("www.kohls.com,clothes");
        final Text outputKey = new Text("clothes");
        final Text outputValue = new Text("www.kohls.com");

        mapDriver.withInput(inputKey, inputValue);
        final List<Pair<Text, Text>> result = mapDriver.run();

        assertThat(result)
                .isNotNull()
                .hasSize(1)
                .containsExactly(new Pair<>(outputKey, outputValue));
    }

    @Test
    public void mapTestWithSingleInputMultiplyOutput() throws IOException {
        final LongWritable inputKey = new LongWritable(0);
        final Text inputValue = new Text("www.kohls.com,clothes,shoes,beauty,toys");
        final Pair clothes = new Pair<>(new Text("clothes"), new Text("www.kohls.com"));
        final Pair shoes = new Pair<>(new Text("shoes"), new Text("www.kohls.com"));
        final Pair beauty = new Pair<>(new Text("beauty"), new Text("www.kohls.com"));
        final Pair toys = new Pair<>(new Text("toys"), new Text("www.kohls.com"));
        mapDriver.withInput(inputKey, inputValue);
        final List<Pair<Text, Text>> result = mapDriver.run();

        assertThat(result)
                .isNotNull()
                .hasSize(4)
                .containsExactlyInAnyOrder(clothes, shoes, beauty, toys);
    }


}