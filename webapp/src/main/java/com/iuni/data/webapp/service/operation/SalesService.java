package com.iuni.data.webapp.service.operation;

import com.iuni.data.persist.model.operation.SalesQueryDto;
import com.iuni.data.persist.model.operation.SalesTableDto;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface SalesService {

    List<SalesTableDto> selectSales(SalesQueryDto salesQueryDto);

}
