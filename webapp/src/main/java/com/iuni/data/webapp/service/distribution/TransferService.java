package com.iuni.data.webapp.service.distribution;

import com.iuni.data.persist.model.distribution.TransferDetailsOfInTableDto;
import com.iuni.data.persist.model.distribution.TransferDetailsQueryDto;
import com.iuni.data.persist.model.distribution.TransferDetailsOfOutTableDto;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface TransferService {

    /**
     * 调拔明细 - 物流
     * @param queryDto
     * @return
     */
    List<TransferDetailsOfOutTableDto> selectTransferDetailsOfOut(TransferDetailsQueryDto queryDto);

    /**
     * 调拔明细 - 物入
     * @param queryDto
     * @return
     */
    List<TransferDetailsOfInTableDto> selectTransferDetailsOfIn(TransferDetailsQueryDto queryDto);

}
