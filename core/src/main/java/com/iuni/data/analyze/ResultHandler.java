package com.iuni.data.analyze;

import com.iuni.data.analyze.cube.Result;
import com.iuni.data.common.TType;
import com.iuni.data.lifecycle.LifecycleAware;
import com.iuni.data.lifecycle.LifecycleState;
import com.iuni.data.persist.domain.config.Channel;
import com.iuni.data.persist.domain.webkpi.ClickWebKpi;
import com.iuni.data.persist.domain.webkpi.PageWebKpi;
import com.iuni.data.persist.domain.webkpi.WebKpi;
import com.iuni.data.persist.repository.config.ChannelRepository;
import com.iuni.data.persist.repository.webkpi.ClickWebKpiRepository;
import com.iuni.data.persist.repository.webkpi.PageWebKpiRepository;
import com.iuni.data.persist.repository.webkpi.WebKpiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service("analyzeResultHandler")
public class ResultHandler implements LifecycleAware {

    private static Logger logger = LoggerFactory.getLogger(ResultHandler.class);

    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private PageWebKpiRepository pageWebKpiRepository;
    @Autowired
    private ClickWebKpiRepository clickWebKpiRepository;
    @Autowired
    private WebKpiRepository webKpiRepository;

    private LifecycleState lifecycleState;

    private BlockingQueue<Result> resultBlockingQueue;

    public ResultHandler() {
        this.lifecycleState = LifecycleState.IDLE;
    }

    public void init(BlockingQueue<Result> resultBlockingQueue) {
        this.resultBlockingQueue = resultBlockingQueue;
    }

    private Channel saveChannel(Channel channel, Map<String, Channel> channelMap) {
        if (!channelMap.containsKey(channel.getCode())) {
            Channel existChannel = channelRepository.findByCode(channel.getCode());
            if (existChannel != null)
                channel = existChannel;
            else
                channel = channelRepository.save(channel);
            channelMap.put(channel.getCode(), channel);
        }
        return channelMap.get(channel.getCode());
    }

    private void savePageWebKpi(TType tType, Date time, Map<String, PageWebKpi> pageWebKpiMap) {
        Date date = new Date();
        pageWebKpiRepository.delete(pageWebKpiRepository.findByTimeAndTtype(time, tType.getPattern()));
        // save data
        Map<String, Channel> channelMap = new HashMap<>();
        for (PageWebKpi pageWebKpi : pageWebKpiMap.values()) {
            pageWebKpi.setCreateDate(date);
            pageWebKpi.setChannel(saveChannel(pageWebKpi.getChannel(), channelMap));
        }
        pageWebKpiRepository.save(pageWebKpiMap.values());
    }

    private void saveClickWebKpi(TType tType, Date time, Map<String, ClickWebKpi> clickWebKpiMap) {
        Date date = new Date();
        clickWebKpiRepository.delete(clickWebKpiRepository.findByTimeAndTtype(time, tType.getPattern()));
        // save data
        Map<String, Channel> channelMap = new HashMap<>();
        for (ClickWebKpi clickWebKpi : clickWebKpiMap.values()) {
            clickWebKpi.setCreateDate(date);
            clickWebKpi.setChannel(saveChannel(clickWebKpi.getChannel(), channelMap));
        }
        clickWebKpiRepository.save(clickWebKpiMap.values());
    }

    private void saveWebKpi(TType tType, Date time, Map<String, WebKpi> webKpiMap) {
        Date date = new Date();
        webKpiRepository.delete(webKpiRepository.findByTimeAndTtype(time, tType.getPattern()));
        // save data
        Map<String, Channel> channelMap = new HashMap<>();
        for (WebKpi webKpi : webKpiMap.values()) {
            webKpi.setCreateDate(date);
            webKpi.setChannel(saveChannel(webKpi.getChannel(), channelMap));
        }
        webKpiRepository.save(webKpiMap.values());
    }

    @Override
    public void start() {
        logger.info("analyzeResultHandler starting");
        this.lifecycleState = LifecycleState.START;
        Thread saveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("start result handler save runner");
                while (lifecycleState == LifecycleState.START) {
                    try {
                        Result result = resultBlockingQueue.take();
                        if (result.getPageWebKpiMap() != null && result.getPageWebKpiMap().size() != 0) {
                            savePageWebKpi(result.gettType(), result.getTime(), result.getPageWebKpiMap());
                        }
                        if (result.getClickWebKpiMap() != null && result.getClickWebKpiMap().size() != 0) {
                            saveClickWebKpi(result.gettType(), result.getTime(), result.getClickWebKpiMap());
                        }
                        if (result.getPageWebKpiForWholeSiteMap() != null && result.getPageWebKpiForWholeSiteMap().size() != 0) {
                            saveWebKpi(result.gettType(), result.getTime(), result.getPageWebKpiForWholeSiteMap());
                        }
                    } catch (InterruptedException e) {
                        logger.error("save result to db error, {}", e);
                    }
                }
                logger.info("stop result handler save runner");
            }
        });
        saveThread.setName("thread-save-result");
        saveThread.start();
    }

    @Override
    public void stop() {
        logger.info("analyzeResultHandler stopping");
        this.lifecycleState = LifecycleState.STOP;
    }

    @Override
    public LifecycleState getLifecycleState() {
        return this.lifecycleState;
    }

    public void setResultBlockingQueue(BlockingQueue<Result> resultBlockingQueue) {
        this.resultBlockingQueue = resultBlockingQueue;
    }
}
