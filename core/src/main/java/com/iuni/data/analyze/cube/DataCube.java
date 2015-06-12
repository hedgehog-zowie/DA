package com.iuni.data.analyze.cube;

import com.iuni.data.common.Constants;
import com.iuni.data.persist.domain.config.Channel;
import com.iuni.data.persist.domain.config.FlowSource;
import com.iuni.data.persist.domain.config.RTag;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class DataCube {

    // channel map, key is code
    private final static Map<String, Channel> channelMap = new HashMap<>();
    // page tag map, key is code + url
    private final static Map<String, RTag> rTagMap = new HashMap<>();
    // flow source map
    private final static Map<String, FlowSource> flowSourceMap = new HashMap<>();
    // default flow source
    private static FlowSource defaultSource;

    // result queue
    private final static BlockingQueue<Result> resultBlockingQueue = new LinkedBlockingDeque();

    private final static Lock resultLock = new ReentrantLock();

    public static Channel newChannel(String code) {
        Date date = new Date();
        Channel newChannel = new Channel();
        newChannel.setCancelFlag(0);
        int codeLength = code.length() > 10 ? 10 : code.length();
        newChannel.setCode(code.substring(0, codeLength));
        newChannel.setCreateBy(DataCube.class.getSimpleName());
        newChannel.setCreateDate(date);
        newChannel.setName(code.substring(0, codeLength));
        newChannel.setStatus(1);
        newChannel.setUpdateBy(DataCube.class.getSimpleName());
        newChannel.setUpdateDate(date);
        return newChannel;
    }

    /**
     * find channel
     *
     * @param code
     * @return
     */
    public static Channel findChannelByCode(String code) {
        if (StringUtils.isBlank(code))
            code = Constants.DEFAULT_CHANNEL_CODE;
        Channel channel = channelMap.get(code);
        if (channel == null) {
            // if channel is null, then setBasicInfoForCreate a new one
            channel = newChannel(code);
            channelMap.put(code, channel);
        }
        return channel;
    }

    /**
     * find rTag in list
     *
     * @param code the code used to find rTag
     * @param url  the url used to find rTag
     * @return rTag
     */
    public static RTag findRTagByCode(String code, String url) {
        if (StringUtils.isBlank(code))
            code = Constants.DEFAULT_RTAG_CODE;
        return rTagMap.get(code + url);
    }

    /**
     * find source by url
     *
     * @param url the url of source
     * @return source
     */
    public static FlowSource findSourceByUrl(String url) {
        for (Map.Entry<String, FlowSource> entry : flowSourceMap.entrySet()) {
            FlowSource flowSource = entry.getValue();
            if (url.contains(flowSource.getUrl()))
                return flowSource;
        }
        return defaultSource;
    }

    public static Map<String, Channel> getChannelMap() {
        return channelMap;
    }

    public static Map<String, RTag> getrTagMap() {
        return rTagMap;
    }

    public static BlockingQueue<Result> getResultBlockingQueue() {
        return resultBlockingQueue;
    }

    public static Map<String, FlowSource> getFlowSourceMap() {
        return flowSourceMap;
    }

    public static FlowSource getDefaultSource() {
        return defaultSource;
    }

    public static void setDefaultSource(FlowSource defaultSource) {
        if (defaultSource == null)
            defaultSource = new FlowSource();
        DataCube.defaultSource = defaultSource;
    }

    public static void addResult(Result result) {
        resultLock.lock();
        try {
            resultBlockingQueue.add(result);
        } finally {
            resultLock.unlock();
        }
    }
}
