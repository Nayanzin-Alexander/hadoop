package com.nayaynzin.hadoop.filecopy;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.*;
import java.net.URI;

public class FileCopyWithProgress {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: <source> <destination>");
        }
        String localSrc = args[0];
        String dst = args[1];

        InputStream in = new BufferedInputStream(new FileInputStream(localSrc));

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(dst), conf);
        OutputStream out = fs.create(new Path(dst), () -> System.out.print('.'));

        IOUtils.copyBytes(in, out, 4096, true);
    }
}
