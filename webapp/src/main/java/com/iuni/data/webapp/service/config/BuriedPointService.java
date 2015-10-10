package com.iuni.data.webapp.service.config;

import com.iuni.data.persist.domain.config.BuriedPoint;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface BuriedPointService {

    BuriedPoint getById(Long id);

    List<BuriedPoint> listBuriedPoint(final BuriedPoint buriedPoint);

    boolean addBuriedPoint(BuriedPoint buriedPoint);

    boolean updateBuriedPoint(BuriedPoint buriedPoint);

    boolean deleteBuriedPoint(String ids);

}
