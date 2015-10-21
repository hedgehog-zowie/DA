package com.iuni.data.app;

import com.google.common.eventbus.Subscribe;
import com.iuni.data.Analyze;
import com.iuni.data.analyze.cube.DataCube;
import com.iuni.data.analyze.cube.Result;
import com.iuni.data.common.Constants;
import com.iuni.data.lifecycle.LifecycleAware;
import com.iuni.data.lifecycle.LifecycleState;
import com.iuni.data.lifecycle.LifecycleSupervisor;
import com.iuni.data.persist.domain.config.Channel;
import com.iuni.data.persist.domain.config.FlowSource;
import com.iuni.data.persist.domain.config.RTag;
import com.iuni.data.persist.domain.config.RTagType;
import com.iuni.data.persist.repository.config.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service("taskExecutor")
public class TaskExecutor {

    private static Logger logger = LoggerFactory.getLogger(TaskExecutor.class);

    private List<LifecycleAware> components;
    private final LifecycleSupervisor supervisor;
    private MaterializedConfiguration materializedConfiguration;

    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private PageTagRepository pageTagRepository;
    @Autowired
    private PageTagTypeRepository pageTagTypeRepository;
    @Autowired
    private FlowSourceRepository flowSourceRepository;
//    @Autowired
//    private HolidayRepository holidayRepository;

    @Autowired
    private ResultHandler resultHandler;

    // type 4 is click
    private final Long RTAG_TYPE_ID_OF_CLICK = 4L;

    public TaskExecutor() {
        this(new ArrayList<LifecycleAware>(0));
    }

    public TaskExecutor(List<LifecycleAware> components) {
        this.components = components;
        supervisor = new LifecycleSupervisor();
    }

    public synchronized void start() {
        for (LifecycleAware component : components) {
            supervisor.supervise(component, new LifecycleSupervisor.SupervisorPolicy.AlwaysRestartPolicy(), LifecycleState.START);
        }
    }

    public synchronized void stop() {
        supervisor.stop();
    }

    @Subscribe
    public synchronized void handleConfigurationEvent(MaterializedConfiguration conf) {
        stopAllComponents();
        startAllComponents(conf);
    }

    private void startAllComponents(MaterializedConfiguration materializedConfiguration) {
        logger.info("Starting new configuration:{}", materializedConfiguration);
        if (materializedConfiguration.getAnalyzes().size() == 0) {
            logger.error("Application.materializedConfiguration don't have any analyze, please check analyze config.");
            return;
        }

        // init channel
        List<Channel> channelList = channelRepository.findByStatusAndCancelFlag(1, 0);
        if (channelList.size() == 0) {
            logger.info("no channel configured");
        }
        Map<String, Channel> channelMap = DataCube.getChannelMap();
        for (Channel channel : channelList)
            channelMap.put(channel.getCode(), channel);

        // init rTag
        RTagType rTagType = pageTagTypeRepository.findOne(RTAG_TYPE_ID_OF_CLICK);
        List<RTag> rTagList = pageTagRepository.findByStatusAndCancelFlag(1, 0);
        if (rTagList.size() == 0)
            logger.info("no rTag configured.");
        Map<String, RTag> rTagMap = DataCube.getrTagMap();
        for (RTag rTag : rTagList) {
            if (rTag.getrTagType().getId() == rTagType.getId())
                // key is code + url
                rTagMap.put(rTag.getRtag() + rTag.getInfo(), rTag);
        }

        // init flow source
        DataCube.setDefaultSource(flowSourceRepository.findByName(Constants.default_flow_source_name));
        List<FlowSource> flowSourceList = flowSourceRepository.findByStatusAndCancelFlag(1, 0);
        if (flowSourceList.size() == 0)
            logger.info("no flow source configured.");
        Map<String, FlowSource> flowSourceMap = DataCube.getFlowSourceMap();
        for(FlowSource flowSource : flowSourceList)
            flowSourceMap.put(flowSource.getUrl(), flowSource);

        BlockingQueue<Result> resultBlockingQueue = DataCube.getResultBlockingQueue();
        resultHandler.setResultBlockingQueue(resultBlockingQueue);
        supervisor.supervise(resultHandler, new LifecycleSupervisor.SupervisorPolicy.AlwaysRestartPolicy(), LifecycleState.START);

        this.materializedConfiguration = materializedConfiguration;

        for (Map.Entry<String, Analyze> entry : materializedConfiguration.getAnalyzes().entrySet()) {
            try {
                logger.info("Starting Analyze " + entry.getKey());
                supervisor.supervise(entry.getValue(), new LifecycleSupervisor.SupervisorPolicy.AlwaysRestartPolicy(), LifecycleState.START);
            } catch (Exception e) {
                logger.error("Error while starting {}", entry.getValue(), e);
            }
        }
    }

    private void stopAllComponents() {
        if (this.materializedConfiguration != null) {
            supervisor.unsupervise(resultHandler);
            logger.info("Shutting down configuration: {}", this.materializedConfiguration);
            for (Map.Entry<String, Analyze> entry : this.materializedConfiguration.getAnalyzes().entrySet()) {
                try {
                    logger.info("Stopping Analyze " + entry.getKey());
                    supervisor.unsupervise(entry.getValue());
                } catch (Exception e) {
                    logger.error("Error while stopping {}", entry.getValue(), e);
                }
            }
        }
    }

    public void setComponents(List<LifecycleAware> components) {
        this.components = components;
    }

}
