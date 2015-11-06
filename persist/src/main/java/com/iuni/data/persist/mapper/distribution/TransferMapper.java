package com.iuni.data.persist.mapper.distribution;

import com.iuni.data.persist.model.distribution.TransferDetailsOfInTableDto;
import com.iuni.data.persist.model.distribution.TransferDetailsQueryDto;
import com.iuni.data.persist.model.distribution.TransferDetailsOfOutTableDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Repository("transferMapperOfDistribution")
public interface TransferMapper {

    /**
     * 调拔明细 - 调出
     *
     * @param queryDto
     * @return
     */
    List<TransferDetailsOfOutTableDto> selectTransferDetailsOfOut(TransferDetailsQueryDto queryDto);

    /**
     * 调拔明细 - 调入
     *
     * @param queryDto
     * @return
     */
    List<TransferDetailsOfInTableDto> selectTransferDetailsOfIn(TransferDetailsQueryDto queryDto);

}
