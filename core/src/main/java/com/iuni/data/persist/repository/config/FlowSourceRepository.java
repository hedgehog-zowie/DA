package com.iuni.data.persist.repository.config;

import com.iuni.data.persist.domain.config.FlowSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
// setBasicInfoForUpdate to old code
// public interface FlowSourceRepository extends JpaRepository<IuniDaFlowSource, Long> {
public interface FlowSourceRepository extends JpaRepository<FlowSource, Long>, JpaSpecificationExecutor<FlowSource> {

    List<FlowSource> findByStatusAndCancelFlag(int status, int cancelFlag);

    FlowSource findByName(String name);
}
