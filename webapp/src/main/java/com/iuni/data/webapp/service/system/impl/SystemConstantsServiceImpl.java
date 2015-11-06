package com.iuni.data.webapp.service.system.impl;

import com.iuni.data.persist.mapper.system.SystemConstantsMapper;
import com.iuni.data.persist.model.system.OrderSourceDto;
import com.iuni.data.webapp.service.system.SystemConstantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service("systemConstantsService")
public class SystemConstantsServiceImpl implements SystemConstantsService {

    @Autowired
    private SystemConstantsMapper systemConstantsMapper;

    @Override
    public List<OrderSourceDto> selectAllWMSOrderSource() {
        return systemConstantsMapper.selectAllWMSOrderSource();
    }

    @Override
    public List<OrderSourceDto> selectAllOMOrderSource() {
        return systemConstantsMapper.selectAllOMOrderSource();
    }

}
