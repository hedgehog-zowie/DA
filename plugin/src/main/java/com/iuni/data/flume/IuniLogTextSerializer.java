package com.iuni.data.flume;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.iuni.data.hbase.sindex.HbaseColumns;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.serialization.EventSerializer;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IuniLogTextSerializer implements EventSerializer {
	private static final Logger logger = LoggerFactory.getLogger(IuniLogTextSerializer.class);

	private final OutputStream out;

	private IuniLogTextSerializer(OutputStream out, Context ctx) {
		this.out = out;
	}

	public boolean supportsReopen() {
		return true;
	}

	public void afterCreate() {
	}

	public void afterReopen() {
	}

	public void beforeClose() {
	}

	public void write(Event e) throws IOException {
		this.out.write(getValueFromHeader(e.getHeaders(), HbaseColumns.COLUMN_TIME.getName()));
		this.out.write(getValueFromHeader(e.getHeaders(), HbaseColumns.COLUMN_HOST.getName()));
		this.out.write(getValueFromHeader(e.getHeaders(), HbaseColumns.COLUMN_METHOD.getName()));
		this.out.write(getValueFromHeader(e.getHeaders(), HbaseColumns.COLUMN_REQUEST_URL.getName()));
		this.out.write(getValueFromHeader(e.getHeaders(), HbaseColumns.COLUMN_PROTOCOL_TYPE.getName()));
		this.out.write(getValueFromHeader(e.getHeaders(), HbaseColumns.COLUMN_ADID.getName()));
		this.out.write(getValueFromHeader(e.getHeaders(), HbaseColumns.COLUMN_HTTP_REFERER.getName()));
		this.out.write(getValueFromHeader(e.getHeaders(), HbaseColumns.COLUMN_COUNTRY.getName()));
		this.out.write(getValueFromHeader(e.getHeaders(), HbaseColumns.COLUMN_AREA.getName()));
		this.out.write(getValueFromHeader(e.getHeaders(), HbaseColumns.COLUMN_REGION.getName()));
		this.out.write(getValueFromHeader(e.getHeaders(), HbaseColumns.COLUMN_CITY.getName()));
		this.out.write(getValueFromHeader(e.getHeaders(), HbaseColumns.COLUMN_COUNTY.getName()));
		this.out.write(getValueFromHeader(e.getHeaders(), HbaseColumns.COLUMN_ISP.getName()));
		this.out.write(e.getBody());
		out.write('\n');
	}

	private byte[] getValueFromHeader(Map<String,String> headers, String headerName){
		String value = headers.get(headerName);
		value = value == null ? "" : value;
		return Bytes.toBytes(value + "\001");
	}

	public void flush() throws IOException {
	}

	public static class Builder implements EventSerializer.Builder {
		public EventSerializer build(Context context, OutputStream out) {
			IuniLogTextSerializer s = new IuniLogTextSerializer(out, context);
			return s;
		}
	}
}