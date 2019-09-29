package com.nayanzin.hadoop.salescountry;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

class SalesCountryDriver {
    public static void main(String[] args) throws Exception {
        JobConf jobConf;
        try (JobClient myClient = new JobClient()) {

            // Create  a configuration client for the job.
            jobConf = new JobConf(SalesCountryDriver.class);
            jobConf.setJobName("SalesPerCountry");

            // I/O formats.
            jobConf.setInputFormat(TextInputFormat.class);
            jobConf.setOutputFormat(TextOutputFormat.class);

            // Specify mapper and reducer classes.
            jobConf.setMapperClass(SalesCountryMapper.class);
            jobConf.setReducerClass(SalesCountryReducer.class);

            // Data types for output key and value.
            jobConf.setOutputKeyClass(Text.class);
            jobConf.setOutputValueClass(IntWritable.class);

            // Set input and output directories using command line arguments.
            FileInputFormat.setInputPaths(jobConf, new Path(args[0]));
            FileOutputFormat.setOutputPath(jobConf, new Path(args[1]));

            // Set configuration.
            myClient.setConf(jobConf);

            // Run the job.
            JobClient.runJob(jobConf);
        }
    }
}
