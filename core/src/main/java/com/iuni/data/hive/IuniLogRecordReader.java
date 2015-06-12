package com.iuni.data.hive;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.LineRecordReader;
import org.apache.hadoop.mapred.RecordReader;

/**
 * Iuni 自定义日志分隔符 {]
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Deprecated
public class IuniLogRecordReader implements RecordReader<LongWritable, Text> {
    LineRecordReader reader;
    Text text;

    public IuniLogRecordReader(LineRecordReader reader) {
        this.reader = reader;
        text = reader.createValue();
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    @Override
    public LongWritable createKey() {
        return reader.createKey();
    }

    @Override
    public Text createValue() {
        return new Text();
    }

    @Override
    public long getPos() throws IOException {
        return reader.getPos();
    }

    @Override
    public float getProgress() throws IOException {
        return reader.getProgress();
    }

    @Override
    public boolean next(LongWritable key, Text value) throws IOException {
        while (reader.next(key, text)) {
            String strReplace = text.toString().toLowerCase().replaceAll("\\{\\]", "\001");
            Text txtReplace = new Text();
            txtReplace.set(strReplace);
            value.set(txtReplace.getBytes(), 0, txtReplace.getLength());
            return true;
        }
        return false;
    }
}
