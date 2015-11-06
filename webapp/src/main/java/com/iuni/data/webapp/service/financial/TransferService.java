package com.iuni.data.webapp.service.financial;

import com.iuni.data.persist.model.financial.InTransferDetailsQueryDto;
import com.iuni.data.persist.model.financial.InTransferDetailsTableDto;
import com.iuni.data.persist.model.financial.TransferDetailsQueryDto;
import com.iuni.data.persist.model.financial.TransferDetailsTableDto;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface TransferService {

    /**
     * 调拔明细
     * @param queryDto
     * @return
     */
    List<TransferDetailsTableDto> selectTransferDetails(TransferDetailsQueryDto queryDto);

    /**
     * 内部调拔明细
     * @param queryDto
     * @return
     */
    List<InTransferDetailsTableDto> selectInTransferDetails(InTransferDetailsQueryDto queryDto);

}
