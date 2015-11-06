package com.iuni.data.webapp.service.distribution.impl;

import com.iuni.data.persist.mapper.distribution.TransferMapper;
import com.iuni.data.persist.model.distribution.TransferDetailsOfInTableDto;
import com.iuni.data.persist.model.distribution.TransferDetailsQueryDto;
import com.iuni.data.persist.model.distribution.TransferDetailsOfOutTableDto;
import com.iuni.data.webapp.service.distribution.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service("transferServiceOfDistribution")
public class TransferServiceImpl implements TransferService{

    @Autowired
    private TransferMapper transferMapper;

    /**
     * 调拔明细 - 调出
     * @param queryDto
     * @return
     */
    @Override
    public List<TransferDetailsOfOutTableDto> selectTransferDetailsOfOut(TransferDetailsQueryDto queryDto){
        return transferMapper.selectTransferDetailsOfOut(queryDto);
    }

    /**
     * 调拔明细 - 调出
     * @param queryDto
     * @return
     */
    @Override
    public List<TransferDetailsOfInTableDto> selectTransferDetailsOfIn(TransferDetailsQueryDto queryDto){
        return transferMapper.selectTransferDetailsOfIn(queryDto);
    }

}
