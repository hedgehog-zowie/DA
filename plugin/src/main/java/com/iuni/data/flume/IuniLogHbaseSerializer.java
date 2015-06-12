package com.iuni.data.flume;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.FlumeException;
import org.apache.flume.conf.ComponentConfiguration;
import org.apache.flume.sink.hbase.HbaseEventSerializer;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Row;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;

/**
 * insert into hbase table with column from event headers. 
 * rowkey can set to timestamp, random, nano, uuid, iuni. default is iuni
 * 
 * @author Nicholas
 * 
 */
public class IuniLogHbaseSerializer implements HbaseEventSerializer {
	private static final Logger logger = LoggerFactory.getLogger(IuniLogHbaseSerializer.class);
	
	private String rowPrefix;
	private byte[] incrementRow;
	private byte[] cf;
	private byte[] plCol;
	private byte[] incCol;
	private KeyType keyType;
	private byte[] payload;
	private Map<String, String> headers;
	private List<byte[]> columns;
	// 以ctrl - A作为分隔符
	private final String spliter = "\001";

	/**
	 * config
	 * @param context
	 */
	public void configure(Context context) {
		String pCol = context.getString("payloadColumn", "pCol");
		String colNameStr = context.getString("colNames", "s1 s2 s3 s4 s5 s6 s7 s8 s9 s10 s11 s12 s13 s14 s15 s16 s17 s18 s19 s20");
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
			plCol = pCol.getBytes(Charsets.UTF_8);
		}
		if (iCol != null && !iCol.isEmpty()) {
			incCol = iCol.getBytes(Charsets.UTF_8);
		}
		columns = new ArrayList<byte[]>();
		if (colNameStr != null && !colNameStr.isEmpty()) {
			String[] colStrs = colNameStr.split("\\s+");
			for (String colStr : colStrs) {
				columns.add(colStr.trim().getBytes());
			}
		}
		incrementRow = context.getString("incrementRow", "totalRow").getBytes(Charsets.UTF_8);
	}

	public void configure(ComponentConfiguration conf) {
	}

	public void initialize(Event event, byte[] cf) {
		this.payload = event.getBody();
		this.headers = event.getHeaders();
		this.cf = cf;
	}

	public List<Row> getActions() throws FlumeException {
		List<Row> actions = new LinkedList<Row>();
		// 列名不能为空，headers中的usable为usefull
		if (this.plCol != null &&
				IuniLogInterceptor.Constants.SUFFIX_USEFULL.equals(headers.get(IuniLogInterceptor.Constants.SUFFIX_USABLE))) {
			byte[] rowKey;
			try {
				if (keyType == null) {
					rowKey = IuniRowKeyGenerator.getUUIDKey();
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
                        rowKey = IuniRowKeyGenerator.getUUIDKey();
					}
				}
				Put put = new Put(rowKey);
				for (String header : headers.keySet()){
					put.add(cf, header.getBytes(Charsets.UTF_8), headers.get(header).getBytes(Charsets.UTF_8));
				}
				String bodyStr = new String(payload, Charsets.UTF_8);
				String[] bodys = bodyStr.split(spliter);
				int s = columns.size() > bodys.length ? bodys.length : columns.size();
				for (int i = 0; i < s; ++i) {
					String str = bodys[i];
					if(!StringUtils.isBlank(str))
						put.add(cf, columns.get(i), Bytes.toBytes(str));
				}
				actions.add(put);
			} catch (Exception e) {
				throw new FlumeException("hbase serializer error, could not get row key!", e);
			}
		}
		return actions;
	}

	public List<Increment> getIncrements() {
		List<Increment> incs = new LinkedList<Increment>();
		if (this.incCol != null) {
			Increment inc = new Increment(this.incrementRow);
			inc.addColumn(this.cf, this.incCol, 1L);
			incs.add(inc);
		}
		return incs;
	}

	public void close() {
	}

	public static enum KeyType {
		UUID, RANDOM, TS, TSNANO;
	}
	
}
