package com.nayanzin.invertedindex;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class InvertedIndexReducerTest {

    private ReduceDriver<Text, Text, Text, Text> reduceDriver;

    @Before
    public void setUp() {
        InvertedIndexReducer reducer = new InvertedIndexReducer();
        reduceDriver = new ReduceDriver<>(reducer);
    }

    @Test
    public void testReducer() throws IOException {
        final Text inputKey = new Text("books");
        final List<Text> inputValue = Arrays.asList(new Text("www.amazon.com"),
                new Text("www.ebay.com"));

        reduceDriver.withInput(inputKey, inputValue);
        final List<Pair<Text, Text>> result = reduceDriver.run();

        assertThat(result)
                .isNotNull()
                .hasSize(1)
                .containsExactly(new Pair<>(inputKey, new Text("www.amazon.com,www.ebay.com")));
    }
}