package com.iuni.data.webapp.service.operation.impl;

import com.iuni.data.persist.mapper.operation.SalesMapper;
import com.iuni.data.persist.model.operation.SalesQueryDto;
import com.iuni.data.persist.model.operation.SalesTableDto;
import com.iuni.data.webapp.service.operation.SalesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service("salesServiceOfOperation")
public class SalesServiceImpl implements SalesService {

    private static final Logger logger = LoggerFactory.getLogger(SalesServiceImpl.class);

    @Autowired
    private SalesMapper salesMapper;

    @Override
    public List<SalesTableDto> selectSales(SalesQueryDto salesQueryDto) {
        return salesMapper.selectSales(salesQueryDto);
    }

}
