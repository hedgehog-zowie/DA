package com.iuni.data.flume;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileWriterLinkedHashMap extends LinkedHashMap<String, BucketFileWriter> {

	private static final Logger logger = LoggerFactory.getLogger(FileWriterLinkedHashMap.class);

	private static final long serialVersionUID = 1L;
	private final int maxOpenFiles;

	public FileWriterLinkedHashMap(int maxOpenFiles) {
		super(16, 0.75f, true); // stock initial capacity/load, access
		this.maxOpenFiles = maxOpenFiles;
	}

	@Override
	protected boolean removeEldestEntry(Entry<String, BucketFileWriter> eldest) {
		if (size() > maxOpenFiles) {
			// If we have more that max open files, then close the last one
			// and
			// return true
			try {
				eldest.getValue().close();
			} catch (IOException e) {
				logger.warn(eldest.getKey().toString(), e);
			} catch (InterruptedException e) {
				logger.warn(eldest.getKey().toString(), e);
				Thread.currentThread().interrupt();
			}
			return true;
		} else {
			return false;
		}
	}
}