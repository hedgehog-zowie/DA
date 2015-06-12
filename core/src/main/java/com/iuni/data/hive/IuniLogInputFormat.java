package com.iuni.data.hive;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.LineRecordReader;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;

/**
 * Iuni 自定义日志分隔符 {]
 * 
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Deprecated
public class IuniLogInputFormat extends TextInputFormat {

    @Override
    public RecordReader<LongWritable, Text> getRecordReader(
            InputSplit genericSplit, JobConf job, Reporter reporter)
            throws IOException {
        String aa = "{]";
        reporter.setStatus(genericSplit.toString());
        return new IuniLogRecordReader(new LineRecordReader(job, (FileSplit) genericSplit));
    }

}
