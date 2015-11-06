package com.iuni.data.persist.mapper.operation;

import com.iuni.data.persist.model.operation.SalesQueryDto;
import com.iuni.data.persist.model.operation.SalesTableDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Repository("salesMapperOfOperation")
public interface SalesMapper {

    List<SalesTableDto> selectSales(SalesQueryDto salesQueryDto);

}
