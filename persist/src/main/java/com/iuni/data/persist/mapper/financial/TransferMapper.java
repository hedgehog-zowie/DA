package com.iuni.data.persist.mapper.financial;

import com.iuni.data.persist.model.financial.TransferDetailsQueryDto;
import com.iuni.data.persist.model.financial.TransferDetailsTableDto;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface TransferMapper {

    List<TransferDetailsTableDto> selectTransferDetails(TransferDetailsQueryDto queryDto);

}
