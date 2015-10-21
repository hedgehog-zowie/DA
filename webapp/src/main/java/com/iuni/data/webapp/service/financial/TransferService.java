package com.iuni.data.webapp.service.financial;

import com.iuni.data.persist.model.financial.TransferDetailsQueryDto;
import com.iuni.data.persist.model.financial.TransferDetailsTableDto;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface TransferService {

    List<TransferDetailsTableDto> selectTransferDetails(TransferDetailsQueryDto queryDto);

}
