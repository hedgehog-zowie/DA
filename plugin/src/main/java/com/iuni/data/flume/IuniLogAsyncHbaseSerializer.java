package com.iuni.data.flume;

import com.google.common.base.Charsets;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.FlumeException;
import org.apache.flume.conf.ComponentConfiguration;
import org.apache.flume.interceptor.TimestampInterceptor;
import org.apache.flume.sink.hbase.AsyncHbaseEventSerializer;
import org.apache.flume.sink.hbase.SimpleHbaseEventSerializer.KeyType;
import org.hbase.async.AtomicIncrementRequest;
import org.hbase.async.PutRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * insert into hbase table with column from event headers. 
 * rowkey can set to timestamp, random, nano, uuid, iuni. default is iuni
 * 
 * @author Nicholas
 * 
 */
public class IuniLogAsyncHbaseSerializer implements AsyncHbaseEventSerializer {
	
	private static final Logger logger = LoggerFactory.getLogger(IuniLogAsyncHbaseSerializer.class);
	
	private byte[] table;
	private byte[] cf;
	private byte[] payload;
	private byte[] payloadColumn;
	private byte[] incrementColumn;
	private List<byte[]> columns;
	private String rowPrefix;
	private byte[] incrementRow;
	private KeyType keyType;
	private Map<String, String> headers;

	private final String spliter = "\\{\\]";

	@Override
	public void initialize(byte[] table, byte[] cf) {
		this.table = table;
		this.cf = cf;
	}

	@Override
	public List<PutRequest> getActions() {
		List<PutRequest> actions = new ArrayList<PutRequest>();
		if (payloadColumn != null) {
			byte[] rowKey;
			try {
				String timestamp = headers.get(TimestampInterceptor.Constants.TIMESTAMP);
				if (keyType == null) {
					rowKey = IuniRowKeyGenerator.getReverseTimestampKey(timestamp == null ? "" : timestamp);
				} else {
					switch (keyType) {
					case TS:
						rowKey = IuniRowKeyGenerator.getTimestampKey(rowPrefix);
						break;
					case TSNANO:
						rowKey = IuniRowKeyGenerator.getNanoTimestampKey(rowPrefix);
						break;
					case RANDOM:
						rowKey = IuniRowKeyGenerator.getRandomKey(rowPrefix);
						break;
					case UUID:
						rowKey = IuniRowKeyGenerator.getUUIDKey(rowPrefix);
						break;
					default:
						rowKey = IuniRowKeyGenerator.getReverseTimestampKey(timestamp == null ? "" : timestamp);
						break;
					}
				}
				for (String header : headers.keySet()) {
					PutRequest putRequest = new PutRequest(table, rowKey, cf, header.getBytes(Charsets.UTF_8), headers.get(header).getBytes(Charsets.UTF_8));
					actions.add(putRequest);
				}
				String bodyStr = new String(payload, Charsets.UTF_8);
				String[] bodys = bodyStr.split(spliter);
				int s = columns.size() > bodys.length ? bodys.length : columns.size();
				for (int i = 0; i < s; ++i) {
					PutRequest putRequest = new PutRequest(table, rowKey, cf, columns.get(i), bodys[i].trim().getBytes());
					actions.add(putRequest);
				}
			} catch (Exception e) {
				throw new FlumeException("Could not get row key!", e);
			}
		}
		return actions;
	}

	public List<AtomicIncrementRequest> getIncrements() {
		List<AtomicIncrementRequest> actions = new ArrayList<AtomicIncrementRequest>();
		if (incrementColumn != null) {
			AtomicIncrementRequest inc = new AtomicIncrementRequest(table, incrementRow, cf, incrementColumn);
			actions.add(inc);
		}
		return actions;
	}

	@Override
	public void cleanUp() {
	}

	@Override
	public void configure(Context context) {
		String pCol = context.getString("payloadColumn", "pCol");
		String colNameStr = context.getString("colNames", "payload");
		String iCol = context.getString("incrementColumn", "iCol");
		rowPrefix = context.getString("rowPrefix", "iuni-");
		String suffix = context.getString("suffix", "iuni");
		if (pCol != null && !pCol.isEmpty()) {
			if (suffix.equals("timestamp")) {
				keyType = KeyType.TS;
			} else if (suffix.equals("random")) {
				keyType = KeyType.RANDOM;
			} else if (suffix.equals("nano")) {
				keyType = KeyType.TSNANO;
			} else if (suffix.equals("uuid")) {
				keyType = KeyType.UUID;
			}
			payloadColumn = pCol.getBytes(Charsets.UTF_8);
		}
		if (iCol != null && !iCol.isEmpty()) {
			incrementColumn = iCol.getBytes(Charsets.UTF_8);
		}
		columns = new ArrayList<byte[]>();
		if (colNameStr != null && !colNameStr.isEmpty()) {
			String[] colStrs = colNameStr.split(",");
			for (String colStr : colStrs) {
				columns.add(colStr.trim().getBytes());
			}
		}
		incrementRow = context.getString("incrementRow", "totalRow").getBytes(Charsets.UTF_8);
	}

	@Override
	public void setEvent(Event event) {
		this.payload = event.getBody();
		this.headers = event.getHeaders();
	}

	@Override
	public void configure(ComponentConfiguration conf) {
	}

}
