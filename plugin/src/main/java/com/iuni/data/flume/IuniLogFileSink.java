package com.iuni.data.flume;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Sink;
import org.apache.flume.Transaction;
import org.apache.flume.conf.Configurable;
import org.apache.flume.instrumentation.SinkCounter;
import org.apache.flume.serialization.EventSerializer;
import org.apache.flume.serialization.EventSerializerFactory;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

public class IuniLogFileSink extends AbstractSink implements Configurable {
	private static final Logger logger = LoggerFactory.getLogger(IuniLogFileSink.class);
	private static final String fileName = "logNode";
	private static final String suffix = ".log";
	
	private String serializerType;
	private Context serializerContext;
	private EventSerializer serializer;
	private SinkCounter sinkCounter;
	private String filePath;
	// all log file
	private File file;
	// category log file
	private File cateFile;
	private OutputStream outputStream;
	private int batchSize = 100;

	public IuniLogFileSink() {
	}

	public void configure(Context context) {
		filePath = context.getString("sink.directory");

		this.serializerType = context.getString("sink.serializer", "TEXT");
		this.serializerContext = new Context(context.getSubProperties("sink.serializer."));

		Preconditions.checkArgument(filePath != null, "Directory may not be null");
		Preconditions.checkNotNull(this.serializerType, "Serializer type is undefined");
		
		this.batchSize = context.getInteger("sink.batchSize", batchSize).intValue();
		
		filePath = filePath  + "/" + fileName + "_" + context.getString("sink.file.suffix", "") + suffix;
		this.file = new File(filePath);

		if (this.sinkCounter == null)
			this.sinkCounter = new SinkCounter(getName());
	}

	public void start() {
		logger.info("IuniLogFileSink Starting {}...", this);
		this.sinkCounter.start();
		super.start();
		
		logger.info("RollingFileSink {} started.", getName());
	}

	public Sink.Status process() throws EventDeliveryException {
		if (this.outputStream == null) {
			try {
				this.outputStream = new BufferedOutputStream(new FileOutputStream(file));
				this.serializer = EventSerializerFactory.getInstance(this.serializerType, this.serializerContext, this.outputStream);
				this.serializer.afterCreate();
				this.sinkCounter.incrementConnectionCreatedCount();
			} catch (IOException e) {
				this.sinkCounter.incrementConnectionFailedCount();
				throw new EventDeliveryException("Failed to open file " + filePath + "/" + fileName + " while delivering event", e);
			}
		}

		Channel channel = getChannel();
		Transaction transaction = channel.getTransaction();
		Event event = null;
		Sink.Status result = Sink.Status.READY;
		try {
			transaction.begin();
			int eventAttemptCounter = 0;
			for (int i = 0; i < this.batchSize; ++i) {
				event = channel.take();
				if (event != null) {
					this.sinkCounter.incrementEventDrainAttemptCount();
					++eventAttemptCounter;
					this.serializer.write(event);
				} else {
					result = Sink.Status.BACKOFF;
					break;
				}
			}
			this.serializer.flush();
			this.outputStream.flush();
			transaction.commit();
			this.sinkCounter.addToEventDrainSuccessCount(eventAttemptCounter);
		} catch (Exception ex) {
			throw new EventDeliveryException("Failed to process transaction", ex);
		} finally {
			transaction.close();
		}

		return result;
	}

	public void stop() {
		logger.info("IuniLogFileSink {} stopping...", getName());
		this.sinkCounter.stop();
		super.stop();

		if (this.outputStream != null) {
			logger.debug("Closing file {}", this.filePath);
			try {
				this.serializer.flush();
				this.serializer.beforeClose();
				this.outputStream.close();
				this.sinkCounter.incrementConnectionClosedCount();
			} catch (IOException e) {
				this.sinkCounter.incrementConnectionFailedCount();
				logger.error("Unable to close output stream. Exception follows.", e);
			} finally {
				this.outputStream = null;
				this.serializer = null;
			}
		}
		
		logger.info("IuniLogFileSink {} stopped. Event metrics: {}", getName(), this.sinkCounter);
	}

	public File getDirectory() {
		return this.file;
	}

	public void setDirectory(File directory) {
		this.file = directory;
	}
}

