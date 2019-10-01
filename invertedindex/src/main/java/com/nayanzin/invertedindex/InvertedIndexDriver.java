package com.nayanzin.invertedindex;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class InvertedIndexDriver extends Configured implements Tool {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        System.exit(ToolRunner.run(conf, new InvertedIndexDriver(), args));
    }

    @Override
    @SuppressWarnings("squid:S106") // sonar lint Standard outputs should not be used directly to log anything.
    public int run(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.printf("usage: %s [generic options] <inputfile> <outputdir>%n", getClass().getSimpleName());
        }
        Job job = Job.getInstance(getConf(), "Inverted index");
        job.setJarByClass(InvertedIndexDriver.class);

        job.setMapperClass(InvertedIndexMapper.class);
        job.setReducerClass(InvertedIndexReducer.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        return job.waitForCompletion(true) ? 0 : 1;
    }
}
