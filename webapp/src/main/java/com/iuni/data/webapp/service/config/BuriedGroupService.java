package com.iuni.data.webapp.service.config;

import com.iuni.data.persist.domain.config.BuriedGroup;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface BuriedGroupService {

    BuriedGroup getById(Long id);

    List<BuriedGroup> listBuriedGroup(final BuriedGroup buriedGroup);

    boolean addBuriedGroup(BuriedGroup buriedPoint);

    boolean updateBuriedGroup(BuriedGroup buriedPoint);

    boolean deleteBuriedGroup(String ids);

    boolean enableBuriedGroup(String ids);

    boolean disableBuriedGroup(String ids);

}
